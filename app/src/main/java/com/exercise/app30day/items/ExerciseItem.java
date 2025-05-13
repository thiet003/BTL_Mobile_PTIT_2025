package com.exercise.app30day.items;

import com.exercise.app30day.base.adapter.BaseItem;
import com.exercise.app30day.utils.TimeUtils;

public class ExerciseItem extends BaseItem {
    private String name;
    private String description;

    private long time;

    private double kcal;

    private int loopNumber;

    private String instructionUrl;

    private String animationUrl;

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

    public String getInstructionUrl() {
        return instructionUrl;
    }

    public void setInstructionUrl(String instructionUrl) {
        this.instructionUrl = instructionUrl;
    }

    public String getAnimationUrl() {
        return animationUrl;
    }

    public void setAnimationUrl(String animationUrl) {
        this.animationUrl = animationUrl;
    }

    public String getLoopOrDuration(){
        return loopNumber != 0 ? "x" + loopNumber : TimeUtils.formatMillisecondsToMMSS(time);
    }
}
