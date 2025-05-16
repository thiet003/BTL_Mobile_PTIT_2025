package com.exercise.app30day.features.setup;

import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.repositories.UserRepository;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserSetupViewModel extends ViewModel {

    private final UserRepository userRepository;

    @Inject
    public UserSetupViewModel(UserRepository userRepository) {
        this.userRepository = userRepository;
    }
    public void saveUserInfo(String name, double height, double weight) {
        User user = new User(name, height, weight);
        userRepository.insertUser(user);
    }
}
