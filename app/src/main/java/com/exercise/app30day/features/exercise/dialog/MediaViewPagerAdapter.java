package com.exercise.app30day.features.exercise.dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import com.exercise.app30day.features.exercise.dialog.fragment.AnimFragment;
import com.exercise.app30day.features.exercise.dialog.fragment.WebViewFragment;
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
            return AnimFragment.newInstance(exerciseItem.getAnimationUrl());
        }
        return WebViewFragment.newInstance(exerciseItem.getInstructionUrl());
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
