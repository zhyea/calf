package org.chobit.calf.service;

import org.chobit.calf.except.CalfAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.chobit.calf.constants.Config.PATH_DEFAULT_COVER;
import static org.chobit.calf.constants.Config.URI_UPLOAD;
import static org.chobit.calf.utils.Dates.format;
import static org.chobit.calf.utils.Strings.*;

/**
 * @author robin
 */
@Component
public class UploadComponent {


    private static final Logger logger = LoggerFactory.getLogger(UploadComponent.class);

    @Value("${calf.path-upload}")
    private String pathUpload;

    public String upload(MultipartFile file) {

        String srcFileName = "";
        try {
            if (null != file) {
                srcFileName = file.getOriginalFilename();
                if (isNotBlank(srcFileName) && !file.isEmpty()) {
                    return upload0(srcFileName, file.getBytes());
                }
            }
        } catch (Exception e) {
            logger.error("upload file:[{}] failed.", srcFileName, e);
            throw new CalfAdminException("文件上传出现错误，请联系管理员", e);
        }
        return null;
    }


    public String upload0(String srcFileName, byte[] bytes) throws IOException {

        String ext = srcFileName.substring(srcFileName.lastIndexOf("."));
        String date = format("yyyy/MM/dd", new Date());
        String dir = uploadPath0() + date + "/";

        File d = new File(dir);

        if (!d.exists()) {
            d.mkdirs();
        }

        String fName = uuid() + ext;

        String filePath = dir + fName;
        Files.write(Paths.get(filePath), bytes);

        return URI_UPLOAD + date + "/" + fName;
    }


    public String uploadCover(MultipartFile cover, String curr) {
        if (null != cover && !cover.isEmpty()) {
            if (isNotBlank(curr) && !PATH_DEFAULT_COVER.equals(curr)) {
                delete(curr);
            }
            curr = upload(cover);
        }
        return isBlank(curr) ? PATH_DEFAULT_COVER : curr;
    }


    public String uploadFile(MultipartFile file, String curr) {
        if (!file.isEmpty()) {
            if (isNotBlank(curr)) {
                delete(curr);
            }
            curr = upload(file);
        }
        return isBlank(curr) ? null : curr;
    }


    public boolean delete(String path) {
        if (isBlank(path)) {
            return false;
        }
        if (path.startsWith(URI_UPLOAD)) {
            path = path.substring(URI_UPLOAD.length());
        }
        File f = new File(uploadPath0() + path);
        if (f.exists()) {
            return f.delete();
        }
        return true;
    }

    private static final String FILE_PROTOCOL = "file://";

    public String uploadPath() {
        String path = uploadPath0();
        if (path.startsWith(pathUpload) && !path.startsWith(FILE_PROTOCOL)) {
            path = FILE_PROTOCOL + path;
        }
        return path;
    }


    private String uploadPath0() {
        String path = pathUpload;
        if (isBlank(path) || !pathUpload.startsWith("/")) {
            try {
                path = ResourceUtils.getFile("classpath:").getPath();
                path = (path.endsWith("/") ? path : path + "/") + "upload/";
            } catch (FileNotFoundException e) {
                e.printStackTrace();
                path = "/www/calf/upload/";
            }
        }
        if (!path.endsWith("/")) {
            path = path + "/";
        }
        return path;
    }

}
