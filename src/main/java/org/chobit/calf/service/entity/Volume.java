package org.chobit.calf.service.entity;

/**
 * @author robin
 */
public class Volume extends AbstractEntity {

    private int workId;

    private String name;

    public int getWorkId() {
        return workId;
    }

    public void setWorkId(int workId) {
        this.workId = workId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
