package org.chobit.calf.model;

/**
 * @author robin
 */
public class SettingModel {

    private String name;

    private String description;

    private String keywords;

    private String notice;

    private String logo;

    private String backgroundImg;

    private int bgRepeat;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getKeywords() {
        return keywords;
    }

    public void setKeywords(String keywords) {
        this.keywords = keywords;
    }

    public String getNotice() {
        return notice;
    }

    public void setNotice(String notice) {
        this.notice = notice;
    }

    public String getLogo() {
        return logo;
    }

    public void setLogo(String logo) {
        this.logo = logo;
    }

    public String getBackgroundImg() {
        return backgroundImg;
    }

    public void setBackgroundImg(String backgroundImg) {
        this.backgroundImg = backgroundImg;
    }

    public int getBgRepeat() {
        return bgRepeat;
    }

    public void setBgRepeat(int bgRepeat) {
        this.bgRepeat = bgRepeat;
    }
}
