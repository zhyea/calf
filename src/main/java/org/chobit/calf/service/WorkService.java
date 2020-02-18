package org.chobit.calf.service;

import org.chobit.calf.model.Page;
import org.chobit.calf.model.PageResult;
import org.chobit.calf.model.WorkModel;
import org.chobit.calf.service.entity.Author;
import org.chobit.calf.service.entity.Meta;
import org.chobit.calf.service.entity.Work;
import org.chobit.calf.service.mapper.AuthorMapper;
import org.chobit.calf.service.mapper.WorkMapper;
import org.chobit.calf.tools.UploadKit;
import org.chobit.calf.utils.Args;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

import static org.chobit.calf.utils.Collections2.isEmpty;
import static org.chobit.calf.utils.Strings.isBlank;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "work")
public class WorkService {


    private static final String PATH_DEFAULT_COVER = "/upload/default/cover";

    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private MetaService metaService;
    @Autowired
    private VolumeService volumeService;
    @Autowired
    private ChapterService chapterService;
    @Autowired
    private FeatureService featureService;

    /**
     * 数据维护
     */
    @CacheEvict(allEntries = true)
    public void maintain(int id, String name,
                         int authorId, String author, String country,
                         int catId, String cat,
                         String brief, MultipartFile cover) {
        Args.checkNotBlank(name, "作品名称不能为空");
        Args.checkNotBlank(author, "作者信息不能为空");
        Args.checkNotBlank(country, "作者国籍不能为空");
        Args.checkPositive(catId, "分类信息不能为空");
        Args.checkNotBlank(cat, "分类信息不能为空");

        if (authorId <= 0) {
            Author a = new Author(author, country);
            authorMapper.insert(a);
            authorId = a.getId();
        }

        String pathCover = UploadKit.upload(cover);

        Work work = new Work();
        work.setId(id);
        work.setName(name);
        work.setAuthorId(authorId);
        work.setCategoryId(catId);
        work.setBrief(brief);
        work.setCover(isBlank(pathCover) ? PATH_DEFAULT_COVER : pathCover);
        if (id > 0) {
            workMapper.update(work);
        } else {
            workMapper.insert(work);
        }
    }


    /**
     * 获取作品信息
     */
    @Cacheable(key = "'get' + #id")
    public Work get(int id) {
        if (id <= 0) {
            return null;
        }
        return workMapper.get(id);
    }


    /**
     * 获取作品详情，前端呈现
     */
    @Cacheable(key = "'getModel' + #id")
    public WorkModel getWorkModel(int id) {
        if (id <= 0) {
            return null;
        }
        Work work = workMapper.get(id);
        if (null == work) {
            return null;
        }
        Meta meta = metaService.get(work.getCategoryId());
        Author author = authorMapper.get(work.getAuthorId());
        return new WorkModel(work, author, meta);
    }


    /**
     * 分页查询
     */
    public PageResult<WorkModel> findInPage(Page p) {
        if (isBlank(p.getSearch())) {
            p.setSearch("");
        }
        p.setSearch("%" + p.getSearch() + "%");
        if (!"id".equals(p.getSort()) && !"name".equals(p.getSort())) {
            return new PageResult<>(0, new LinkedList<>());
        }

        long total = countForSearch(p.getSearch());
        List<WorkModel> works = workMapper.findInPage(p);
        return new PageResult<>(total, works);
    }


    /**
     * 分页统计
     */
    public long countForSearch(String search) {
        return workMapper.countForSearch(search);
    }


    /**
     * 根据关键字查询
     */
    public List<WorkModel> findWithKeyword(String key) {
        key = isBlank(key) ? "" : key;
        return workMapper.findWithKeywords("%" + key + "%");
    }


    /**
     * 使用ID删除记录
     */
    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteByIds(Collection<Integer> ids) {
        if (isEmpty(ids)) {
            return false;
        }
        workMapper.deleteByIds(ids);
        volumeService.deleteByWorkIds(ids);
        chapterService.deleteByWorkIds(ids);
        featureService.deleteByWorkIds(ids);
        return true;
    }


    public PageResult<WorkModel> findWithAuthor(int authorId, Page page) {
        List<WorkModel> list = workMapper.findWithAuthor(page, authorId);
        long count = countWithAuthor(authorId);
        return new PageResult<>(count, list);
    }


    public long countWithAuthor(int authorId) {
        return workMapper.countWithAuthor(authorId);
    }


    public PageResult<WorkModel> findWithCat(int catId, Page page) {
        List<WorkModel> list = workMapper.findWithCategory(page, catId);
        long count = countWithCat(catId);
        return new PageResult<>(count, list);
    }


    public long countWithCat(int catId) {
        return workMapper.countWithCategory(catId);
    }


    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean deleteChapters(int workId) {
        chapterService.deleteByWorkId(workId);
        volumeService.deleteByWorkId(workId);
        return true;
    }

}
