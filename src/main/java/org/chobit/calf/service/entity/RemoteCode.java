package org.chobit.calf.service.entity;

/**
 * @author robin
 */
public class RemoteCode extends AbstractEntity {

    private int userId;

    private String code;

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }


}
