package com.exercise.app30day.features.exercise;

import android.os.Handler;
import android.os.Looper;
import android.view.View;

import com.bumptech.glide.Glide;
import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.FragmentExerciseRestBinding;
import com.exercise.app30day.features.dialog.ExerciseBottomDialog;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.ResourceUtils;
import com.exercise.app30day.utils.TimeUtils;

import java.util.List;


public class ExerciseRestFragment extends BaseFragment<FragmentExerciseRestBinding, NoneViewModel> implements View.OnClickListener {

    private final ExerciseViewModel viewModel;

    private final Handler handler = new Handler(Looper.getMainLooper());

    private Runnable restRunnable;

    private final long delayTime = 100;

    private long restTime = 30000;

    private boolean inBackground = false;

    public ExerciseRestFragment(ExerciseViewModel viewModel) {
        this.viewModel = viewModel;
    }

    @Override
    protected void initView() {
        restRunnable = () -> {
            if(viewModel.getTimeCounter() < restTime){
                if(!inBackground) {
                    viewModel.updateTimeCounter(viewModel.getTimeCounter() + delayTime);
                    if((viewModel.getTimeCounter() / delayTime) % (1000 / delayTime) == 0){
                        long remainTime = restTime - viewModel.getTimeCounter();
                        binding.tvRestingTime.setText(TimeUtils.formatMillisecondsToMMSS(remainTime));
                    }
                }
                handler.postDelayed(restRunnable, delayTime);
            }else{
                moveRestToExercise();
            }

        };
        viewModel.onExerciseUiState.observe(getViewLifecycleOwner(), exerciseUiState -> {
            int exercisePosition = exerciseUiState.getExercisePosition();
            List<ExerciseItem> listExerciseItem = exerciseUiState.getListExerciseItem();
            ExerciseItem item = listExerciseItem.get(exercisePosition);
            binding.stepSeekBar.setNumSteps(exerciseUiState.getListExerciseItem().size());
            binding.stepSeekBar.setProgress(exerciseUiState.getExercisePosition() + 1);
            int resId = ResourceUtils.getDrawableId(requireContext(), item.getAnimationFileName());
            if(item.getAnimationFileType().equals("gif")){
                Glide.with(this).asGif().load(resId).into(binding.ivAnimation);
            }else{
                Glide.with(this).load(resId).into(binding.ivAnimation);
            }
            binding.tvPage.setText(requireContext().getString(R.string.page, exercisePosition + 1, listExerciseItem.size()));
            binding.tvExerciseName.setText(item.getName());
            binding.tvLoopDuration.setText(viewModel.getLoopOrDuration(item));
            handler.postDelayed(restRunnable, delayTime);
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
            ExerciseBottomDialog dialog = new ExerciseBottomDialog(viewModel.getListExerciseItem(), viewModel.getExercisePosition());
            dialog.show(requireActivity().getSupportFragmentManager(), dialog.getTag());
        }else if(v == binding.btnAddTime){
            restTime += 30000;
        }else if(v == binding.btnSkip){
            moveRestToExercise();
        }
    }

    private void moveRestToExercise() {
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
}