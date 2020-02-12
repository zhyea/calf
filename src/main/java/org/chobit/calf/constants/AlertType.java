package org.chobit.calf.constants;

/**
 * 报警类型
 *
 * @author robin
 */
public enum AlertType {

    /**
     * 成功
     */
    SUCCESS("alert-success"),
    /**
     * 消息
     */
    INFO("alert-info"),
    /**
     * 警告
     */
    WARNING("alert-warning"),
    /**
     * 危险
     */
    DANGER("alert-danger"),
    ;

    public final String css;

    AlertType(String type) {
        this.css = type;
    }
}
