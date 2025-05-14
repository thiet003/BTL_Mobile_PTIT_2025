package com.exercise.app30day.features.exercise;

import static com.exercise.app30day.config.AppConfig.DEFAULT_DELAY_MILLIS;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_COURSE;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_CURRENT_EXERCISE_POSITION;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_DAY;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_EXERCISE_LIST;

import android.annotation.SuppressLint;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.ActivityInfo;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.lifecycle.ViewModelProvider;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.ActivityExerciseBinding;
import com.exercise.app30day.features.complete.ExerciseCompleteActivity;
import com.exercise.app30day.features.exercise.dialog.ExerciseBottomDialog;
import com.exercise.app30day.features.setting.workout.MusicBottomDialog;
import com.exercise.app30day.features.setting.workout.WorkoutSettingsActivity;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.items.MusicItem;
import com.exercise.app30day.services.MusicService;
import com.exercise.app30day.utils.HawkKeys;
import com.exercise.app30day.utils.SpeechUtils;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExerciseActivity extends BaseActivity<ActivityExerciseBinding, NoneViewModel>
        implements View.OnClickListener, OnCompleteListener, ExerciseRestFragment.OnSoundControlListener{

    private ExerciseViewModel viewModel;
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable prepareRunnable, exerciseRunnable;
    private boolean inBackground = false;

    private MusicService musicService;
    private boolean musicBound = false;

    private MediaPlayer effectMediaPlayer;

    private final ServiceConnection musicConnection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            MusicService.MusicBinder binder = (MusicService.MusicBinder) service;
            musicService = binder.getService();
            musicBound = true;
            updateAndPlayMusic();
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            musicBound = false;
        }
    };

    private final int SETTING_REQUEST_CODE = 100;

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(this).get(ExerciseViewModel.class);
        initData();
        binding.stepSeekBar.setNumSteps(viewModel.getListExerciseItem().size());
        binding.stepSeekBar.setEnabled(false);
        viewModel.onExerciseUiState.observe(this, exerciseUiState -> {
            ExerciseItem exerciseItem = viewModel.getListExerciseItem().get(exerciseUiState.getExercisePosition());
            binding.stepSeekBar.setProgress(exerciseUiState.getExercisePosition() + 1);
            if(exerciseUiState.getExerciseState() == ExerciseState.PREPARE) {
                setPrepareLayout(exerciseItem);
                long prepareDuration = AppConfig.getExercisePrepareDuration();
                SpeechUtils.speak(getString(R.string.prepare_d_seconds, (int)(prepareDuration / 1000)));
            }else if(exerciseUiState.getExerciseState() == ExerciseState.EXERCISE) {
                setExerciseLayout(exerciseItem);
                SpeechUtils.speak(viewModel.getFirstSentence(exerciseItem.getDescription()));
            }else if(exerciseUiState.getExerciseState() == ExerciseState.REST){
                setRestLayout();
                long restDuration = AppConfig.getExerciseRestDuration();
                SpeechUtils.speak(getString(R.string.rest_d_seconds, (int)(restDuration / 1000)));
            }
        });
        viewModel.onPlayExercise.observe(this, isPlay -> {
            if(isPlay){
                playExercise();
            }else{
                pauseExercise();
            }
        });
    }

    private void initData(){
        List<ExerciseItem> listExerciseItem = (ArrayList<ExerciseItem>) getIntent().getSerializableExtra(EXTRA_EXERCISE_LIST);
        DayItem dayItem = (DayItem) getIntent().getSerializableExtra(EXTRA_DAY);
        CourseItem courseItem = (CourseItem) getIntent().getSerializableExtra(EXTRA_COURSE);
        int currentExerciseId = getIntent().getIntExtra(EXTRA_CURRENT_EXERCISE_POSITION, 0);
        if(listExerciseItem == null || listExerciseItem.isEmpty() || dayItem == null || courseItem == null){
            finish();
            return;
        }
        viewModel.initData(listExerciseItem, dayItem, courseItem, currentExerciseId);
    }

    private void setPrepareLayout(ExerciseItem item) {
        binding.layoutPrepare.setVisibility(View.VISIBLE);
        binding.layoutExercise.setVisibility(View.GONE);
        binding.mediaPlayer.load(item.getAnimationUrl());
        binding.tvExerciseName.setText(item.getName());
        long prepareDuration = AppConfig.getExercisePrepareDuration();
        prepareRunnable = () -> {
            if(viewModel.getTimeCounter() < prepareDuration){
                if(!inBackground){
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + DEFAULT_DELAY_MILLIS);
                    if((viewModel.getTimeCounter() / DEFAULT_DELAY_MILLIS) % (1000 / DEFAULT_DELAY_MILLIS) == 0){
                        binding.tvProgress.setText(String.valueOf((viewModel.getTimeCounter()/1000)));
                    }
                    binding.circleView.setValue((float) (viewModel.getTimeCounter() * 100) / prepareDuration);
                    onShowTickSound();
                }else{
                    onPauseSound();
                }
                handler.postDelayed(prepareRunnable, DEFAULT_DELAY_MILLIS);
            }else{
                movePrepareToExercise();
            }
        };
        handler.postDelayed(prepareRunnable, DEFAULT_DELAY_MILLIS);
    }

    private void setExerciseLayout(ExerciseItem item){
        binding.layoutPrepare.setVisibility(View.GONE);
        binding.layoutExercise.setVisibility(View.VISIBLE);
        binding.tvLoopDuration.setText(item.getLoopOrDuration());
        binding.tvExerciseName2.setText(item.getName());
        binding.mediaPlayer.load(item.getAnimationUrl());
        if(item.getLoopNumber() != 0){
            binding.btnCompleted.setVisibility(View.VISIBLE);
            binding.btnProgress.setVisibility(View.GONE);
        }else{
            binding.btnCompleted.setVisibility(View.GONE);
            binding.btnProgress.setVisibility(View.VISIBLE);
            long totalDuration = item.getTime();

            exerciseRunnable = () -> {
                if(viewModel.getTimeCounter() < totalDuration) {
                    if (!inBackground && viewModel.isPlayExercise()) {
                        viewModel.updateTimeCounter(viewModel.getTimeCounter() + DEFAULT_DELAY_MILLIS);
                        binding.sbExercise.setProgress((int) (viewModel.getTimeCounter() * 100 / totalDuration));
                        onShowTickSound();
                    }else{
                        onPauseSound();
                    }
                    handler.postDelayed(exerciseRunnable, DEFAULT_DELAY_MILLIS);
                }else{
                    moveExerciseToRest();
                }
            };
            handler.postDelayed(exerciseRunnable, DEFAULT_DELAY_MILLIS);
        }
    }

    private void setRestLayout(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        ExerciseRestFragment restFragment = new ExerciseRestFragment();

        transaction.replace(binding.mainLayout.getId(), restFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void moveExerciseToRest(){
        handler.removeCallbacks(exerciseRunnable);
        onStopSound();
        viewModel.moveExerciseToRest(this);
    }

    private void movePrepareToExercise(){
        handler.removeCallbacks(prepareRunnable);
        onStopSound();
        viewModel.movePrepareToExercise();
    }

    @Override
    protected void initListener() {
        binding.btnBack.setOnClickListener(this);
        binding.btnInfo.setOnClickListener(this);
        binding.btnInfo2.setOnClickListener(this);
        binding.btnSkipPrepare.setOnClickListener(this);
        binding.btnNext.setOnClickListener(this);
        binding.btnPrevious.setOnClickListener(this);
        binding.viewProgress.setOnClickListener(this);
        binding.tvExerciseName.setOnClickListener(this);
        binding.tvExerciseName2.setOnClickListener(this);
        binding.btnSetting.setOnClickListener(this);
        binding.btnMusic.setOnClickListener(this);
        binding.btnCompleted.setOnClickListener(this);
        binding.btnRotate.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnBack){
            finish();
        }else if(v == binding.btnInfo || v == binding.btnInfo2 || v == binding.tvExerciseName || v == binding.tvExerciseName2){
            ExerciseBottomDialog exerciseBottomDialog = ExerciseBottomDialog.newInstance(viewModel.getListExerciseItem(), viewModel.getExercisePosition());
            exerciseBottomDialog.show(getSupportFragmentManager(), exerciseBottomDialog.getTag());
        }else if(v == binding.btnSkipPrepare){
            movePrepareToExercise();
        }else if(v == binding.btnNext || v == binding.btnCompleted){
            moveExerciseToRest();
        }else if(v == binding.btnPrevious) {
            if (viewModel.getExercisePosition() > 0) {
                handler.removeCallbacks(exerciseRunnable);
                viewModel.movePreviousExercise();
            }else{
                finish();
            }
        }
        else if(v == binding.viewProgress){
            viewModel.setPlayExercise(!viewModel.isPlayExercise());
        }else if(v == binding.btnSetting){
            Intent intent = new Intent(this, WorkoutSettingsActivity.class);
            startActivityForResult(intent, SETTING_REQUEST_CODE);
        } else if (v == binding.btnMusic) {
            int musicId = Hawk.get(HawkKeys.MUSIC_ID_KEY, viewModel.getMusicItems().get(0).getId());
            MusicBottomDialog musicBottomDialog = new MusicBottomDialog(viewModel.getMusicItems(), viewModel.findMusicItemPosition(musicId));
            musicBottomDialog.setMusicSelectionListener(music -> {
                Hawk.put(HawkKeys.MUSIC_ID_KEY, music.getId());
                MusicItem musicItem = viewModel.finMusicItem(music.getId());
                if(musicItem !=null && musicBound){
                    float volume = AppConfig.getBackgroundMusicVolume();
                    musicService.initMusic(musicItem.getAudio(), volume, true);
                    if(AppConfig.isPlayBackgroundMusic()) musicService.playMusic();
                }
                Toast.makeText(ExerciseActivity.this,
                        getString(R.string.selected_music, ExerciseActivity.this.getString(music.getName())), Toast.LENGTH_SHORT).show();
            });
            musicBottomDialog.show(getSupportFragmentManager(), MusicBottomDialog.class.getName());
        }else if(v == binding.btnRotate){
            int currentOrientation = getResources().getConfiguration().orientation;

            if (currentOrientation == Configuration.ORIENTATION_PORTRAIT) {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE);
            } else {
                setRequestedOrientation(ActivityInfo.SCREEN_ORIENTATION_PORTRAIT);
            }
        }
    }

    private void playExercise(){
        if(musicBound && AppConfig.isPlayBackgroundMusic()) musicService.playMusic();
        binding.ivPlayPause.setImageResource(R.drawable.ic_pause);
        binding.mediaPlayer.onStart();
    }

    private void pauseExercise(){
        if(musicBound) musicService.pauseMusic();
        binding.ivPlayPause.setImageResource(R.drawable.ic_play);
        binding.mediaPlayer.onPause();
    }

    @Override
    protected void onStart() {
        super.onStart();
        inBackground = false;
        if (!musicBound) {
            Intent intent = new Intent(this, MusicService.class);
            bindService(intent, musicConnection, Context.BIND_AUTO_CREATE);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        inBackground = true;
        SpeechUtils.stop();
        onPauseSound();
    }

    @Override
    protected void onDestroy() {
        binding.mediaPlayer.onDestroy();
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        viewModel.saveStopTime();
        if (musicBound) {
            unbindService(musicConnection);
            musicBound = false;
        }
        onStopSound();
    }

    @SuppressLint("MissingSuperCall")
    @Override
    public void onBackPressed() {
        finish();
    }

    @Override
    public void onCompleteExercise(ExerciseItem exerciseItem) {

    }

    @Override
    public void onCompleteDay() {
        Intent intent = new Intent(this, ExerciseCompleteActivity.class);
        intent.putExtra(EXTRA_DAY, viewModel.getDayItem());
        intent.putExtra(EXTRA_COURSE, viewModel.getCourseItem());
        intent.putExtra(EXTRA_EXERCISE_LIST, new ArrayList<>(viewModel.getListExerciseItem()));
        startActivity(intent);
        SpeechUtils.speak(getString(R.string.completing_today_exercises));
        finish();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode == SETTING_REQUEST_CODE){
            if(AppConfig.isPlayBackgroundMusic()){
                updateAndPlayMusic();
            }else{
                if(musicBound) musicService.stopMusic();
            }
        }
    }

    private void updateAndPlayMusic(){
        int musicId = Hawk.get(HawkKeys.MUSIC_ID_KEY, viewModel.getMusicItems().get(0).getId());
        MusicItem musicItem = viewModel.finMusicItem(musicId);
        if(musicBound && musicItem != null && viewModel.isPlayExercise()){
            float volume = AppConfig.getBackgroundMusicVolume();
            musicService.initMusic(musicItem.getAudio(), volume,true);
            if(AppConfig.isPlayBackgroundMusic()) musicService.playMusic();
        }
    }

    @Override
    public void onShowTickSound() {
        if(!AppConfig.isCountdownSoundEnabled()) return;
        if(effectMediaPlayer != null && !effectMediaPlayer.isPlaying()){
            effectMediaPlayer.start();
        }else if(effectMediaPlayer == null){
            effectMediaPlayer = MediaPlayer.create(this, R.raw.tick_coundown_sound);
            effectMediaPlayer.setVolume(0.5f, 0.5f);
            effectMediaPlayer.setLooping(true);
            effectMediaPlayer.start();
        }
    }

    @Override
    public void onPauseSound() {
        if(effectMediaPlayer != null && effectMediaPlayer.isPlaying()){
            effectMediaPlayer.pause();
        }
    }

    @Override
    public void onStopSound() {
        if(effectMediaPlayer != null){
            effectMediaPlayer.stop();
            effectMediaPlayer.release();
            effectMediaPlayer = null;
        }
    }
}