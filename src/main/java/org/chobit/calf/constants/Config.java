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
            tmp = ResourceUtils.getFile("classpath:").getPath();
            tmp = (tmp.endsWith("/") ? tmp : tmp + "/") + "upload/";
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        PATH_UPLOAD = tmp;
    }

    public static final String URI_UPLOAD = "/upload/";


    /**
     * 相对路径，指的是在template下的路径
     */
    public static final String PATH_ADMIN = "admin/";

    /**
     * 前端页长度
     */
    public static final int DEFAULT_PAGE_LENGTH = 12;

    /**
     * 默认封面路径
     */
    public static final String PATH_DEFAULT_COVER = "/upload/default/cover/nocover.png";

}
