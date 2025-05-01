package com.exercise.app30day.features.setting.workout;

import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemMusicBinding;
import com.exercise.app30day.items.MusicItem;

public class MusicAdapter extends BaseRecyclerViewAdapter<MusicItem, ItemMusicBinding> {

    private int selectedPosition = RecyclerView.NO_POSITION;

    public void setSelectedPosition(int selectedPosition) {
        notifyItemChanged(this.selectedPosition);
        notifyItemChanged(selectedPosition);
        this.selectedPosition = selectedPosition;
    }

    @Override
    protected void bindData(ItemMusicBinding binding, MusicItem item, int position) {
        boolean isSelected = position == selectedPosition;
        binding.musicItemContainer.setSelected(isSelected);
        binding.tvMusicTitle.setText(item.getName());
        binding.ivMusicIcon.setImageResource(item.getImage());
        binding.ivSelectedIcon.setVisibility(isSelected ? View.VISIBLE : View.GONE);
        binding.tvMusicTitle.setTextColor(ContextCompat.getColor(getContext(), isSelected ? R.color.textSecondaryColor : R.color.textPrimaryColor));
    }
}