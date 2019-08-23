package com.duallab.busschedule.filter;


import com.duallab.busschedule.model.BusSchedule;
import com.google.common.annotations.VisibleForTesting;

import java.sql.Time;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class  FilterMoreThenOneHour implements Filter<BusSchedule> {

    @Override
    public List<BusSchedule> applyFilter(List<BusSchedule> list) {
        return list.stream()
                .filter(isInTime)
                .collect(Collectors.toList());
    }

    public static Comparator<BusSchedule> compareByCompanyNameAndTimeOut = Comparator
            .comparing(BusSchedule::getCompanyName).reversed()
            .thenComparing(BusSchedule::getTimeOut);


    public static Predicate<BusSchedule> isInTime = busSchedule -> busSchedule.getTimeIn().getTime() - busSchedule.getTimeOut().getTime() < 3600 * 1000;

    static Comparator<BusSchedule> compareByTimeIn = Comparator
            .comparing(BusSchedule::getTimeIn);


    static Comparator<BusSchedule> compareByTimeOut = Comparator
            .comparing(BusSchedule::getTimeOut);

    public Function<List<BusSchedule>, List<BusSchedule>> clean1Imp = list -> {
        List<Time> timeOutList = list.stream().map(item -> item.getTimeOut()).distinct().collect(Collectors.toList());
        List<BusSchedule> cleanedList = new ArrayList<>();
        for (Time outTime : timeOutList) {
            BusSchedule busSchedule = list.stream()
                    .filter(item -> item.getTimeOut().equals(outTime))
                    .sorted(compareByTimeIn)
                    .findFirst()
                    .get();
            cleanedList.add(busSchedule);
        }
        return cleanedList;
    };


    public Function<List<BusSchedule>, List<BusSchedule>> clean2Imp = list -> {
        List<Time> timeOutList = list.stream().map(item -> item.getTimeIn()).distinct().collect(Collectors.toList());
        List<BusSchedule> cleanedList = new ArrayList<>();
        for (Time timeIn : timeOutList) {
            BusSchedule busSchedule = list.stream()
                    .filter(item -> item.getTimeIn().equals(timeIn))
                    .sorted(compareByTimeOut.reversed())
                    .findFirst()
                    .get();
            cleanedList.add(busSchedule);
        }
        return cleanedList;
    };

    public Function<List<BusSchedule>, List<BusSchedule>> clean3Imp = list -> {
        return list.stream().filter(elem -> list.stream().filter(bigElem -> bigElem.getTimeOut().after(elem.getTimeOut()) && bigElem.getTimeIn().before(elem.getTimeIn())).count() == 0).collect(Collectors.toList());
    };

}
