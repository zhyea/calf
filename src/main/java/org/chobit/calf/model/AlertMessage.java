package org.chobit.calf.model;

import org.chobit.calf.constants.AlertType;

/**
 * 报警对象
 *
 * @author robin
 */
public class AlertMessage {

    private AlertType type;

    private String message;

    public AlertMessage(AlertType type, String message) {
        this.type = type;
        this.message = message;
    }

    public AlertType getType() {
        return type;
    }

    public String getMessage() {
        return message;
    }
}
