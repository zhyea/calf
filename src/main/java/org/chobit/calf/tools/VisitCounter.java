package org.chobit.calf.tools;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.DoubleAdder;

/**
 * @author robin
 */
public abstract class VisitCounter {

    private static final ConcurrentHashMap<Integer, DoubleAdder>
            counter = new ConcurrentHashMap<>(128);

    public static void add(Integer workId, double step) {
        DoubleAdder c = counter.get(workId);
        if (null == c) {
            c = counter.putIfAbsent(workId, new DoubleAdder());
            if (null == c) {
                c = counter.get(workId);
            }
        }
        c.add(step);
    }


    public static Map<Integer, Long> dumpAll() {
        Map<Integer, Long> map = new HashMap<>(counter.size() + 32);
        for (Map.Entry<Integer, DoubleAdder> e : counter.entrySet()) {
            map.put(e.getKey(), null == e.getValue() ? 0L : e.getValue().longValue());
        }
        counter.clear();
        return map;
    }


    private VisitCounter() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}
