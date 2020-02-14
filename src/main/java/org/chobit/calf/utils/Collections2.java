package org.chobit.calf.utils;

import org.chobit.calf.model.Pair;

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

/**
 * @author robin
 */
public abstract class Collections2 {


    public static boolean isEmpty(Collection<?> collection) {
        return collection == null || collection.isEmpty();
    }


    public static <K, V> Map<K, V> pairToMap(Collection<Pair<K, V>> pairs) {
        if (isEmpty(pairs)) {
            return new HashMap<>(0);
        }
        Map<K, V> map = new HashMap<>(pairs.size());
        for (Pair<K, V> p : pairs) {
            map.put(p.getKey(), p.getValue());
        }
        return map;
    }


    private Collections2() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}
