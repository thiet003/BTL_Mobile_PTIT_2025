package com.exercise.app30day.features.report;

import android.content.Intent;
import android.graphics.Color;
import android.view.View;

import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.exercise.app30day.R;
import com.exercise.app30day.base.BaseFragment;
import com.exercise.app30day.databinding.FragmentReportBinding;
import com.exercise.app30day.features.report.history.WorkoutHistoryActivity;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayHistoryItem;
import com.exercise.app30day.items.UserItem;
import com.github.mikephil.charting.components.XAxis;
import com.github.mikephil.charting.components.YAxis;
import com.github.mikephil.charting.data.BarData;
import com.github.mikephil.charting.data.BarDataSet;
import com.github.mikephil.charting.data.BarEntry;
import com.github.mikephil.charting.data.Entry;
import com.github.mikephil.charting.data.LineData;
import com.github.mikephil.charting.data.LineDataSet;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.IndexAxisValueFormatter;
import com.github.mikephil.charting.formatter.ValueFormatter;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;

import dagger.hilt.android.AndroidEntryPoint;

@AndroidEntryPoint
public class ReportFragment extends BaseFragment<FragmentReportBinding, ReportViewModel> {

    private double currentWeight = 70.0, currentHeight = 170.0;

    @Override
    protected void initView() {
        DayHistoryAdapter dayHistoryAdapter = new DayHistoryAdapter();
        LinearLayoutManager layoutManager = new LinearLayoutManager(requireContext(), LinearLayoutManager.VERTICAL, false);
        binding.rvRecentActivities.setLayoutManager(layoutManager);
        binding.rvRecentActivities.setAdapter(dayHistoryAdapter);

        viewModel.getAllDayHistoryItems().observe(this, dayHistoryItems -> {
            binding.tvTotalWorkouts.setAnimationDuration(1000).countAnimation(0, dayHistoryItems.size());
            binding.tvTotalTime.setAnimationDuration(1000).countAnimation(0, (int) viewModel.calculateTotalMinute(dayHistoryItems));
            binding.tvTotalCalories.setAnimationDuration(1000).countAnimation(0, (int) viewModel.calculateTotalCalories(dayHistoryItems));
            setupWeeklyWorkoutsChart(dayHistoryItems);
            dayHistoryAdapter.setData(viewModel.getLatestDayHistoryItems(dayHistoryItems, 3));
        });

        viewModel.countCompletedDays().observe(this, count -> {
            binding.tvCompletedDays.setAnimationDuration(1000).countAnimation(0, count);
        });

        viewModel.getHistoryUserItem().observe(this, userItems -> {
            if(!userItems.isEmpty()){
                UserItem lastUserItem = userItems.get(userItems.size() - 1);
                currentHeight = lastUserItem.getHeight();
                currentWeight = lastUserItem.getWeight();
                binding.tvHeight.setText(String.format(Locale.getDefault(), getString(R.string.f_cm), currentHeight));
                binding.tvCurrentWeight.setText(String.format(Locale.getDefault(), getString(R.string.f_kg), currentWeight));
                setupWeightHistoryChart(userItems);
            }
        });

        viewModel.getAllCourseItems().observe(this, this::setupCourseCompletionChart);
    }

    @Override
    protected void initListener() {
        binding.btnUpdateMetrics.setOnClickListener(v->{
            UserMetricsBottomSheet bottomSheet= UserMetricsBottomSheet.newInstance(currentWeight, currentHeight, (UserMetricsBottomSheet.OnMetricsUpdatedListener) (weight, height) -> {
                viewModel.saveUser(height, weight);
            });
            bottomSheet.show(getParentFragmentManager(), bottomSheet.getTag());
        });
        binding.btnViewAllHistory.setOnClickListener(v->{
            startActivity(new Intent(requireContext(), WorkoutHistoryActivity.class));
        });
    }

    private void setupWeightHistoryChart(List<UserItem> userItems) {
        List<Entry> entries = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
        List<String> xLabels = new ArrayList<>();

        for (int i = 0; i < userItems.size(); i++) {
            UserItem userItem = userItems.get(i);
            entries.add(new Entry(i, (float) userItem.getWeight()));
            xLabels.add(sdf.format(new Date(userItem.getDate())));
        }

        LineDataSet dataSet = new LineDataSet(entries, getString(R.string.weight) + " " + getString(R.string.kg) );
        dataSet.setColor(ContextCompat.getColor(requireContext(), R.color.primary));
        dataSet.setCircleColor(ContextCompat.getColor(requireContext(), R.color.primary));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        binding.chartWeightHistory.setData(lineData);

        XAxis xAxis = binding.chartWeightHistory.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));

        binding.chartWeightHistory.getDescription().setEnabled(false);
        binding.chartWeightHistory.getLegend().setEnabled(false);
        binding.chartWeightHistory.setExtraBottomOffset(10f);
        binding.chartWeightHistory.invalidate();
    }

    private void setupWeeklyWorkoutsChart(List<DayHistoryItem> dayHistoryItems) {
        String[] weekDays = getWeekdayNames();
        List<BarEntry> entries = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        // Đặt về đầu ngày hôm nay
        calendar.set(Calendar.HOUR_OF_DAY, 0);
        calendar.set(Calendar.MINUTE, 0);
        calendar.set(Calendar.SECOND, 0);
        calendar.set(Calendar.MILLISECOND, 0);

        // Tìm ngày đầu tuần (Thứ 2)
        int dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK);
        // Trong Calendar, Chủ nhật = 1, Thứ 2 = 2... Thứ 7 = 7
        int daysToSubtract = dayOfWeek == Calendar.SUNDAY ? 6 : dayOfWeek - Calendar.MONDAY;
        calendar.add(Calendar.DAY_OF_MONTH, -daysToSubtract);

        // Tạo một mảng để lưu trữ lượng calo đốt cho mỗi ngày trong tuần
        float[] dailyCalories = new float[7];

        // Tạo mảng lưu thời gian bắt đầu của mỗi ngày trong tuần
        long[] weekDayTimestamps = new long[7];

        // Lưu timestamp bắt đầu của mỗi ngày trong tuần
        Calendar tempCal = (Calendar) calendar.clone();
        for (int i = 0; i < 7; i++) {
            weekDayTimestamps[i] = tempCal.getTimeInMillis();
            tempCal.add(Calendar.DAY_OF_MONTH, 1);
        }

        // Lấy timestamp kết thúc tuần (đầu ngày Thứ 2 tuần sau)
        long endOfWeekTimestamp = tempCal.getTimeInMillis();

        // Tính tổng calo cho mỗi ngày dựa trên dữ liệu dayHistories
        for (DayHistoryItem dayHistoryItem : dayHistoryItems) {
            long completionTime = dayHistoryItem.getStopTime();
            if (completionTime >= weekDayTimestamps[0] && completionTime < endOfWeekTimestamp) {
                for (int i = 0; i < 7; i++) {
                    long nextDayTimestamp = (i < 6) ? weekDayTimestamps[i + 1] : endOfWeekTimestamp;
                    if (completionTime >= weekDayTimestamps[i] && completionTime < nextDayTimestamp) {
                        dailyCalories[i] += (float) dayHistoryItem.getKcal();
                        break;
                    }
                }
            }
        }

        // Tạo các BarEntry từ dữ liệu calo đã tính
        for (int i = 0; i < 7; i++) {
            entries.add(new BarEntry(i, dailyCalories[i]));
        }

        // Cập nhật biểu đồ với dữ liệu thực
        BarDataSet dataSet = new BarDataSet(entries, getString(R.string.calo_kcal));
        dataSet.setColors(ContextCompat.getColor(requireContext(), R.color.primary));
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.6f);

        binding.chartWeeklyWorkouts.setData(barData);
        binding.chartWeeklyWorkouts.setFitBars(true);

        XAxis xAxis = binding.chartWeeklyWorkouts.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekDays));

        // Đặt nhãn trục Y để làm rõ đơn vị
        YAxis leftAxis = binding.chartWeeklyWorkouts.getAxisLeft();
        leftAxis.setAxisMinimum(0f);

        // Vô hiệu hóa trục Y bên phải
        binding.chartWeeklyWorkouts.getAxisRight().setEnabled(false);

        binding.chartWeeklyWorkouts.getDescription().setEnabled(false);
        binding.chartWeeklyWorkouts.getLegend().setEnabled(true);
        binding.chartWeeklyWorkouts.setExtraBottomOffset(10f);
        binding.chartWeeklyWorkouts.invalidate();
    }

    private void setupCourseCompletionChart(List<CourseItem> courseItems) {
        List<PieEntry> entries = new ArrayList<>();
        for (CourseItem courseItem : courseItems) {
            entries.add(new PieEntry(courseItem.getDayProgress(), courseItem.getName()));
        }
        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(ContextCompat.getColor(requireContext(), R.color.primary),
                ContextCompat.getColor(requireContext(), R.color.blue),
                ContextCompat.getColor(requireContext(), R.color.green));
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.1f%%", value);
            }
        });

        binding.chartCourseCompletion.setData(pieData);
        binding.chartCourseCompletion.getDescription().setEnabled(false);
        binding.chartCourseCompletion.setHoleRadius(40f);
        binding.chartCourseCompletion.setTransparentCircleRadius(45f);
        binding.chartCourseCompletion.setEntryLabelTextSize(12f);
        binding.chartCourseCompletion.setEntryLabelColor(Color.WHITE);
        binding.chartCourseCompletion.setDrawEntryLabels(true);
        binding.chartCourseCompletion.setDrawCenterText(true);
        binding.chartCourseCompletion.setCenterText(getString(R.string.completed_course));
        binding.chartCourseCompletion.setCenterTextSize(14f);
        binding.chartCourseCompletion.invalidate();
    }

    private String[] getWeekdayNames() {
        String[] dayNames = new String[7];
        Calendar calendar = Calendar.getInstance();

        calendar.set(Calendar.DAY_OF_WEEK, calendar.getFirstDayOfWeek());

        for (int i = 0; i < 7; i++) {
            Calendar day = (Calendar) calendar.clone();
            dayNames[i] = new SimpleDateFormat("EEE", Locale.getDefault()).format(day.getTime());
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        }
        return dayNames;
    }
}