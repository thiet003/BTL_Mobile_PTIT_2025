package com.exercise.app30day.features.day;

import android.content.Intent;
import android.view.View;

import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityDayBinding;
import com.exercise.app30day.features.exercise.ExerciseActivity;
import com.exercise.app30day.features.exercise.dialog.ExerciseBottomDialog;
import com.exercise.app30day.features.setting.workout.WorkoutSettingsActivity;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.utils.IntentKeys;

import java.util.ArrayList;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class DayActivity extends BaseActivity<ActivityDayBinding, DayViewModel> implements View.OnClickListener {

    ExerciseAdapter exerciseAdapter;

    private DayItem dayItem;

    private CourseItem courseItem;

    private int currentExercisePosition;

    @Override
    protected void initView() {
        courseItem = (CourseItem) getIntent().getSerializableExtra(IntentKeys.EXTRA_COURSE);
        dayItem = (DayItem) getIntent().getSerializableExtra(IntentKeys.EXTRA_DAY);
        if(dayItem == null || courseItem == null){
            finish();
            return;
        }
        binding.tvDay.setText(getString(R.string.day, dayItem.getDay()));
        binding.tvDifficult.setText(courseItem.getType());
        binding.itemExercise.tvLabel.setText(R.string.exercises);
        binding.itemCalo.tvLabel.setText(R.string.calo);
        binding.itemTime.tvLabel.setText(R.string.time);

        exerciseAdapter = new ExerciseAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvExercise.setLayoutManager(linearLayoutManager);
        binding.rvExercise.setAdapter(exerciseAdapter);
        viewModel.getExerciseItems(dayItem.getId()).observe(this, exerciseItems -> {
            binding.itemExercise.tvValue.setText(String.valueOf(exerciseItems.size()));
            binding.itemTime.tvValue.setText(getString(R.string.minute_number, viewModel.calculateMinutes(exerciseItems)));
            binding.itemCalo.tvValue.setText(getString(R.string.calo_number, viewModel.calculateAndFormatCalories(exerciseItems)));
            exerciseAdapter.setData(exerciseItems);
        });

        viewModel.getCurrentExercisePosition(dayItem.getId()).observe(this, integer -> {
            currentExercisePosition = integer;
            binding.btnStart.setOnClickListener(DayActivity.this);
            binding.btnStart.setText(currentExercisePosition == 0 ? R.string.start : R.string.text_continue);
        });

        exerciseAdapter.setOnItemClickListener((data, position) -> {
            ExerciseBottomDialog exerciseBottomDialog = ExerciseBottomDialog.newInstance(exerciseAdapter.getDataList(), position);
            exerciseBottomDialog.show(getSupportFragmentManager(), exerciseBottomDialog.getTag());
        });

    }

    @Override
    protected void initListener() {
        binding.ibBack.setOnClickListener(this);
        binding.ibSetting.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.ibBack){
            finish();
        }else if(v == binding.btnStart){
            gotoExercise();
        }else if(v == binding.ibSetting){
            startActivity(new Intent(this, WorkoutSettingsActivity.class));
        }
    }

    private void gotoExercise(){
        Intent intent = new Intent(DayActivity.this, ExerciseActivity.class);
        intent.putExtra(IntentKeys.EXTRA_EXERCISE_LIST, new ArrayList<>(exerciseAdapter.getDataList()));
        intent.putExtra(IntentKeys.EXTRA_DAY, dayItem);
        intent.putExtra(IntentKeys.EXTRA_COURSE, courseItem);
        intent.putExtra(IntentKeys.EXTRA_CURRENT_EXERCISE_POSITION, currentExercisePosition);
        DayActivity.this.startActivity(intent);
    }
}