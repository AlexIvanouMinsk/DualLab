package com.duallab.busschedule.model;

import com.google.common.annotations.VisibleForTesting;
import lombok.Builder;
import lombok.Data;

import java.sql.Time;
import java.util.function.Function;

@Data
@Builder
public class BusSchedule {
    private String companyName;
    private Time timeOut;
    private Time timeIn;

    @VisibleForTesting
    public static Function<String, BusSchedule> createBusScheduleFromString = data -> {
        String[] splitedData = data.split(" ");
        BusSchedule busSchedule = BusSchedule.builder()
                .companyName(splitedData[0])
                .timeOut(Time.valueOf(splitedData[1] + ":00"))
                .timeIn(Time.valueOf(splitedData[2] + ":00"))
                .build();
        return busSchedule;
    };

    @VisibleForTesting
    public static Function<BusSchedule, String> busScheduleToString = busSchedule -> {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append(busSchedule.getCompanyName())
                .append(" ")
                .append(busSchedule.timeOut.toString().substring(0, 5))
                .append(" ")
                .append(busSchedule.getTimeIn().toString().substring(0, 5));

        return stringBuilder.toString();
    };
}

