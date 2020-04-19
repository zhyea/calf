package org.chobit.calf.model;

/**
 * @author robin
 */
public class RemoteWorkRequest {

    private String remoteCode;

    private String workName;

    private String authorName;

    private String catName;

    private String brief;

    private String keywords;

    public RemoteWorkRequest() {
    }

    public String getRemoteCode() {
        return remoteCode;
    }

    public void setRemoteCode(String remoteCode) {
        this.remoteCode = remoteCode;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getCatName() {
        return catName;
    }

    public void setCatName(String catName) {
        this.catName = catName;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }
}
