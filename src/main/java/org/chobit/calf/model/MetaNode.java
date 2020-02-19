package org.chobit.calf.model;

import org.chobit.calf.constants.MetaType;

import java.util.LinkedList;
import java.util.List;

/**
 * @author robin
 */
public class MetaNode {

    private int id;

    private MetaType type = MetaType.CATEGORY;

    private String name;

    private String slug;

    private List<MetaNode> children = new LinkedList<>();


    public MetaNode() {
    }

    public MetaNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(MetaNode node) {
        this.children.add(node);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<MetaNode> getChildren() {
        return children;
    }

    public void setChildren(List<MetaNode> children) {
        this.children = children;
    }
}
