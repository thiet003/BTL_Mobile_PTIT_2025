package com.exercise.app30day.utils;

import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.models.ConcentrationArea;
import com.exercise.app30day.models.Course;
import com.exercise.app30day.models.CourseDayExercise;
import com.exercise.app30day.models.Exercise;
import com.exercise.app30day.models.ExerciseAttachment;
import com.exercise.app30day.models.ExerciseConcentrationArea;
import com.exercise.app30day.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {

    /**
     * Concentration Areas - muscle groups
     */
    public static List<ConcentrationArea> getConcentrationAreas() {
        List<ConcentrationArea> areas = new ArrayList<>();

        areas.add(new ConcentrationArea("area_abs", "desc_abs"));
        areas.add(new ConcentrationArea("area_arms", "desc_arms"));
        areas.add(new ConcentrationArea("area_chest", "desc_chest"));
        areas.add(new ConcentrationArea("area_back", "desc_back"));
        areas.add(new ConcentrationArea("area_shoulders", "desc_shoulders"));
        areas.add(new ConcentrationArea("area_legs", "desc_legs"));
        areas.add(new ConcentrationArea("area_glutes", "desc_glutes"));
        areas.add(new ConcentrationArea("area_core", "desc_core"));
        areas.add(new ConcentrationArea("area_full_body", "desc_full_body"));
        areas.add(new ConcentrationArea("area_cardio", "desc_cardio"));

        return areas;
    }

    /**
     * Courses - 3 courses with different difficulty levels
     */
    public static List<Course> getCourses() {
        List<Course> courses = new ArrayList<>();

        courses.add(new Course("course_beginner", "1"));
        courses.add(new Course("course_intermediate", "2"));
        courses.add(new Course("course_advanced", "3"));

        return courses;
    }

    /**
     * Exercises - diverse set of exercises
     */
    public static List<Exercise> getExercises() {
        List<Exercise> exercises = new ArrayList<>();

        exercises.add(new Exercise("exercise_jumping_jacks", "desc_jumping_jacks", 60000, 8.0, 3));
        exercises.add(new Exercise("exercise_high_knees", "desc_high_knees", 45000, 9.5, 3));
        exercises.add(new Exercise("exercise_burpees", "desc_burpees", 30000, 10.0, 2));
        exercises.add(new Exercise("exercise_mountain_climbers", "desc_mountain_climbers", 45000, 8.0, 3));
        exercises.add(new Exercise("exercise_jump_rope", "desc_jump_rope", 60000, 10.0, 1));
        exercises.add(new Exercise("exercise_jumping_lunges", "desc_jumping_lunges", 45000, 9.0, 3));
        exercises.add(new Exercise("exercise_plank_jacks", "desc_plank_jacks", 40000, 7.5, 3));

        exercises.add(new Exercise("exercise_crunches", "desc_crunches", 30000, 3.5, 15));
        exercises.add(new Exercise("exercise_russian_twists", "desc_russian_twists", 45000, 6.0, 20));
        exercises.add(new Exercise("exercise_bicycle_crunches", "desc_bicycle_crunches", 40000, 8.0, 12));
        exercises.add(new Exercise("exercise_leg_raises", "desc_leg_raises", 30000, 4.0, 10));
        exercises.add(new Exercise("exercise_plank", "desc_plank", 60000, 5.0, 3));
        exercises.add(new Exercise("exercise_side_plank", "desc_side_plank", 30000, 4.0, 2));
        exercises.add(new Exercise("exercise_v_ups", "desc_v_ups", 30000, 7.0, 10));

        exercises.add(new Exercise("exercise_push_ups", "desc_push_ups", 30000, 5.0, 10));
        exercises.add(new Exercise("exercise_tricep_dips", "desc_tricep_dips", 30000, 4.5, 12));
        exercises.add(new Exercise("exercise_diamond_push_ups", "desc_diamond_push_ups", 30000, 6.0, 8));
        exercises.add(new Exercise("exercise_arm_circles", "desc_arm_circles", 30000, 2.0, 20));
        exercises.add(new Exercise("exercise_plank_shoulder_taps", "desc_plank_shoulder_taps", 45000, 5.5, 15));
        exercises.add(new Exercise("exercise_wall_push_ups", "desc_wall_push_ups", 30000, 2.5, 15));

        exercises.add(new Exercise("exercise_squats", "desc_squats", 45000, 7.0, 15));
        exercises.add(new Exercise("exercise_lunges", "desc_lunges", 45000, 6.5, 10));
        exercises.add(new Exercise("exercise_glute_bridges", "desc_glute_bridges", 30000, 4.0, 15));
        exercises.add(new Exercise("exercise_calf_raises", "desc_calf_raises", 30000, 3.0, 20));
        exercises.add(new Exercise("exercise_sumo_squats", "desc_sumo_squats", 45000, 7.5, 12));
        exercises.add(new Exercise("exercise_wall_sit", "desc_wall_sit", 60000, 6.0, 2));
        exercises.add(new Exercise("exercise_side_leg_raises", "desc_side_leg_raises", 30000, 3.5, 15));

        exercises.add(new Exercise("exercise_inchworm", "desc_inchworm", 45000, 7.0, 8));
        exercises.add(new Exercise("exercise_bear_crawls", "desc_bear_crawls", 40000, 8.0, 3));
        exercises.add(new Exercise("exercise_plank_up_downs", "desc_plank_up_downs", 45000, 7.0, 10));
        exercises.add(new Exercise("exercise_superman", "desc_superman", 30000, 3.0, 10));
        exercises.add(new Exercise("exercise_kettlebell_swings", "desc_kettlebell_swings", 40000, 8.5, 15));
        exercises.add(new Exercise("exercise_burpee_push_up", "desc_burpee_push_up", 45000, 12.0, 8));

        exercises.add(new Exercise("exercise_superman_pull", "desc_superman_pull", 30000, 4.0, 12));
        exercises.add(new Exercise("exercise_bird_dog", "desc_bird_dog", 30000, 3.0, 10));
        exercises.add(new Exercise("exercise_reverse_snow_angels", "desc_reverse_snow_angels", 30000, 3.5, 12));
        exercises.add(new Exercise("exercise_dolphin_kicks", "desc_dolphin_kicks", 30000, 4.5, 15));

        exercises.add(new Exercise("exercise_pike_push_ups", "desc_pike_push_ups", 30000, 5.5, 10));
        exercises.add(new Exercise("exercise_arm_raises", "desc_arm_raises", 30000, 3.0, 12));
        exercises.add(new Exercise("exercise_ytwls", "desc_ytwls", 45000, 4.0, 8));

        exercises.add(new Exercise("exercise_dead_bug", "desc_dead_bug", 30000, 3.5, 12));
        exercises.add(new Exercise("exercise_bird_dog_crunch", "desc_bird_dog_crunch", 30000, 5.0, 10));
        exercises.add(new Exercise("exercise_hollow_hold", "desc_hollow_hold", 30000, 4.5, 3));
        exercises.add(new Exercise("exercise_windshield_wipers", "desc_windshield_wipers", 30000, 6.0, 10));

        return exercises;
    }

    /**
     * Exercise Attachments - media files for exercises
     */
    public static List<ExerciseAttachment> getExerciseAttachments() {
        List<ExerciseAttachment> attachments = new ArrayList<>();

        // Add attachments for some exercises (assuming exercise IDs start from 1)
        // Images
        attachments.add(new ExerciseAttachment(1, "jumping_jacks.jpg", "image"));
        attachments.add(new ExerciseAttachment(2, "high_knees.jpg", "image"));
        attachments.add(new ExerciseAttachment(3, "burpees.jpg", "image"));
        attachments.add(new ExerciseAttachment(8, "crunches.jpg", "image"));
        attachments.add(new ExerciseAttachment(15, "push_ups.jpg", "image"));
        attachments.add(new ExerciseAttachment(22, "squats.jpg", "image"));
        attachments.add(new ExerciseAttachment(25, "sumo_squats.jpg", "image"));
        attachments.add(new ExerciseAttachment(29, "bear_crawls.jpg", "image"));
        attachments.add(new ExerciseAttachment(36, "pike_push_ups.jpg", "image"));
        attachments.add(new ExerciseAttachment(40, "hollow_hold.jpg", "image"));

        // Videos
        attachments.add(new ExerciseAttachment(1, "jumping_jacks_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(3, "burpees_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(8, "crunches_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(9, "russian_twists_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(15, "push_ups_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(17, "diamond_push_ups_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(22, "squats_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(23, "lunges_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(28, "inchworm_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(32, "burpee_push_up_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(35, "reverse_snow_angels_demo.mp4", "video"));
        attachments.add(new ExerciseAttachment(40, "hollow_hold_demo.mp4", "video"));

        // Audio instructions
        attachments.add(new ExerciseAttachment(1, "jumping_jacks_audio.mp3", "audio"));
        attachments.add(new ExerciseAttachment(5, "jump_rope_audio.mp3", "audio"));
        attachments.add(new ExerciseAttachment(8, "crunches_audio.mp3", "audio"));
        attachments.add(new ExerciseAttachment(12, "plank_audio.mp3", "audio"));
        attachments.add(new ExerciseAttachment(15, "push_ups_audio.mp3", "audio"));
        attachments.add(new ExerciseAttachment(22, "squats_audio.mp3", "audio"));
        attachments.add(new ExerciseAttachment(26, "wall_sit_audio.mp3", "audio"));
        attachments.add(new ExerciseAttachment(33, "superman_audio.mp3", "audio"));

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
        List<CourseDayExercise> mappings = new ArrayList<>();

        // Helper method to create a day's exercises

        // Beginner Course (ID 1)
        // --- Day 1: Introduction to fitness basics ---
        mappings.addAll(createDayExercises(1, 1, new int[]{1, 8, 15, 20, 22, 24, 12, 33, 38, 41, 19}, 1));

        // --- Day 2: Building foundational strength ---
        mappings.addAll(createDayExercises(1, 2, new int[]{2, 9, 16, 21, 25, 31, 13, 35, 39, 42, 5, 18}, 1));

        // --- Day 3: Core stability focus ---
        mappings.addAll(createDayExercises(1, 3, new int[]{1, 10, 17, 23, 26, 32, 34, 36, 41, 43, 6}, 1));

        // --- Day 4: Lower body emphasis ---
        mappings.addAll(createDayExercises(1, 4, new int[]{3, 11, 18, 22, 27, 30, 37, 40, 44, 7, 14}, 1));

        // --- Day 5: Rest and recovery (lighter workout) ---
        mappings.addAll(createDayExercises(1, 5, new int[]{1, 12, 19, 23, 28, 33, 35, 38, 42, 5, 9}, 1));

        // Continue with days 6-30 for Beginner Course
        // Days 6-10: Repeat first week with slight progression
        for (int day = 6; day <= 10; day++) {
            int baseDay = day - 5;
            List<CourseDayExercise> baseDayExercises = new ArrayList<>();
            for (CourseDayExercise cde : mappings) {
                if (cde.getCourseId() == 1 && cde.getOrderNumber() >= (baseDay-1)*12+1 && cde.getOrderNumber() <= baseDay*12) {
                    baseDayExercises.add(new CourseDayExercise(cde.getExerciseId(), 1, cde.getOrderNumber() + 100));
                }
            }
            mappings.addAll(baseDayExercises);
        }

        // Days 11-30: Create new variations for remaining days
        // Day 11-15: Focus on endurance
        mappings.addAll(createDayExercises(1, 11, new int[]{2, 8, 15, 21, 25, 28, 34, 38, 41, 5, 10, 13}, 200));
        mappings.addAll(createDayExercises(1, 12, new int[]{3, 9, 16, 22, 26, 29, 35, 39, 42, 6, 11}, 220));
        mappings.addAll(createDayExercises(1, 13, new int[]{4, 10, 17, 23, 27, 30, 36, 40, 43, 7, 12, 14}, 240));
        mappings.addAll(createDayExercises(1, 14, new int[]{1, 11, 18, 24, 25, 31, 37, 38, 44, 5, 13}, 260));
        mappings.addAll(createDayExercises(1, 15, new int[]{2, 12, 19, 21, 26, 32, 34, 39, 41, 6, 14, 8}, 280));

        // Days 16-20: Focus on strength building
        mappings.addAll(createDayExercises(1, 16, new int[]{3, 13, 15, 22, 27, 28, 35, 40, 42, 7, 9}, 300));
        mappings.addAll(createDayExercises(1, 17, new int[]{4, 14, 16, 23, 25, 29, 36, 38, 43, 5, 10, 11}, 320));
        mappings.addAll(createDayExercises(1, 18, new int[]{1, 8, 17, 24, 26, 30, 37, 39, 44, 6, 12}, 340));
        mappings.addAll(createDayExercises(1, 19, new int[]{2, 9, 18, 21, 27, 31, 34, 40, 41, 7, 13, 19}, 360));
        mappings.addAll(createDayExercises(1, 20, new int[]{3, 10, 19, 22, 25, 32, 35, 38, 42, 5, 14}, 380));

        // Days 21-25: Focus on flexibility and mobility
        mappings.addAll(createDayExercises(1, 21, new int[]{4, 11, 20, 23, 26, 28, 36, 39, 43, 6, 8, 15}, 400));
        mappings.addAll(createDayExercises(1, 22, new int[]{1, 12, 15, 24, 27, 29, 37, 40, 44, 7, 9}, 420));
        mappings.addAll(createDayExercises(1, 23, new int[]{2, 13, 16, 21, 25, 30, 34, 38, 41, 5, 10, 17}, 440));
        mappings.addAll(createDayExercises(1, 24, new int[]{3, 14, 17, 22, 26, 31, 35, 39, 42, 6, 11}, 460));
        mappings.addAll(createDayExercises(1, 25, new int[]{4, 8, 18, 23, 27, 32, 36, 40, 43, 7, 12, 19}, 480));

        // Days 26-30: Final progress push
        mappings.addAll(createDayExercises(1, 26, new int[]{1, 9, 19, 24, 25, 28, 37, 38, 44, 5, 13}, 500));
        mappings.addAll(createDayExercises(1, 27, new int[]{2, 10, 20, 21, 26, 29, 34, 39, 41, 6, 14, 17}, 520));
        mappings.addAll(createDayExercises(1, 28, new int[]{3, 11, 15, 22, 27, 30, 35, 40, 42, 7, 8}, 540));
        mappings.addAll(createDayExercises(1, 29, new int[]{4, 12, 16, 23, 25, 31, 36, 38, 43, 5, 9, 18}, 560));
        mappings.addAll(createDayExercises(1, 30, new int[]{1, 13, 17, 24, 26, 32, 37, 39, 44, 6, 10}, 580));

        // Intermediate Course (ID 2)
        // --- Day 1: Foundation with increased intensity ---
        mappings.addAll(createDayExercises(2, 1, new int[]{3, 9, 15, 20, 22, 29, 12, 33, 38, 41, 6, 14}, 600));

        // --- Day 2: Core and upper body focus ---
        mappings.addAll(createDayExercises(2, 2, new int[]{4, 10, 16, 21, 25, 31, 34, 36, 42, 7, 13}, 620));

        // --- Day 3: Lower body power ---
        mappings.addAll(createDayExercises(2, 3, new int[]{5, 11, 17, 23, 26, 32, 35, 39, 43, 2, 8, 19}, 640));

        // --- Day 4: Full body conditioning ---
        mappings.addAll(createDayExercises(2, 4, new int[]{6, 12, 18, 22, 27, 30, 37, 40, 44, 3, 9}, 660));

        // --- Day 5: Active recovery with mobility ---
        mappings.addAll(createDayExercises(2, 5, new int[]{1, 13, 19, 24, 28, 33, 35, 38, 41, 4, 10, 16}, 680));

        // Continue with days 6-30 for Intermediate Course
        // Create more challenging progressions for remaining days following similar pattern

        // Advanced Course (ID 3)
        // --- Day 1: High-intensity full body ---
        mappings.addAll(createDayExercises(3, 1, new int[]{3, 9, 17, 20, 22, 29, 32, 36, 41, 7, 14, 19}, 1200));

        // --- Day 2: Power and explosiveness ---
        mappings.addAll(createDayExercises(3, 2, new int[]{4, 10, 15, 21, 25, 31, 34, 40, 43, 2, 13, 18}, 1220));

        // --- Day 3: Advanced core and strength ---
        mappings.addAll(createDayExercises(3, 3, new int[]{5, 11, 16, 23, 26, 32, 35, 38, 44, 3, 8, 17}, 1240));

        // --- Day 4: Endurance and stamina ---
        mappings.addAll(createDayExercises(3, 4, new int[]{6, 12, 18, 22, 27, 30, 37, 39, 41, 4, 9, 20}, 1260));

        // --- Day 5: Technical skill focus ---
        mappings.addAll(createDayExercises(3, 5, new int[]{1, 13, 19, 24, 28, 33, 36, 40, 42, 5, 10, 16}, 1280));

        // Continue with days 6-30 for Advanced Course
        // Create even more challenging progressions for remaining days

        return mappings;
    }

    private static List<CourseDayExercise> createDayExercises(int courseId, int day, int[] exerciseIds, int startOrderNumber) {
        List<CourseDayExercise> result = new ArrayList<>();
        int orderNumber = startOrderNumber;
        for (int exerciseId : exerciseIds) {
            result.add(new CourseDayExercise(exerciseId, courseId, orderNumber++));
        }
        return result;
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

        db.userDao().insertUser(new User());
    }
}