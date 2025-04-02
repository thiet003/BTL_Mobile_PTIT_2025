package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;

public class ExerciseItem extends BaseItem {
    private String name;
    private String description;

    private long time;

    private double kcal;

    private int loopNumber;

    private String animationFileName;

    private String animationFileType;
    public ExerciseItem(int id) {
        super(id);
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

    public long getTime() {
        return time;
    }

    public void setTime(long time) {
        this.time = time;
    }

    public double getKcal() {
        return kcal;
    }

    public void setKcal(double kcal) {
        this.kcal = kcal;
    }

    public int getLoopNumber() {
        return loopNumber;
    }

    public void setLoopNumber(int loopNumber) {
        this.loopNumber = loopNumber;
    }

    public String getAnimationFileName() {
        return animationFileName;
    }

    public void setAnimationFileName(String animationFileName) {
        this.animationFileName = animationFileName;
    }

    public String getAnimationFileType() {
        return animationFileType;
    }

    public void setAnimationFileType(String animationFileType) {
        this.animationFileType = animationFileType;
    }
}
