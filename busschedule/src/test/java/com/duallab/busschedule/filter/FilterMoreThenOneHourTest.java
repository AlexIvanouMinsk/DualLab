package com.duallab.busschedule.filter;


import com.duallab.busschedule.model.BusSchedule;
import org.junit.Test;

import java.sql.Time;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.*;

public class FilterMoreThenOneHourTest {

    @Test
    public void checkWhenTimeLessOneHour(){
        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        BusSchedule busScheduleGood = BusSchedule.builder()
                .companyName("name")
                .timeOut(Time.valueOf("10:20:00"))
                .timeIn(Time.valueOf("11:19:00"))
                .build();
        assertTrue(filterMoreThenOneHour.isInTime.test(busScheduleGood));
    }

    @Test
    public void checkWhenTimeMoreOneHour(){
        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        BusSchedule busScheduleBad = BusSchedule.builder()
                .companyName("name")
                .timeOut(Time.valueOf("10:20:00"))
                .timeIn(Time.valueOf("11:21:00"))
                .build();
        assertFalse(filterMoreThenOneHour.isInTime.test(busScheduleBad));
    }


    @Test
    public void checkClean1(){
        List<String> dirtyList = Arrays.asList("Posh 10:15 11:10", "Posh 10:15 11:00", "Posh 9:15 15:10", "Grotty 12:45 13:25");
        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        List<BusSchedule> busSchedules = dirtyList.stream().map(scheduleString -> BusSchedule.createBusScheduleFromString.apply(scheduleString))
                .collect(Collectors.toList());
        List<BusSchedule> busSchedulesFiltered = filterMoreThenOneHour.clean1Imp.apply(busSchedules);
        assertEquals("Posh", busSchedulesFiltered.get(0).getCompanyName());
        assertEquals(Time.valueOf("10:15:00"), busSchedulesFiltered.get(0).getTimeOut());
        assertEquals(Time.valueOf("11:00:00"), busSchedulesFiltered.get(0).getTimeIn());
    }

    @Test
    public void checkClean2(){
        List<String> dirtyList = Arrays.asList("Posh 10:15 11:10", "Posh 10:40 11:10", "Posh 9:15 15:10", "Grotty 12:45 13:25");
        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        List<BusSchedule> busSchedules = dirtyList.stream().map(scheduleString -> BusSchedule.createBusScheduleFromString.apply(scheduleString))
                .collect(Collectors.toList());
        List<BusSchedule> busSchedulesFiltered = filterMoreThenOneHour.clean2Imp.apply(busSchedules);

        assertEquals("Posh", busSchedulesFiltered.get(0).getCompanyName());
        assertEquals(Time.valueOf("10:40:00"), busSchedulesFiltered.get(0).getTimeOut());
        assertEquals(Time.valueOf("11:10:00"), busSchedulesFiltered.get(0).getTimeIn());
    }

    @Test
    public void checkClean3(){
        List<String> dirtyList = Arrays.asList("Posh 10:15 11:10", "Posh 10:40 11:05", "Posh 9:15 15:10", "Grotty 12:45 13:25");
        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        List<BusSchedule> busSchedules = dirtyList.stream().map(scheduleString -> BusSchedule.createBusScheduleFromString.apply(scheduleString))
                .collect(Collectors.toList());
        List<BusSchedule> busSchedulesFiltered = filterMoreThenOneHour.clean3Imp.apply(busSchedules);

        assertEquals("Posh", busSchedulesFiltered.get(0).getCompanyName());
        assertEquals(Time.valueOf("10:40:00"), busSchedulesFiltered.get(0).getTimeOut());
        assertEquals(Time.valueOf("11:05:00"), busSchedulesFiltered.get(0).getTimeIn());
    }

}
