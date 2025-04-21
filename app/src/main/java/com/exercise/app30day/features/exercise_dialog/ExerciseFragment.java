package com.exercise.app30day.features.exercise_dialog;

import android.annotation.SuppressLint;
import android.view.View;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.FragmentExerciseBinding;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.TimeUtils;


public class ExerciseFragment extends BaseFragment<FragmentExerciseBinding, NoneViewModel> {

    private ExerciseItem exerciseItem;

    private MediaViewPagerAdapter mediaViewPagerAdapter;

    public ExerciseFragment(){

    }
    public ExerciseFragment(ExerciseItem exerciseItem) {
        this.exerciseItem = exerciseItem;
    }

    @SuppressLint("SetJavaScriptEnabled")
    @Override
    protected void initView() {
        binding.tvDurationValue.setText(exerciseItem.getLoopNumber() != 0 ? "x" + exerciseItem.getLoopNumber() : TimeUtils.formatMillisecondsToMMSS(exerciseItem.getTime()));
        binding.tvDuration.setText(requireContext().getString(exerciseItem.getLoopNumber() != 0 ? R.string.repeat : R.string.duration));
        binding.tvInstructions.setText(exerciseItem.getDescription());
        this.mediaViewPagerAdapter = new MediaViewPagerAdapter(requireActivity(), exerciseItem);
        binding.mediaViewpager.setAdapter(mediaViewPagerAdapter);
        binding.mediaViewpager.setEnabled(false);
    }

    @Override
    protected void initListener() {
        binding.buttonGroupRounded.setOnPositionChangedListener(position -> {
            binding.mediaViewpager.setCurrentItem(position);
        });
    }
}