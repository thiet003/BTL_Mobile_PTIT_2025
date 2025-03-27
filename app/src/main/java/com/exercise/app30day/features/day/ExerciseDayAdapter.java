package com.exercise.app30day.features.day;

import android.view.ViewGroup;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemDayBinding;
import com.exercise.app30day.items.CourseDayExerciseItem;

public class ExerciseDayAdapter extends BaseRecyclerViewAdapter<CourseDayExerciseItem, ItemDayBinding> {

    public ExerciseDayViewModel viewModel;

    public ExerciseDayAdapter(ExerciseDayViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void bindData(ItemDayBinding binding, CourseDayExerciseItem item, int position) {
        binding.tvDay.setText(getContext().getString(R.string.day, item.getDay()));
        binding.tvExercise.setText(getContext().getString(R.string.exercises, item.getNumberOfExercises()));
        binding.getRoot().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_item_day_unselected));
        if((position == 0 && !item.isCompleted()) || (getItem(position - 1).isCompleted() && !item.isCompleted())){
            binding.btnStart.setVisibility(ViewGroup.VISIBLE);
            binding.ivCircleCheck.setVisibility(ViewGroup.GONE);
            binding.ivLock.setVisibility(ViewGroup.GONE);
            binding.getRoot().setBackground(ContextCompat.getDrawable(getContext(), R.drawable.bg_item_day_selected));
        }else if(item.isCompleted()){
            binding.btnStart.setVisibility(ViewGroup.GONE);
            binding.ivCircleCheck.setVisibility(ViewGroup.VISIBLE);
            binding.ivLock.setVisibility(ViewGroup.GONE);
            binding.tvExercise.setText(getContext().getString(R.string.completed));
        }else {
            binding.btnStart.setVisibility(ViewGroup.GONE);
            binding.ivCircleCheck.setVisibility(ViewGroup.GONE);
            binding.ivLock.setVisibility(ViewGroup.VISIBLE);
        }
    }
}
