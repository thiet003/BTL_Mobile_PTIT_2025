package com.exercise.app30day.features.setting;

import android.view.View;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.databinding.FragmentSettingBinding;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> implements View.OnClickListener {

    @Override
    protected void initView() {
        binding.tvVersionName.setText(getString(R.string.version_s, viewModel.getVersionName(requireContext())));
        binding.ivFlag.setImageResource(viewModel.getLanguageItem().getFlag());
    }

    @Override
    protected void initListener() {
        binding.itemLanguage.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == binding.itemLanguage){
            LanguageDialog dialog = new LanguageDialog(requireActivity(), viewModel.getLanguageItems());
            dialog.show();
        }
    }
}