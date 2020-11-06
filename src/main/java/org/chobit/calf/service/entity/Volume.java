package org.chobit.calf.service.entity;

/**
 * table volume
 *
 * @author robin
 */
public class Volume extends AbstractEntity {

    private int workId;

    private String name;

    public Volume() {
    }

    public Volume(int workId, String name) {
        this.workId = workId;
        this.name = name;
    }

    public Volume(int id, int workId, String name) {
        super.setId(id);
        this.workId = workId;
        this.name = name;
    }

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
