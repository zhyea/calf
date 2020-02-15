package org.chobit.calf.utils;

import org.chobit.calf.except.CalfArgsException;
import org.chobit.calf.model.Page;

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


    public static void checkPositive(long arg, String errMsg) {
        check(arg > 0, errMsg);
    }


    public static void checkPage(Page page) {
        check(page.getLimit() >= 0, "limit value in Page is lower than zero.");
        check(page.getOffset() >= 0, "offset value in Page is lower than zero.");
        if (null == page.getOrder()) {
            page.setOrder(Page.Direct.desc);
        }
    }

    public static void checkNotBlank(String src, String errMsg) {
        check(isNotBlank(src), errMsg);
    }


    private Args() {
        throw new UnsupportedOperationException("Private constructor, cannot be accessed.");
    }

}
