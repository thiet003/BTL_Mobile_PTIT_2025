package com.exercise.app30day.features.exercise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.repositories.DayExerciseRepository;
import com.exercise.app30day.data.repositories.DayRepository;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.TimeUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExerciseViewModel extends ViewModel {

    private final MutableLiveData<ExerciseUiState> _onExerciseUiState = new MutableLiveData<>(new ExerciseUiState());

    public LiveData<ExerciseUiState> onExerciseUiState = _onExerciseUiState;

    private long timeCounter = 0;

    private final long exerciseStartTime = System.currentTimeMillis();

    private DayItem dayItem;

    private CourseItem courseItem;

    private List<ExerciseItem> listExerciseItem;

    private boolean playExercise = true;

    private final DayRepository dayRepository;

    private final DayExerciseRepository dayExerciseRepository;

    @Inject
    public ExerciseViewModel(DayRepository dayRepository, DayExerciseRepository dayExerciseRepository) {
        this.dayRepository = dayRepository;
        this.dayExerciseRepository = dayExerciseRepository;
    }


    public void movePrepareToExercise(){
        timeCounter = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            state.setExerciseState(ExerciseState.EXERCISE);
            _onExerciseUiState.setValue(state);
        }
    }

    public void moveExerciseToRest(OnCompleteListener listener){
        timeCounter = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            if(state.getExercisePosition() < listExerciseItem.size() - 1){
                state.setExerciseState(ExerciseState.REST);
                state.setExercisePosition(state.getExercisePosition() + 1);
                _onExerciseUiState.setValue(state);
                dayExerciseRepository.updateDayExercise(dayItem.getId(), listExerciseItem.get(getExercisePosition()).getId(), true);
                listener.onCompleteExercise(listExerciseItem.get(state.getExercisePosition()));
            }else{
                dayRepository.updateDay(dayItem.getId(), true);
                listener.onCompleteDay();
            }
        }
    }

    public void moveRestToExercise(){
        timeCounter = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            state.setExerciseState(ExerciseState.EXERCISE);
            _onExerciseUiState.setValue(state);
        }
    }

    public void movePreviousExercise(){
        timeCounter = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            if(state.getExercisePosition() > 0){
                state.setExercisePosition(state.getExercisePosition() - 1);
                _onExerciseUiState.setValue(state);
            }
        }
    }

    public void setListExerciseItem(List<ExerciseItem> listExerciseItem){
        this.listExerciseItem = listExerciseItem;
    }

    public List<ExerciseItem> getListExerciseItem(){
        return listExerciseItem;
    }

    public int getExercisePosition(){
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            return state.getExercisePosition();
        }
        return 0;
    }

    public String getLoopOrDuration(ExerciseItem item){
        return item.getTime() != 0 ? TimeUtils.formatMillisecondsToMMSS(item.getTime()) : "x" + item.getLoopNumber();
    }

    public long calculateDuration(ExerciseItem item){
        return item.getTime() != 0 ? item.getTime() : item.getLoopNumber() * 3000L;
    }

    public long getTimeCounter() {
        return timeCounter;
    }

    public void updateTimeCounter(long timeCounter) {
        this.timeCounter = timeCounter;
    }

    public long getExerciseStartTime() {
        return exerciseStartTime;
    }

    public DayItem getDayItem() {
        return dayItem;
    }

    public void setDayItem(DayItem dayItem) {
        this.dayItem = dayItem;
    }

    public CourseItem getCourseItem() {
        return courseItem;
    }

    public void setCourseItem(CourseItem courseItem) {
        this.courseItem = courseItem;
    }

    public boolean isPlayExercise() {
        return playExercise;
    }

    public void setPlayExercise(boolean playExercise) {
        this.playExercise = playExercise;
    }
}
