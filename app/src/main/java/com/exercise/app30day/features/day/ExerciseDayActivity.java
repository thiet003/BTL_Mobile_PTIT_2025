package com.exercise.app30day.features.day;

import android.animation.ArgbEvaluator;
import android.animation.ValueAnimator;
import android.graphics.Color;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityExerciseDayBinding;
import com.exercise.app30day.items.CourseDayExerciseItem;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.ResourceUtils;
import com.google.android.material.appbar.AppBarLayout;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ExerciseDayActivity extends BaseActivity<ActivityExerciseDayBinding, ExerciseDayViewModel> implements View.OnClickListener{


    ExerciseDayAdapter exerciseDayAdapter;

    boolean isTopBarHidden = false;
    @Override
    protected void initView() {

        int courseId = getIntent().getIntExtra(IntentKeys.EXTRA_COURSE_ID,1);
        viewModel.getCourseItemById(courseId).observe(this, courseItem -> {
            int imgRes = ResourceUtils.getDrawableId(ExerciseDayActivity.this, "img_course_" + courseItem.getDifficultLevel().toLowerCase());
            binding.imgCourse.setImageResource(imgRes);
            binding.tvName.setText(courseItem.getName());
            binding.rbCourse.setRating(viewModel.getLevel(courseItem.getDifficultLevel()));
            binding.tvLevel.setText(courseItem.getDifficultLevel());
            int daysRemain = viewModel.calculateDaysRemain(courseItem.getNumberOfCompletedDays(), courseItem.getNumberOfDays());
            binding.tvRemain.setText(getString(R.string.days_remain, daysRemain));
            binding.tvTopBarCourseName.setText(courseItem.getName());
        });

        exerciseDayAdapter = new ExerciseDayAdapter(viewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvDay.setLayoutManager(linearLayoutManager);
        binding.rvDay.setAdapter(exerciseDayAdapter);

        viewModel.getListCourseDayExercise(courseId).observe(this, courseDayExerciseItems -> {
            exerciseDayAdapter.setData(courseDayExerciseItems);
        });
    }

    @Override
    protected void initListener() {
        binding.ibBack.setOnClickListener(this);
        binding.ibBackTempTopBar.setOnClickListener(this);
        binding.nestScrollView.setOnScrollChangeListener((NestedScrollView.OnScrollChangeListener) (v, scrollX, scrollY, oldScrollX, oldScrollY) -> {
            int topBarHeight = binding.topBar.getHeight();
            int animStartY = topBarHeight - (int)(topBarHeight / 1.5);
            if (scrollY > oldScrollY && scrollY > animStartY && !isTopBarHidden) {
                isTopBarHidden = true;
                fadeOut(binding.topBar, 1200);
                fadeIn(binding.tempTopBar, 800);
            } else if (scrollY < oldScrollY && scrollY <= animStartY && isTopBarHidden) {
                isTopBarHidden = false;
                fadeIn(binding.topBar, 1200);
                fadeOut(binding.tempTopBar, 800);
            }
        });
    }

    private void fadeIn(View view, long duration) {
        view.setVisibility(View.VISIBLE);
        AlphaAnimation alphaAnimation = new AlphaAnimation(0f, 1f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        view.startAnimation(alphaAnimation);
    }

    private void fadeOut(View view, long duration){
        AlphaAnimation alphaAnimation = new AlphaAnimation(1f, 0f);
        alphaAnimation.setDuration(duration);
        alphaAnimation.setFillAfter(true);
        alphaAnimation.setAnimationListener(new Animation.AnimationListener() {
            @Override
            public void onAnimationStart(Animation animation) {

            }

            @Override
            public void onAnimationEnd(Animation animation) {
                view.setVisibility(View.INVISIBLE);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(alphaAnimation);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.ibBack || v == binding.ibBackTempTopBar){
            finish();
        }
    }
}