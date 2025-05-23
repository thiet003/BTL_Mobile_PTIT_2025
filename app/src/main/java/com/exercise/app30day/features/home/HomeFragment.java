package com.exercise.app30day.features.home;

import android.content.Intent;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.databinding.FragmentHomeBinding;
import com.exercise.app30day.features.course.CourseActivity;
import com.exercise.app30day.utils.IntentKeys;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class HomeFragment extends BaseFragment<FragmentHomeBinding, HomeViewModel> {

    CourseAdapter courseAdapter;
    @Override
    protected void initView() {
        courseAdapter = new CourseAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(getContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvCourse.setLayoutManager(layoutManager);
        binding.rvCourse.setAdapter(courseAdapter);
        viewModel.getAllCourseItems().observe(getViewLifecycleOwner(), courseItems -> {
            courseAdapter.setData(courseItems);
        });
        viewModel.getLongestWorkoutStreak().observe(getViewLifecycleOwner(), dayStreak -> {
            binding.tvSpark.setText(String.valueOf(dayStreak));
        });
    }

    @Override
    protected void initListener() {
        courseAdapter.setOnItemClickListener((item, position) -> {
            Intent intent = new Intent(requireContext(), CourseActivity.class);
            intent.putExtra(IntentKeys.EXTRA_COURSE_ID, item.getId());
            requireContext().startActivity(intent);
        });
    }
}