package com.exercise.app30day.features.home;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.databinding.FragmentHomeBinding;

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
        viewModel.listCourseItemData.observe(getViewLifecycleOwner(), courseItems -> {
            courseAdapter.setData(courseItems);
        });
    }

    @Override
    protected void initListener() {

    }
}