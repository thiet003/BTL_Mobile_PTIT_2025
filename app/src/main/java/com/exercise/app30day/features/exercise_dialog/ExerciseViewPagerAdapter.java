package com.exercise.app30day.features.exercise_dialog;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

import java.util.List;

public class ExerciseViewPagerAdapter extends FragmentStateAdapter {

    List<ExerciseFragment> listFragment;
    public ExerciseViewPagerAdapter(@NonNull FragmentActivity fragmentActivity, List<ExerciseFragment> listFragment) {
        super(fragmentActivity);
        this.listFragment = listFragment;
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        return listFragment.get(position);
    }

    @Override
    public int getItemCount() {
        return listFragment.size();
    }
}
