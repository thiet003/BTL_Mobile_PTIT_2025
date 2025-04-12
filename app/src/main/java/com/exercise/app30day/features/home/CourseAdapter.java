package com.exercise.app30day.features.home;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemCourseBinding;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.utils.ResourceUtils;

public class CourseAdapter extends BaseRecyclerViewAdapter<CourseItem, ItemCourseBinding> {

    private final HomeViewModel homeViewModel;
    public CourseAdapter(HomeViewModel homeViewModel){
        this.homeViewModel = homeViewModel;
    }
    @Override
    protected void bindData(ItemCourseBinding binding, CourseItem item, int position) {
        int imgRes = ResourceUtils.getDrawableId(getContext(), item.getImage());
        binding.imgCourse.setImageResource(imgRes);
        binding.tvName.setText(item.getName());
        binding.tvDay.setText(getContext().getString(R.string.day_number, item.getNumberOfDays()));
        binding.tvLevel.setText(item.getDifficultLevel());
        binding.rbCourse.setRating(item.getLevel());
        int dayProgress = item.getDayProgress();
        binding.tvProgress.setText(getContext().getString(R.string.progress, dayProgress));
        binding.progressCourse.setProgress(dayProgress);
    }
}
