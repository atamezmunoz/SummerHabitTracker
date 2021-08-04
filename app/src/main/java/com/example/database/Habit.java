package com.example.database;


import java.text.DateFormat;
import java.text.NumberFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.TimeUnit;

public class Habit {
    /**
     * Constructs a habit with name, frequency, duration, reminders, reminderTime and completion attributes.
     */
    String habitGuid;
    String name;
    String frequency;
    String startDate;
    String endDate;
    boolean reminders;
    String reminderTime;
    long total_completion;
    boolean daily_completion;


    /** Constructs a habit with data that got directly from the database.
     *
     * @param name  habit name
     * @param frequency habit frequency
     * @param startDate
     * @param endDate
     * @param reminders
     * @param reminderTime
     * @throws ParseException
     */
    public Habit(String habitGuid, String name, String frequency, String startDate, String endDate,
                 boolean reminders, String reminderTime)
    {
        this.habitGuid = habitGuid;
        this.name = name;
        this.frequency = frequency;
        this.startDate = startDate;
        this.endDate = endDate;
        this.reminders = reminders;
        this.reminderTime = reminderTime;
        this.daily_completion = false;
        this.total_completion = 0;

    }

    // Get the frequency list.
    public String[] getFrequencyList() {
        return frequency.split(",");
    }

    public String getHabitName() {
        return name;
    }

    /**
     * Calculate the total number of days you need to keep the habit.
     * @return total days
     */
    public long daysToKeep()  {
        long total = 0;
        String[] frequency = getFrequencyList();
        for (String f : frequency) {
            int number_of_day = getTheDay(f);
            total += weekend(startDate, endDate, number_of_day);
        }
        return total;
    }


    /**
     * Compute the number days between start date and end date.
     * @param startDate
     * @param endDate
     * @return
     * @throws ParseException
     */
    private long getDays(String startDate, String endDate) throws ParseException {
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        Date start = format.parse(startDate);
        Date end = format.parse(endDate);
        long diff = end.getTime() - start.getTime();
        long days = TimeUnit.DAYS.convert(diff, TimeUnit.MILLISECONDS);
        return days;
    }

    /**
     * If this habit is done, set its daily_completion to true.
     */
    public void setDailyCompletion() {
        this.daily_completion = true;
    }

    /**
     * Store the total completion of a habit.
     */
    public void setTotalCompletion() {
        this.total_completion++;
    }

    public String percentageOfCompletion() throws ParseException {

        NumberFormat nt = NumberFormat.getPercentInstance();
        nt.setMinimumFractionDigits(0);
        String percent = nt.format(1.0 * total_completion / daysToKeep());
        return percent;
    }

    /**
     * Given a time period and day of the week, calculate how many given days of the week there are
     * in the time period.
     * @param start start date, format isdd-MM-yyyy
     * @param end end date，format is dd-MM-yyyy
     * @param a The day of the week, from Sunday to Saturday, are represented by numbers 1-7
     * @return count days
     */

    private long weekend(String start, String end, int a){
        DateFormat format = new SimpleDateFormat("dd-MM-yyyy");
        long sunDay = 0;//count days
        try {
            Calendar startDate = Calendar.getInstance(); //start date
            startDate.setTime(format.parse(start));

            Calendar endDate = Calendar.getInstance(); //end date
            endDate.setTime(format.parse(end));

            int SW = startDate.get(Calendar.DAY_OF_WEEK) - 1; //what the day of the given start date
            int EW = endDate.get(Calendar.DAY_OF_WEEK) -1 ; //what the day of the given end date

            long diff = endDate.getTimeInMillis() - startDate.getTimeInMillis();
            long days = diff / (1000 * 60 * 60 * 24); //how many days in the given time period
            long w = Math.round(Math.ceil(((days + SW + (7 - EW)) / 7.0))); //how many weeks in the given time
            sunDay = w; //total number of days of the week
            if(a < SW) //the given day of the week is before the day of the start date, so it needs to be reduced by one
                sunDay--;
            if(a > EW) //the given day of the week is after the day of the start date, so it needs to be reduced by one
                sunDay--;
        } catch(Exception se) {
            se.printStackTrace();
        }
        return sunDay;
    }

    private int getTheDay(String week) {
        Map<String, Integer> weekMap = new HashMap<>(7);
        weekMap.put("Sunday", 1);
        weekMap.put("Monday", 2);
        weekMap.put("Tuesday", 3);
        weekMap.put("Wednesday", 4);
        weekMap.put("Thursday", 5);
        weekMap.put("Friday", 6);
        weekMap.put("Saturday", 7);

        return weekMap.get(week);

    }

    @Override
    public String toString() {
        return "Habit{" +
                "habitGuid='" + habitGuid + '\'' +
                ", name='" + name + '\'' +
                ", frequency='" + frequency + '\'' +
                ", startDate='" + startDate + '\'' +
                ", endDate='" + endDate + '\'' +
                ", reminders=" + reminders +
                ", reminderTime='" + reminderTime + '\'' +
                ", total_completion=" + total_completion +
                ", daily_completion=" + daily_completion +
                '}';
    }
}
