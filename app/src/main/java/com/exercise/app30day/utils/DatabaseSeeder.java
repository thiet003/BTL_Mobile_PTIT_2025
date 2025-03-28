package com.exercise.app30day.utils;

import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.models.ConcentrationArea;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.CourseDayExercise;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.ExerciseAttachment;
import com.exercise.app30day.data.models.ExerciseConcentrationArea;
import com.exercise.app30day.data.models.User;
import com.orhanobut.hawk.Hawk;

import java.util.ArrayList;
import java.util.List;

public final class DatabaseSeeder {

    /**
     * Concentration Areas - muscle groups
     */
    public static List<ConcentrationArea> getConcentrationAreas() {
        List<ConcentrationArea> areas = new ArrayList<>();

        areas.add(new ConcentrationArea("Abs", "Abdominal muscles including rectus abdominis, obliques, and transverse abdominis"));
        areas.add(new ConcentrationArea("Arms", "Upper and lower arm muscles including biceps, triceps, and forearms"));
        areas.add(new ConcentrationArea("Chest", "Pectoral muscles including pectoralis major and pectoralis minor"));
        areas.add(new ConcentrationArea("Back", "Back muscles including latissimus dorsi, rhomboids, and trapezius"));
        areas.add(new ConcentrationArea("Shoulders", "Deltoid muscles and rotator cuff"));
        areas.add(new ConcentrationArea("Legs", "Leg muscles including quadriceps, hamstrings, and calves"));
        areas.add(new ConcentrationArea("Glutes", "Gluteal muscles including gluteus maximus, medius, and minimus"));
        areas.add(new ConcentrationArea("Core", "Deep core muscles including transverse abdominis and erector spinae"));
        areas.add(new ConcentrationArea("Full Body", "Comprehensive exercises that engage multiple muscle groups"));
        areas.add(new ConcentrationArea("Cardio", "Exercises designed to elevate heart rate and improve cardiovascular health"));

        return areas;
    }

    /**
     * Courses - 3 courses with different difficulty levels
     */
    public static List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();

        courses.add(new Course("Beginner's Fitness Journey", "Beginner"));
        courses.add(new Course("Intermediate Strength & Endurance", "Intermediate"));
        courses.add(new Course("Advanced Athletic Performance", "Advanced"));

        return courses;
    }

    /**
     * Exercises - diverse set of exercises
     */
    public static List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise("Jumping Jacks", "Stand with feet together, jump and spread legs while raising arms overhead", 60000, 8.0, 3));
        exercises.add(new Exercise("High Knees", "Run in place lifting knees as high as possible", 45000, 9.5, 3));
        exercises.add(new Exercise("Burpees", "Begin in standing position, drop to push-up position, return to standing, and jump", 30000, 10.0, 2));
        exercises.add(new Exercise("Mountain Climbers", "In plank position, rapidly alternate bringing knees to chest", 45000, 8.0, 3));
        exercises.add(new Exercise("Jump Rope", "Simulate jumping rope, rotating wrists and jumping on balls of feet", 60000, 10.0, 1));
        exercises.add(new Exercise("Jumping Lunges", "Alternate legs in lunge position with a jump between transitions", 45000, 9.0, 3));
        exercises.add(new Exercise("Plank Jacks", "In plank position, jump feet out and in like jumping jacks", 40000, 7.5, 3));

        // Abs exercises
        exercises.add(new Exercise("Crunches", "Lie on back with knees bent, lift shoulders towards knees", 30000, 3.5, 15));
        exercises.add(new Exercise("Russian Twists", "Seated with elevated feet, rotate torso side to side", 45000, 6.0, 20));
        exercises.add(new Exercise("Bicycle Crunches", "Lie on back, alternate bringing elbow to opposite knee", 40000, 8.0, 12));
        exercises.add(new Exercise("Leg Raises", "Lie on back, raise legs to 90 degrees and lower without touching floor", 30000, 4.0, 10));
        exercises.add(new Exercise("Plank", "Hold push-up position with weight on forearms and toes", 60000, 5.0, 3));
        exercises.add(new Exercise("Side Plank", "Balance on forearm and side of foot with body straight", 30000, 4.0, 2));
        exercises.add(new Exercise("V-Ups", "Lie on back, simultaneously lift legs and upper body to form V shape", 30000, 7.0, 10));

        // Arm exercises
        exercises.add(new Exercise("Push-Ups", "Start in plank position, lower chest to ground and push back up", 30000, 5.0, 10));
        exercises.add(new Exercise("Tricep Dips", "Using chair or bench, lower body by bending elbows then push back up", 30000, 4.5, 12));
        exercises.add(new Exercise("Diamond Push-Ups", "Push-ups with hands close together forming diamond shape", 30000, 6.0, 8));
        exercises.add(new Exercise("Arm Circles", "Extend arms and make small circles forward and backward", 30000, 2.0, 20));
        exercises.add(new Exercise("Plank Shoulder Taps", "In plank position, alternate touching opposite shoulder with hand", 45000, 5.5, 15));
        exercises.add(new Exercise("Wall Push-Ups", "Push-ups performed standing against wall", 30000, 2.5, 15));

        // Leg exercises
        exercises.add(new Exercise("Squats", "Stand with feet shoulder-width apart, lower hips as if sitting in chair", 45000, 7.0, 15));
        exercises.add(new Exercise("Lunges", "Step forward and lower back knee towards ground, alternate legs", 45000, 6.5, 10));
        exercises.add(new Exercise("Glute Bridges", "Lie on back with knees bent, lift hips off floor", 30000, 4.0, 15));
        exercises.add(new Exercise("Calf Raises", "Stand and rise onto balls of feet, lower and repeat", 30000, 3.0, 20));
        exercises.add(new Exercise("Sumo Squats", "Wide-stance squats with toes pointed outward", 45000, 7.5, 12));
        exercises.add(new Exercise("Wall Sit", "Lean against wall in sitting position with knees at 90 degrees", 60000, 6.0, 2));
        exercises.add(new Exercise("Side Leg Raises", "Lie on side and lift top leg upward", 30000, 3.5, 15));

        // Full body exercises
        exercises.add(new Exercise("Inchworm", "From standing, walk hands out to plank and back", 45000, 7.0, 8));
        exercises.add(new Exercise("Bear Crawls", "Move forward and backward on hands and feet with knees hovering above ground", 40000, 8.0, 3));
        exercises.add(new Exercise("Plank Up-Downs", "Alternate between forearm plank and high plank positions", 45000, 7.0, 10));
        exercises.add(new Exercise("Superman", "Lie on stomach and lift arms and legs simultaneously", 30000, 3.0, 10));
        exercises.add(new Exercise("Kettlebell Swings", "Using kettlebell or dumbbell, swing from between legs to chest height", 40000, 8.5, 15));
        exercises.add(new Exercise("Burpee with Push-Up", "Standard burpee with added push-up at bottom position", 45000, 12.0, 8));

        // Back exercises
        exercises.add(new Exercise("Superman Pull", "Superman position while pulling arms back as if rowing", 30000, 4.0, 12));
        exercises.add(new Exercise("Bird Dog", "On hands and knees, extend opposite arm and leg", 30000, 3.0, 10));
        exercises.add(new Exercise("Reverse Snow Angels", "Lie face down, sweep arms from sides to overhead", 30000, 3.5, 12));
        exercises.add(new Exercise("Dolphin Kicks", "Lie face down, kick legs up and down", 30000, 4.5, 15));

        // Shoulder exercises
        exercises.add(new Exercise("Pike Push-Ups", "Downward dog position, bend elbows to lower head toward ground", 30000, 5.5, 10));
        exercises.add(new Exercise("Arm Raises", "Raise arms to sides then overhead, lower and repeat", 30000, 3.0, 12));
        exercises.add(new Exercise("YTWLs", "Series of arm positions resembling letters Y, T, W, and L", 45000, 4.0, 8));

        // Core exercises
        exercises.add(new Exercise("Dead Bug", "Lie on back, extend opposite arm and leg while keeping low back flat", 30000, 3.5, 12));
        exercises.add(new Exercise("Bird-Dog Crunch", "Bird dog with elbow to knee crunch in between", 30000, 5.0, 10));
        exercises.add(new Exercise("Hollow Hold", "Lie on back, elevate shoulders and legs while keeping low back pressed to floor", 30000, 4.5, 3));
        exercises.add(new Exercise("Windshield Wipers", "Lie on back with legs extended upward, rotate legs side to side", 30000, 6.0, 10));

        return exercises;
    }

    /**
     * Exercise Attachments - media files for exercises
     */
    public static List<ExerciseAttachment> getExerciseAttachments() {
        List<ExerciseAttachment> attachments = new ArrayList<>();
        return attachments;
    }

    /**
     * Exercise-ConcentrationArea Mappings
     */
    public static List<ExerciseConcentrationArea> getExerciseConcentrationAreas() {
        List<ExerciseConcentrationArea> mappings = new ArrayList<>();

        // Cardio exercises (1-7)
        for (int i = 1; i <= 7; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 10)); // All cardio exercises map to Cardio area (10)
        }
        mappings.add(new ExerciseConcentrationArea(1, 9)); // Jumping Jacks - Full Body
        mappings.add(new ExerciseConcentrationArea(3, 9)); // Burpees - Full Body
        mappings.add(new ExerciseConcentrationArea(4, 8)); // Mountain Climbers - Core
        mappings.add(new ExerciseConcentrationArea(6, 6)); // Jumping Lunges - Legs

        // Abs exercises (8-14)
        for (int i = 8; i <= 14; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 1)); // All abs exercises map to Abs area (1)
        }
        mappings.add(new ExerciseConcentrationArea(9, 8)); // Russian Twists - Core
        mappings.add(new ExerciseConcentrationArea(12, 8)); // Plank - Core
        mappings.add(new ExerciseConcentrationArea(13, 8)); // Side Plank - Core

        // Arm exercises (15-20)
        for (int i = 15; i <= 20; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 2)); // All arm exercises map to Arms area (2)
        }
        mappings.add(new ExerciseConcentrationArea(15, 3)); // Push-Ups - Chest
        mappings.add(new ExerciseConcentrationArea(15, 5)); // Push-Ups - Shoulders
        mappings.add(new ExerciseConcentrationArea(17, 3)); // Diamond Push-Ups - Chest
        mappings.add(new ExerciseConcentrationArea(19, 5)); // Plank Shoulder Taps - Shoulders

        // Leg exercises (21-27)
        for (int i = 21; i <= 27; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 6)); // All leg exercises map to Legs area (6)
        }
        mappings.add(new ExerciseConcentrationArea(22, 7)); // Squats - Glutes
        mappings.add(new ExerciseConcentrationArea(23, 7)); // Lunges - Glutes
        mappings.add(new ExerciseConcentrationArea(24, 7)); // Glute Bridges - Glutes
        mappings.add(new ExerciseConcentrationArea(25, 7)); // Sumo Squats - Glutes

        // Full body exercises (28-33)
        for (int i = 28; i <= 33; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 9)); // All full body exercises map to Full Body area (9)
        }
        mappings.add(new ExerciseConcentrationArea(30, 2)); // Plank Up-Downs - Arms
        mappings.add(new ExerciseConcentrationArea(30, 8)); // Plank Up-Downs - Core
        mappings.add(new ExerciseConcentrationArea(31, 4)); // Superman - Back
        mappings.add(new ExerciseConcentrationArea(33, 7)); // Burpee with Push-Up - Glutes

        // Back exercises (34-37)
        for (int i = 34; i <= 37; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 4)); // All back exercises map to Back area (4)
        }
        mappings.add(new ExerciseConcentrationArea(35, 8)); // Bird Dog - Core

        // Shoulder exercises (38-40)
        for (int i = 38; i <= 40; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 5)); // All shoulder exercises map to Shoulders area (5)
        }
        mappings.add(new ExerciseConcentrationArea(38, 2)); // Pike Push-Ups - Arms

        // Core exercises (41-44)
        for (int i = 41; i <= 44; i++) {
            mappings.add(new ExerciseConcentrationArea(i, 8)); // All core exercises map to Core area (8)
        }
        mappings.add(new ExerciseConcentrationArea(41, 1)); // Dead Bug - Abs
        mappings.add(new ExerciseConcentrationArea(43, 1)); // Hollow Hold - Abs
        mappings.add(new ExerciseConcentrationArea(44, 1)); // Windshield Wipers - Abs

        return mappings;
    }

    /**
     * CourseDayExercise - Maps exercises to courses and days
     */
    public static List<CourseDayExercise> getCourseDayExercises() {
        List<CourseDayExercise> courseDayExercises = new ArrayList<>();

        for (int courseId = 1; courseId <= 3; courseId++) {
            int startExerciseId, endExerciseId;

            // Xác định phạm vi exerciseId cho từng course
            if (courseId == 1) {
                startExerciseId = 1;
                endExerciseId = 14;
            } else if (courseId == 2) {
                startExerciseId = 15;
                endExerciseId = 29;
            } else {
                startExerciseId = 30;
                endExerciseId = 44;
            }
            for (int day = 1; day <= 30; day++) {
                for (int exerciseId = startExerciseId; exerciseId <= endExerciseId; exerciseId++) {
                    courseDayExercises.add(new CourseDayExercise(exerciseId, courseId, day));
                }
            }
        }

        return courseDayExercises;
    }

    /**
     * Sample method to demonstrate inserting all data
     */
    public static void seedDatabase(AppDatabase db) {
        // Insert concentration areas
        List<ConcentrationArea> areas = getConcentrationAreas();
        for (ConcentrationArea area : areas) {
            db.concentrationAreaDao().insertConcentrationArea(area);
        }

        // Insert courses
        List<Course> courses = getCourses();
        for (Course course : courses) {
            db.courseDao().insertCourse(course);
        }

        // Insert exercises
        List<Exercise> exercises = getExercises();
        for (Exercise exercise : exercises) {
            db.exerciseDao().insertExercise(exercise);
        }

        // Insert exercise attachments
        List<ExerciseAttachment> attachments = getExerciseAttachments();
        for (ExerciseAttachment attachment : attachments) {
            db.exerciseAttachmentDao().insertExerciseAttachment(attachment);
        }

        // Insert exercise-concentration area mappings
        List<ExerciseConcentrationArea> exerciseAreaMappings = getExerciseConcentrationAreas();
        for (ExerciseConcentrationArea mapping : exerciseAreaMappings) {
            db.exerciseConcentrationAreaDao().insertExerciseConcentrationArea(mapping);
        }

        // Insert course day exercises
        List<CourseDayExercise> courseDayExercises = getCourseDayExercises();
        for (CourseDayExercise cde : courseDayExercises) {
            db.courseDayExerciseDao().insertCourseDayExercise(cde);
        }

        User user = new User();
        user.setId(1);
        db.userDao().insertUser(user);
        Hawk.put(HawkKeys.INSTANCE_USER_KEY, user);
    }
}