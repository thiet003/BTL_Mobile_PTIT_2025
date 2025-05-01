package com.exercise.app30day.features.report;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemDayHistoryBinding;
import com.exercise.app30day.items.DayHistoryItem;
import com.exercise.app30day.utils.TimeUtils;

import java.util.Locale;

public class DayHistoryAdapter extends BaseRecyclerViewAdapter<DayHistoryItem, ItemDayHistoryBinding> {

    @Override
    protected void bindData(ItemDayHistoryBinding binding, DayHistoryItem item, int position) {
        binding.tvCourseName.setText(item.getCourseName());
        binding.tvDayInfo.setText(getContext().getString(R.string.day, item.getDay()));
        binding.tvWorkoutDate.setText(TimeUtils.formatDate(item.getStopTime()));
        binding.tvDuration.setText(getContext().getString(R.string.d_minutes,  (int)((item.getStopTime() - item.getStartTime()) /60000)));
        binding.tvCalories.setText(String.format(Locale.getDefault(), "%.0f kcal", item.getKcal()));
        binding.tvRestTime.setText(getContext().getString(R.string.d_seconds, (int)(item.getRestTime() / 1000)));
        binding.tvExercisesCount.setText(String.valueOf(item.getExercisePosition() + 1));
    }

    @Override
    public int getItemCount() {
        return super.getItemCount();
    }
}