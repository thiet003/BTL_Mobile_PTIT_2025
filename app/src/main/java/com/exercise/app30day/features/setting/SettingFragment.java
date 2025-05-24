package com.exercise.app30day.features.setting;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.view.View;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.databinding.FragmentSettingBinding;
import com.exercise.app30day.features.setting.reminder.ReminderActivity;
import com.exercise.app30day.features.setting.workout.WorkoutSettingsActivity;
import com.exercise.app30day.features.splash.SplashActivity;
import com.exercise.app30day.items.LanguageItem;
import com.exercise.app30day.utils.LanguageUtils;

public class SettingFragment extends BaseFragment<FragmentSettingBinding, SettingViewModel> implements View.OnClickListener {

    @Override
    protected void initView() {
        binding.tvVersionName.setText(getString(R.string.version_s, getVersionName()));
        LanguageItem languageItem = LanguageUtils.getLanguage();
        binding.ivFlag.setImageResource(languageItem != null ? languageItem.getFlag() : R.drawable.ic_flag_vietnamese);
    }

    @Override
    protected void initListener() {
        binding.itemLanguage.setOnClickListener(this);
        binding.itemRestart.setOnClickListener(this);
        binding.itemWorkoutSettings.setOnClickListener(this);
        binding.itemReminder.setOnClickListener(this);
    }


    @Override
    public void onClick(View v) {
        if(v == binding.itemLanguage){
                LanguageDialog dialog = new LanguageDialog(requireActivity(), LanguageUtils.getLanguages());
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
                        AppDatabase.resetAll(requireContext());
                    }).setNegativeButton(R.string.no, null)
                    .setIcon(R.drawable.ic_warning)
                    .show();
        } else if (v == binding.itemWorkoutSettings) {
            requireContext().startActivity(new Intent(requireContext(), WorkoutSettingsActivity.class));
        } else if(v == binding.itemReminder){
            requireContext().startActivity(new Intent(requireContext(), ReminderActivity.class));
        }
    }

    private String getVersionName() {
        try {
            Context context = requireContext();
            PackageInfo packageInfo = context.getPackageManager()
                    .getPackageInfo(context.getPackageName(), 0);
            return packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
            return "1.0";
        }
    }
}