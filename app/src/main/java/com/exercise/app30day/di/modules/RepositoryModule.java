package com.exercise.app30day.di.modules;

import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.DayExerciseRepository;
import com.exercise.app30day.data.repositories.DayRepository;
import com.exercise.app30day.data.repositories.DayTimeRepository;
import com.exercise.app30day.data.repositories.ExerciseRepository;
import com.exercise.app30day.data.repositories.impl.CourseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.DayExerciseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.DayRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.DayTimeRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.ExerciseRepositoryImpl;

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
    public abstract DayTimeRepository bindDayTimeRepository(DayTimeRepositoryImpl impl);

}
