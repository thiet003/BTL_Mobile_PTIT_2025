package com.exercise.app30day.di.modules;

import android.content.Context;

import com.exercise.app30day.data.dao.CompleteDayDao;
import com.exercise.app30day.data.dao.CompleteExerciseDao;
import com.exercise.app30day.data.dao.ConcentrationAreaDao;
import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.dao.CourseDayExerciseDao;
import com.exercise.app30day.data.dao.ExerciseAttachmentDao;
import com.exercise.app30day.data.dao.ExerciseConcentrationAreaDao;
import com.exercise.app30day.data.dao.TrainingHistoryDao;
import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.dao.ExerciseDao;
import com.exercise.app30day.data.AppDatabase;
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
    public static CompleteDayDao provideCompleteDayDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).completeDayDao();
    }

    @Provides
    @Singleton
    public static CompleteExerciseDao provideCompleteExerciseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).completeExerciseDao();
    }

    @Provides
    @Singleton
    public static ConcentrationAreaDao provideConcentrationAreaDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).concentrationAreaDao();
    }

    @Provides
    @Singleton
    public static CourseDao provideCourseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).courseDao();
    }

    @Provides
    @Singleton
    public static CourseDayExerciseDao provideCourseDayExerciseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).courseDayExerciseDao();
    }

    @Provides
    @Singleton
    public static ExerciseAttachmentDao provideExerciseAttachmentDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).exerciseAttachmentDao();
    }

    @Provides
    @Singleton
    public static ExerciseDao provideExerciseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).exerciseDao();
    }

    @Provides
    @Singleton
    public static ExerciseConcentrationAreaDao provideExerciseConcentrationAreaDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).exerciseConcentrationAreaDao();
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
    
}
