package com.exercise.app30day.features.setting.workout;

import androidx.lifecycle.ViewModel;

import com.exercise.app30day.items.MusicItem;

import java.util.List;

public class WorkoutSettingsViewModel extends ViewModel {

    public final List<MusicItem> musicItems;

    public WorkoutSettingsViewModel() {
        musicItems = MusicItem.getMusicItems();
    }

    public MusicItem findMusicItem(int id) {
        for (MusicItem item : musicItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public int findMusicItemPosition(int id) {
        for (int i = 0; i < musicItems.size(); i++) {
            if (musicItems.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }
}
