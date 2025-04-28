package com.exercise.app30day.features.report;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.Day;
import com.exercise.app30day.data.models.DayExercise;
import com.exercise.app30day.data.models.DayHistory;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.items.WorkoutHistoryItem;
import com.prolificinteractive.materialcalendarview.CalendarDay;
import com.prolificinteractive.materialcalendarview.DayViewDecorator;
import com.prolificinteractive.materialcalendarview.DayViewFacade;
import com.prolificinteractive.materialcalendarview.MaterialCalendarView;
import com.prolificinteractive.materialcalendarview.spans.DotSpan;

import org.threeten.bp.LocalDate;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Locale;

public class WorkoutHistoryActivity extends AppCompatActivity implements AdapterView.OnItemSelectedListener {

    private ImageButton btnBack;
    private MaterialCalendarView calendarView;
    private TextView tvTotalWorkouts;
    private TextView tvThisMonth;
    private TextView tvCurrentStreak;
    private Spinner spinnerFilter;
    private RecyclerView rvWorkoutHistory;

    private WorkoutHistoryAdapter adapter;
    private List<WorkoutHistoryItem> allHistoryItems;
    private List<WorkoutHistoryItem> filteredHistoryItems;

    // Dummy data
    private List<Course> courses;
    private List<Day> days;
    private List<Exercise> exercises;
    private List<DayExercise> dayExercises;
    private List<DayHistory> dayHistories;
    private HashSet<CalendarDay> workoutDays;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_workout_history);

        initViews();
        initDummyData();
        setupCalendar();
        setupRecyclerView();
        updateSummaryStats();
        setupListeners();
    }

    private void initViews() {
        btnBack = findViewById(R.id.btn_back);
        calendarView = findViewById(R.id.calendar_view);
        tvTotalWorkouts = findViewById(R.id.tv_total_workouts);
        tvThisMonth = findViewById(R.id.tv_this_month);
        tvCurrentStreak = findViewById(R.id.tv_current_streak);
        spinnerFilter = findViewById(R.id.spinner_filter);
        rvWorkoutHistory = findViewById(R.id.rv_workout_history);
    }

    private void initDummyData() {
        // Initialize data structures
        courses = new ArrayList<>();
        days = new ArrayList<>();
        exercises = new ArrayList<>();
        dayExercises = new ArrayList<>();
        dayHistories = new ArrayList<>();
        workoutDays = new HashSet<>();
        allHistoryItems = new ArrayList<>();
        filteredHistoryItems = new ArrayList<>();

        // Add dummy courses
        courses.add(new Course("Full Body Workout", 2, "workout_full_body.jpg"));
        courses.add(new Course("Ab Challenge", 1, "workout_abs.jpg"));
        courses.add(new Course("Cardio Blast", 3, "workout_cardio.jpg"));

        // Add dummy exercises
        exercises.add(new Exercise("Push-ups", "Classic push-ups", 60000, 8.5, 12, "pushup.gif"));
        exercises.add(new Exercise("Squats", "Standard squats", 60000, 9.2, 15, "squat.gif"));
        exercises.add(new Exercise("Plank", "Core exercise", 45000, 7.0, 3, "plank.gif"));
        exercises.add(new Exercise("Jumping Jacks", "Cardio exercise", 60000, 12.0, 30, "jumping_jacks.gif"));

        // Generate workout history for the last 30 days
        Calendar calendar = Calendar.getInstance();
        SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());

        for (int i = 0; i < 30; i++) {
            // Skip some days to simulate non-workout days
            if (i % 3 == 0) continue;

            calendar.setTime(new Date());
            calendar.add(Calendar.DAY_OF_MONTH, -i);

            // Add to workout days for calendar highlighting
            int year = calendar.get(Calendar.YEAR);
            int month = calendar.get(Calendar.MONTH) + 1; // Month is 0-based in Calendar
            int day = calendar.get(Calendar.DAY_OF_MONTH);

            CalendarDay calendarDay = CalendarDay.from(
                    LocalDate.of(year, month, day)
            );
            workoutDays.add(calendarDay);

            // Create history item for this day
            Course course = courses.get(i % courses.size());
            int dayNumber = (i % 30) + 1;

            long durationMinutes = 0;
            double calories = 0;

            // Add all exercises for this workout
            for (Exercise exercise : exercises) {
                durationMinutes += exercise.getTime() / 60000; // Convert ms to minutes
                calories += exercise.getKcal();
            }

            WorkoutHistoryItem item = new WorkoutHistoryItem(
                    course.getName(),
                    dayNumber,
                    sdf.format(calendar.getTime()),
                    durationMinutes,
                    calories,
                    30, // 30 seconds rest
                    exercises.size()
            );

            allHistoryItems.add(item);
        }

        // Initially show all items
        filteredHistoryItems.addAll(allHistoryItems);
    }

    private void setupCalendar() {
        // Setup calendar view
        calendarView.setSelectionMode(MaterialCalendarView.SELECTION_MODE_SINGLE);
        calendarView.setShowOtherDates(MaterialCalendarView.SHOW_OTHER_MONTHS);

        // Decorate workout days with dots
        calendarView.addDecorator(new DayViewDecorator() {
            @Override
            public boolean shouldDecorate(CalendarDay day) {
                return workoutDays.contains(day);
            }

            @Override
            public void decorate(DayViewFacade view) {
                view.addSpan(new DotSpan(8, getResources().getColor(R.color.primary)));
            }
        });

        // Set current date selected
        calendarView.setSelectedDate(CalendarDay.today());
    }

    private void setupRecyclerView() {
        adapter = new WorkoutHistoryAdapter(filteredHistoryItems);
        rvWorkoutHistory.setLayoutManager(new LinearLayoutManager(this));
        rvWorkoutHistory.setAdapter(adapter);
    }

    private void updateSummaryStats() {
        // Update summary statistics
        tvTotalWorkouts.setText(String.valueOf(allHistoryItems.size()));

        // Count workouts this month
        Calendar calendar = Calendar.getInstance();
        int currentMonth = calendar.get(Calendar.MONTH);
        int currentYear = calendar.get(Calendar.YEAR);

        SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
        SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

        int thisMonthCount = 0;
        for (WorkoutHistoryItem item : allHistoryItems) {
            try {
                Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(item.getDate());
                int month = Integer.parseInt(monthFormat.format(date)) - 1; // Month is 0-based
                int year = Integer.parseInt(yearFormat.format(date));

                if (month == currentMonth && year == currentYear) {
                    thisMonthCount++;
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

        tvThisMonth.setText(String.valueOf(thisMonthCount));

        // Calculate current streak
        int streak = calculateCurrentStreak();
        tvCurrentStreak.setText(String.valueOf(streak));
    }

    private int calculateCurrentStreak() {
        // Simplified streak calculation for demo purposes
        // In a real app, this would check consecutive days from today backward
        return 5; // Dummy value
    }

    private void filterHistoryItems(String filter) {
        filteredHistoryItems.clear();

        switch (filter) {
            case "Tất cả":
                filteredHistoryItems.addAll(allHistoryItems);
                break;

            case "Tháng này":
                Calendar calendar = Calendar.getInstance();
                int currentMonth = calendar.get(Calendar.MONTH);
                int currentYear = calendar.get(Calendar.YEAR);

                SimpleDateFormat monthFormat = new SimpleDateFormat("MM", Locale.getDefault());
                SimpleDateFormat yearFormat = new SimpleDateFormat("yyyy", Locale.getDefault());

                for (WorkoutHistoryItem item : allHistoryItems) {
                    try {
                        Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(item.getDate());
                        int month = Integer.parseInt(monthFormat.format(date)) - 1; // Month is 0-based
                        int year = Integer.parseInt(yearFormat.format(date));

                        if (month == currentMonth && year == currentYear) {
                            filteredHistoryItems.add(item);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;

            case "Tuần qua":
                Calendar weekAgo = Calendar.getInstance();
                weekAgo.add(Calendar.DAY_OF_MONTH, -7);

                for (WorkoutHistoryItem item : allHistoryItems) {
                    try {
                        Date date = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(item.getDate());
                        if (date.after(weekAgo.getTime())) {
                            filteredHistoryItems.add(item);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
                break;
        }

        adapter.notifyDataSetChanged();
    }

    private void setupListeners() {
        btnBack.setOnClickListener(v -> finish());

        spinnerFilter.setOnItemSelectedListener(this);

        calendarView.setOnDateChangedListener((widget, date, selected) -> {
            if (selected) {
                // Filter history to show only the selected date
                filteredHistoryItems.clear();

                LocalDate localDate = date.getDate();
                int year = localDate.getYear();
                int month = localDate.getMonthValue();
                int day = localDate.getDayOfMonth();

                Calendar cal = Calendar.getInstance();
                cal.set(year, month - 1, day); // Month is 0-based in Calendar

                SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy", Locale.getDefault());
                String selectedDate = sdf.format(cal.getTime());

                for (WorkoutHistoryItem item : allHistoryItems) {
                    if (item.getDate().equals(selectedDate)) {
                        filteredHistoryItems.add(item);
                    }
                }

                adapter.notifyDataSetChanged();

                // Reset spinner to avoid confusion
                spinnerFilter.setSelection(0, false);
            }
        });
    }

    @Override
    public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
        String filter = parent.getItemAtPosition(position).toString();
        filterHistoryItems(filter);

        // Reset calendar selection when filter changes
        calendarView.clearSelection();
    }

    @Override
    public void onNothingSelected(AdapterView<?> parent) {
        // Do nothing
    }

    // Inner class for WorkoutHistoryAdapter and WorkoutHistoryItem would be here
    // but since they're defined in ReportFragment, we're referencing them
    // If needed, they could be moved to a separate file for reuse
}