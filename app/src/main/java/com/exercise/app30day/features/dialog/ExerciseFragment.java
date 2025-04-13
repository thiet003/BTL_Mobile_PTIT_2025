package com.exercise.app30day.features.dialog;

import com.bumptech.glide.Glide;
import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.FragmentExerciseBinding;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.GlideUtils;
import com.exercise.app30day.utils.ResourceUtils;
import com.exercise.app30day.utils.TimeUtils;


public class ExerciseFragment extends BaseFragment<FragmentExerciseBinding, NoneViewModel> {

    private final ExerciseItem exerciseItem;

    public ExerciseFragment(ExerciseItem exerciseItem) {
        this.exerciseItem = exerciseItem;
    }

    @Override
    protected void initView() {
        binding.tvExerciseName.setText(exerciseItem.getName());
        binding.tvDurationValue.setText(exerciseItem.getTime() != 0 ? TimeUtils.formatMillisecondsToMMSS(exerciseItem.getTime()) : "x" + exerciseItem.getLoopNumber());
        binding.tvDuration.setText(requireContext().getString(exerciseItem.getTime() != 0 ? R.string.duration : R.string.repeat));
        binding.tvInstructions.setText(exerciseItem.getDescription());
        GlideUtils.loadImage(getContext(), binding.ivAnim, exerciseItem.getAnimationFileName());
    }

    @Override
    protected void initListener() {

    }
}