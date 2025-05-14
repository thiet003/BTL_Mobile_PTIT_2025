package com.exercise.app30day.features.setting.workout;

import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.IBinder;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.SeekBar;
import android.widget.Toast;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.ActivityWorkoutSettingsBinding;
import com.exercise.app30day.items.MusicItem;
import com.exercise.app30day.services.MusicService;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

public class WorkoutSettingsActivity extends BaseActivity<ActivityWorkoutSettingsBinding, WorkoutSettingsViewModel>
        implements View.OnClickListener, SeekBar.OnSeekBarChangeListener, CompoundButton.OnCheckedChangeListener {

    private MusicService musicService;
    private boolean bound = false;

    private final ServiceConnection serviceConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            bound = true;
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            bound = false;
        }
    };

    @Override
    protected void initView() {
        int musicId = Hawk.get(HawkKeys.MUSIC_ID_KEY, viewModel.musicItems.get(0).getId());
        MusicItem selectedMusic = viewModel.findMusicItem(musicId);
        binding.tvSelectedMusic.setText(selectedMusic.getName());
        binding.ivMusic.setImageResource(selectedMusic.getImage());
        updateRestTimeText((int)(AppConfig.getExerciseRestDuration() / 1000));
        updatePrepTimeText((int) (AppConfig.getExercisePrepareDuration() / 1000));
        binding.volumeSeekBar.setProgress((int)(AppConfig.getBackgroundMusicVolume() * 100));

        binding.toggleBackgroundMusic.setChecked(AppConfig.isPlayBackgroundMusic());
        binding.toggleVoiceGuidance.setChecked(AppConfig.isVoiceEnabled());
        binding.toggleCountdownSound.setChecked(AppConfig.isCountdownSoundEnabled());

        float pitch = AppConfig.getVoicePitch();
        binding.voicePitchSeekBar.setProgress((int) ((pitch - 0.5f) * 2));

        float speed = AppConfig.getVoiceSpeed();
        binding.voiceSpeedSeekBar.setProgress((int) ((speed - 0.5f) * 2));

    }

    @Override
    protected void initListener() {
        binding.volumeSeekBar.setOnSeekBarChangeListener(this);
        binding.btnBack.setOnClickListener(this);
        binding.layoutSelectMusic.setOnClickListener(this);
        binding.layoutRestTime.setOnClickListener(this);
        binding.layoutPrepTime.setOnClickListener(this);
        binding.voicePitchSeekBar.setOnSeekBarChangeListener(this);
        binding.voiceSpeedSeekBar.setOnSeekBarChangeListener(this);
        binding.toggleBackgroundMusic.setOnCheckedChangeListener(this);
        binding.toggleVoiceGuidance.setOnCheckedChangeListener(this);
        binding.toggleCountdownSound.setOnCheckedChangeListener(this);
    }

    private void showMusicBottomDialog() {
        int musicId = Hawk.get(HawkKeys.MUSIC_ID_KEY, viewModel.musicItems.get(0).getId());
        MusicBottomDialog bottomSheet = new MusicBottomDialog(viewModel.musicItems, viewModel.findMusicItemPosition(musicId));

        bottomSheet.setMusicSelectionListener(music -> {
            binding.tvSelectedMusic.setText(music.getName());
            binding.ivMusic.setImageResource(music.getImage());
            Hawk.put(HawkKeys.MUSIC_ID_KEY, music.getId());
            if(bound){
                float volume = AppConfig.getBackgroundMusicVolume();
                musicService.initMusic(music.getAudio(), volume, true);
                if(AppConfig.isPlayBackgroundMusic()) musicService.playMusic();
            }
            Toast.makeText(WorkoutSettingsActivity.this,
                    getString(R.string.selected_music, WorkoutSettingsActivity.this.getString(music.getName())), Toast.LENGTH_SHORT).show();
        });

        bottomSheet.show(getSupportFragmentManager(), MusicBottomDialog.class.getName());
    }
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
        }else if(v == binding.layoutSelectMusic){
            showMusicBottomDialog();
        }else if(v == binding.layoutRestTime){
            showRestTimePickerDialog();
        }else if(v == binding.layoutPrepTime){
            showPrepTimePickerDialog();
        }
    }

    @Override
    protected void onStart() {
        super.onStart();
        if (!bound) {
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, serviceConnection, BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (bound) {
            unbindService(serviceConnection);
            bound = false;
        }
    }

    @Override
    public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
        if(seekBar == binding.volumeSeekBar){
            if(bound) musicService.setVolume(progress / 100f);
        }
    }

    @Override
    public void onStartTrackingTouch(SeekBar seekBar) {

    }

    @Override
    public void onStopTrackingTouch(SeekBar seekBar) {
        if(seekBar == binding.volumeSeekBar){
            AppConfig.setBackgroundMusicVolume(seekBar.getProgress() / 100f);
        }else if(seekBar == binding.voicePitchSeekBar){
            AppConfig.setVoicePitch(seekBar.getProgress() * 0.5f + 0.5f);
        }else if (seekBar == binding.voiceSpeedSeekBar){
            AppConfig.setVoiceSpeed(seekBar.getProgress() * 0.5f + 0.5f);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if(buttonView == binding.toggleBackgroundMusic){
            binding.layoutSelectMusic.setEnabled(isChecked);
            binding.layoutSelectMusic.setAlpha(isChecked ? 1.0f : 0.5f);
            binding.volumeSeekBar.setEnabled(isChecked);
            binding.volumeSeekBar.setAlpha(isChecked ? 1.0f : 0.5f);
            AppConfig.setPlayBackgroundMusic(isChecked);
            if(!isChecked){
                if(bound) musicService.stopMusic();
            }
        }else if(buttonView == binding.toggleVoiceGuidance){
            binding.voicePitchSeekBar.setEnabled(isChecked);
            binding.voiceSpeedSeekBar.setEnabled(isChecked);
            binding.layoutSelectSpeed.setAlpha(isChecked ? 1.0f : 0.5f);
            binding.layoutSelectPitch.setAlpha(isChecked ? 1.0f : 0.5f);
            AppConfig.setVoiceEnabled(isChecked);
        }else if(buttonView == binding.toggleCountdownSound){
            AppConfig.setEnableCountdownSound(isChecked);
        }
    }
}