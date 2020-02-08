package org.chobit.calf.utils;


import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.stream.Collectors;


public abstract class Reflections {

    /**
     * 获取类的所有属性成员的名称
     */
    public static List<String> nameOfFields(Class<?> clazz) {
        return fieldsOf(clazz).stream().map(Field::getName).collect(Collectors.toList());
    }

    /**
     * 获取类的所有属性成员，含父类成员
     */
    public static List<Field> fieldsOf(Class clazz) {
        return fieldsOf(clazz, true);
    }

    /**
     * 获取类的所有属性成员
     *
     * @param clazz       目标类
     * @param recursively 是否递归获取父类成员
     * @return 类的所有属性成员
     */
    public static List<Field> fieldsOf(Class clazz, boolean recursively) {
        List<Field> fields = new LinkedList<>();
        Class tmp = clazz;

        while (null != tmp) {
            Field[] arr = tmp.getDeclaredFields();
            fields.addAll(Arrays.asList(arr));

            if (recursively) {
                tmp = tmp.getSuperclass();
            } else {
                tmp = null;
            }
        }

        return fields;
    }

    private Reflections() {
    }

}
