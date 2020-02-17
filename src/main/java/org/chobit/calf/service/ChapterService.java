package org.chobit.calf.service;

import org.chobit.calf.except.CalfAdminException;
import org.chobit.calf.model.VolumeModel;
import org.chobit.calf.service.entity.Chapter;
import org.chobit.calf.service.entity.Volume;
import org.chobit.calf.service.entity.Work;
import org.chobit.calf.service.mapper.ChapterMapper;
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

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
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


    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public void maintain(int id, int workId, String name, int volumeId, String volume, String content) {
        Args.checkPositive(workId, "找不到作品信息");
        Args.checkNotBlank(name, "章节名称不能为空");
        Args.checkNotBlank(content, "章节内容不能为空");

        Work work = workService.get(workId);
        Args.checkNotNull(work, "找不到作品信息");

        volumeId = volumeService.addOrUpdate(volumeId, workId, volume);

        Chapter chapter = new Chapter();
        chapter.setId(id);
        chapter.setWorkId(workId);
        chapter.setVolumeId(volumeId);
        chapter.setName(name);
        chapter.setContent(content);

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
                vm.addChapter(c);
            }
        }

        if (unBundled.hasChapters()) {
            result.add(unBundled);
        }

        return result;
    }


    @CacheEvict(allEntries = true)
    @Transactional(rollbackFor = Exception.class)
    public boolean delete(int volId, int id) {
        chapterMapper.delete(id);
        deleteEmptyVolume(volId);
        return true;
    }


    @CacheEvict(allEntries = true)
    public int deleteByWorkIds(Collection<Integer> workIds) {
        return chapterMapper.deleteByWorkIds(workIds);
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


    public void upload(int workId, MultipartFile file) {
        Work work = workService.get(workId);
        Args.checkNotNull(work, "无法获取作品信息");
        try {
            readFromFile(work, file);
        } catch (IOException e) {
            logger.error("upload content for work[{}-{}] failed", workId, work.getName(), e);
            throw new CalfAdminException("上传失败，请联系管理员");
        }

    }


    private static final String PATTERN_VOLUME = "^第?[\\s]{0,9}[\\d〇零一二三四五六七八九十百千万]{1,6}[\\s]{0,9}[章回节卷篇讲卷集]?([\\s]{1,9}.{0,32})?$";

    private static final List<String> ARRAY_SHORT = Arrays.asList("楔子", "引子", "引言", "序章", "尾声", "终章", "后记", "序");

    private void readFromFile(Work work, MultipartFile file) throws IOException {

        try (BufferedReader reader = new BufferedReader(new InputStreamReader(file.getInputStream()));) {
            String volName = "";
            String chapterName = "";
            StringBuilder content = new StringBuilder();

            String line = null;
            while (null != (line = reader.readLine())) {
                line = line.replaceAll("　", " ").trim();
                if (ARRAY_SHORT.contains(line) || line.matches(PATTERN_VOLUME)) {
                    if (content.length() == 0 && isNotBlank(chapterName)) {
                        // 处理存在两级章节的情形
                        chapterName = line;
                    } else if (content.length() > 0) {
                        // 处理读到章节末尾，出现新章节的情形
                        addChapter(work, volName, chapterName, content.toString());
                        content = new StringBuilder();
                        volName = line;
                        chapterName = line;
                    } else {
                        // 处理读到首章的情形
                        volName = line;
                        chapterName = line;
                    }
                } else {
                    content.append("<p>").append(line).append("</p>");
                }
            }
        }
    }


    @CacheEvict(allEntries = true)
    public void addChapter(Work work, String volName, String chapterName, String content) {
        int workId = work.getId();
        if (volName.equals(chapterName)) {
            volName = "正文";
        }

        if (isBlank(chapterName)) {
            chapterName = "引子";
            volName = "引子";
        }

        Volume vol = volumeService.getByWorkIdAndName(workId, volName);
        if (null == vol) {
            vol = volumeService.add(workId, volName);
        }

        Chapter chapter = new Chapter();
        chapter.setWorkId(workId);
        chapter.setVolumeId(vol.getId());
        chapter.setName(chapterName);
        chapter.setContent(content);

        chapterMapper.insert(chapter);
    }


}
