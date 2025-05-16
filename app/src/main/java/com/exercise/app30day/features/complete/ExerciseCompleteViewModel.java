package com.exercise.app30day.features.complete;

import static com.exercise.app30day.config.AppConfig.MAX_HEIGHT;
import static com.exercise.app30day.config.AppConfig.MAX_WEIGHT;
import static com.exercise.app30day.config.AppConfig.MIN_HEIGHT;
import static com.exercise.app30day.config.AppConfig.MIN_WEIGHT;

import android.annotation.SuppressLint;
import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.R;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.repositories.DayHistoryRepository;
import com.exercise.app30day.data.repositories.UserRepository;
import com.exercise.app30day.items.DayHistoryItem;
import com.exercise.app30day.items.UserItem;
import com.exercise.app30day.utils.TimeUtils;

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

    private final DayHistoryRepository dayHistoryRepository;

    private final UserRepository userRepository;
    private final List<String> genders;

    @Inject
    public ExerciseCompleteViewModel(@ApplicationContext Context context, DayHistoryRepository dayHistoryRepository, UserRepository userRepository) {
        genders = List.of(
                context.getString(R.string.female),
                context.getString(R.string.other),
                context.getString(R.string.male)
        );
        this.dayHistoryRepository = dayHistoryRepository;
        this.userRepository = userRepository;
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
        if(height < MIN_HEIGHT || height > MAX_HEIGHT){
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
        if(weight < MIN_WEIGHT || weight > MAX_WEIGHT){
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

    public BmiResult calculateBmi(UserUiState userUiState) {

        double weightKg = userUiState.getWeight();
        double heightCm = userUiState.getHeight();
        int gender = userUiState.getUserGenderIndex();

        double heightM = heightCm / 100.0;
        double bmi = weightKg / (heightM * heightM);

        int status;
        int color;
        if(gender == 2){
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
        }else if(gender == 0){
            if (bmi < 16) {
                status = R.string.bmi_underweight;
                color = R.color.blue; // Blue
            } else if (bmi < 18.5) {
                status = R.string.bmi_normal;
                color = R.color.green; // Green
            } else if (bmi < 25) {
                status = R.string.bmi_pre_obese;
                color = R.color.orange; // Orange
            } else if (bmi < 30) {
                status = R.string.bmi_obese_class_1;
                color = R.color.red; // Red
            } else if (bmi < 35) {
                status = R.string.bmi_obese_class_2;
                color = R.color.dark_red; // Dark Red
            } else {
                status = R.string.bmi_obese_class_3;
                color = R.color.deep_dark_red; // Deep Dark Red
            }
        }else{
            if (bmi < 16) {
                status = R.string.bmi_underweight;
                color = R.color.blue;
            } else if (bmi < 18.5) {
                status = R.string.bmi_normal;
                color = R.color.green;
            } else if (bmi < 25) {
                status = R.string.bmi_pre_obese;
                color = R.color.orange;
            } else if (bmi < 30) {
                status = R.string.bmi_obese_class_1;
                color = R.color.red;
            } else if (bmi < 35) {
                status = R.string.bmi_obese_class_2;
                color = R.color.dark_red;
            } else {
                status = R.string.bmi_obese_class_3;
                color = R.color.deep_dark_red;
            }
        }

        return new BmiResult(bmi, color, status);
    }

    @SuppressLint("DefaultLocale")
    public String calculateAndFormatCalories(List<DayHistoryItem> dayHistoryItems){
        if(dayHistoryItems == null || dayHistoryItems.isEmpty()){
            return "0.0";
        }
        double totalCalo = 0;
        for (DayHistoryItem item : dayHistoryItems){
            totalCalo += item.getKcal();
        }
        return String.format("%.1f", totalCalo).replace(",", ".");
    }

    public String calculateAndFormatTotalTime(List<DayHistoryItem> dayHistoryItems){
        long totalTime = 0;
        for (DayHistoryItem item : dayHistoryItems){
            totalTime += item.getStopTime() - item.getStartTime();
        }
        return TimeUtils.formatMillisecondsToMMSS(totalTime);

    }

    public LiveData<List<DayHistoryItem>> getDayHistoryItems(int dayId){
        return dayHistoryRepository.getDayHistoryItems(dayId);
    }

    public void saveUser(double height, double weight){
        new Thread(()->{
            UserItem userItem = userRepository.getUserItemSync();
            User user = new User(userItem.getName(), height, weight);
            userRepository.insertUser(user);
        }).start();
    }
}
