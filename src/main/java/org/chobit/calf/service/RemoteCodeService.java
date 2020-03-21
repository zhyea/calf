package org.chobit.calf.service;

import org.chobit.calf.except.CalfArgsException;
import org.chobit.calf.except.CalfRemoteException;
import org.chobit.calf.model.RemoteChapterRequest;
import org.chobit.calf.model.RemoteResponse;
import org.chobit.calf.model.RemoteWorkRequest;
import org.chobit.calf.service.entity.*;
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
    @Autowired
    private AuthorService authorService;
    @Autowired
    private MetaService metaService;

    public RemoteCode add() {
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
        return rc;
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


    public RemoteResponse addChapter(String code, RemoteChapterRequest request) {
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


    public RemoteResponse addWork(String code, RemoteWorkRequest request) {
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
        if (null != work) {
            throw new CalfRemoteException("同名作品已存在");
        }
        Author author = authorService.getByName(request.getAuthorName());
        if (null == author) {
            throw new CalfRemoteException("作者不存在");
        }
        Meta meta = metaService.getByName(request.getCatName());
        if (null == meta) {
            throw new CalfRemoteException("分类信息不存在");
        }
        workService.maintain(0, request.getWorkName(),
                author.getId(), author.getName(), author.getCountry(),
                meta.getId(), meta.getName(),
                request.getBrief(), null);
        return new RemoteResponse(true, "新增作品成功");
    }
}
