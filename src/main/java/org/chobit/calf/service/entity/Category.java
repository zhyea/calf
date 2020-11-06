package org.chobit.calf.service.entity;

import org.chobit.calf.constants.MetaType;

/**
 * table: category
 *
 * @author robin
 */
public class Category extends AbstractEntity {

    private int parent;

    private MetaType type;

    private String name;

    private String slug;

    private String remark;

    private int sn;

    public int getParent() {
        return parent;
    }

    public void setParent(int parent) {
        this.parent = parent;
    }

    public MetaType getType() {
        return type;
    }

    public void setType(MetaType type) {
        this.type = type;
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

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public int getSn() {
        return sn;
    }

    public void setSn(int sn) {
        this.sn = sn;
    }
}
