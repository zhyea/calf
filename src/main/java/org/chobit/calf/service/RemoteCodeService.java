package org.chobit.calf.service;

import org.chobit.calf.except.CalfArgsException;
import org.chobit.calf.except.CalfRemoteException;
import org.chobit.calf.model.RemoteRequest;
import org.chobit.calf.model.RemoteResponse;
import org.chobit.calf.service.entity.RemoteCode;
import org.chobit.calf.service.entity.User;
import org.chobit.calf.service.entity.Work;
import org.chobit.calf.service.mapper.RemoteCodeMapper;
import org.chobit.calf.tools.SessionHolder;
import org.chobit.calf.tools.ShortCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

import static org.chobit.calf.utils.Strings.isBlank;

/**
 * @author robin
 */
@Service
public class RemoteCodeService {


    @Autowired
    private RemoteCodeMapper remoteCodeMapper;
    @Autowired
    private WorkService workService;
    @Autowired
    private ChapterService chapterService;

    public String add() {
        User user = SessionHolder.getUser();
        if (null == user) {
            SessionHolder.clear();
            throw new CalfArgsException("无法获取用户信息");
        }
        RemoteCode rc = remoteCodeMapper.getByUserId(user.getId());
        if (null == rc) {
            rc = new RemoteCode();
            rc.setUserId(user.getId());
        }
        rc.setCode(ShortCode.gen());
        insertOrUpdate(rc);
        return rc.getCode();
    }


    public RemoteCode getByCode(String code) {
        return remoteCodeMapper.getByCode(code);
    }


    public boolean isValid(RemoteCode code) {
        if (null == code) {
            return false;
        }
        long time = code.getOpTime().getTime();
        long diff = System.currentTimeMillis() - time;
        return diff < TimeUnit.MINUTES.toMillis(30L);
    }

    private void insertOrUpdate(RemoteCode rc) {
        if (rc.getId() <= 0) {
            remoteCodeMapper.insert(rc);
        } else {
            remoteCodeMapper.update(rc);
        }
    }


    public RemoteResponse handle(String code, RemoteRequest request) {
        if (null == request) {
            throw new CalfRemoteException("请求内容为空");
        }

        if (isBlank(code) || !code.equals(request.getRemoteCode())) {
            throw new CalfRemoteException("请求内容错误");
        }

        RemoteCode rc = getByCode(request.getRemoteCode());
        if (!isValid(rc)) {
            throw new CalfRemoteException("交互码无效或已过期");
        }

        Work work = workService.getByName(request.getWorkName());
        if (null == work) {
            throw new CalfRemoteException("无法获取作品信息");
        }

        if (isBlank(request.getChapterName())) {
            throw new CalfRemoteException("章节名称不能为空");
        }

        if (isBlank(request.getContent())) {
            throw new CalfRemoteException("内容不能为空");
        }

        chapterService.addChapter(work.getId(), request.getVolName(), request.getChapterName(), request.getContent());

        return new RemoteResponse(true, "新增章节成功");
    }
}
