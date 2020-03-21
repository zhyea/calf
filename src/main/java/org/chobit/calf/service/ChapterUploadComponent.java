package org.chobit.calf.service;

import org.chobit.calf.except.CalfAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.List;

import static org.chobit.calf.utils.Strings.isNotBlank;

/**
 * @author robin
 */
@Component
public class ChapterUploadComponent {


    private static final Logger logger = LoggerFactory.getLogger(ChapterUploadComponent.class);

    @Autowired
    private ChapterService chapterService;


    public void uploadChapters(int workId, MultipartFile file) {
        try {
            readFromFile(workId, file);
        } catch (IOException e) {
            logger.error("upload content for work[{}] failed", workId, e);
            throw new CalfAdminException("上传失败，请联系管理员");
        }
    }


    private static final String PATTERN_VOLUME = "^第?[\\s]{0,9}[\\d〇零一二三四五六七八九十百千万上中下]{1,6}[\\s]{0,9}[、．\\.]?[章回节卷部篇讲集分]{0,2}([\\s]{1,9}.{0,32})?$";

    private static final List<String> ARRAY_SHORT = Arrays.asList("楔子", "引子", "引言", "序章", "序曲", "尾声", "终章", "后记", "序", "附", "附言");

    private void readFromFile(int workId, MultipartFile file) throws IOException {

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
                        chapterService.addChapter(workId, volName, chapterName, content.toString());
                        content = new StringBuilder();
                        volName = line;
                        chapterName = line;
                    } else {
                        // 处理读到首章的情形
                        volName = line;
                        chapterName = line;
                    }
                } else {
                    line = line.replace(" ", "");
                    if (isNotBlank(line)) {
                        content.append("<p>").append(line).append("</p>");
                    }
                }
            }
            // 处理读完文章不会出现新章节的情形
            chapterService.addChapter(workId, volName, chapterName, content.toString());
        }
    }

}
