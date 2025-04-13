package com.exercise.app30day.features.complete;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.R;
import com.exercise.app30day.items.WeightPickerItem;

import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ExerciseCompleteViewModel extends ViewModel {

    private final MutableLiveData<UserUiState> _onUserUiState = new MutableLiveData<>(new UserUiState());
    public LiveData<UserUiState> onUserUiState = _onUserUiState;

    private final MutableLiveData<BmiResult> _onBmiResult = new MutableLiveData<>();
    public LiveData<BmiResult> onBmiResult = _onBmiResult;

    private final Observer<UserUiState> userObserver = this::updateBmi;

    private final Context context;

    @Inject
    public ExerciseCompleteViewModel(@ApplicationContext Context context) {
        this.context = context;
        onUserUiState.observeForever(userObserver);
    }

    public int getGenderFocusedIndex() {
        return Objects.requireNonNull(onUserUiState.getValue()).getGenderFocusedIndex();
    }

    public void setGenderFocusedIndex(int genderFocusedIndex) {
        UserUiState userUiState = Objects.requireNonNull(_onUserUiState.getValue());
        userUiState.setGenderFocusedIndex(genderFocusedIndex);
        _onUserUiState.setValue(userUiState);
    }

    public List<String> getGenders() {
        return List.of(
                context.getString(R.string.female),
                context.getString(R.string.other),
                context.getString(R.string.male)
        );
    }

    public int getWeightPickerIndex() {
        return Objects.requireNonNull(_onUserUiState.getValue()).getWeightPickerIndex();
    }

    public void setWeightPickerIndex(int weightPickerIndex) {
        UserUiState userUiState = Objects.requireNonNull(_onUserUiState.getValue());
        userUiState.setWeightPickerIndex(weightPickerIndex);
        _onUserUiState.setValue(userUiState);
    }

    public int getHeightPickerIndex() {
        return Objects.requireNonNull(_onUserUiState.getValue()).getHeightPickerIndex();
    }

    public void setHeightPickerIndex(int heightPickerIndex) {
        UserUiState userUiState = Objects.requireNonNull(_onUserUiState.getValue());
        userUiState.setHeightPickerIndex(heightPickerIndex);
        _onUserUiState.setValue(userUiState);
    }

    public List<WeightPickerItem> getWeights() {
        return IntStream.range(30, 200)
                .mapToObj(i -> new WeightPickerItem(i - 29, i))
                .collect(Collectors.toList());
    }

    public List<CharSequence> getHeights(){
        return IntStream.range(130, 250)
                .mapToObj(String::valueOf)
                .collect(Collectors.toList());
    }

    private void updateBmi(UserUiState userUiState){
        BmiResult result = calculateBmi(userUiState.getGenderFocusedIndex(),
                getWeights().get(userUiState.getWeightPickerIndex()).getValue(),
                Integer.parseInt(getHeights().get(userUiState.getHeightPickerIndex()).toString()));
        _onBmiResult.setValue(result);
    }

    private BmiResult calculateBmi(int gender, double weightKg, double heightCm) {
        double heightM = heightCm / 100.0;
        double bmi = weightKg / (heightM * heightM);
        String status;
        String color;

        if (gender == 0) {
            if (bmi < 18.5) {
                status = "You are Underweight";
                color = "#2196F3"; // Blue
            } else if (bmi < 23) {
                status = "You are Healthy";
                color = "#4CAF50"; // Green
            } else if (bmi < 27) {
                status = "You are Overweight";
                color = "#FF9800"; // Orange
            } else {
                status = "You are Suffering from Obesity";
                color = "#F44336"; // Red
            }
        } else if (gender == 2) {
            if (bmi < 18.5) {
                status = "You are Underweight";
                color = "#2196F3";
            } else if (bmi < 25) {
                status = "You are Healthy";
                color = "#4CAF50";
            } else if (bmi < 30) {
                status = "You are Overweight";
                color = "#FF9800";
            } else {
                status = "You are Suffering from Obesity";
                color = "#F44336";
            }
        } else { // "other" or non-binary
            if (bmi < 18.5) {
                status = "You are Underweight";
                color = "#2196F3";
            } else if (bmi < 24) {
                status = "You are Healthy";
                color = "#4CAF50";
            } else if (bmi < 28) {
                status = "You are Overweight";
                color = "#FF9800";
            } else {
                status = "You are Suffering from Obesity";
                color = "#F44336";
            }
        }

        return new BmiResult(bmi, color, status);
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        onUserUiState.removeObserver(userObserver);
    }
}
