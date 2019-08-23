package com.duallab.busschedule.reader;


import com.duallab.busschedule.model.BusSchedule;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;

@Data
@AllArgsConstructor
public class BusScheduleFileReader implements Reader<BusSchedule> {

    private String fileName;

    @Override
    public List<BusSchedule> readAll() {
        List<BusSchedule> busSchedules = new ArrayList<>();
        try (Stream<String> stream = Files.lines(Paths.get(fileName))) {
            stream.forEach(str -> {
                busSchedules.add(BusSchedule.createBusScheduleFromString.apply(str));
            });
        } catch (IOException e) {
            e.printStackTrace();
        }
        return busSchedules;
    }


}
