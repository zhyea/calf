package org.chobit.calf.service.entity;

/**
 * table feature
 *
 * @author robin
 */
public class Feature extends AbstractEntity {

    private String cover;

    private String background;

    private int bgRepeat = 1;

    private String name;

    private String alias;

    private String keywords;

    private String brief;

    private long recordsCount;

    public String getCover() {
        return cover;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public String getBackground() {
        return background;
    }

    public void setBackground(String background) {
        this.background = background;
    }

    public int getBgRepeat() {
        return bgRepeat;
    }

    public void setBgRepeat(int bgRepeat) {
        this.bgRepeat = bgRepeat;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAlias() {
        return alias;
    }

    public void setAlias(String alias) {
        this.alias = alias;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getBrief() {
        return brief;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }

    public long getRecordsCount() {
        return recordsCount;
    }

    public void setRecordsCount(long recordsCount) {
        this.recordsCount = recordsCount;
    }
}
