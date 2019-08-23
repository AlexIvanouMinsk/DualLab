package com.duallab.busschedule;

import com.duallab.busschedule.filter.FilterMoreThenOneHour;
import com.duallab.busschedule.model.BusSchedule;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

/**
 * Unit test for simple App.
 */
public class AppTest 
{
    /**
     * Rigorous Test :-)
     */
    @Test
    public void shouldAnswerWithTrue()
    {
        assertTrue( true );
    }

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
    public void checkConstructor(){
        App app = new App();
        BusSchedule busScheduleBad = BusSchedule.builder()
                .companyName("name")
                .timeOut(Time.valueOf("10:20:00"))
                .timeIn(Time.valueOf("11:21:00"))
                .build();
        BusSchedule busSchedule = BusSchedule.createBusScheduleFromString.apply("Posh 10:15 11:10");
        assertEquals("Posh", busSchedule.getCompanyName());
    }

    @Test
    public void checkFirsTimeIn(){
        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        BusSchedule busScheduleBad = BusSchedule.builder()
                .companyName("name")
                .timeOut(Time.valueOf("10:20:00"))
                .timeIn(Time.valueOf("11:21:00"))
                .build();
        assertFalse(filterMoreThenOneHour.isInTime.test(busScheduleBad));
    }
}
