package com.exercise.app30day.features.home;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.UserRepository;
import com.exercise.app30day.models.User;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class HomeViewModel extends ViewModel {

    private final UserRepository userRepository;


    @Inject
    public HomeViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    public void insertUser(double height, double weight) {
        if(height < 0 || weight < 0) return;
        User user = new User();
        user.setHeight(height);
        user.setWeight(weight);
        new Thread(() -> userRepository.insertUser(user)).start();
    }

    public void updateUserById(int id, double height, double weight) {
        new Thread(() -> userRepository.updateUserById(id, height, weight)).start();
    }

    public void updateUser(User user) {
        new Thread(() -> userRepository.updateUser(user)).start();
    }

    public void deleteUser(User user) {
        new Thread(() -> userRepository.deleteUser(user)).start();
    }

    public LiveData<List<User>> getAllUsers() {
        return userRepository.getAllUsers();
    }

    public LiveData<User> getUserById(int id) {
        return userRepository.getUserById(id);
    }
}
