package com.exercise.app30day.features.day;

import com.bumptech.glide.Glide;
import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemExerciseBinding;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.ResourceUtils;
import com.exercise.app30day.utils.TimeUtils;

public class ExerciseAdapter extends BaseRecyclerViewAdapter<ExerciseItem, ItemExerciseBinding> {
    @Override
    protected void bindData(ItemExerciseBinding binding, ExerciseItem item, int position) {
        binding.tvExercise.setText(item.getName());
        binding.tvTimeOrLoop.setText(item.getTime() != 0 ? TimeUtils.formatMillisecondsToMMSS(item.getTime()) : "x" + item.getLoopNumber());
        int resId = ResourceUtils.getDrawableId(getContext(), item.getFileName());
        if(item.getFileName().equals("gif")){
            Glide.with(getContext()).asGif().load(resId).into(binding.ivAnim);
        }else{
            Glide.with(getContext()).load(resId).into(binding.ivAnim);
        }

    }
}
