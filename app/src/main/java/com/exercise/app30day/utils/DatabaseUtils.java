package com.exercise.app30day.utils;

import android.content.Context;

import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.Day;
import com.exercise.app30day.data.models.DayExercise;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.User;
import com.google.firebase.remoteconfig.FirebaseRemoteConfig;
import com.google.firebase.remoteconfig.FirebaseRemoteConfigSettings;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.orhanobut.hawk.Hawk;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.List;

public final class DatabaseUtils {

    public static void fetchData(AppDatabase db) {
        FirebaseRemoteConfig remoteConfig = FirebaseRemoteConfig.getInstance();
        FirebaseRemoteConfigSettings configSettings = new FirebaseRemoteConfigSettings.Builder()
                .setMinimumFetchIntervalInSeconds(0)
                .build();
        remoteConfig.setConfigSettingsAsync(configSettings);
        remoteConfig.fetchAndActivate().addOnCanceledListener(() -> {

        }).addOnCompleteListener(task -> {
            if(task.isSuccessful()){
                remoteConfig.activate();
                String coursesJson = remoteConfig.getString("courses_data");
                String exercisesJson = remoteConfig.getString("exercises_data");
                String daysJson = remoteConfig.getString("days_data");
                String dayExercisesJson = remoteConfig.getString("day_exercises_data");

                new Thread(()->{
                    System.out.println("coursesJson: " + coursesJson);
                    Type coursesType = new TypeToken<List<Course>>(){}.getType();
                    List<Course> courses = new Gson().fromJson(coursesJson, coursesType);
                    db.courseDao().insertCourses(courses);

                    Type exercisesType = new TypeToken<List<Exercise>>(){}.getType();
                    List<Exercise> exercises = new Gson().fromJson(exercisesJson, exercisesType);
                    db.exerciseDao().insertExercises(exercises);

                    Type daysType = new TypeToken<List<Day>>(){}.getType();
                    List<Day> days = new Gson().fromJson(daysJson, daysType);
                    db.dayDao().insertDays(days);

                    Type dayExerciseType = new TypeToken<List<DayExercise>>(){}.getType();
                    List<DayExercise> dayExercises = new Gson().fromJson(dayExercisesJson, dayExerciseType);
                    db.dayExerciseDao().insertDayExercises(dayExercises);
                    Hawk.put(HawkKeys.DATABASE_INITIALIZED, true);

                    User user = new User();
                    user.setId(1);
                    db.userDao().insertUser(user);
                    Hawk.put(HawkKeys.INSTANCE_USER_KEY, user);
                }).start();
            }
        });
    }

    private static String readJsonFromAsset(Context context, String fileName) {
        StringBuilder sb = new StringBuilder();
        try{
            InputStream is = context.getAssets().open("data/" + fileName);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));
            String line;
            while ((line = br.readLine()) != null) {
                sb.append(line);
            }
            br.close();
        }catch (IOException e){
            e.printStackTrace();
        }

        return sb.toString();
    }
}