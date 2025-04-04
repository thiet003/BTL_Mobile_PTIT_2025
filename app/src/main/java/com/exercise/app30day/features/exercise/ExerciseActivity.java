package com.exercise.app30day.features.exercise;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.ActivityExerciseBinding;
import com.exercise.app30day.features.complete.ExerciseCompleteActivity;
import com.exercise.app30day.features.dialog.ExerciseBottomDialog;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.keys.IntentKeys;
import com.exercise.app30day.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExerciseActivity extends BaseActivity<ActivityExerciseBinding, ExerciseViewModel> implements View.OnClickListener, OnCompleteListener{
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable prepareRunnable, exerciseRunnable;
    private boolean inBackground = false;
    private final long delayMillis = 100;

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
        List<ExerciseItem> listExerciseItem = (ArrayList<ExerciseItem>) getIntent().getSerializableExtra(IntentKeys.EXTRA_EXERCISE_LIST);
        DayItem dayItem = (DayItem) getIntent().getSerializableExtra(IntentKeys.EXTRA_DAY);
        CourseItem courseItem = (CourseItem) getIntent().getSerializableExtra(IntentKeys.EXTRA_COURSE);
        if(listExerciseItem == null || listExerciseItem.isEmpty() || dayItem == null || courseItem == null){
            finish();
            return;
        }
        viewModel.setListExerciseItem(listExerciseItem);
        viewModel.setDayItem(dayItem);
        viewModel.setCourseItem(courseItem);
    }

    private void setPrepareLayout(ExerciseItem item) {
        binding.layoutPrepare.setVisibility(View.VISIBLE);
        binding.layoutExercise.setVisibility(View.GONE);
        int resId = ResourceUtils.getDrawableId(this, item.getAnimationFileName());
        if(item.getAnimationFileType().equals("gif")){
            Glide.with(this).asGif().load(resId).into(binding.ivAnimation);
        }else{
            Glide.with(this).load(resId).into(binding.ivAnimation);
        }
        binding.tvExerciseName.setText(item.getName());
        long prepareDuration = AppConfig.getExercisePrepareDuration();
        prepareRunnable = () -> {
            if(viewModel.getTimeCounter() < prepareDuration){
                if(!inBackground){
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + delayMillis);
                    if((viewModel.getTimeCounter() / delayMillis) % (1000 / delayMillis) == 0){
                        binding.tvProgress.setText(String.valueOf((viewModel.getTimeCounter()/1000)));
                    }
                    binding.circleView.setValue((float) (viewModel.getTimeCounter() * 100) / prepareDuration);
                }
                handler.postDelayed(prepareRunnable, delayMillis);
            }else{
                movePrepareToExercise();
            }
        };
        handler.postDelayed(prepareRunnable, delayMillis);
    }

    private void setExerciseLayout(ExerciseItem item){
        binding.layoutPrepare.setVisibility(View.GONE);
        binding.layoutExercise.setVisibility(View.VISIBLE);
        binding.tvLoopDuration.setText(viewModel.getLoopOrDuration(item));
        binding.tvExerciseName2.setText(item.getName());

        int resId = ResourceUtils.getDrawableId(this, item.getAnimationFileName());
        if(item.getAnimationFileType().equals("gif")) {
            Glide.with(this).asGif().load(resId).into(binding.ivAnimation);
        }else {
            Glide.with(this).load(resId).into(binding.ivAnimation);
        }

        long totalDuration = viewModel.calculateDuration(item);

        exerciseRunnable = () -> {
            if(viewModel.getTimeCounter() < totalDuration) {
                if (!inBackground && viewModel.isPlayExercise()) {
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + delayMillis);
                    binding.sbExercise.setProgress((int) (viewModel.getTimeCounter() * 100 / totalDuration));
                }
                handler.postDelayed(exerciseRunnable, delayMillis);
            }else{
                moveExerciseToRest();
            }
        };
        handler.postDelayed(exerciseRunnable, delayMillis);

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
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnBack){
            finish();
        }else if(v == binding.btnInfo || v == binding.btnInfo2){
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
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
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
        intent.putExtra(IntentKeys.EXTRA_DAY, viewModel.getDayItem());
        intent.putExtra(IntentKeys.EXTRA_COURSE, viewModel.getCourseItem());
        intent.putExtra(IntentKeys.EXTRA_EXERCISE_LIST, new ArrayList<>(viewModel.getListExerciseItem()));
        startActivity(intent);
        finish();
    }
}