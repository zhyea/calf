package org.chobit.calf;


import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;

import static org.chobit.calf.constants.Config.PATH_UPLOAD;

public class MyApp {


    public static void main(String[] args) throws FileNotFoundException {

        String p = ResourceUtils.getURL("classpath:").getPath();

        System.out.println(p);
        File f = ResourceUtils.getFile(PATH_UPLOAD + "/test");
        System.out.println(f.exists());
        File f2 = ResourceUtils.getFile(PATH_UPLOAD );

        System.out.println(f.getPath());
        System.out.println(f2.exists());

    }


}
