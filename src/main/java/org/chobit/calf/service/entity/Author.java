package org.chobit.calf.service.entity;

/**
 * table author
 *
 * @author robin
 */
public class Author extends AbstractEntity {

    private String name;

    private String country = "未知";

    private String bio;

    public Author() {
    }

    public Author(String name, String country) {
        this.name = name;
        this.country = country;
    }

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
