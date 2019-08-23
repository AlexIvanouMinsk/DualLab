package com.duallab.busschedule;

import com.duallab.busschedule.filter.FilterMoreThenOneHour;
import com.duallab.busschedule.model.BusSchedule;
import com.duallab.busschedule.reader.Reader;
import com.duallab.busschedule.writer.BusScheduleFileWriter;
import com.duallab.busschedule.writer.Writer;
import com.google.common.annotations.VisibleForTesting;
import com.duallab.busschedule.reader.BusScheduleFileReader;


import java.sql.Time;
import java.util.Comparator;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;


public class App {
    public static void main(String[] args)  {


        FilterMoreThenOneHour filterMoreThenOneHour = new FilterMoreThenOneHour();
        Reader<BusSchedule> reader = new BusScheduleFileReader(args[0]);
        List<BusSchedule> dirtyList = reader.readAll();
        List<BusSchedule> sortedBusSchedule  = dirtyList.stream()
                .filter(filterMoreThenOneHour. isInTime)
                .sorted(filterMoreThenOneHour.compareByCompanyNameAndTimeOut)
                .collect(Collectors.toList());

        List<BusSchedule> resListImp = filterMoreThenOneHour.clean1Imp.andThen(filterMoreThenOneHour.clean2Imp).andThen(filterMoreThenOneHour.clean3Imp).apply(sortedBusSchedule);

        Writer<BusSchedule> busScheduleWriter =  new BusScheduleFileWriter();
        busScheduleWriter.writeAll(resListImp);

        System.out.println("The schedule of buses is created");
    }





}
