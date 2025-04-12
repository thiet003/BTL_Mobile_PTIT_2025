package com.exercise.app30day.di.modules;

import android.content.Context;

import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.dao.DayDao;
import com.exercise.app30day.data.dao.DayExerciseDao;
import com.exercise.app30day.data.dao.ExerciseDao;
import com.exercise.app30day.data.dao.TrainingHistoryDao;
import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.dao.WeightDao;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class DatabaseModule {

    @Provides
    @Singleton
    public static CourseDao provideCourseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).courseDao();
    }

    @Provides
    @Singleton
    public static ExerciseDao provideExerciseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).exerciseDao();
    }

    @Provides
    @Singleton
    public static TrainingHistoryDao provideTrainingHistoryDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).trainingHistoryDao();
    }


    @Provides
    @Singleton
    public static UserDao provideUserDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).userDao();
    }

    @Provides
    @Singleton
    public static WeightDao provideWeightDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).weightDao();
    }

    @Provides
    @Singleton
    public static DayDao provideDayDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).dayDao();
    }

    @Provides
    @Singleton
    public static DayExerciseDao provideDayExerciseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).dayExerciseDao();
    }
    
}
