package org.chobit.calf.utils;

import org.chobit.calf.except.CalfArgsException;

import static org.chobit.calf.utils.Strings.isNotBlank;


/**
 * @author robin
 */
public abstract class Args {

    public static void check(boolean expect, String errMsg) {
        if (!expect) {
            throw new CalfArgsException(errMsg);
        }
    }

    public static void checkNotNull(Object src, String errMsg) {
        check(null != src, errMsg);
    }

    public static void checkNull(Object src, String errMsg) {
        check(null == src, errMsg);
    }


    public static void checkNotBlank(String src, String errMsg) {
        check(isNotBlank(src), errMsg);
    }


    private Args() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}
