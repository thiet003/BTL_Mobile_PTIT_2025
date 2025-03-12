package com.exercise.app30day.di.modules;


import android.content.Context;

import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.database.UserDatabase;
import com.exercise.app30day.data.repositories.UserRepository;
import com.exercise.app30day.data.repositories.impl.UserRepositoryImpl;

import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import dagger.hilt.InstallIn;
import dagger.hilt.android.qualifiers.ApplicationContext;
import dagger.hilt.components.SingletonComponent;

@Module
@InstallIn(SingletonComponent.class)
public class UserModule {

    @Provides
    @Singleton
    public static UserDao provideUserDao(@ApplicationContext Context context) {
        return UserDatabase.getInstance(context).userDao();
    }


    @Provides
    @Singleton
    public static UserRepository provideUserRepository(UserDao userDao) {
        return new UserRepositoryImpl(userDao);
    }
}
