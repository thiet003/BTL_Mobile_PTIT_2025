package com.exercise.app30day.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import com.exercise.app30day.data.dao.ConversationDao;
import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.dao.DayDao;
import com.exercise.app30day.data.dao.DayExerciseDao;
import com.exercise.app30day.data.dao.DayHistoryDao;
import com.exercise.app30day.data.dao.ExerciseDao;
import com.exercise.app30day.data.dao.ReminderDao;
import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.dao.HistoryChatDao;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.Day;
import com.exercise.app30day.data.models.DayExercise;
import com.exercise.app30day.data.models.DayHistory;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.Reminder;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.models.Conversation;
import com.exercise.app30day.data.models.HistoryChat;
import com.exercise.app30day.data.utils.Converters;
import com.exercise.app30day.items.ReminderItem;
import com.exercise.app30day.utils.AlarmUtils;
import com.exercise.app30day.utils.HawkKeys;
import com.google.firebase.remoteconfig.CustomSignals;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Type;
import java.util.Arrays;
import java.util.List;

@Database(entities = {
        Course.class,
        Exercise.class,
        Day.class,
        DayExercise.class,
        DayHistory.class,
        Reminder.class,
        User.class,
        Conversation.class,
        HistoryChat.class
}, version = 2, exportSchema = false)
@TypeConverters({Converters.class})
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public abstract CourseDao courseDao();
    public abstract ExerciseDao exerciseDao();
    public abstract DayDao dayDao();
    public abstract DayExerciseDao dayExerciseDao();
    public abstract DayHistoryDao dayHistoryDao();
    public abstract ReminderDao reminderDao();
    public abstract UserDao userDao();
    public abstract ConversationDao conversationDao();
    public abstract HistoryChatDao historyChatDao();

    public static final String COURSES_DATA = "courses_data";

    public static final String EXERCISES_DATA = "exercises_data";

    public static final String DAYS_DATA = "days_data";

    public static final String DAY_EXERCISES_DATA = "day_exercises_data";

    private static final String LANGUAGE_CODE = "language_code";
    
    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .build();
                }
            }
        }
        return instance;
    }

    public static void initializeData(Context context) {
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);
        remoteConfig.fetchAndActivate().addOnCanceledListener(() -> {

        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                remoteConfig.activate();
                String coursesJson = remoteConfig.getString(COURSES_DATA);
                String exercisesJson = remoteConfig.getString(EXERCISES_DATA);
                String daysJson = remoteConfig.getString(DAYS_DATA);
                String dayExercisesJson = remoteConfig.getString(DAY_EXERCISES_DATA);

                new Thread(() -> {
                    Type coursesType = new TypeToken<List<Course>>() {
                    }.getType();
                    List<Course> courses = new Gson().fromJson(coursesJson, coursesType);
                    getInstance(context).courseDao().insertCourses(courses);

                    Type exercisesType = new TypeToken<List<Exercise>>() {
                    }.getType();
                    List<Exercise> exercises = new Gson().fromJson(exercisesJson, exercisesType);
                    getInstance(context).exerciseDao().insertExercises(exercises);

                    Type daysType = new TypeToken<List<Day>>() {
                    }.getType();
                    List<Day> days = new Gson().fromJson(daysJson, daysType);
                    getInstance(context).dayDao().insertDays(days);

                    Type dayExerciseType = new TypeToken<List<DayExercise>>() {
                    }.getType();
                    List<DayExercise> dayExercises = new Gson().fromJson(dayExercisesJson, dayExerciseType);
                    getInstance(context).dayExerciseDao().insertDayExercises(dayExercises);

                    boolean[] daysOfWeek = new boolean[7];
                    Arrays.fill(daysOfWeek, true);
                    Reminder reminder = new Reminder(6, 0, false, daysOfWeek, true);
                    long reminderId = getInstance(context).reminderDao().insertReminder(reminder);
                    ReminderItem reminderItem = new ReminderItem((int) reminderId);
                    reminderItem.setHour(reminder.getHour());
                    reminderItem.setMinute(reminder.getMinute());
                    reminderItem.setAM(reminder.isAM());
                    reminderItem.setDaysOfWeek(reminder.getDaysOfWeek());
                    reminderItem.setEnabled(reminder.isEnabled());
                    AlarmUtils.scheduleReminder(context, reminderItem);
                    Hawk.put(HawkKeys.DATABASE_DATA_INITIALIZED_KEY, true);
                }).start();
            }
        });
    }

    public static boolean isDataInitialized() {
        return Hawk.get(HawkKeys.DATABASE_DATA_INITIALIZED_KEY, false);
    }

    public static void updateLanguage(Context context, OnUpdateListener listener){
        if(!isDataInitialized()) return;
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        CustomSignals customSignals = new CustomSignals.Builder()
                .put(LANGUAGE_CODE, Hawk.get(HawkKeys.LANGUAGE_CODE_SNIP_KEY))
                .build();
        remoteConfig.setCustomSignals(customSignals);
        remoteConfig.setConfigSettingsAsync(configSettings);
        remoteConfig.fetchAndActivate().addOnCanceledListener(() -> {

        }).addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                remoteConfig.activate();
                String coursesJson = remoteConfig.getString(COURSES_DATA);
                String exercisesJson = remoteConfig.getString(EXERCISES_DATA);

                new Thread(() -> {
                    Type coursesType = new TypeToken<List<Course>>() {
                    }.getType();
                    List<Course> courses = new Gson().fromJson(coursesJson, coursesType);
                    getInstance(context).courseDao().updateCourses(courses);

                    Type exercisesType = new TypeToken<List<Exercise>>() {
                    }.getType();
                    List<Exercise> exercises = new Gson().fromJson(exercisesJson, exercisesType);
                    getInstance(context).exerciseDao().updateExercises(exercises);
                    listener.onCompleted();
                }).start();
            }
        });
    }

    public interface OnUpdateListener{
        void onCompleted();
    }

    public static void resetAll(Context context) {
        new Thread(() -> {
            getInstance(context).dayDao().resetAll();
            getInstance(context).dayExerciseDao().resetAll();
            getInstance(context).dayHistoryDao().deleteAll();
        }).start();
    }
}
