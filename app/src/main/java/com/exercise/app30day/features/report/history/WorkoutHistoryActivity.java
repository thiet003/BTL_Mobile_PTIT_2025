package com.exercise.app30day.features.report.history;

import android.view.View;
import android.widget.AdapterView;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseActivity;
import com.exercise.app30day.databinding.ActivityWorkoutHistoryBinding;
import com.exercise.app30day.features.report.DayHistoryAdapter;
import com.exercise.app30day.items.DayHistoryItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;

import java.util.Calendar;
import java.util.List;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class WorkoutHistoryActivity extends BaseActivity<ActivityWorkoutHistoryBinding, WorkoutHistoryViewModel>
        implements AdapterView.OnItemSelectedListener, View.OnClickListener{

    private DayHistoryAdapter dayHistoryAdapter;

    @Override
    protected void initView() {

        dayHistoryAdapter = new DayHistoryAdapter();
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        binding.rvWorkoutHistory.setLayoutManager(linearLayoutManager);
        binding.rvWorkoutHistory.setAdapter(dayHistoryAdapter);

        viewModel.onDayHistoryItem.observe(this, dayHistoryItems -> {
            dayHistoryAdapter.setData(dayHistoryItems);
            binding.tvTotalWorkouts.setText(String.valueOf(dayHistoryItems.size()));
            binding.tvCurrentStreak.setText(String.valueOf(viewModel.getLongestWorkoutStreak(dayHistoryItems)));
            binding.tvThisMonth.setText(String.valueOf(viewModel.countSessionsThisMonth(dayHistoryItems)));
            setupCalenderView(dayHistoryItems);
        });
    }

    @Override
    protected void initListener() {
        binding.spinnerFilter.setOnItemSelectedListener(this);
        binding.btnBack.setOnClickListener(this);
    }

    private void setupCalenderView(List<DayHistoryItem> dayHistoryItems){
        binding.calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        binding.calendarView.setShowOtherDates(MaterialCalendarView.SHOW_OTHER_MONTHS);

        // Decorate workout days with dots
        binding.calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay calendarDay) {
                for(DayHistoryItem dayHistoryItem : dayHistoryItems){
                    Calendar calendar = Calendar.getInstance();
                    calendar.setTimeInMillis(dayHistoryItem.getStopTime());
                    int year = calendar.get(Calendar.YEAR);
                    int month = calendar.get(Calendar.MONTH) + 1;
                    int day = calendar.get(Calendar.DAY_OF_MONTH);
                    if(year == calendarDay.getYear() && month == calendarDay.getMonth() && day == calendarDay.getDay()){
                        return true;
                    }
                }
                return false;
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(8, ContextCompat.getColor(WorkoutHistoryActivity.this, R.color.primary)));
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        switch (position){
            case 0:
                dayHistoryAdapter.setData(viewModel.getAllHistory());
                break;
            case 1:
                dayHistoryAdapter.setData(viewModel.filterHistoryBySameMoth());
                break;
            case 2:
                dayHistoryAdapter.setData(viewModel.filterHistoryBySameWeek());
                break;
        }
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {

    }

    @Override
    public void onClick(View v) {
        if(v == binding.btnBack){
            finish();
        }
    }
}