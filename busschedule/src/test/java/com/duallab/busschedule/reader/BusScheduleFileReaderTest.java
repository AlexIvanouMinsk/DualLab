package com.duallab.busschedule.reader;

import com.duallab.busschedule.model.BusSchedule;
import com.duallab.busschedule.reader.exceptions.ExtractObjectException;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class BusScheduleFileReaderTest {

    @Test
    public void checkReader() throws Exception{
        File inputDataFile = new File(this.getClass().getResource("/inputData.txt").getFile());
        BusScheduleFileReader reader = new BusScheduleFileReader(inputDataFile.getPath());
        List<BusSchedule> result = reader.readAll();
        assertEquals(8, result.size());
    }

    @Test(expected = ExtractObjectException.class)
    public void checkException() throws Exception{
        BusScheduleFileReader reader = new BusScheduleFileReader("\"/notExists.txt\"");
        List<BusSchedule> result = reader.readAll();
    }

    @Test(expected = Exception.class)
    public void checkExceptionForBadFormat() throws Exception{
        File inputDataFile = new File(this.getClass().getResource("/inputDataBadFormat.txt").getFile());
        BusScheduleFileReader reader = new BusScheduleFileReader(inputDataFile.getPath());
        List<BusSchedule> result = reader.readAll();
    }
}
