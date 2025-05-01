package com.exercise.app30day.features.setting;

import android.app.AlertDialog;
import android.content.Intent;
import android.view.View;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.databinding.FragmentSettingBinding;
import com.exercise.app30day.features.setting.workout.WorkoutSettingsActivity;
import com.exercise.app30day.features.splash.SplashActivity;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> implements View.OnClickListener {

    @Override
    protected void initView() {
        binding.tvVersionName.setText(getString(R.string.version_s, viewModel.getVersionName(requireContext())));
        binding.ivFlag.setImageResource(viewModel.getLanguageItem().getFlag());
    }

    @Override
    protected void initListener() {
        binding.itemLanguage.setOnClickListener(this);
        binding.itemRestart.setOnClickListener(this);
        binding.itemWorkoutSettings.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == binding.itemLanguage){
            LanguageDialog dialog = new LanguageDialog(requireActivity(), viewModel.getLanguageItems());
            dialog.show();
        }else if(v == binding.itemRestart){
            new AlertDialog.Builder(requireContext())
                    .setTitle(R.string.restart_confirmation)
                    .setMessage(R.string.are_you_sure_you_want_to_restart_your_process)
                    .setPositiveButton(R.string.yes, (dialog, which) -> {
                        Intent intent = new Intent(requireContext(), SplashActivity.class);
                        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        startActivity(intent);
                        requireActivity().finish();
                    }).setNegativeButton(R.string.no, null)
                    .setIcon(R.drawable.ic_warning)
                    .show();
            AppDatabase.resetAll(requireContext());
        } else if (v == binding.itemWorkoutSettings) {
            requireContext().startActivity(new Intent(requireContext(), WorkoutSettingsActivity.class));
        }
    }
}