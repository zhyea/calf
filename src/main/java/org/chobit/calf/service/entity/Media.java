package org.chobit.calf.service.entity;

/**
 * table media
 *
 * @author robin
 */
public class Media extends AbstractEntity {

    private String type;

    private String name;

    private String path;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }
}
