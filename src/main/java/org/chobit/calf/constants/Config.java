package org.chobit.calf.constants;

import org.springframework.util.ResourceUtils;

import java.io.FileNotFoundException;

/**
 * @author robin
 */
public final class Config {

    public static final String PATH_UPLOAD;

    static {
        String tmp = "/tmp/calf/upload/";
        try {
            tmp = ResourceUtils.getFile("classpath:upload/").getPath();
        } catch (FileNotFoundException e) {
        }
        PATH_UPLOAD = tmp.endsWith("/") ? tmp : tmp + "/";
    }

    public static final String URI_UPLOAD = "/upload/";


    /**
     * 相对路径，指的是在template下的路径
     */
    public static final String PATH_ADMIN = "admin/";

}
