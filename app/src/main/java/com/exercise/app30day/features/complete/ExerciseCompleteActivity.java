package com.exercise.app30day.features.complete;

import static com.exercise.app30day.config.AppConfig.MAX_HEIGHT;
import static com.exercise.app30day.config.AppConfig.MAX_WEIGHT;
import static com.exercise.app30day.config.AppConfig.MIN_WEIGHT;

import android.annotation.SuppressLint;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.content.res.ColorStateList;
import android.os.Build;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityExerciseCompleteBinding;
import com.exercise.app30day.features.course.CourseActivity;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.TimeUtils;
import com.shawnlin.numberpicker.NumberPicker;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import me.sujanpoudel.wheelview.WheelView;

@AndroidEntryPoint
public class ExerciseCompleteActivity extends BaseActivity<ActivityExerciseCompleteBinding, ExerciseCompleteViewModel>
        implements View.OnClickListener, NumberPicker.OnValueChangeListener{

    DayItem dayItem;

    CourseItem courseItem;

    List<ExerciseItem> listExerciseItem;

    WheelView genderWheelView;

    NumberPicker weightPicker, heightPicker;


    @SuppressLint("DefaultLocale")
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
        binding.layoutExercise.tvValue.setText(String.valueOf(listExerciseItem.size()));
        viewModel.getDayHistoryItems(dayItem.getId()).observe(this, dayHistoryItems -> {
            binding.layoutTime.tvValue.setText(viewModel.calculateAndFormatTotalTime(dayHistoryItems));
            binding.layoutCalo.tvValue.setText(String.valueOf(viewModel.calculateAndFormatCalories(listExerciseItem, dayHistoryItems)));
        });

        genderWheelView = binding.genderWheelView;

        genderWheelView.setTitles(viewModel.getGenders());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            genderWheelView.setFocusedByDefault(true);
        }
        genderWheelView.setSelected(true);
        genderWheelView.setFocusedIndex(viewModel.getUserGenderIndex());


        weightPicker = binding.weightPicker;
        heightPicker = binding.heightPicker;

        weightPicker.setMaxValue(MAX_WEIGHT);
        weightPicker.setMinValue(MIN_WEIGHT);
        weightPicker.setValue(viewModel.getUserWeight());

        heightPicker.setMaxValue(MAX_HEIGHT);
        heightPicker.setMinValue(MIN_WEIGHT);
        heightPicker.setValue(viewModel.getUserHeight());

        viewModel.onUserUiState.observe(this, userUiState -> {
            binding.tvTime.setText(TimeUtils.formatDate(userUiState.getCalendar()));
            BmiResult bmiResult = viewModel.calculateBmi(userUiState);
            binding.tvBmi.setText(String.format("%.1f", bmiResult.getBmi()));
            int color = ContextCompat.getColor(this, bmiResult.getColor());
            binding.bmiColor.setBackgroundTintList(ColorStateList.valueOf(color));
            binding.healthStatus.setText(bmiResult.getHealthStatus());
        });
    }

    @Override
    protected void initListener() {
        genderWheelView.setSelectListener(integer -> {
            viewModel.setUserGenderIndex(integer);
            return null;
        });
        heightPicker.setOnValueChangedListener(this);
        weightPicker.setOnValueChangedListener(this);
        binding.btnUp.setOnClickListener(this);
        binding.btnDown.setOnClickListener(this);
        binding.tvTime.setOnClickListener(this);
        binding.btnSkip.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnUp){
            int newValue = heightPicker.getValue() - 1;
            heightPicker.setValue(newValue);
            viewModel.setUserHeight(newValue);
        }else if(v == binding.btnDown){
            int newValue = heightPicker.getValue() + 1;
            heightPicker.setValue(newValue);
            viewModel.setUserHeight(newValue);
        }else if(v == binding.tvTime){
            DatePickerDialog datePickerDialog = getDatePickerDialog();
            datePickerDialog.show();
        }else if(v == binding.btnSkip){
            goBackToCourseActivity();
        }
    }

    @NonNull
    private DatePickerDialog getDatePickerDialog() {
        Calendar calendar = viewModel.getCalendar();
        int year = calendar.get(Calendar.YEAR);
        int month = calendar.get(Calendar.MONTH);
        int day = calendar.get(Calendar.DAY_OF_MONTH);

        return new DatePickerDialog(this,
                (view, selectedYear, selectedMonth, selectedDay) -> {
                    Calendar selectedDate = Calendar.getInstance();
                    selectedDate.set(selectedYear, selectedMonth, selectedDay);
                    viewModel.setCalendar(selectedDate);
                }, year, month, day);
    }

    @Override
    public void onValueChange(NumberPicker picker, int oldVal, int newVal) {
        if(picker == weightPicker) {
            viewModel.setUserWeight(newVal);
        }
    }

    private void goBackToCourseActivity(){
        Intent intent = new Intent(ExerciseCompleteActivity.this, CourseActivity.class);
        intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(intent);
    }
}