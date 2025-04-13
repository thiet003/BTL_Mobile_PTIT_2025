package com.exercise.app30day.features.complete;

import android.annotation.SuppressLint;
import android.content.res.ColorStateList;
import android.graphics.Color;
import android.os.Build;
import android.view.View;

import androidx.core.view.ViewCompat;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearSnapHelper;
import androidx.recyclerview.widget.RecyclerView;
import androidx.recyclerview.widget.SnapHelper;

import com.cncoderx.wheelview.OnWheelChangedListener;
import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityExerciseCompleteBinding;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.TimeUtils;

import java.util.ArrayList;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;
import me.sujanpoudel.wheelview.WheelView;
import travel.ithaka.android.horizontalpickerlib.PickerLayoutManager;

@AndroidEntryPoint
public class ExerciseCompleteActivity extends BaseActivity<ActivityExerciseCompleteBinding, ExerciseCompleteViewModel> implements View.OnClickListener{

    DayItem dayItem;

    CourseItem courseItem;

    List<ExerciseItem> listExerciseItem;

    WheelView genderWheelView;

    WeightPickerAdapter weightPickerAdapter;

    RecyclerView weightRecyclerBtn;

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
        binding.layoutCalo.tvValue.setText(String.valueOf(listExerciseItem.size()));
        binding.tvTime.setText(TimeUtils.convertMillisToDate(System.currentTimeMillis()));

        genderWheelView = binding.genderWheelView;

        genderWheelView.setTitles(viewModel.getGenders());
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            genderWheelView.setFocusedByDefault(true);
        }
        genderWheelView.setSelected(true);
        genderWheelView.setFocusedIndex(viewModel.getGenderFocusedIndex());


        weightRecyclerBtn = binding.weightRecyclerBtn;
        SnapHelper snapHelper = new LinearSnapHelper();
        snapHelper.attachToRecyclerView(weightRecyclerBtn);
        weightPickerAdapter = new WeightPickerAdapter();
        weightPickerAdapter.setData(viewModel.getWeights());
        weightRecyclerBtn.setLayoutManager(getPickerLayoutManager());
        weightRecyclerBtn.setAdapter(weightPickerAdapter);

        binding.heightWheel.setEntries(viewModel.getHeights());
        binding.heightWheel.setCurrentIndex(viewModel.getHeightPickerIndex());

        viewModel.onBmiResult.observe(this, bmiResult -> {
            binding.tvBmi.setText(String.format("%.1f", bmiResult.getBmi()));
            int color = Color.parseColor(bmiResult.getColorHex());
            binding.bmiColor.setBackgroundTintList(ColorStateList.valueOf(color));
            binding.healthStatus.setText(bmiResult.getHealthStatus());
        });
    }

    @Override
    protected void initListener() {
        genderWheelView.setSelectListener(integer -> {
            viewModel.setGenderFocusedIndex(integer);
            return null;
        });
        binding.heightWheel.setOnWheelChangedListener((view, oldIndex, newIndex) -> {
            viewModel.setHeightPickerIndex(newIndex);
        });
        binding.btnUp.setOnClickListener(this);
        binding.btnDown.setOnClickListener(this);
    }

    private PickerLayoutManager getPickerLayoutManager() {
        PickerLayoutManager pickerLayoutManager = new PickerLayoutManager(this, PickerLayoutManager.HORIZONTAL, false);
        pickerLayoutManager.setChangeAlpha(true);
        pickerLayoutManager.setScaleDownBy(0.99f);
        pickerLayoutManager.setScaleDownDistance(0.8f);
        pickerLayoutManager.setInitialPrefetchItemCount(3);
        pickerLayoutManager.setSmoothScrollbarEnabled(true);
        pickerLayoutManager.scrollToPosition(viewModel.getWeightPickerIndex());
        pickerLayoutManager.setOnScrollStopListener(view -> {
            int position = weightRecyclerBtn.getChildAdapterPosition(view);
            viewModel.setWeightPickerIndex(position);
        });
        return pickerLayoutManager;
    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnUp){
            binding.heightWheel.setCurrentIndex(binding.heightWheel.getCurrentIndex() - 1);
        }else if(v == binding.btnDown){
            binding.heightWheel.setCurrentIndex(binding.heightWheel.getCurrentIndex() + 1);
        }
    }
}