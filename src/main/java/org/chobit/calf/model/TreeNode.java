package org.chobit.calf.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author robin
 */
public class TreeNode {

    private int id;

    private String name;

    private String slug;

    private List<TreeNode> children = new LinkedList<>();


    public TreeNode() {
    }

    public TreeNode(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public void addChild(TreeNode node) {
        this.children.add(node);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
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

    public List<TreeNode> getChildren() {
        return children;
    }

    public void setChildren(List<TreeNode> children) {
        this.children = children;
    }
}
