package com.exercise.app30day.features.exercise;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import com.exercise.app30day.data.models.DayHistory;
import com.exercise.app30day.data.repositories.DayExerciseRepository;
import com.exercise.app30day.data.repositories.DayHistoryRepository;
import com.exercise.app30day.data.repositories.DayRepository;
import com.exercise.app30day.items.CourseItem;
import com.exercise.app30day.items.DayItem;
import com.exercise.app30day.items.ExerciseItem;
import com.exercise.app30day.items.MusicItem;
import com.exercise.app30day.utils.TimeUtils;

import java.util.List;

import javax.inject.Inject;

import dagger.hilt.android.lifecycle.HiltViewModel;

@HiltViewModel
public class ExerciseViewModel extends ViewModel {

    private final MutableLiveData<ExerciseUiState> _onExerciseUiState = new MutableLiveData<>(new ExerciseUiState());

    public LiveData<ExerciseUiState> onExerciseUiState = _onExerciseUiState;

    private final MutableLiveData<Boolean> _onPlayExercise = new MutableLiveData<>(true);
    public LiveData<Boolean> onPlayExercise = _onPlayExercise;

    private long timeCounter = 0;

    private DayItem dayItem;

    private CourseItem courseItem;

    private List<ExerciseItem> listExerciseItem;

    private DayHistory dayHistory;

    private final Observer<ExerciseUiState> exerciseUiStateObserver = this::updateDayHistoryExerciseItem;

    private final DayRepository dayRepository;

    private final DayExerciseRepository dayExerciseRepository;

    private final DayHistoryRepository dayHistoryRepository;

    private final List<MusicItem> musicItems;

    private boolean isPortrait = true;

    @Inject
    public ExerciseViewModel(DayRepository dayRepository, DayExerciseRepository dayExerciseRepository, DayHistoryRepository dayHistoryRepository) {
        this.dayRepository = dayRepository;
        this.dayExerciseRepository = dayExerciseRepository;
        this.dayHistoryRepository = dayHistoryRepository;
        musicItems = MusicItem.getMusicItems();
    }


    public void movePrepareToExercise(){
        timeCounter = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            state.setExerciseState(ExerciseState.EXERCISE);
            _onExerciseUiState.setValue(state);
        }
        this.dayHistory.setCreatedAt(System.currentTimeMillis());
    }

    private long exerciseStartTime = 0;
    public void moveExerciseToRest(OnCompleteListener listener){
        timeCounter = 0;
        long exerciseDuration = System.currentTimeMillis() - exerciseStartTime;
        exerciseStartTime = 0;
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            ExerciseItem exerciseItem = listExerciseItem.get(getExercisePosition());
            double kcal = exerciseItem.getKcal() * (Math.min((double) exerciseDuration/ exerciseItem.getTime(), 1.0));
            dayHistory.setKcal(dayHistory.getKcal() + kcal);
            if(state.getExercisePosition() < listExerciseItem.size() - 1){
                state.setExerciseState(ExerciseState.REST);
                state.setExercisePosition(state.getExercisePosition() + 1);
                _onExerciseUiState.setValue(state);
                _onPlayExercise.setValue(true);
                dayExerciseRepository.updateDayExercise(dayItem.getId(), listExerciseItem.get(getExercisePosition()).getId(), true);
                listener.onCompleteExercise(listExerciseItem.get(state.getExercisePosition()));
            }else{
                dayRepository.updateDay(dayItem.getId(), true);
                dayHistory.setExercisePosition(0);
                listener.onCompleteDay();
            }
        }
    }

    public void moveRestToExercise(){
        timeCounter = 0;
        exerciseStartTime = System.currentTimeMillis();
        ExerciseUiState state = _onExerciseUiState.getValue();
        if(state != null){
            state.setExerciseState(ExerciseState.EXERCISE);
            _onExerciseUiState.setValue(state);
        }
    }

    public void movePreviousExercise(){
        timeCounter = 0;
        exerciseStartTime = System.currentTimeMillis();
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

    public void initData(List<ExerciseItem> listExerciseItem, DayItem dayItem, CourseItem courseItem, int currentExercisePosition){
        this.listExerciseItem = listExerciseItem;
        this.dayItem = dayItem;
        this.courseItem = courseItem;
        this.dayHistory = new DayHistory(dayItem.getId(), currentExercisePosition);

        _onExerciseUiState.observeForever(exerciseUiStateObserver);

        ExerciseUiState exerciseUiState = _onExerciseUiState.getValue();
        if(exerciseUiState != null){
            exerciseUiState.setExercisePosition(currentExercisePosition);
            _onExerciseUiState.setValue(exerciseUiState);
        }
    }

    private void updateDayHistoryExerciseItem(ExerciseUiState exerciseUiState) {
        this.dayHistory.setExercisePosition(exerciseUiState.getExercisePosition());
    }

    public void saveStopTime(){
        this.dayHistory.setStopTime(System.currentTimeMillis());
        this.dayHistoryRepository.insertDayHistory(this.dayHistory);
    }

    public void addRestTime(long addedTime){
        this.dayHistory.setRestTime(this.dayHistory.getRestTime() + addedTime);
    }

    public int findMusicItemPosition(int id) {
        for (int i = 0; i < musicItems.size(); i++) {
            if (musicItems.get(i).getId() == id) {
                return i;
            }
        }
        return -1;
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        _onExerciseUiState.removeObserver(this.exerciseUiStateObserver);
    }

    public long getTimeCounter() {
        return timeCounter;
    }

    public void updateTimeCounter(long timeCounter) {
        this.timeCounter = timeCounter;
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
        return Boolean.TRUE.equals(onPlayExercise.getValue());
    }

    public void setPlayExercise(boolean playExercise) {
        _onPlayExercise.setValue(playExercise);
    }

    public List<MusicItem> getMusicItems() {
        return musicItems;
    }

    public MusicItem finMusicItem(int id) {
        for (MusicItem item : musicItems) {
            if (item.getId() == id) {
                return item;
            }
        }
        return null;
    }

    public String getFirstSentence(String text){
        if (text == null || text.isEmpty()) {
            return "";
        }
        String[] sentences = text.split("(?<=[.!?])\\s*");
        if (sentences.length > 0) {
            return sentences[0];
        }
        return text;
    }

    public boolean isPortrait() {
        return isPortrait;
    }

    public void setPortrait(boolean portrait) {
        isPortrait = portrait;
    }
}
