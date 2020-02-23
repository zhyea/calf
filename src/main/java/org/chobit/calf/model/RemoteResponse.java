package org.chobit.calf.model;

/**
 * @author robin
 */
public class RemoteResponse {

    private int code;

    public boolean success;

    private String message;

    public RemoteResponse() {
    }

    public RemoteResponse(int code, boolean success, String message) {
        this.code = code;
        this.success = success;
        this.message = message;
    }

    public RemoteResponse(boolean success, String message) {
        if (success) {
            this.code = 200;
        } else {
            this.code = 500;
        }
        this.success = success;
        this.message = message;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
