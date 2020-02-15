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
import org.springframework.web.multipart.MultipartFile;

import java.util.LinkedList;
import java.util.List;

import static org.chobit.calf.utils.Strings.isBlank;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "work")
public class WorkService {


    private static final String DEFAULT_COVER = "/upload/default/cover.jpg";

    @Autowired
    private WorkMapper workMapper;
    @Autowired
    private AuthorMapper authorMapper;
    @Autowired
    private MetaService metaService;

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
        work.setCover(isBlank(pathCover) ? DEFAULT_COVER : pathCover);
        if (id > 0) {
            workMapper.update(work);
        } else {
            workMapper.insert(work);
        }
    }


    @Cacheable(key = "'get' + #id")
    public WorkModel get(int id) {
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


    @Cacheable(key = "'count-search' + #search")
    public long countForSearch(String search) {
        return workMapper.countForSearch(search);
    }

}
