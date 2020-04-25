package org.chobit.calf.service.entity;

import org.chobit.calf.constants.NavType;

/**
 * @author robin
 */
public class Navigator extends AbstractEntity {

    private int parent;

    private String name;

    private NavType type = NavType.category;

    private String url;

    private String remark;

    private int sn;

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

    public NavType getType() {
        return type;
    }

    public void setType(NavType type) {
        this.type = type;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
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
