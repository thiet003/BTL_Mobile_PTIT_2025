package com.exercise.app30day.items;

import androidx.annotation.DrawableRes;
import androidx.annotation.RawRes;
import androidx.annotation.StringRes;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseItem;

import java.util.List;

public class MusicItem extends BaseItem {
    @StringRes
    private int name;
    @DrawableRes
    private int image;
    @RawRes
    private int audio;

    public MusicItem(int id, int audio, int image, int name) {
        super(id);
        this.audio = audio;
        this.image = image;
        this.name = name;
    }

    public int getName() {
        return name;
    }

    public void setName(int name) {
        this.name = name;
    }

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
        this.image = image;
    }

    public int getAudio() {
        return audio;
    }

    public void setAudio(int audio) {
        this.audio = audio;
    }

    public static List<MusicItem> getMusicItems() {
        return List.of(
                new MusicItem(1, R.raw.music_1, R.drawable.img_music_1, R.string.music_1),
                new MusicItem(2, R.raw.music_2, R.drawable.img_music_2, R.string.music_2),
                new MusicItem(3, R.raw.music_3, R.drawable.img_music_3, R.string.music_3),
                new MusicItem(4, R.raw.music_4, R.drawable.img_music_4, R.string.music_4),
                new MusicItem(5, R.raw.music_5, R.drawable.img_music_5, R.string.music_5)
        );
    }
}
