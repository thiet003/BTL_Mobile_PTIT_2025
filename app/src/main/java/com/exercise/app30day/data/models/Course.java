package com.exercise.app30day.data.models;


import androidx.room.Entity;

@Entity(tableName = "course", inheritSuperIndices = true)
public class Course extends BaseEntity {
    private String name;

    private int level;

    private String image;

    public Course(String name, int level, String image) {
        this.name = name;
        this.level = level;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    public int getLevel() {
        return level;
    }
    public void setLevel(int level) {
        this.level = level;
    }

    public String getImage() {
        return image;
    }
}
