package org.chobit.calf.model;

import java.util.LinkedList;
import java.util.List;

/**
 * @author robin
 */
public class TreeViewNode {

    private int id;

    private String text;

    private String ext;

    private String ext2;

    private List<String> tags = new LinkedList<>();

    private List<TreeViewNode> nodes;

    public TreeViewNode() {
    }

    public TreeViewNode(String text) {
        this.text = text;
    }

    public TreeViewNode(int id, String text, String url) {
        this.id = id;
        this.text = text;
        this.ext = url;
    }

    public void addNode(TreeViewNode node) {
        if (null == this.nodes) {
            this.nodes = new LinkedList<>();
        }
        this.nodes.add(node);
    }

    public void addTag(String tag) {
        this.tags.add(tag);
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getExt() {
        return ext;
    }

    public void setExt(String ext) {
        this.ext = ext;
    }

    public List<String> getTags() {
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<TreeViewNode> getNodes() {
        return nodes;
    }

    public void setNodes(List<TreeViewNode> nodes) {
        this.nodes = nodes;
    }

    public String getExt2() {
        return ext2;
    }

    public void setExt2(String ext2) {
        this.ext2 = ext2;
    }
}
