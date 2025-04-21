package com.exercise.app30day.features.course;

import android.content.Intent;
import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityCourseBinding;
import com.exercise.app30day.features.day.DayActivity;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.ResourceUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CourseActivity extends BaseActivity<ActivityCourseBinding, CourseViewModel> implements View.OnClickListener{


    DayAdapter dayAdapter;

    boolean isTopBarHidden = false;

    int courseId;

    int readyToStartDayPosition;

    private CourseItem courseItem;

    @Override
    protected void initView() {

        courseId = getIntent().getIntExtra(IntentKeys.EXTRA_COURSE_ID,1);
        viewModel.getCourseItemById(courseId).observe(this, courseItem -> {
            int imgRes = ResourceUtils.getDrawableId(CourseActivity.this, "img_course_" + courseItem.getDifficultLevel().toLowerCase());
            binding.imgCourse.setImageResource(imgRes);
            binding.tvName.setText(courseItem.getName());
            binding.rbCourse.setRating(courseItem.getLevel());
            binding.tvLevel.setText(courseItem.getDifficultLevel());
            binding.tvRemain.setText(getString(R.string.days_remain, courseItem.getRemainDays()));
            binding.tvTopBarCourseName.setText(courseItem.getName());
            CourseActivity.this.courseItem = courseItem;
        });

        dayAdapter = new DayAdapter(viewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        linearLayoutManager.setSmoothScrollbarEnabled(true);
        binding.rvDay.setLayoutManager(linearLayoutManager);
        binding.rvDay.setAdapter(dayAdapter);

        viewModel.getDayItems(courseId).observe(this, dayItems -> {
            readyToStartDayPosition = viewModel.findReadyToStartDayPosition(dayItems);
            binding.btnContinue.setText(readyToStartDayPosition == 0 ? R.string.start : R.string.text_continue);
            dayAdapter.setData(dayItems);
        });
    }

    @Override
    protected void initListener() {
        binding.ibBack.setOnClickListener(this);
        binding.ibBackTempTopBar.setOnClickListener(this);
        binding.btnContinue.setOnClickListener(this);
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
        dayAdapter.setOnItemClickListener((data, position) -> {
            if(viewModel.getExerciseState(data, dayAdapter.getItem(position - 1)) == DayState.LOCKED){
                return;
            }
            gotoDayActivity();
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
                alphaAnimation.setAnimationListener(null);
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }
        });
        view.startAnimation(alphaAnimation);
    }

    private void gotoDayActivity(){
        if(courseItem == null) return;
        Intent intent = new Intent(CourseActivity.this, DayActivity.class);
        intent.putExtra(IntentKeys.EXTRA_COURSE, courseItem);
        intent.putExtra(IntentKeys.EXTRA_DAY, dayAdapter.getItem(readyToStartDayPosition));
        startActivity(intent);
    }

    @Override
    public void onClick(View v) {
        if(v == binding.ibBack || v == binding.ibBackTempTopBar){
            finish();
        }
        else if(v == binding.btnContinue){
            gotoDayActivity();
        }
    }
}