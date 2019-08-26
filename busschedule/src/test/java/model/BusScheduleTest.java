package model;

import com.duallab.busschedule.model.BusSchedule;
import org.junit.Test;

import java.sql.Time;

import static org.junit.Assert.assertEquals;

public class BusScheduleTest {

    @Test
    public void checkConvertToString(){
        BusSchedule busSchedule = BusSchedule.builder()
                .companyName("name")
                .timeOut(Time.valueOf("10:20:00"))
                .timeIn(Time.valueOf("11:19:00"))
                .build();
        String result = busSchedule.busScheduleToString.apply(busSchedule);
        assertEquals("name 10:20 11:19", result);
    }

    @Test
    public void checkExtractFromToString(){
        BusSchedule expectedBusSchedule = BusSchedule.builder()
                .companyName("name")
                .timeOut(Time.valueOf("10:20:00"))
                .timeIn(Time.valueOf("11:19:00"))
                .build();
        BusSchedule result = BusSchedule.createBusScheduleFromString.apply("name 10:20 11:19");
        assertEquals(expectedBusSchedule, result );
    }
}
