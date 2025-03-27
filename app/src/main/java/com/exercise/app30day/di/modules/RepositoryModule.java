package com.exercise.app30day.di.modules;

import com.exercise.app30day.data.repositories.CourseDayExerciseRepository;
import com.exercise.app30day.data.repositories.CourseRepository;
import com.exercise.app30day.data.repositories.impl.CourseDayExerciseRepositoryImpl;
import com.exercise.app30day.data.repositories.impl.CourseRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class RepositoryModule {
    @Provides
    @Singleton
    public static CourseRepository provideCourseRepository(CourseRepositoryImpl courseRepository) {
        return courseRepository;
    }

    @Provides
    @Singleton
    public static CourseDayExerciseRepository provideCourseDayExerciseRepository(
            CourseDayExerciseRepositoryImpl courseDayExerciseRepository) {
        return courseDayExerciseRepository;
    }
}
