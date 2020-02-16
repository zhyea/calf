package org.chobit.calf.model;

import org.chobit.calf.service.entity.Chapter;
import org.chobit.calf.service.entity.Volume;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * @author robin
 */
public class VolumeModel {

    private int id;

    private String name;

    private final List<Chapter> chapters = new LinkedList<>();


    public VolumeModel(int id, String name) {
        this.id = id;
        this.name = name;
    }

    public VolumeModel(Volume volume) {
        this(volume.getId(), volume.getName());
    }

    public void addChapter(Chapter chapter) {
        this.chapters.add(chapter);
    }

    public void addChapters(Collection<Chapter> chapters) {
        this.chapters.addAll(chapters);
    }

    public boolean hasChapters() {
        return !chapters.isEmpty();
    }

    public int getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<Chapter> getChapters() {
        return chapters;
    }
}
