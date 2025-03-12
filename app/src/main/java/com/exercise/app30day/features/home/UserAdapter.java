package com.exercise.app30day.features.home;

import android.annotation.SuppressLint;

import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemUserBinding;
import com.exercise.app30day.models.User;
import com.exercise.app30day.utils.TimeUtils;

public class UserAdapter extends BaseRecyclerViewAdapter<User, ItemUserBinding> {

    private OnUserClickDeleteListener onUserClickDeleteListener;
    @SuppressLint("SetTextI18n")
    @Override
    protected void bindData(ItemUserBinding binding, User item, int position) {
        binding.tvHeight.setText(item.getHeight() + " cm");
        binding.tvWeight.setText(item.getWeight() + " kg");
        binding.tvCreatedAt.setText("Created at: " + TimeUtils.convertLongToDate(item.getCreatedAt()));
        binding.tvUpdatedAt.setText("Updated at: " + TimeUtils.convertLongToDate(item.getUpdatedAt()));
        binding.tvId.setText("ID: " + item.getId());
        binding.btnDelete.setOnClickListener(v -> {
            if (onUserClickDeleteListener != null) {
                onUserClickDeleteListener.onUserClickDelete(item, position);
            }
        });
    }

    interface OnUserClickDeleteListener {
        void onUserClickDelete(User user, int position);
    }

    public void setOnUserClickDeleteListener(OnUserClickDeleteListener onUserClickDeleteListener) {
        this.onUserClickDeleteListener = onUserClickDeleteListener;
    }
}
