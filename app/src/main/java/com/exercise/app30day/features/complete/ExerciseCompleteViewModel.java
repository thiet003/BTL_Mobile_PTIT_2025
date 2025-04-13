package com.exercise.app30day.features.complete;

import android.content.Context;

import androidx.annotation.ColorRes;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.R;

import java.util.Calendar;
import java.util.List;
import java.util.Objects;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;
import dagger.hilt.android.qualifiers.ApplicationContext;

@HiltViewModel
public class ExerciseCompleteViewModel extends ViewModel {

    private final MutableLiveData<UserUiState> _onUserUiState = new MutableLiveData<>(new UserUiState());
    public final LiveData<UserUiState> onUserUiState = _onUserUiState;

    private final int maxWeight = 200, minWeight = 30, maxHeight = 250, minHeight = 100;

    private final List<String> genders;

    private final Context context;

    @Inject
    public ExerciseCompleteViewModel(@ApplicationContext Context context) {
        genders = List.of(
                context.getString(R.string.female),
                context.getString(R.string.other),
                context.getString(R.string.male)
        );
        this.context = context;
    }

    public List<String> getGenders() {
        return genders;
    }

    public int getUserGenderIndex() {
        return Objects.requireNonNull(_onUserUiState.getValue()).getUserGenderIndex();
    }

    public void setUserGenderIndex(int index) {
        if(index < 0 || index > genders.size()){
            return;
        }
        UserUiState userUiState = Objects.requireNonNull(_onUserUiState.getValue());
        userUiState.setUserGenderIndex(index);
        _onUserUiState.setValue(userUiState);
    }

    public int getUserHeight(){
        return Objects.requireNonNull(_onUserUiState.getValue()).getHeight();
    }

    public void setUserHeight(int height) {
        if(height < minHeight || height > maxHeight){
            return;
        }
        UserUiState userUiState = Objects.requireNonNull(_onUserUiState.getValue());
        userUiState.setHeight(height);
        _onUserUiState.setValue(userUiState);
    }

    public int getUserWeight(){
        return Objects.requireNonNull(_onUserUiState.getValue()).getWeight();
    }

    public void setUserWeight(int weight){
        if(weight < minWeight || weight > maxWeight){
            return;
        }
        UserUiState userUiState = Objects.requireNonNull(_onUserUiState.getValue());
        userUiState.setWeight(weight);
        _onUserUiState.setValue(userUiState);
    }

    public void setCalendar(Calendar calendar) {
        UserUiState userUiState = Objects.requireNonNull(_onUserUiState.getValue());
        userUiState.setCalendar(calendar);
        _onUserUiState.setValue(userUiState);
    }

    public Calendar getCalendar() {
        return Objects.requireNonNull(_onUserUiState.getValue()).getCalendar();
    }

    public int getMaxWeight() {
        return maxWeight;
    }

    public int getMinWeight() {
        return minWeight;
    }

    public int getMaxHeight() {
        return maxHeight;
    }

    public int getMinHeight() {
        return minHeight;
    }

    public BmiResult calculateBmi(UserUiState userUiState) {

        double weightKg = userUiState.getWeight();
        double heightCm = userUiState.getHeight();

        double heightM = heightCm / 100.0;
        double bmi = weightKg / (heightM * heightM);

        int status;
        int color;

        if (bmi < 18.5) {
            status = R.string.bmi_underweight;
            color = R.color.blue; // Blue
        } else if (bmi < 25) {
            status = R.string.bmi_normal;
            color = R.color.green; // Green
        } else if (bmi < 30) {
            status = R.string.bmi_pre_obese;
            color = R.color.orange; // Orange
        } else if (bmi < 35) {
            status = R.string.bmi_obese_class_1;
            color = R.color.red; // Red
        } else if (bmi < 40) {
            status = R.string.bmi_obese_class_2;
            color = R.color.dark_red; // Dark Red
        } else {
            status = R.string.bmi_obese_class_3;
            color = R.color.deep_dark_red; // Deep Dark Red
        }

        return new BmiResult(bmi, color, status);
    }

}
