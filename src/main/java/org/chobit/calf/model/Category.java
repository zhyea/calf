package org.chobit.calf.model;

import org.chobit.calf.service.entity.Meta;

/**
 * @author robin
 */
public class Category {

    private int id;

    private int parent;

    private String name;

    private String slug;

    private int sn;

    private int subCount;

    public Category() {
    }

    public Category(Meta meta, int childrenNum) {
        this.id = meta.getId();
        this.parent = meta.getParent();
        this.name = meta.getName();
        this.slug = meta.getSlug();
        this.sn = meta.getSn();
        this.subCount = childrenNum;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSlug() {
        return slug;
    }

    public void setSlug(String slug) {
        this.slug = slug;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }

    public int getSubCount() {
        return subCount;
    }

    public void setSubCount(int subCount) {
        this.subCount = subCount;
    }
}
