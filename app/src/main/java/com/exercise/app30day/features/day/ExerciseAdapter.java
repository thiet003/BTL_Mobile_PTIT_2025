package com.exercise.app30day.features.day;

import com.bumptech.glide.Glide;
import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemExerciseBinding;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.TimeUtils;

public class ExerciseAdapter extends BaseRecyclerViewAdapter<ExerciseItem, ItemExerciseBinding> {
    @Override
    protected void bindData(ItemExerciseBinding binding, ExerciseItem item, int position) {
        binding.tvExercise.setText(item.getName());
        binding.tvTimeOrLoop.setText(item.getTime() != 0 ? TimeUtils.formatMillisecondsToMMSS(item.getTime()) : "x" + item.getLoopNumber());
        Glide.with(getContext()).asGif().load(R.drawable.img_push_up).into(binding.ivAnim);
    }
}
