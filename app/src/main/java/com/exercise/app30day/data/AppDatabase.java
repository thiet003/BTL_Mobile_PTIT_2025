package com.exercise.app30day.data;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.sqlite.db.SupportSQLiteDatabase;

import com.exercise.app30day.data.dao.CompleteDayDao;
import com.exercise.app30day.data.dao.CompleteExerciseDao;
import com.exercise.app30day.data.dao.ConcentrationAreaDao;
import com.exercise.app30day.data.dao.CourseDao;
import com.exercise.app30day.data.dao.CourseDayExerciseDao;
import com.exercise.app30day.data.dao.ExerciseAttachmentDao;
import com.exercise.app30day.data.dao.ExerciseConcentrationAreaDao;
import com.exercise.app30day.data.dao.ExerciseDao;
import com.exercise.app30day.data.dao.TrainingHistoryDao;
import com.exercise.app30day.data.dao.UserDao;
import com.exercise.app30day.data.dao.WeightDao;
import com.exercise.app30day.data.models.CompleteDay;
import com.exercise.app30day.data.models.CompleteExercise;
import com.exercise.app30day.data.models.ConcentrationArea;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.CourseDayExercise;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.ExerciseAttachment;
import com.exercise.app30day.data.models.ExerciseConcentrationArea;
import com.exercise.app30day.data.models.TrainingHistory;
import com.exercise.app30day.data.models.User;
import com.exercise.app30day.data.models.Weight;
import com.exercise.app30day.utils.DatabaseUtils;

@Database(entities = {
        CompleteDay.class,
        CompleteExercise.class,
        ConcentrationArea.class, 
        Course.class, 
        CourseDayExercise.class, 
        ExerciseAttachment.class, 
        ExerciseConcentrationArea.class, 
        Exercise.class, 
        User.class,
        Weight.class,
        TrainingHistory.class
}, version = 1, exportSchema = false)
public abstract class AppDatabase extends RoomDatabase {
    private static volatile AppDatabase instance;
    public abstract CompleteDayDao completeDayDao();
    public abstract CompleteExerciseDao completeExerciseDao();
    public abstract ConcentrationAreaDao concentrationAreaDao();
    public abstract CourseDao courseDao();
    public abstract CourseDayExerciseDao courseDayExerciseDao();
    public abstract ExerciseAttachmentDao exerciseAttachmentDao();
    public abstract ExerciseConcentrationAreaDao exerciseConcentrationAreaDao();
    public abstract ExerciseDao exerciseDao();
    public abstract TrainingHistoryDao trainingHistoryDao();
    
    public abstract UserDao userDao();
    
    public abstract WeightDao weightDao();
    
    public static AppDatabase getInstance(Context context) {
        if(instance == null){
            synchronized (AppDatabase.class){
                if(instance == null){
                    instance = Room.databaseBuilder(context.getApplicationContext(), AppDatabase.class, "app_database")
                            .fallbackToDestructiveMigration()
                            .addCallback(sRoomDatabaseCallback)
                            .build();
                }
            }
        }
        return instance;
    }
    
    private static final RoomDatabase.Callback sRoomDatabaseCallback = new RoomDatabase.Callback(){
        @Override
        public void onCreate(@NonNull SupportSQLiteDatabase db) {
            super.onCreate(db);
            new Thread(()-> DatabaseUtils.seedData(instance)).start();
        }
    };
    
    
}
