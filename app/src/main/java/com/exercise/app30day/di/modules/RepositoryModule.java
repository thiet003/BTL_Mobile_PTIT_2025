package com.exercise.app30day.di.modules;

import com.exercise.app30day.data.repositories.CompleteDayRepository;
import com.exercise.app30day.data.repositories.CompleteExerciseRepository;
import com.exercise.app30day.data.repositories.CourseDayExerciseRepository;
import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.ExerciseRepository;
import com.exercise.app30day.data.repositories.impl.CompleteDayRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.CompleteExerciseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.CourseDayExerciseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.CourseRepositoryImpl;
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
    public abstract CourseDayExerciseRepository bindCourseDayExerciseRepository(CourseDayExerciseRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract CompleteDayRepository bindCompleteDayRepository(CompleteDayRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract ExerciseRepository bindExerciseRepository(ExerciseRepositoryImpl impl);

    @Binds
    @Singleton
    public abstract CompleteExerciseRepository bindCompleteExerciseRepository(CompleteExerciseRepositoryImpl impl);

}
