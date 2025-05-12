package com.exercise.app30day.items;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.exercise.app30day.base.adapter.BaseItem;

public class LanguageItem extends BaseItem {

    @StringRes
    private int name;
    @DrawableRes
    private int flag;
    private String codeSnip;


    public LanguageItem(int id, int name, int flag, String codeSnip) {
        super(id);
        this.name = name;
        this.flag = flag;
        this.codeSnip = codeSnip;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getFlag() {
        return flag;
    }

    public void setFlag(int flag) {
        this.flag = flag;
    }

    public String getCodeSnip() {
        return codeSnip;
    }

    public void setCodeSnip(String codeSnip) {
        this.codeSnip = codeSnip;
    }
}
