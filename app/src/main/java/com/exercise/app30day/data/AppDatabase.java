package com.exercise.app30day.data;

import android.content.Context;

import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;

import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.dao.DayDao;
import com.exercise.app30day.data.dao.DayExerciseDao;
import com.exercise.app30day.data.dao.DayTimeDao;
import com.exercise.app30day.data.dao.ExerciseDao;
import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.dao.WeightDao;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.Day;
import com.exercise.app30day.data.models.DayExercise;
import com.exercise.app30day.data.models.DayTime;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.models.Weight;
import com.exercise.app30day.utils.HawkKeys;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.hawk.Hawk;

import java.lang.reflect.Type;
import java.util.List;

@Database(entities = {
        Course.class,
        Exercise.class, 
        User.class,
        Weight.class,
        Day.class,
        DayExercise.class,
        DayTime.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public abstract CourseDao courseDao();
    public abstract ExerciseDao exerciseDao();
    public abstract UserDao userDao();
    public abstract WeightDao weightDao();
    public abstract DayDao dayDao();
    public abstract DayExerciseDao dayExerciseDao();
    public abstract DayTimeDao dayTimeDao();
    
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
                String coursesJson = remoteConfig.getString("courses_data");
                String exercisesJson = remoteConfig.getString("exercises_data");
                String daysJson = remoteConfig.getString("days_data");
                String dayExercisesJson = remoteConfig.getString("day_exercises_data");

                new Thread(() -> {
                    System.out.println("coursesJson: " + coursesJson);
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

                    User user = new User();
                    user.setId(1);
                    getInstance(context).userDao().insertUser(user);
                    Hawk.put(HawkKeys.INSTANCE_USER_KEY, user);

                    Hawk.put(HawkKeys.DATABASE_DATA_INITIALIZED_KEY, true);
                }).start();
            }
        });
    }

    public static boolean isDataInitialized() {
        return Hawk.get(HawkKeys.DATABASE_DATA_INITIALIZED_KEY, false);
    }
}
