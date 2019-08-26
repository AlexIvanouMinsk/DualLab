package com.duallab.busschedule;

import com.duallab.busschedule.filter.BusScheduleFilter;
import com.duallab.busschedule.model.BusSchedule;
import com.duallab.busschedule.reader.BusScheduleFileReader;
import com.duallab.busschedule.reader.Reader;
import com.duallab.busschedule.writer.BusScheduleFileWriter;

import java.util.List;

public class App {
    public static void main(String[] args) throws Exception {
        Reader<BusSchedule> reader = new BusScheduleFileReader(args[0]);
        List<BusSchedule> dirtyList = reader.readAll();

        List<BusSchedule> result = new BusScheduleFilter().filter(dirtyList);

        new BusScheduleFileWriter().writeAll(result);

        System.out.println("The schedule of buses is created");
    }
}
