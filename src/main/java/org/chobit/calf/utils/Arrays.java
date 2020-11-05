package org.chobit.calf.utils;

import java.util.LinkedList;
import java.util.List;

public final class Arrays {


    public static String[] merge(String[]... arrays) {
        List<String> list = new LinkedList<>();
        for (String[] arr : arrays) {
            list.addAll(java.util.Arrays.asList(arr));
        }
        return list.toArray(new String[0]);
    }


    private Arrays() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}
