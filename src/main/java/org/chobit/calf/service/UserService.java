package org.chobit.calf.service;

import org.chobit.calf.service.entity.User;
import org.chobit.calf.service.mapper.UserMapper;
import org.chobit.calf.utils.Args;
import org.chobit.calf.utils.MD5;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;

import static org.springframework.util.CollectionUtils.isEmpty;

/**
 * @author robin
 */
@Service
public class UserService {

    @Autowired
    private UserMapper userMapper;


    public List<User> all() {
        return userMapper.findAll();
    }


    public User get(int id) {
        return userMapper.get(id);
    }


    public Integer maintain(int id, String username, String password, String email, String nickname) {
        Args.checkNotNull(username, "用户名不能为空");
        Args.checkNotNull(email, "邮箱不能为空");
        Args.checkNotNull(nickname, "昵称不能为空");
        Args.checkNotNull(password, "密码不能为空");

        Integer flag = userMapper.checkByUsernameAndEmail(username, email);
        Args.check(null == flag, "用户名或邮箱已存在");

        User user = new User(username, MD5.encode(password), email, nickname);
        user.setId(id);

        if (id > 0) {
            userMapper.update(user);
        } else {
            userMapper.insert(user);
        }
        return user.getId();
    }


    public User check(String username, String password) {
        User user = userMapper.getByUsernameAndPassword(username, MD5.encode(password));
        return user;
    }


    public boolean deleteByIds(Collection<Integer> ids) {
        if (isEmpty(ids)) {
            return true;
        }
        return userMapper.deleteByIds(ids);
    }


}
