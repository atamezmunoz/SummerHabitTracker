package com.example.database;

import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

public class Streak {
    ArrayList<Habit> habits; // Contains all habits set by a user.
    long score;

    // Constructor.
    public Streak() {
        this.habits = new ArrayList<>();
        score = 0;
    }

    // Add a habit to the streak.
    public void addAHabit(Habit habit) {
        habits.add(habit);
    }



    /**
     * Creates a list of today's tasks.
     * @return a list of tasks need to be done today
     */
    public ArrayList<Habit> createDailyHabitsList() {
        ArrayList<Habit> todayHabits = new ArrayList<>();
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (Habit habit : habits) {
            String start = habit.startDate;
            String end = habit.endDate;
            String today = dateFormat.format(Calendar.getInstance().getTime());
            if ((start.compareTo(today) <= 0) && today.compareTo(end) <= 0) {
                String day = getTheDay();
                String[] frequency = habit.getFrequencyList();
                if (contains(frequency, day)) {
                    todayHabits.add(habit);
                }
            }
        }
        return todayHabits;
    }
    
    private boolean contains(String[] frequency, String day) {
        for (String s : frequency) {
            if (s.compareTo(day) == 0) {
                return true;
            }
        }
        return false;
    }

    /**
     * Get the number of habits completed of today.
     * @param todayHabits a list contains all the tasks of today
     * @return the number of habits completed
     */
    public int habitsDone(ArrayList<Habit> todayHabits) {
        int finish = 0;
        for (Habit habit : todayHabits) {
            if (habit.daily_completion) {
                finish++;
            }
        }
        return finish;
    }

    /**
     * If the user finished all of the tasks for the day, increment the score; otherwise, set it to 0.
     * @param todayHabits a list contains all the tasks of today
     * @return the percentage completion
     */
    public double checkDailyCompletion(ArrayList<Habit> todayHabits) {
        int dailyCompletion = habitsDone(todayHabits);
        if (dailyCompletion == todayHabits.size()) {
            score++;
        } else {
            score = 0;
        }
        return 1.0 * dailyCompletion / todayHabits.size();
    }

    public void deleteHabit() {
        SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
        for (Habit habit : habits) {
            String end = habit.endDate;
            String today = dateFormat.format(Calendar.getInstance().getTime());
            if (today.compareTo(end) > 0) {
                habits.remove(habit);
            }
        }
    }

    private String getTheDay() {
        Map<Integer, String> weekMap = new HashMap<>(7);
        weekMap.put(1, "Sunday");
        weekMap.put(2, "monday");
        weekMap.put(3, "tuesday");
        weekMap.put(4, "wednesday");
        weekMap.put(5, "thursday");
        weekMap.put(6, "friday");
        weekMap.put(7, "saturday");

        Calendar calendar = Calendar.getInstance();
//        System.out.println(calendar.get(Calendar.DAY_OF_WEEK));
        return weekMap.get(calendar.get(Calendar.DAY_OF_WEEK));

    }

}
