package com.exercise.app30day.features.home;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemCourseBinding;
import com.exercise.app30day.models.CourseItem;
import com.exercise.app30day.utils.ResourceUtils;

public class CourseAdapter extends BaseRecyclerViewAdapter<CourseItem, ItemCourseBinding> {
    @Override
    protected void bindData(ItemCourseBinding binding, CourseItem item, int position) {
        int imgRes = ResourceUtils.getDrawableId(getContext(), "img_course_" + item.getDifficultLevel().toLowerCase());
        binding.imgCourse.setImageResource(imgRes);
        binding.tvName.setText(item.getName());
        binding.tvDay.setText(getContext().getString(R.string.day_numbers, item.getNumberOfDays()));
        binding.tvLevel.setText(item.getDifficultLevel());
        binding.rbCourse.setRating(item.getLevel());
    }
}
