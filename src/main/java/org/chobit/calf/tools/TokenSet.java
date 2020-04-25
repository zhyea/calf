package org.chobit.calf.tools;

import java.util.*;

/**
 * @author robin
 */
public class TokenSet extends AbstractSet<String> {

    private final TreeMap<Integer, List<String>> map = new TreeMap<>((o1, o2) -> o2 - o1);


    @Override
    public boolean add(String s) {
        if (null == s) {
            return false;
        }
        Integer key = s.length();
        List<String> list = map.computeIfAbsent(key, k -> new LinkedList<>());
        list.add(s);
        return true;
    }



    @Override
    public Iterator<String> iterator() {
        List<String> list = new LinkedList<>();
        for (Map.Entry<Integer, List<String>> e : map.entrySet()) {
            if (null != e.getValue()) {
                list.addAll(e.getValue());
            }
        }
        return list.iterator();
    }

    @Override
    public int size() {
        int size = 0;

        for (Map.Entry<Integer, List<String>> e : map.entrySet()) {
            if (null != e.getValue()) {
                size += e.getValue().size();
            }
        }
        return size;
    }
}
