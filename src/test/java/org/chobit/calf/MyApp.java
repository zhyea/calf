package org.chobit.calf;


import org.chobit.calf.utils.Pinyin;

public class MyApp {

    public static void main(String[] args) {
        String s = Pinyin.firstChar("大仲马");
        System.out.println(s);
        String s2 = Pinyin.firstChar("110");
        System.out.println(s2);
        String s3 = Pinyin.firstChar("Jim");
        System.out.println(s3);
        String s4 = Pinyin.firstChar("jill");
        System.out.println(s4);
    }

}
