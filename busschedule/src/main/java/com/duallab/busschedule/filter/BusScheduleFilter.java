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

@VisibleForTesting
public class BusScheduleFilter implements Filter<BusSchedule> {

    @Override
    public List<BusSchedule> filter(List<BusSchedule> list) {
        return startsSameTimeAndReachesEarlier.andThen(startsLaterAndReachesAtSameTime).andThen(startsLaterAndReachesEarlier)
                .apply(list.stream()
                .filter(isInTime)
                .sorted(compareByCompanyNameAndTimeOut)
                .collect(Collectors.toList()));
    }

    @VisibleForTesting
    Comparator<BusSchedule> compareByTimeIn = Comparator
            .comparing(BusSchedule::getTimeIn);

    @VisibleForTesting
    Comparator<BusSchedule> compareByTimeOut = Comparator
            .comparing(BusSchedule::getTimeOut);

    @VisibleForTesting
    Comparator<BusSchedule> compareByCompanyNameAndTimeOut = Comparator
            .comparing(BusSchedule::getCompanyName).reversed()
            .thenComparing(BusSchedule::getTimeOut);

    @VisibleForTesting
    Predicate<BusSchedule> isInTime = busSchedule -> busSchedule.getTimeIn().getTime() - busSchedule.getTimeOut().getTime() < 3600 * 1000;

    @VisibleForTesting
    Function<List<BusSchedule>, List<BusSchedule>> startsSameTimeAndReachesEarlier = list -> {
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

    @VisibleForTesting
    Function<List<BusSchedule>, List<BusSchedule>>  startsLaterAndReachesAtSameTime = list -> {
        List<Time> timeInList = list.stream().map(item -> item.getTimeIn()).distinct().collect(Collectors.toList());
        List<BusSchedule> cleanedList = new ArrayList<>();
        for (Time timeIn : timeInList) {
            BusSchedule busSchedule = list.stream()
                    .filter(item -> item.getTimeIn().equals(timeIn))
                    .sorted(compareByTimeOut.reversed())
                    .findFirst()
                    .get();
            cleanedList.add(busSchedule);
        }
        return cleanedList;
    };

    @VisibleForTesting
    public Function<List<BusSchedule>, List<BusSchedule>> startsLaterAndReachesEarlier = list -> {
        return list.stream().filter(elem -> list.stream().filter(bigElem -> bigElem.getTimeOut().after(elem.getTimeOut()) && bigElem.getTimeIn().before(elem.getTimeIn())).count() == 0).collect(Collectors.toList());
    };
}
