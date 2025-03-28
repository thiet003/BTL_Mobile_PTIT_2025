package com.exercise.app30day.features.course;

import android.view.View;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;

import androidx.annotation.NonNull;
import androidx.core.widget.NestedScrollView;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.base.adapter.BaseRecyclerViewAdapter;
import com.exercise.app30day.databinding.ActivityCourseBinding;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.utils.IntentKeys;
import com.exercise.app30day.utils.ResourceUtils;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class CourseActivity extends BaseActivity<ActivityCourseBinding, CourseViewModel>
        implements View.OnClickListener, NestedScrollView.OnScrollChangeListener, BaseRecyclerViewAdapter.OnItemClickListener<DayItem>{


    DayAdapter dayAdapter;

    boolean isTopBarHidden = false;

    int courseId;
    @Override
    protected void initView() {

        courseId = getIntent().getIntExtra(IntentKeys.EXTRA_COURSE_ID,1);
        viewModel.getCourseItemById(courseId).observe(this, courseItem -> {
            int imgRes = ResourceUtils.getDrawableId(CourseActivity.this, "img_course_" + courseItem.getDifficultLevel().toLowerCase());
            binding.imgCourse.setImageResource(imgRes);
            binding.tvName.setText(courseItem.getName());
            binding.rbCourse.setRating(viewModel.getLevel(courseItem.getDifficultLevel()));
            binding.tvLevel.setText(courseItem.getDifficultLevel());
            int daysRemain = viewModel.calculateDaysRemain(courseItem.getNumberOfCompletedDays(), courseItem.getNumberOfDays());
            binding.tvRemain.setText(getString(R.string.days_remain, daysRemain));
            binding.tvTopBarCourseName.setText(courseItem.getName());
        });

        dayAdapter = new DayAdapter(viewModel);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvDay.setLayoutManager(linearLayoutManager);
        binding.rvDay.setAdapter(dayAdapter);

        viewModel.getListDay(courseId).observe(this, courseDayExerciseItems -> {
            for(DayItem item : courseDayExerciseItems){
                System.out.println(item);
            }
            dayAdapter.setData(courseDayExerciseItems);
        });
    }

    @Override
    protected void initListener() {
        binding.ibBack.setOnClickListener(this);
        binding.ibBackTempTopBar.setOnClickListener(this);
        binding.nestScrollView.setOnScrollChangeListener(this);
        dayAdapter.setOnItemClickListener(this);
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

    @Override
    public void onClick(View v) {
        if(v == binding.ibBack || v == binding.ibBackTempTopBar){
            finish();
        }
    }

    @Override
    public void onScrollChange(@NonNull NestedScrollView v, int scrollX, int scrollY, int oldScrollX, int oldScrollY) {
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
    }

    @Override
    public void onItemClick(DayItem data, int position) {

    }
}