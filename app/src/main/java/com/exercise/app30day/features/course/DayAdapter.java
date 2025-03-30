package com.exercise.app30day.features.course;

import android.view.ViewGroup;

import androidx.core.content.ContextCompat;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemDayBinding;
import com.exercise.app30day.items.DayItem;

public class DayAdapter extends BaseRecyclerViewAdapter<DayItem, ItemDayBinding> {

    public CourseViewModel viewModel;

    public DayAdapter(CourseViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void bindData(ItemDayBinding binding, DayItem item, int position) {
        binding.tvDay.setText(getContext().getString(R.string.day, item.getDay()));
        binding.tvExercise.setText(getContext().getString(R.string.exercise_number, item.getNumberOfExercises()));
        binding.getRoot().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_item_day_unselected));
        DayState dayState = viewModel.getExerciseState(item, getItem(position - 1));
        switch (dayState){
            case READY_TO_START:
                binding.btnStart.setVisibility(ViewGroup.VISIBLE);
                binding.ivCircleCheck.setVisibility(ViewGroup.GONE);
                binding.ivLock.setVisibility(ViewGroup.GONE);
                binding.getRoot().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_item_day_selected));
                break;
            case COMPLETED:
                binding.btnStart.setVisibility(ViewGroup.GONE);
                binding.ivCircleCheck.setVisibility(ViewGroup.VISIBLE);
                binding.ivLock.setVisibility(ViewGroup.GONE);
                binding.tvExercise.setText(getContext().getString(R.string.completed));
                break;
            case LOCKED:
                binding.btnStart.setVisibility(ViewGroup.GONE);
                binding.ivCircleCheck.setVisibility(ViewGroup.GONE);
                binding.ivLock.setVisibility(ViewGroup.VISIBLE);
                break;

        }
    }
}
