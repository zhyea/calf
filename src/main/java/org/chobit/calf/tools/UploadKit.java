package org.chobit.calf.tools;

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
import static org.chobit.calf.utils.Strings.isNotBlank;
import static org.chobit.calf.utils.Strings.uuid;

/**
 * @author robin
 */
public abstract class UploadKit {


    private static final Logger logger = LoggerFactory.getLogger(UploadKit.class);


    public static String upload(MultipartFile file) throws IOException {

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
            throw e;
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


    private UploadKit() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }


}
