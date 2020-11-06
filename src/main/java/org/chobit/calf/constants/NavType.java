package org.chobit.calf.constants;

/**
 * 导航链接类型
 *
 * @author robin
 */
public enum NavType {

    /**
     * 直接连接
     */
    url("自定义"),

    /**
     * 分类
     */
    category("分类"),

    /**
     * 专题
     */
    feature("专题"),

    /**
     * 自定义
     */
    custom("自定义"),

    ;


    public final String desc;

    NavType(String desc) {
        this.desc = desc;
    }
}
