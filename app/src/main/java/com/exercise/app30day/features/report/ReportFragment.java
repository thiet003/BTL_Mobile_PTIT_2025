package com.exercise.app30day.features.report;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.Day;
import com.exercise.app30day.data.models.DayExercise;
import com.exercise.app30day.data.models.DayHistory;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.models.Weight;
import com.exercise.app30day.items.WorkoutHistoryItem;
import com.github.mikephil.charting.charts.BarChart;
import com.github.mikephil.charting.charts.LineChart;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.XAxis;
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

public class ReportFragment extends Fragment {

    // UI Components
    private TextView tvTotalWorkouts;
    private TextView tvCompletedDays;
    private TextView tvTotalCalories;
    private TextView tvTotalTime;
    private TextView tvHeight;
    private TextView tvCurrentWeight;
    private LineChart chartWeightHistory;
    private BarChart chartWeeklyWorkouts;
    private PieChart chartCourseCompletion;
    private RecyclerView rvRecentActivities;
    private Button btnUpdateWeight;
    private Button btnViewAllHistory;

    // Dummy data for demonstration
    private User user;
    private List<Weight> weightHistory;
    private List<Course> courses;
    private List<Day> days;
    private List<Exercise> exercises;
    private List<DayExercise> dayExercises;
    private List<DayHistory> dayHistories;
    private WorkoutHistoryAdapter adapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_report, container, false);
        initViews(view);
        initDummyData();
        setupUIData();
        setupCharts();
        setupRecyclerView();
        setupListeners();
        return view;
    }

    private void initViews(View view) {
        // Summary Cards
        tvTotalWorkouts = view.findViewById(R.id.tv_total_workouts);
        tvCompletedDays = view.findViewById(R.id.tv_completed_days);
        tvTotalCalories = view.findViewById(R.id.tv_total_calories);
        tvTotalTime = view.findViewById(R.id.tv_total_time);

        // User Metrics
        tvHeight = view.findViewById(R.id.tv_height);
        tvCurrentWeight = view.findViewById(R.id.tv_current_weight);
        chartWeightHistory = view.findViewById(R.id.chart_weight_history);

        // Weekly Progress
        chartWeeklyWorkouts = view.findViewById(R.id.chart_weekly_workouts);

        // Course Completion
        chartCourseCompletion = view.findViewById(R.id.chart_course_completion);

        // Recent Activities
        rvRecentActivities = view.findViewById(R.id.rv_recent_activities);

        // Buttons
        btnUpdateWeight = view.findViewById(R.id.btn_update_weight);
        btnViewAllHistory = view.findViewById(R.id.btn_view_all_history);
    }

    private void initDummyData() {
        // User data
        user = new User();
        user.setHeight(175.0);
        user.setWeight(70.0);

        // Weight history
        weightHistory = new ArrayList<>();
        Calendar calendar = Calendar.getInstance();
        for (int i = 30; i >= 0; i -= 5) {
            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);
            double randomWeight = 70.0 + Math.random() * 2 - 1; // Random weight between 69-71
            weightHistory.add(new Weight(1, randomWeight, calendar.getTimeInMillis()));
        }

        // Courses
        courses = new ArrayList<>();
        courses.add(new Course("Full Body Workout", 2, "workout_full_body.jpg"));
        courses.add(new Course("Ab Challenge", 1, "workout_abs.jpg"));
        courses.add(new Course("Cardio Blast", 3, "workout_cardio.jpg"));

        // Days
        days = new ArrayList<>();
        for (int i = 0; i < courses.size(); i++) {
            for (int j = 1; j <= 30; j++) {
                boolean completed = j <= 15; // First 15 days completed
                days.add(new Day(i + 1, j, completed));
            }
        }

        // Exercises
        exercises = new ArrayList<>();
        exercises.add(new Exercise("Push-ups", "Classic push-ups", 60000, 8.5, 12, "pushup.gif"));
        exercises.add(new Exercise("Squats", "Standard squats", 60000, 9.2, 15, "squat.gif"));
        exercises.add(new Exercise("Plank", "Core exercise", 45000, 7.0, 3, "plank.gif"));
        exercises.add(new Exercise("Jumping Jacks", "Cardio exercise", 60000, 12.0, 30, "jumping_jacks.gif"));
        exercises.add(new Exercise("Lunges", "Leg exercise", 60000, 10.0, 12, "lunge.gif"));

        // Day Exercises
        dayExercises = new ArrayList<>();
        for (int i = 1; i <= days.size(); i++) {
            for (int j = 1; j <= exercises.size(); j++) {
                boolean completed = i <= 15; // Exercises for first 15 days completed
                dayExercises.add(new DayExercise(j, i, completed));
            }
        }

        // Day Histories
        dayHistories = new ArrayList<>();
        Calendar historyCalendar = Calendar.getInstance();
        for (int i = 1; i <= 15; i++) { // 15 completed days
            historyCalendar.setTime(new Date());
            historyCalendar.add(Calendar.DAY_OF_MONTH, -i);

            for (int j = 0; j < exercises.size(); j++) {
                DayHistory history = new DayHistory(i, j);
                history.setStopTime(historyCalendar.getTimeInMillis());
                history.setRestTime(30000); // 30 seconds rest time
                dayHistories.add(history);
            }
        }
    }

    private void setupUIData() {
        // Summary Cards
        int totalWorkouts = dayHistories.size() / exercises.size(); // Number of completed workouts
        int completedDays = (int) days.stream().filter(Day::isCompleted).count();
        double totalCalories = exercises.stream().mapToDouble(Exercise::getKcal).sum() * completedDays;
        long totalMinutes = exercises.stream().mapToLong(Exercise::getTime).sum() * completedDays / 60000; // Convert ms to minutes

        tvTotalWorkouts.setText(String.valueOf(totalWorkouts));
        tvCompletedDays.setText(String.valueOf(completedDays));
        tvTotalCalories.setText(String.format(Locale.getDefault(), "%.0f", totalCalories));
        tvTotalTime.setText(String.valueOf(totalMinutes));

        // User Metrics
        tvHeight.setText(String.format(Locale.getDefault(), "%.1f cm", user.getHeight()));
        tvCurrentWeight.setText(String.format(Locale.getDefault(), "%.1f kg", user.getWeight()));
    }

    private void setupCharts() {
        // Weight History Chart
        setupWeightHistoryChart();

        // Weekly Workouts Chart
        setupWeeklyWorkoutsChart();

        // Course Completion Chart
        setupCourseCompletionChart();
    }

    private void setupWeightHistoryChart() {
        List<Entry> entries = new ArrayList<>();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM", Locale.getDefault());
        List<String> xLabels = new ArrayList<>();

        for (int i = 0; i < weightHistory.size(); i++) {
            Weight weight = weightHistory.get(i);
            entries.add(new Entry(i, (float) weight.getWeight()));
            xLabels.add(sdf.format(new Date(weight.getDate())));
        }

        LineDataSet dataSet = new LineDataSet(entries, "Cân nặng (kg)");
        dataSet.setColor(getResources().getColor(R.color.primary));
        dataSet.setCircleColor(getResources().getColor(R.color.primary));
        dataSet.setLineWidth(2f);
        dataSet.setCircleRadius(4f);
        dataSet.setDrawValues(true);
        dataSet.setValueTextSize(10f);
        dataSet.setMode(LineDataSet.Mode.CUBIC_BEZIER);

        LineData lineData = new LineData(dataSet);
        chartWeightHistory.setData(lineData);

        XAxis xAxis = chartWeightHistory.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(xLabels));

        chartWeightHistory.getDescription().setEnabled(false);
        chartWeightHistory.getLegend().setEnabled(false);
        chartWeightHistory.setExtraBottomOffset(10f);
        chartWeightHistory.invalidate();
    }

    private void setupWeeklyWorkoutsChart() {
        String[] weekDays = new String[]{"T2", "T3", "T4", "T5", "T6", "T7", "CN"};
        List<BarEntry> entries = new ArrayList<>();

        // Generate some random workout minutes for each day of the week
        for (int i = 0; i < 7; i++) {
            float minutes = (float) (Math.random() * 60); // Random minutes (0-60)
            entries.add(new BarEntry(i, minutes));
        }

        BarDataSet dataSet = new BarDataSet(entries, "Phút tập luyện");
        dataSet.setColors(getResources().getColor(R.color.blue));
        dataSet.setValueTextSize(10f);

        BarData barData = new BarData(dataSet);
        barData.setBarWidth(0.6f);

        chartWeeklyWorkouts.setData(barData);
        chartWeeklyWorkouts.setFitBars(true);

        XAxis xAxis = chartWeeklyWorkouts.getXAxis();
        xAxis.setPosition(XAxis.XAxisPosition.BOTTOM);
        xAxis.setGranularity(1f);
        xAxis.setValueFormatter(new IndexAxisValueFormatter(weekDays));

        chartWeeklyWorkouts.getDescription().setEnabled(false);
        chartWeeklyWorkouts.getLegend().setEnabled(false);
        chartWeeklyWorkouts.setExtraBottomOffset(10f);
        chartWeeklyWorkouts.invalidate();
    }

    private void setupCourseCompletionChart() {
        List<PieEntry> entries = new ArrayList<>();

        // Calculate course completion percentages
        for (Course course : courses) {
            int totalDays = 30; // Each course has 30 days
            int completedDays = (int) days.stream()
                    .filter(d -> d.getCourseId() == course.getId() && d.isCompleted())
                    .count();

            float completionPercentage = (float) completedDays / totalDays * 100;
            entries.add(new PieEntry(completionPercentage, course.getName()));
        }

        PieDataSet dataSet = new PieDataSet(entries, "");
        dataSet.setColors(new int[]{
                getResources().getColor(R.color.primary),
                getResources().getColor(R.color.blue),
                getResources().getColor(R.color.green)
        });
        dataSet.setValueTextSize(12f);
        dataSet.setValueTextColor(Color.WHITE);

        PieData pieData = new PieData(dataSet);
        pieData.setValueFormatter(new ValueFormatter() {
            @Override
            public String getFormattedValue(float value) {
                return String.format(Locale.getDefault(), "%.1f%%", value);
            }
        });

        chartCourseCompletion.setData(pieData);
        chartCourseCompletion.getDescription().setEnabled(false);
        chartCourseCompletion.setHoleRadius(40f);
        chartCourseCompletion.setTransparentCircleRadius(45f);
        chartCourseCompletion.setEntryLabelTextSize(12f);
        chartCourseCompletion.setEntryLabelColor(Color.WHITE);
        chartCourseCompletion.setDrawEntryLabels(true);
        chartCourseCompletion.setDrawCenterText(true);
        chartCourseCompletion.setCenterText("Hoàn thành\nkhóa học");
        chartCourseCompletion.setCenterTextSize(14f);
        chartCourseCompletion.invalidate();
    }

    private void setupRecyclerView() {
        adapter = new WorkoutHistoryAdapter(getRecentWorkoutHistory());
        rvRecentActivities.setLayoutManager(new LinearLayoutManager(getContext()));
        rvRecentActivities.setAdapter(adapter);
    }

    private List<WorkoutHistoryItem> getRecentWorkoutHistory() {
        List<WorkoutHistoryItem> historyItems = new ArrayList<>();

        // Group day histories by day to create workout sessions
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (int i = 0; i < 5; i++) { // Show last 5 workouts
            if (i >= dayHistories.size() / exercises.size()) break;

            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -(i+1));

            int dayId = i + 1;
            Course course = courses.get(dayId % courses.size());
            int dayNumber = (dayId % 30) + 1;

            // Calculate total exercise time for the day
            long totalTime = exercises.stream().mapToLong(Exercise::getTime).sum();
            double totalCalories = exercises.stream().mapToDouble(Exercise::getKcal).sum();

            // Get average rest time
            long avgRestTime = 30000; // 30 seconds (dummy value)

            WorkoutHistoryItem item = new WorkoutHistoryItem(
                    course.getName(),
                    dayNumber,
                    sdf.format(calendar.getTime()),
                    totalTime / 60000, // Convert to minutes
                    totalCalories,
                    avgRestTime / 1000, // Convert to seconds
                    exercises.size()
            );

            historyItems.add(item);
        }

        return historyItems;
    }

    private void setupListeners() {
        btnUpdateWeight.setOnClickListener(v -> {
            UpdateMetricsBottomSheet bottomSheet = new UpdateMetricsBottomSheet();
            bottomSheet.show(getParentFragmentManager(), bottomSheet.getTag());
        });

        btnViewAllHistory.setOnClickListener(v -> {
            startActivity(new Intent(requireContext(), WorkoutHistoryActivity.class));
        });
    }
}