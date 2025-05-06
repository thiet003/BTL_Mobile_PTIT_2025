package com.exercise.app30day.features.exercise;

import static com.exercise.app30day.config.AppConfig.DEFAULT_DELAY_MILLIS;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import androidx.lifecycle.ViewModelProvider;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.config.AppConfig;
import com.exercise.app30day.databinding.FragmentExerciseRestBinding;
import com.exercise.app30day.features.exercise.dialog.ExerciseBottomDialog;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.TimeUtils;

import java.util.List;


public class ExerciseRestFragment extends BaseFragment<FragmentExerciseRestBinding, NoneViewModel> implements View.OnClickListener {

    private ExerciseViewModel viewModel;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private Runnable restRunnable;

    private boolean inBackground = false;

    private long restDuration = AppConfig.getExerciseRestDuration();

    @Override
    protected void initView() {
        viewModel = new ViewModelProvider(requireActivity()).get(ExerciseViewModel.class);
        restRunnable = () -> {
            if(viewModel.getTimeCounter() < restDuration){
                if(!inBackground) {
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + DEFAULT_DELAY_MILLIS);
                    if((viewModel.getTimeCounter() / DEFAULT_DELAY_MILLIS) % (1000 / DEFAULT_DELAY_MILLIS) == 0){
                        long remainTime = restDuration - viewModel.getTimeCounter();
                        binding.tvRestingTime.setText(TimeUtils.formatMillisecondsToMMSS(remainTime));
                    }
                }
                handler.postDelayed(restRunnable, DEFAULT_DELAY_MILLIS);
            }else{
                moveRestToExercise();
            }

        };
        viewModel.onExerciseUiState.observe(getViewLifecycleOwner(), exerciseUiState -> {
            int exercisePosition = exerciseUiState.getExercisePosition();
            List<ExerciseItem> listExerciseItem = viewModel.getListExerciseItem();
            ExerciseItem item = listExerciseItem.get(exercisePosition);
            binding.stepSeekBar.setNumSteps(listExerciseItem.size());
            binding.stepSeekBar.setProgress(exerciseUiState.getExercisePosition() + 1);
            binding.mediaPlayer.load(item.getAnimationUrl());
            binding.tvPage.setText(requireContext().getString(R.string.page, exercisePosition + 1, listExerciseItem.size()));
            binding.tvExerciseName.setText(item.getName());
            binding.tvLoopDuration.setText(viewModel.getLoopOrDuration(item));
            handler.postDelayed(restRunnable, DEFAULT_DELAY_MILLIS);
        });
    }

    @Override
    protected void initListener() {
        binding.btnInfo.setOnClickListener(this);
        binding.btnAddTime.setOnClickListener(this);
        binding.btnSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnInfo){
            ExerciseBottomDialog dialog = ExerciseBottomDialog.newInstance(viewModel.getListExerciseItem(), viewModel.getExercisePosition());
            dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
        }else if(v == binding.btnAddTime){
            restDuration += 30000;
        }else if(v == binding.btnSkip){
            moveRestToExercise();
        }
    }

    private void moveRestToExercise() {
        viewModel.addRestTime(viewModel.getTimeCounter());
        handler.removeCallbacks(restRunnable);
        viewModel.moveRestToExercise();
        requireActivity().getSupportFragmentManager().popBackStack();
    }

    @Override
    public void onStart() {
        super.onStart();
        inBackground = false;
    }

    @Override
    public void onStop() {
        super.onStop();
        inBackground = true;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        handler.removeCallbacksAndMessages(null);
    }

    @Override
    public void onDestroyView() {
        binding.mediaPlayer.onDestroy();
        super.onDestroyView();
    }
}