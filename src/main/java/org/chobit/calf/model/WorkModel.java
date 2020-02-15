package org.chobit.calf.model;

import org.chobit.calf.service.entity.Author;
import org.chobit.calf.service.entity.Meta;
import org.chobit.calf.service.entity.Work;

/**
 * @author robin
 */
public class WorkModel {

    private int id;

    private String name;

    private String cover;

    private int authorId;

    private String author;

    private String country;

    private int catId;

    private String cat;

    private String brief;


    public WorkModel() {
    }

    public WorkModel(Work work, Author author, Meta meta) {
        this.id = work.getId();
        this.name = work.getName();
        this.cover = work.getCover();
        this.brief = work.getBrief();
        this.authorId = author.getId();
        this.author = author.getName();
        this.country = author.getCountry();
        this.catId = meta.getId();
        this.cat = meta.getName();
    }

    public Work obtainWork() {
        Work w = new Work();
        w.setId(id);
        w.setAuthorId(authorId);
        w.setCategoryId(catId);
        w.setName(name);
        w.setCover(cover);
        w.setBrief(brief);
        return w;
    }


    public Author obtainAuthor() {
        Author a = new Author();
        a.setId(authorId);
        a.setName(author);
        a.setCountry(country);
        return a;
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getCover() {
        return cover;
    }

    public int getAuthorId() {
        return authorId;
    }

    public String getAuthor() {
        return author;
    }

    public String getCountry() {
        return country;
    }

    public int getCatId() {
        return catId;
    }

    public String getCat() {
        return cat;
    }

    public String getBrief() {
        return brief;
    }

    public void setId(int id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setCover(String cover) {
        this.cover = cover;
    }

    public void setAuthorId(int authorId) {
        this.authorId = authorId;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public void setCountry(String country) {
        this.country = country;
    }

    public void setCatId(int catId) {
        this.catId = catId;
    }

    public void setCat(String cat) {
        this.cat = cat;
    }

    public void setBrief(String brief) {
        this.brief = brief;
    }
}
