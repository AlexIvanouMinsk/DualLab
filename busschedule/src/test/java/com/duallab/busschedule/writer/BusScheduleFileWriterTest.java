package com.duallab.busschedule.writer;


import com.duallab.busschedule.model.BusSchedule;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.*;

public class BusScheduleFileWriterTest {

    @Test
    public void checkConvertToString(){
        BusSchedule busScheduleGood = BusSchedule.builder()
                .companyName("name")
                .timeOut(Time.valueOf("10:20:00"))
                .timeIn(Time.valueOf("11:19:00"))
                .build();
        String result = busScheduleGood.busScheduleToString.apply(busScheduleGood);
        assertEquals("name 10:20 11:19", result);
    }

/*    @Test
    public void checkConvertToString2(){
        List<String> dirtyList = Arrays.asList("Posh 10:15 11:10", "Posh 10:40 11:10", "Posh 9:15 15:10", "Grotty 12:45 13:25");
        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        List<BusSchedule> busSchedules = dirtyList.stream().map(scheduleString -> BusSchedule.createBusScheduleFromString.apply(scheduleString))
                .collect(Collectors.toList());
        BusScheduleFileWriter busScheduleFileWriter = new BusScheduleFileWriter();
        List<String> result = busScheduleFileWriter.createListForFileSave(busSchedules);
        assertEquals(5, result.size());
    }*/
}
