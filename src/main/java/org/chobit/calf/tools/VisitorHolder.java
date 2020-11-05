package org.chobit.calf.tools;

import org.chobit.calf.model.Visitor;

/**
 * @author robin
 */
public final class VisitorHolder {


    private static final ThreadLocal<Visitor> visitors = new ThreadLocal<>();


    public static void add(Visitor visitor) {
        visitors.set(visitor);
    }


    public static Visitor get() {
        return visitors.get();
    }


    public static boolean exists() {
        return null != get();
    }


}
