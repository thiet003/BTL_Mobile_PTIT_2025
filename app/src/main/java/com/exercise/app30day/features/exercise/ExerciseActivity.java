package com.exercise.app30day.features.exercise;

import static com.exercise.app30day.config.AppConfig.DEFAULT_DELAY_MILLIS;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_COURSE;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_CURRENT_EXERCISE_POSITION;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_DAY;
import static com.exercise.app30day.utils.IntentKeys.EXTRA_EXERCISE_LIST;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.ActivityExerciseBinding;
import com.exercise.app30day.features.complete.ExerciseCompleteActivity;
import com.exercise.app30day.features.exercise_dialog.ExerciseBottomDialog;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExerciseActivity extends BaseActivity<ActivityExerciseBinding, ExerciseViewModel> implements View.OnClickListener, OnCompleteListener{
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable prepareRunnable, exerciseRunnable;
    private boolean inBackground = false;

    @Override
    protected void initView() {
        initData();
        binding.stepSeekBar.setNumSteps(viewModel.getListExerciseItem().size());
        binding.stepSeekBar.setEnabled(false);
        viewModel.onExerciseUiState.observe(this, exerciseUiState -> {
            ExerciseItem exerciseItem = viewModel.getListExerciseItem().get(exerciseUiState.getExercisePosition());
            binding.stepSeekBar.setProgress(exerciseUiState.getExercisePosition() + 1);
            if(exerciseUiState.getExerciseState() == ExerciseState.PREPARE) {
                setPrepareLayout(exerciseItem);
            }else if(exerciseUiState.getExerciseState() == ExerciseState.EXERCISE) {
                setExerciseLayout(exerciseItem);
            }else if(exerciseUiState.getExerciseState() == ExerciseState.REST){
                setRestLayout();
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
        binding.mediaPlayer.display(item.getAnimationUrl());
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
        binding.tvLoopDuration.setText(viewModel.getLoopOrDuration(item));
        binding.tvExerciseName2.setText(item.getName());
        binding.mediaPlayer.display(item.getAnimationUrl());
        long totalDuration = viewModel.calculateDuration(item);

        exerciseRunnable = () -> {
            if(viewModel.getTimeCounter() < totalDuration) {
                if (!inBackground && viewModel.isPlayExercise()) {
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + DEFAULT_DELAY_MILLIS);
                    binding.sbExercise.setProgress((int) (viewModel.getTimeCounter() * 100 / totalDuration));
                }
                handler.postDelayed(exerciseRunnable, DEFAULT_DELAY_MILLIS);
            }else{
                moveExerciseToRest();
            }
        };
        handler.postDelayed(exerciseRunnable, DEFAULT_DELAY_MILLIS);

    }

    private void setRestLayout(){
        FragmentManager fragmentManager = getSupportFragmentManager();
        FragmentTransaction transaction = fragmentManager.beginTransaction();

        ExerciseRestFragment restFragment = new ExerciseRestFragment(viewModel);

        transaction.replace(binding.mainLayout.getId(), restFragment);
        transaction.addToBackStack(null);
        transaction.commit();
    }

    private void moveExerciseToRest(){
        handler.removeCallbacks(exerciseRunnable);
        viewModel.moveExerciseToRest(this);
    }

    private void movePrepareToExercise(){
        handler.removeCallbacks(prepareRunnable);
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
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnBack){
            finish();
        }else if(v == binding.btnInfo || v == binding.btnInfo2 || v == binding.tvExerciseName || v == binding.tvExerciseName2){
            ExerciseBottomDialog exerciseBottomDialog = new ExerciseBottomDialog(viewModel.getListExerciseItem(), viewModel.getExercisePosition());
            exerciseBottomDialog.show(getSupportFragmentManager(), exerciseBottomDialog.getTag());
        }else if(v == binding.btnSkipPrepare){
            movePrepareToExercise();
        }else if(v == binding.btnNext){
            playExercise();
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
            if(viewModel.isPlayExercise()){
                pauseExercise();
            }else{
               playExercise();
            }
        }
    }

    private void playExercise(){
        binding.ivPlayPause.setImageResource(R.drawable.ic_pause);
        viewModel.setPlayExercise(true);
    }

    private void pauseExercise(){
        binding.ivPlayPause.setImageResource(R.drawable.ic_play);
        viewModel.setPlayExercise(false);
    }

    @Override
    protected void onStart() {
        super.onStart();
        inBackground = false;
    }

    @Override
    protected void onStop() {
        super.onStop();
        inBackground = true;
    }

    @Override
    protected void onDestroy() {
        binding.mediaPlayer.onDestroy();
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
        viewModel.saveStopTime();
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
        finish();
    }
}