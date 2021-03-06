package org.chobit.calf.service.entity;

/**
 * tables of settings
 *
 * @author robin
 */
public class PairRecord extends AbstractEntity {

    private String name;

    private String value;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
