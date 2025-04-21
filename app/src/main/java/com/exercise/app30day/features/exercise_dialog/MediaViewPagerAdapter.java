package com.exercise.app30day.features.exercise_dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.exercise.app30day.items.ExerciseItem;

public class MediaViewPagerAdapter extends FragmentStateAdapter {

    private final ExerciseItem exerciseItem;
    public MediaViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, ExerciseItem exerciseItem) {
        super(fragmentActivity);
        this.exerciseItem = exerciseItem;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        if (position == 0) {
            return new MediaFragment(exerciseItem.getAnimationUrl(), position);
        }
        return new MediaFragment(exerciseItem.getInstructionUrl(), position);
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
