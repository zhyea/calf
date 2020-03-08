package org.chobit.calf;


public class MyApp {

    public static void main(String[] args) {
        String s = "a/b.html";
        int idx = s.indexOf(".html");
        System.out.println(s.substring(0, idx));
    }

}
