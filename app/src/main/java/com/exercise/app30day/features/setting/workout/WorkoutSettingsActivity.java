package com.exercise.app30day.features.setting.workout;

import android.view.View;
import android.widget.Toast;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.ActivityWorkoutSettingsBinding;
import com.exercise.app30day.items.MusicItem;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

public class WorkoutSettingsActivity extends BaseActivity<ActivityWorkoutSettingsBinding, WorkoutSettingsViewModel> implements View.OnClickListener {


    @Override
    protected void initView() {
        int musicId = Hawk.get(HawkKeys.MUSIC_ID_KEY, viewModel.musicItems.get(0).getId());
        MusicItem selectedMusic = viewModel.findMusicItem(musicId);
        boolean isFemaleVoice = AppConfig.isFemaleVoice();
        binding.tvSelectedMusic.setText(selectedMusic.getName());
        binding.ivMusic.setImageResource(selectedMusic.getImage());
        binding.tvSelectedVoice.setText(isFemaleVoice ? getString(R.string.female) : getString(R.string.male));
        updateRestTimeText((int)(AppConfig.getExerciseRestDuration() / 1000));
        updatePrepTimeText((int) (AppConfig.getExercisePrepareDuration() / 1000));
    }

    @Override
    protected void initListener() {
        // Background music toggle
        binding.toggleBackgroundMusic.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.layoutSelectMusic.setEnabled(isChecked);
            binding.layoutSelectMusic.setAlpha(isChecked ? 1.0f : 0.5f);
            binding.volumeSeekBar.setEnabled(isChecked);
            binding.volumeSeekBar.setAlpha(isChecked ? 1.0f : 0.5f);
        });

        // Voice guidance toggle
        binding.toggleVoiceGuidance.setOnCheckedChangeListener((buttonView, isChecked) -> {
            binding.layoutSelectVoice.setEnabled(isChecked);
            binding.layoutSelectVoice.setAlpha(isChecked ? 1.0f : 0.5f);
            binding.voiceVolumeSeekBar.setEnabled(isChecked);
            binding.voiceVolumeSeekBar.setAlpha(isChecked ? 1.0f : 0.5f);
        });
        binding.btnBack.setOnClickListener(this);
        binding.layoutSelectMusic.setOnClickListener(this);
        binding.layoutSelectVoice.setOnClickListener(this);
        binding.layoutRestTime.setOnClickListener(this);
        binding.layoutPrepTime.setOnClickListener(this);
    }

    private void showMusicBottomDialog() {
        int musicId = Hawk.get(HawkKeys.MUSIC_ID_KEY, viewModel.musicItems.get(0).getId());
        MusicBottomDialog bottomSheet = new MusicBottomDialog(viewModel.musicItems, viewModel.findMusicItemPosition(musicId));

        bottomSheet.setMusicSelectionListener(music -> {
            binding.tvSelectedMusic.setText(music.getName());
            binding.ivMusic.setImageResource(music.getImage());
            Hawk.put(HawkKeys.MUSIC_ID_KEY, music.getId());
            Toast.makeText(WorkoutSettingsActivity.this,
                    getString(R.string.selected_music, WorkoutSettingsActivity.this.getString(music.getName())), Toast.LENGTH_SHORT).show();
        });

        bottomSheet.show(getSupportFragmentManager(), MusicBottomDialog.class.getName());
    }

    private void showVoiceSelectionDialog() {
        boolean isFemaleVoice = AppConfig.isFemaleVoice();
        final VoiceSelectionDialog dialog = new VoiceSelectionDialog(this, isFemaleVoice);

        dialog.setOnVoiceSelectedListener(isFemale -> {
            binding.tvSelectedVoice.setText(isFemale ? getString(R.string.female) : getString(R.string.male));
            AppConfig.setFemaleVoice(isFemale);
            Toast.makeText(WorkoutSettingsActivity.this,
                    getString(R.string.selected_voice, (isFemale ? getString(R.string.female) : getString(R.string.male))),
                    Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    /**
     * Shows the rest time picker dialog
     */
    private void showRestTimePickerDialog() {
        long second = AppConfig.getExerciseRestDuration();
        final TimePickerDialog dialog = new TimePickerDialog(
                this,
                getString(R.string.rest_time_between_exercises),
                (int)(second / 1000));

        dialog.setOnTimeSelectedListener(seconds -> {
            AppConfig.setExerciseRestDuration(seconds*1000L);
            updateRestTimeText(seconds);
            Toast.makeText(WorkoutSettingsActivity.this,
                    getString(R.string.rest_time_updated), Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }

    /**
     * Shows the preparation time picker dialog
     */
    private void showPrepTimePickerDialog() {
        long second = AppConfig.getExercisePrepareDuration();
        final TimePickerDialog dialog = new TimePickerDialog(
                this,
                getString(R.string.preparation_time),
                (int) (second / 1000));

        dialog.setOnTimeSelectedListener(seconds -> {
            AppConfig.setExercisePrepareDuration(seconds * 1000L);
            updatePrepTimeText(seconds);
            Toast.makeText(WorkoutSettingsActivity.this,
                    getString(R.string.preparation_time_updated), Toast.LENGTH_SHORT).show();
        });

        dialog.show();
    }
    private void updateRestTimeText(int seconds) {
        binding.tvRestTime.setText(getString(R.string.d_sec, seconds));
    }
    private void updatePrepTimeText(int seconds) {
        binding.tvPrepTime.setText(getString(R.string.d_sec, seconds));
    }

    @Override
    public void onClick(View v) {
        if(v ==binding.btnBack){
            finish();
        }else if(v == binding.layoutSelectVoice){
            showVoiceSelectionDialog();
        }else if(v == binding.layoutSelectMusic){
            showMusicBottomDialog();
        }else if(v == binding.layoutRestTime){
            showRestTimePickerDialog();
        }else if(v == binding.layoutPrepTime){
            showPrepTimePickerDialog();
        }
    }
}