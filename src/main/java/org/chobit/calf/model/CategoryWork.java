package org.chobit.calf.model;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author robin
 */
public class CategoryWork {

    private int id;

    private String name;

    private String slug;

    private List<WorkModel> works = new LinkedList<>();

    public CategoryWork() {
    }

    public CategoryWork(int id, String name, String slug) {
        this.id = id;
        this.name = name;
        this.slug = slug;
    }


    public void addWork(WorkModel work) {
        this.works.add(work);
    }


    public void addWorks(Collection<WorkModel> works) {
        this.works.addAll(works);
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

    public List<WorkModel> getWorks() {
        return works;
    }

    public void setWorks(List<WorkModel> works) {
        this.works = works;
    }
}
