package com.exercise.app30day.models;

import androidx.room.Entity;

@Entity(tableName = "concentration_area", inheritSuperIndices = true)
public class ConcentrationArea extends BaseEntity{
    private String name;

    private String description;

    public ConcentrationArea(String name, String description) {
        super();
        this.name = name;
        this.description = description;
    }

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
}
