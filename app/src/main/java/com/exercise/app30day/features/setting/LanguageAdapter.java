package com.exercise.app30day.features.setting;

import android.annotation.SuppressLint;
import android.view.View;

import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ItemLanguageBinding;
import com.exercise.app30day.items.LanguageItem;

public class LanguageAdapter extends BaseRecyclerViewAdapter<LanguageItem, ItemLanguageBinding> {

    private String codeSnip;

    public LanguageAdapter(String codeSnip) {
        this.codeSnip = codeSnip;
    }

    @Override
    protected void bindData(ItemLanguageBinding binding, LanguageItem item, int position) {
        binding.ivFlag.setImageResource(item.getFlag());
        binding.tvLanguage.setText(item.getName());
        binding.ivCheck.setVisibility(item.getCodeSnip().equals(codeSnip) ? View.VISIBLE : View.INVISIBLE);
    }

    @SuppressLint("NotifyDataSetChanged")
    public void setCodeSnip(String codeSnip) {
        this.codeSnip = codeSnip;
        notifyDataSetChanged();
    }
}
