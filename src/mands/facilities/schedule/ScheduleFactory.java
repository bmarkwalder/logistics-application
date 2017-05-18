package mands.facilities.schedule;

import mands.exceptions.InvalidDataException;

/**
 * Created by davidscroggins on 4/17/17.
 */
public class ScheduleFactory {

    public static Schedule createSchedule(Integer rate) throws IllegalArgumentException, InvalidDataException
    {
        return new ScheduleImpl(rate);
    }

}
