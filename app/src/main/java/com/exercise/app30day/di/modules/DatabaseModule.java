package com.exercise.app30day.di.modules;

import android.content.Context;

import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.dao.ConversationDao;
import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.dao.DayDao;
import com.exercise.app30day.data.dao.DayExerciseDao;
import com.exercise.app30day.data.dao.DayHistoryDao;
import com.exercise.app30day.data.dao.ExerciseDao;
import com.exercise.app30day.data.dao.HistoryChatDao;
import com.exercise.app30day.data.dao.ReminderDao;
import com.exercise.app30day.data.dao.WeightHistoryDao;

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
    public static AppDatabase provideAppDatabase(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context);
    }

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
    public static DayDao provideDayDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).dayDao();
    }

    @Provides
    @Singleton
    public static DayExerciseDao provideDayExerciseDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).dayExerciseDao();
    }

    @Provides
    @Singleton
    public static DayHistoryDao provideDayHistoryDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).dayHistoryDao();
    }

    @Provides
    @Singleton
    public static WeightHistoryDao provideWeightHistoryDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).weightHistoryDao();
    }

    @Provides
    @Singleton
    public static ReminderDao provideReminderDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).reminderDao();
    }
    
    @Provides
    @Singleton
    public static ConversationDao provideConversationDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).conversationDao();
    }
    
    @Provides
    @Singleton
    public static HistoryChatDao provideHistoryChatDao(@ApplicationContext Context context) {
        return AppDatabase.getInstance(context).historyChatDao();
    }
}
