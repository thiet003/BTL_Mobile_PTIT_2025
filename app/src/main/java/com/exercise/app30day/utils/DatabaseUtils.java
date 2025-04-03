package com.exercise.app30day.utils;

import android.content.Context;

import com.exercise.app30day.MainApplication;
import com.exercise.app30day.keys.DataStoreKeys;
import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.models.ConcentrationArea;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.CourseDayExercise;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.ExerciseAttachment;
import com.exercise.app30day.data.models.ExerciseConcentrationArea;
import com.exercise.app30day.data.models.User;
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

    public static void seedData(AppDatabase db) {

        Context context = MainApplication.INSTANCE;
        // Insert concentration areas
        String areasJson = readJsonFromAsset(context, "concentration_areas.json");
        Type areasType = new TypeToken<List<ConcentrationArea>>(){}.getType();
        List<ConcentrationArea> areas = new Gson().fromJson(areasJson, areasType);
        db.concentrationAreaDao().insertConcentrationAreas(areas);

        // Insert courses
        String coursesJson = readJsonFromAsset(context, "courses.json");
        Type coursesType = new TypeToken<List<Course>>(){}.getType();
        List<Course> courses = new Gson().fromJson(coursesJson, coursesType);
        db.courseDao().insertCourses(courses);

        // Insert exercises
        String exercisesJson = readJsonFromAsset(context, "exercises.json");
        Type exercisesType = new TypeToken<List<Exercise>>(){}.getType();
        List<Exercise> exercises = new Gson().fromJson(exercisesJson, exercisesType);
        db.exerciseDao().insertExercises(exercises);

        // Insert exercise attachments
        String attachmentsJson = readJsonFromAsset(context, "exercise_attachments.json");
        Type attachmentsType = new TypeToken<List<ExerciseAttachment>>(){}.getType();
        List<ExerciseAttachment> attachments = new Gson().fromJson(attachmentsJson, attachmentsType);
        db.exerciseAttachmentDao().insertExerciseAttachments(attachments);

        // Insert exercise-concentration area mappings
        String exerciseConcentrationAreasJson = readJsonFromAsset(context, "exercise_concentration_areas.json");
        Type exerciseConcentrationAreasType = new TypeToken<List<ExerciseConcentrationArea>>(){}.getType();
        List<ExerciseConcentrationArea> exerciseConcentrationAreas = new Gson().fromJson(exerciseConcentrationAreasJson, exerciseConcentrationAreasType);
        db.exerciseConcentrationAreaDao().insertExerciseConcentrationAreas(exerciseConcentrationAreas);

        // Insert course day exercises
        String courseDayExercisesJson = readJsonFromAsset(context, "course_day_exercises.json");
        Type courseDayExercisesType = new TypeToken<List<CourseDayExercise>>(){}.getType();
        List<CourseDayExercise> courseDayExercises = new Gson().fromJson(courseDayExercisesJson, courseDayExercisesType);
        db.courseDayExerciseDao().insertCourseDayExercises(courseDayExercises);

        User user = new User();
        user.setId(1);
        db.userDao().insertUser(user);
        Hawk.put(DataStoreKeys.INSTANCE_USER_KEY, user);
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