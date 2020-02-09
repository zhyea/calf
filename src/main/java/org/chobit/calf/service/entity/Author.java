package org.chobit.calf.service.entity;

/**
 * @author robin
 */
public class Author extends AbstractEntity {

    private String name;

    private String country = "未知";

    private String bio;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }
}