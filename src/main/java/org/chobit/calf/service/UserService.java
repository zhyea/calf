package org.chobit.calf.service;

import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.chobit.calf.except.CalfArgsException;
import org.chobit.calf.service.entity.User;
import org.chobit.calf.service.mapper.UserMapper;
import org.chobit.calf.tools.SessionHolder;
import org.chobit.calf.utils.Args;
import org.chobit.calf.utils.MD5;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletRequest;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author robin
 */
@Service
public class UserService implements InitializingBean {


    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    @Autowired
    private UserMapper userMapper;

    @Value("${calf.passoword-salt}")
    private String passwordSalt;

    private ScheduledExecutorService executorService = Executors.newSingleThreadScheduledExecutor();

    /**
     * 查询全部用户信息
     */
    public List<User> all() {
        return userMapper.findAll();
    }


    /**
     * 根据ID获取用户信息
     */
    public User get(int id) {
        if (id <= 0) {
            return null;
        }
        return userMapper.get(id);
    }


    /**
     * 维护用户信息
     */
    public Integer maintain(int id, String username, String password, String email, String nickname) {
        Args.checkNotBlank(username, "用户名不能为空");
        Args.checkNotBlank(email, "邮箱不能为空");
        Args.checkNotBlank(nickname, "昵称不能为空");
        Args.checkNotBlank(password, "密码不能为空");

        Integer flag = userMapper.checkByUsernameAndEmail(id, username, email);
        Args.check(null == flag, "用户名或邮箱已存在");

        User user = new User(username, MD5.encode(passwordSalt, password), email, nickname);
        user.setId(id);

        if (id > 0) {
            userMapper.update(user);
        } else {
            userMapper.insert(user);
        }
        return user.getId();
    }

    private Cache<String, Long> ipCache = Caffeine.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();
    private Cache<String, Long> userCache = Caffeine.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();
    private Cache<String, Integer> retryCache = Caffeine.newBuilder().expireAfterAccess(15, TimeUnit.MINUTES).build();

    /**
     * 校验用户名和密码
     */
    public User check(String username, String password, HttpServletRequest request) {
        String ip = request.getRemoteHost();
        Long lastUserTime = userCache.getIfPresent(username);
        Long lastIpTime = ipCache.getIfPresent(ip);

        long time = Math.max(null == lastUserTime ? 0 : lastUserTime, null == lastIpTime ? 0 : lastIpTime);

        if (System.currentTimeMillis() - time < TimeUnit.MINUTES.toMillis(10L)) {
            throw new CalfArgsException("您已被屏蔽，请稍后再尝试登录");
        }

        logger.info("user {} signs in from {}", username, ip);

        User user = userMapper.getByUsernameAndPassword(username, MD5.encode(passwordSalt, password));
        if (null == user) {
            Integer count = retryCache.getIfPresent(username);
            count = null == count ? 0 : count;
            retryCache.put(username, ++count);
            if (count == 3) {
                retryCache.invalidate(username);
                userCache.put(username, System.currentTimeMillis());
                ipCache.put(ip, System.currentTimeMillis());
            }
            throw new CalfArgsException("用户名或密码错误，十分钟内您还能重试" + (3 - count) + "次");
        } else {
            SessionHolder.addUser(request, user);
        }
        return user;
    }


    /**
     * 根据ID删除记录
     */
    public boolean deleteByIds(Collection<Integer> ids) {
        if (isEmpty(ids)) {
            return true;
        }
        return userMapper.deleteByIds(ids);
    }


    @Override
    public void afterPropertiesSet() throws Exception {
        executorService.scheduleAtFixedRate(() -> {
            ipCache.cleanUp();
            userCache.cleanUp();
            retryCache.cleanUp();
        }, 0L, 6, TimeUnit.HOURS);
    }
}
