package com.duallab.busschedule.writer;


import com.duallab.busschedule.filter.BusScheduleFilter;
import com.duallab.busschedule.model.BusSchedule;
import org.junit.Test;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.Assert.assertTrue;

public class BusScheduleFileWriterTest {
    @Test
    public void checkSavingToFile() throws Exception{
        List<String> dirtyList = Arrays.asList("Posh 10:15 11:10", "Posh 10:40 11:05", "Posh 9:15 15:10", "Grotty 12:45 13:25");
        BusScheduleFilter busScheduleFilter = new BusScheduleFilter();
        List<BusSchedule> busSchedules = dirtyList.stream().map(scheduleString -> BusSchedule.createBusScheduleFromString.apply(scheduleString))
                .collect(Collectors.toList());
        BusScheduleFileWriter writer = new BusScheduleFileWriter();
        writer.writeAll(busSchedules);
        Path target = Paths.get("result.txt");
        assertTrue(target.toFile().exists());
    }
}
