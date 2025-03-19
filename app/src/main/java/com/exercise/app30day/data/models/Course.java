package com.exercise.app30day.data.models;


import androidx.room.Entity;

@Entity(tableName = "course", inheritSuperIndices = true)
public class Course extends BaseEntity {
    private String name;

    private String difficultLevel;

    public Course(String name, String difficultLevel) {
        super();
        this.name = name;
        this.difficultLevel = difficultLevel;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDifficultLevel() {
        return difficultLevel;
    }

    public void setDifficultLevel(String difficultLevel) {
        this.difficultLevel = difficultLevel;
    }
}
