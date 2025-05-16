package com.exercise.app30day.di.modules;

import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.DayExerciseRepository;
import com.exercise.app30day.data.repositories.DayHistoryRepository;
import com.exercise.app30day.data.repositories.DayRepository;
import com.exercise.app30day.data.repositories.ExerciseRepository;
import com.exercise.app30day.data.repositories.ReminderRepository;
import com.exercise.app30day.data.repositories.UserRepository;
import com.exercise.app30day.data.repositories.impl.CourseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.DayExerciseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.DayHistoryRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.DayRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.ExerciseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.ReminderRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Binds;
import dagger.Module;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public abstract class RepositoryModule {
    @Binds
    @Singleton
    public abstract CourseRepository bindCourseRepository(CourseRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract ExerciseRepository bindExerciseRepository(ExerciseRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract DayRepository bindDayRepository(DayRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract DayExerciseRepository bindDayExerciseRepository(DayExerciseRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract DayHistoryRepository bindDayHistoryRepository(DayHistoryRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract ReminderRepository bindReminderRepository(ReminderRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract UserRepository bindUserRepository(UserRepositoryImpl impl);
}
