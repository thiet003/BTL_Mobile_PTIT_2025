package com.exercise.app30day.features.setup;

import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.models.WeightHistory;
import com.exercise.app30day.data.repositories.WeightHistoryRepository;
import com.exercise.app30day.utils.HawkKeys;
import com.orhanobut.hawk.Hawk;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class UserSetupViewModel extends ViewModel {

    private final WeightHistoryRepository weightHistoryRepository;

    @Inject
    public UserSetupViewModel(WeightHistoryRepository weightHistoryRepository) {
        this.weightHistoryRepository = weightHistoryRepository;
    }
    public void saveUserInfo(String name, int height, double weight) {
        User user = new User(0, name, height);
        User existingUser = Hawk.get(HawkKeys.INSTANCE_USER_KEY);
        if (existingUser != null) {
            existingUser.setName(name);
            existingUser.setHeight(height);
            Hawk.put(HawkKeys.INSTANCE_USER_KEY, existingUser);
        }
        else {
            Hawk.put(HawkKeys.INSTANCE_USER_KEY, user);
        }
        saveWeightHistory(weight);
        Hawk.put(HawkKeys.PROFILE_SETUP_KEY, true);
    }
    public void saveWeightHistory(double weight) {
        WeightHistory weightHistory = new WeightHistory(weight, System.currentTimeMillis());
        weightHistoryRepository.insertWeightHistory(weightHistory);
    }
}
