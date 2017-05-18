package mands.facilities.schedule;

import mands.exceptions.InvalidDataException;

import java.io.StringWriter;
import java.util.TreeMap;

/**
 * Created by davidscroggins on 4/17/17.
 */
public class ScheduleImpl implements Schedule {

    private Integer rate;
    private TreeMap<Integer, Integer> scheduleTMap;

    public ScheduleImpl(Integer rateIn) throws InvalidDataException {

        setRate(rateIn);
        scheduleTMap = new TreeMap<>();
        initializeSchedule();

    }

    //Schedule mutators
    private void setRate(Integer rateIn) throws IllegalArgumentException {
        if (rateIn <= 0){
           throw new IllegalArgumentException("ScheduleImpl.setRate(rate): rate must be >0; input: " + rateIn);
        }
        rate = rateIn;
    }

    //Initializes empty schedule for 20 days at default rate
    private void initializeSchedule() throws InvalidDataException {
        /*Might alter this in future. For now in initial set up populates open schedule for
        first 20 days for initial display purposes.*/
        if (scheduleTMap.isEmpty()) {
            for (int i = 1; i < 21; i++){
                scheduleTMap.put(i, rate);
            }
        }
    }

    public void displaySchedule() {
        // TODO Adjust to only display days that have availability?
        System.out.println("\nSchedule:");

        StringWriter dayString = new StringWriter();
        StringWriter availableString = new StringWriter();

        for (Integer day: scheduleTMap.keySet()){

            String key = day.toString();
            dayString.append("\t").append(key);

            String value = scheduleTMap.get(day).toString();
            availableString.append("\t").append(value);
        }
        System.out.println("Day:\t\t" + dayString);
        System.out.println("Available:\t" + availableString);
    }

    // TODO Add getAvailability(), isAvailable(), decreaseAvailability() etc. for Phase II
}
