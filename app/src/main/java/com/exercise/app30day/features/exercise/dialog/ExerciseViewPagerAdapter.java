package com.exercise.app30day.features.exercise.dialog;

import androidx.fragment.app.FragmentActivity;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.LayoutExerciseInfoBinding;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.TimeUtils;

public class ExerciseViewPagerAdapter extends BaseRecyclerViewAdapter<ExerciseItem, LayoutExerciseInfoBinding>{

    private final FragmentActivity fragmentActivity;
    public ExerciseViewPagerAdapter(FragmentActivity fragmentActivity){
        this.fragmentActivity = fragmentActivity;
    }
    @Override
    protected void bindData(LayoutExerciseInfoBinding binding, ExerciseItem item, int position) {
        binding.tvDurationValue.setText(item.getLoopNumber() != 0 ? "x" + item.getLoopNumber() : TimeUtils.formatMillisecondsToMMSS(item.getTime()));
        binding.tvDuration.setText(getContext().getString(item.getLoopNumber() != 0 ? R.string.repeat : R.string.duration));
        binding.tvInstructions.setText(item.getDescription());
        MediaViewPagerAdapter mediaViewPagerAdapter = new MediaViewPagerAdapter(fragmentActivity, item);
        binding.mediaViewpager.setAdapter(mediaViewPagerAdapter);
        binding.mediaViewpager.setEnabled(false);
        binding.buttonGroupRounded.setOnPositionChangedListener(binding.mediaViewpager::setCurrentItem);
    }
}
