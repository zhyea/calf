package org.chobit.calf.tools;

import org.chobit.calf.except.CalfAdminException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Date;

import static org.chobit.calf.constants.Config.PATH_UPLOAD;
import static org.chobit.calf.constants.Config.URI_UPLOAD;
import static org.chobit.calf.utils.Dates.format;
import static org.chobit.calf.utils.Strings.*;

/**
 * @author robin
 */
public abstract class UploadKit {


    private static final Logger logger = LoggerFactory.getLogger(UploadKit.class);


    public static String upload(MultipartFile file) {

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


    public static String upload0(String srcFileName, byte[] bytes) throws IOException {

        String ext = srcFileName.substring(srcFileName.lastIndexOf("."));
        String date = format("yyyy/MM/dd", new Date());
        String dir = PATH_UPLOAD + date + "/";

        File d = new File(dir);

        if (!d.exists()) {
            d.mkdirs();
        }

        String fName = uuid() + ext;

        String filePath = dir + fName;
        Files.write(Paths.get(filePath), bytes);

        return URI_UPLOAD + date + "/" + fName;
    }

    public static boolean delete(String path) {
        if (isBlank(path)) {
            return false;
        }
        if (path.startsWith(URI_UPLOAD)) {
            path = path.substring(URI_UPLOAD.length());
        }
        File f = new File(PATH_UPLOAD + path);
        if (f.exists()) {
            return f.delete();
        }
        return true;
    }


    private UploadKit() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }


}
