package org.chobit.calf;


import java.io.FileNotFoundException;

public class MyApp {

    private static final String PATTERN_SHORT = "^第?[\\s]{0,9}[\\d〇零一二三四五六七八九十百千万]{1,6}[\\s]{0,9}[章回节卷篇讲卷集]([\\s]{1,9}.{0,32})?$";

    public static void main(String[] args) throws FileNotFoundException {

        System.out.println("第二篇的内容十分繁复，分内、外、轻功，却须要兼修并进。".matches(PATTERN_SHORT));

    }


}
