package org.chobit.calf.service;

import org.chobit.calf.model.ChapterAndVol;
import org.chobit.calf.model.VolumeModel;
import org.chobit.calf.service.entity.Chapter;
import org.chobit.calf.service.entity.Volume;
import org.chobit.calf.service.entity.Work;
import org.chobit.calf.service.mapper.ChapterMapper;
import org.chobit.calf.tools.ContentExtractor;
import org.chobit.calf.utils.Args;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.*;

import static org.chobit.calf.utils.Collections2.isEmpty;
import static org.chobit.calf.utils.Strings.isBlank;
import static org.chobit.calf.utils.Strings.isNotBlank;

/**
 * @author robin
 */
@Service
@CacheConfig(cacheNames = "chapter")
public class ChapterService {


    private static final Logger logger = LoggerFactory.getLogger(ChapterService.class);

    @Autowired
    private ChapterMapper chapterMapper;
    @Autowired
    private VolumeService volumeService;
    @Autowired
    private WorkService workService;
    @Autowired
    private ChapterUploadComponent uploadComponent;

    @CacheEvict(cacheNames = {"work", "vol", "chapter"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void maintain(int id, int workId, String name, int volumeId, String volume, String newVolume, String content, String keywords) {
        Args.checkPositive(workId, "找不到作品信息");
        Args.checkNotBlank(name, "章节名称不能为空");
        Args.checkNotBlank(content, "章节内容不能为空");

        Work work = workService.get(workId);
        Args.checkNotNull(work, "找不到作品信息");

        if (isNotBlank(newVolume)) {
            Volume v = volumeService.getByWorkIdAndName(workId, newVolume);
            if (null != v) {
                volumeId = v.getId();
            } else {
                volumeId = volumeService.addOrUpdate(0, workId, newVolume);
            }
        } else {
            volumeId = volumeService.addOrUpdate(volumeId, workId, volume);
        }

        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setWorkId(workId);
        chapter.setVolumeId(volumeId);
        chapter.setName(name);
        chapter.setContent(content);
        chapter.setKeywords(keywords);

        if (id <= 0) {
            chapterMapper.insert(chapter);
        } else {
            chapterMapper.update(chapter);
            deleteEmptyVolume(volumeId);
        }
    }


    @Cacheable(key = "'get' + #id")
    public Chapter get(int id) {
        if (id <= 0) {
            return null;
        }
        return chapterMapper.get(id);
    }


    @Cacheable(key = "'getChapterAndVol' + #id")
    public ChapterAndVol getChapterAndVol(int id) {
        if (id <= 0) {
            return null;
        }
        return chapterMapper.getDetail(id);
    }


    @Cacheable(key = "'chapters' + #workId")
    public List<VolumeModel> chapters(int workId) {

        List<Chapter> chapters = chapterMapper.findByWorkId(workId);

        LinkedList<VolumeModel> result = new LinkedList<>();
        if (isEmpty(chapters)) {
            return result;
        }

        List<Volume> vols = volumeService.findByWorkId(workId);

        VolumeModel unBundled = new VolumeModel(0, "正文");
        if (isEmpty(vols)) {
            unBundled.addChapters(chapters);
        } else {
            Map<Integer, VolumeModel> map = new LinkedHashMap<>(vols.size());
            vols.forEach(e -> {
                VolumeModel model = new VolumeModel(e);
                result.add(model);
                map.put(e.getId(), model);
            });

            for (Chapter c : chapters) {
                VolumeModel vm = map.getOrDefault(c.getVolumeId(), unBundled);
                if (c.getName().length() < 5) {
                    c.setName(c.getName() + "　　　　");
                }
                vm.addChapter(c);
            }
        }

        if (unBundled.hasChapters()) {
            result.add(unBundled);
        }

        return result;
    }


    @CacheEvict(cacheNames = {"work", "vol", "chapter"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Boolean delete(int volId, int id) {
        chapterMapper.delete(id);
        deleteEmptyVolume(volId);
        return true;
    }


    @CacheEvict(cacheNames = {"work", "vol", "chapter"}, allEntries = true)
    public Integer deleteByWorkIds(Collection<Integer> workIds) {
        return chapterMapper.deleteByWorkIds(workIds);
    }


    @CacheEvict(cacheNames = {"work", "vol", "chapter"}, allEntries = true)
    public Integer deleteByWorkId(int workId) {
        return chapterMapper.deleteByWorkId(workId);
    }

    /**
     * 删除空章节
     *
     * @param volId 章节ID
     */
    private void deleteEmptyVolume(int volId) {
        Long count = chapterMapper.countByVolumeId(volId);
        if (null == count || 0 == count) {
            volumeService.delete(volId);
        }
    }


    @CacheEvict(cacheNames = {"work", "vol", "chapter"}, allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public Integer deleteByVolumeId(int volId) {
        if (volId <= 0) {
            return 0;
        }
        volumeService.delete(volId);
        return chapterMapper.deleteByVolumeId(volId);
    }


    @CacheEvict(cacheNames = {"work", "vol", "chapter"}, allEntries = true)
    public void upload(int workId, MultipartFile file) {
        Work work = workService.get(workId);
        Args.checkNotNull(work, "无法获取作品信息");
        uploadComponent.uploadChapters(workId, file);
    }


    @CacheEvict(cacheNames = {"work", "vol", "chapter"}, allEntries = true)
    public void addChapter(int workId, String volName, String chapterName, String content) {
        Volume vol = null;
        if (isBlank(volName) || volName.equals(chapterName)) {
            vol = volumeService.getLatestByWorkId(workId);
            if (null == vol) {
                volName = "正文";
            }
        }

        if (isBlank(chapterName)) {
            chapterName = "引子";
            volName = "引子";
        }
        if (null == vol) {
            vol = volumeService.getByWorkIdAndName(workId, volName);
        }
        if (null == vol) {
            vol = volumeService.add(workId, volName);
        }

        Chapter chapter = new Chapter();
        chapter.setWorkId(workId);
        chapter.setVolumeId(vol.getId());
        chapter.setName(chapterName);
        chapter.setContent(content);

        String keywords = ContentExtractor.extractKeywords2(content, 6);
        chapter.setKeywords(keywords);
        String summary = ContentExtractor.extractSummary(content, 5);
        if (summary.length() > 384) {
            summary = summary.substring(0, 384);
        }
        chapter.setSummary(summary);

        chapterMapper.insert(chapter);
    }


    @Cacheable(key = "'getNext' + #workId + '-' + #chapterId")
    public Chapter getNext(int workId, int chapterId) {
        return chapterMapper.getNext(workId, chapterId);
    }


    @Cacheable(key = "'getLast' + #workId + '-' + #chapterId")
    public Chapter getLast(int workId, int chapterId) {
        return chapterMapper.getLast(workId, chapterId);
    }

}
