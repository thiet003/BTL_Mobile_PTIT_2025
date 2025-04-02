package com.exercise.app30day.features.exercise;

import android.annotation.SuppressLint;
import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import com.bumptech.glide.Glide;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityExerciseBinding;
import com.exercise.app30day.features.dialog.ExerciseBottomDialog;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.ResourceUtils;

import java.util.ArrayList;
import java.util.List;

public class ExerciseActivity extends BaseActivity<ActivityExerciseBinding, ExerciseViewModel> implements View.OnClickListener{
    private final Handler handler = new Handler(Looper.getMainLooper());
    private Runnable prepareRunnable, exerciseRunnable;
    private boolean inBackground = false;

    private final long delayTime = 100;

    private final long prepareTime = 15000;


    @Override
    protected void initView() {
        List<ExerciseItem> listExerciseItem = (ArrayList<ExerciseItem>) getIntent().getSerializableExtra(IntentKeys.EXTRA_EXERCISE_LIST);
        if(listExerciseItem == null || listExerciseItem.isEmpty()){
            finish();
            return;
        }
        viewModel.updateListExerciseItem(listExerciseItem);
        viewModel.onExerciseUiState.observe(this, exerciseUiState -> {
            ExerciseItem exerciseItem = exerciseUiState.getListExerciseItem().get(exerciseUiState.getExercisePosition());
            binding.stepSeekBar.setNumSteps(exerciseUiState.getListExerciseItem().size());
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
        prepareRunnable = () -> {
            if(viewModel.getTimeCounter() < prepareTime){
                if(!inBackground){
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + delayTime);
                    if((viewModel.getTimeCounter() / delayTime) % (1000 / delayTime) == 0){
                        binding.tvProgress.setText(String.valueOf((viewModel.getTimeCounter()/1000)));
                    }
                    binding.circleView.setValue((float) (viewModel.getTimeCounter() * 100) / prepareTime);
                }
                handler.postDelayed(prepareRunnable, delayTime);
            }else{
                movePrepareToExercise();
            }
        };
        handler.postDelayed(prepareRunnable, delayTime);
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

        long totalTime = viewModel.calculateTime(item);

        exerciseRunnable = () -> {
            if(viewModel.getTimeCounter() < totalTime) {
                if (!inBackground) {
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + delayTime);
                    binding.sbExercise.setProgress((int) (viewModel.getTimeCounter() * 100 / totalTime));
                }
                handler.postDelayed(exerciseRunnable, delayTime);
            }else{
                moveExerciseToRest();
            }
        };
        handler.postDelayed(exerciseRunnable, delayTime);

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
        viewModel.moveExerciseToRest();
    }

    private void moveRestingToExercise(){

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
            moveExerciseToRest();
        }
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

    @Override
    public void onBackPressed() {
        finish();
    }
}