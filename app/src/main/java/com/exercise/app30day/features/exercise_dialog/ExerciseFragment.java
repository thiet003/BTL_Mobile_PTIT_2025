package com.exercise.app30day.features.exercise_dialog;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.FragmentExerciseBinding;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.GlideUtils;
import com.exercise.app30day.utils.TimeUtils;


public class ExerciseFragment extends BaseFragment<FragmentExerciseBinding, NoneViewModel> {

    private ExerciseItem exerciseItem;

    public ExerciseFragment(){

    }
    public ExerciseFragment(ExerciseItem exerciseItem) {
        this.exerciseItem = exerciseItem;
    }

    @Override
    protected void initView() {
        binding.tvDurationValue.setText(exerciseItem.getTime() != 0 ? TimeUtils.formatMillisecondsToMMSS(exerciseItem.getTime()) : "x" + exerciseItem.getLoopNumber());
        binding.tvDuration.setText(requireContext().getString(exerciseItem.getTime() != 0 ? R.string.duration : R.string.repeat));
        binding.tvInstructions.setText(exerciseItem.getDescription());
        GlideUtils.loadImage(getContext(), binding.ivAnim, exerciseItem.getAnimationFileName());
    }

    @Override
    protected void initListener() {

    }
}