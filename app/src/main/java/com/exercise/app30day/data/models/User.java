package com.exercise.app30day.data.models;


public class User{
    private long id;

    private String name;
    private double height;

    public User(long id, String name, double height){
        this.id = id;
        this.name = name;
        this.height = height;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getHeight() {
        return height;
    }

    public void setHeight(double height) {
        this.height = height;
    }
}
