package org.chobit.calf.service.entity;

import java.io.Serializable;
import java.util.Date;

/**
 * @author robin
 */
public abstract class AbstractEntity implements Serializable {

    private int id;

    private Date opTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public Date getOpTime() {
        return opTime;
    }

    public void setOpTime(Date opTime) {
        this.opTime = opTime;
    }
}
