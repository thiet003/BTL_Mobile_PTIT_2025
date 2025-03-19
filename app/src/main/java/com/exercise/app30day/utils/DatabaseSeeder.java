package com.exercise.app30day.utils;

import com.exercise.app30day.data.AppDatabase;
import com.exercise.app30day.data.models.ConcentrationArea;
import com.exercise.app30day.data.models.Course;
import com.exercise.app30day.data.models.CourseDayExercise;
import com.exercise.app30day.data.models.Exercise;
import com.exercise.app30day.data.models.ExerciseAttachment;
import com.exercise.app30day.data.models.ExerciseConcentrationArea;
import com.exercise.app30day.data.models.User;

import java.util.ArrayList;
import java.util.List;

public class DatabaseSeeder {

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