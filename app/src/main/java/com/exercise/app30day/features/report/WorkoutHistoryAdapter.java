package com.exercise.app30day.features.report;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.exercise.app30day.R;
import com.exercise.app30day.items.WorkoutHistoryItem;

import java.util.List;
import java.util.Locale;

public class WorkoutHistoryAdapter extends RecyclerView.Adapter<WorkoutHistoryAdapter.ViewHolder> {
    private List<WorkoutHistoryItem> historyItems;

    public WorkoutHistoryAdapter(List<WorkoutHistoryItem> historyItems) {
        this.historyItems = historyItems;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_workout_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        WorkoutHistoryItem item = historyItems.get(position);

        holder.tvCourseName.setText(item.getCourseName());
        holder.tvDayInfo.setText("Ngày " + item.getDayNumber());
        holder.tvWorkoutDate.setText(item.getDate());
        holder.tvDuration.setText(item.getDurationMinutes() + " phút");
        holder.tvCalories.setText(String.format(Locale.getDefault(), "%.0f kcal", item.getCalories()));
        holder.tvRestTime.setText(item.getRestTimeSeconds() + " giây");
        holder.tvExercisesCount.setText(String.valueOf(item.getExercisesCount()));
    }

    @Override
    public int getItemCount() {
        return historyItems.size();
    }

    class ViewHolder extends RecyclerView.ViewHolder {
        TextView tvCourseName;
        TextView tvDayInfo;
        TextView tvWorkoutDate;
        TextView tvDuration;
        TextView tvCalories;
        TextView tvRestTime;
        TextView tvExercisesCount;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tvCourseName = itemView.findViewById(R.id.tv_course_name);
            tvDayInfo = itemView.findViewById(R.id.tv_day_info);
            tvWorkoutDate = itemView.findViewById(R.id.tv_workout_date);
            tvDuration = itemView.findViewById(R.id.tv_duration);
            tvCalories = itemView.findViewById(R.id.tv_calories);
            tvRestTime = itemView.findViewById(R.id.tv_rest_time);
            tvExercisesCount = itemView.findViewById(R.id.tv_exercises_count);
        }
    }
}