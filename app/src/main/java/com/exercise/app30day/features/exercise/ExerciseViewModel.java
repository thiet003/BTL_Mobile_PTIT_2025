package com.exercise.app30day.features.exercise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.utils.TimeUtils;

import java.util.List;

public class ExerciseViewModel extends ViewModel {

    private final MutableLiveData<ExerciseUiState> _onExerciseUiState = new MutableLiveData<>(new ExerciseUiState());

    public LiveData<ExerciseUiState> onExerciseUiState = _onExerciseUiState;

    private long timeCounter = 0;


    public void movePrepareToExercise(){
        timeCounter = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            state.setExerciseState(ExerciseState.EXERCISE);
            _onExerciseUiState.setValue(state);
        }
    }

    public void moveExerciseToRest(){
        timeCounter = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            state.setExerciseState(ExerciseState.REST);
            state.setExercisePosition(state.getExercisePosition() + 1);
            _onExerciseUiState.setValue(state);
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

    public void updateListExerciseItem(List<ExerciseItem> listExerciseItem){
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            state.setListExerciseItem(listExerciseItem);
            _onExerciseUiState.setValue(state);
        }
    }

    public List<ExerciseItem> getListExerciseItem(){
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            return state.getListExerciseItem();
        }
        return List.of();
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

    public long calculateTime(ExerciseItem item){
        return item.getTime() != 0 ? item.getTime() : item.getLoopNumber() * 3000L;
    }

    public long getTimeCounter() {
        return timeCounter;
    }

    public void updateTimeCounter(long timeCounter) {
        this.timeCounter = timeCounter;
    }
}
