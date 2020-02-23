package org.chobit.calf;


import org.chobit.calf.model.RemoteRequest;

import java.io.FileNotFoundException;

import static sun.plugin2.util.PojoUtil.toJson;

public class MyApp {

    private static final String PATTERN_SHORT = "^第?[\\s]{0,9}[\\d〇零一二三四五六七八九十百千万]{1,6}[\\s]{0,9}[章回节卷篇讲卷集]([\\s]{1,9}.{0,32})?$";

    public static void main(String[] args) throws FileNotFoundException {
        RemoteRequest request = new RemoteRequest();
        request.setWorkName("念奴娇");
        request.setChapterName("hehe");
        request.setContent("-------------------------zzzzzz");
        request.setRemoteCode("esuO1V6j");
        System.out.println(toJson(request));
    }


}
