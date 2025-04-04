package com.exercise.app30day.features.complete;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.NoneViewModel;
import com.exercise.app30day.databinding.ActivityExerciseCompleteBinding;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.keys.IntentKeys;
import com.exercise.app30day.utils.TimeUtils;
import com.kevalpatel2106.rulerpicker.RulerValuePickerListener;

import java.util.ArrayList;
import java.util.List;

public class ExerciseCompleteActivity extends BaseActivity<ActivityExerciseCompleteBinding, NoneViewModel>{

    DayItem dayItem;

    CourseItem courseItem;

    List<ExerciseItem> listExerciseItem;

    @Override
    protected void initView() {
        courseItem = (CourseItem) getIntent().getSerializableExtra(IntentKeys.EXTRA_COURSE);
        dayItem = (DayItem) getIntent().getSerializableExtra(IntentKeys.EXTRA_DAY);
        listExerciseItem = (ArrayList<ExerciseItem>) getIntent().getSerializableExtra(IntentKeys.EXTRA_EXERCISE_LIST);
        if(courseItem == null || dayItem == null || listExerciseItem == null){
            finish();
            return;
        }
        binding.tvCourseDay.setText(getString(R.string.course_day,dayItem.getDay(), courseItem.getName()));
        binding.layoutCalo.tvLabel.setText(R.string.calo);
        binding.layoutTime.tvLabel.setText(R.string.time);
        binding.layoutExercise.tvLabel.setText(R.string.exercises);
        binding.layoutCalo.tvValue.setText(String.valueOf(listExerciseItem.size()));
        binding.tvTime.setText(TimeUtils.convertMillisToDate(System.currentTimeMillis()));
    }

    @Override
    protected void initListener() {
        binding.rulerWeight.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                binding.tvWeight.setText(String.valueOf(selectedValue));
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {

            }
        });

        binding.rulerHeight.setValuePickerListener(new RulerValuePickerListener() {
            @Override
            public void onValueChange(int selectedValue) {
                binding.tvHeight.setText(String.valueOf(selectedValue));
            }

            @Override
            public void onIntermediateValueChange(int selectedValue) {

            }
        });

    }
}