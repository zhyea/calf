package org.chobit.calf.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author robin
 */
public class NavNode {

    private int id = 0;

    private int parent = 0;

    private String name;

    private String url;

    private List<NavNode> children = new LinkedList<>();

    public NavNode() {
    }

    public NavNode(int id, String name, String url, int parent) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.parent = parent;
    }

    public void addChild(NavNode node) {
        this.children.add(node);
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

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public List<NavNode> getChildren() {
        return children;
    }

    public void setChildren(List<NavNode> children) {
        this.children = children;
    }
}
