package com.duallab.busschedule.writer;


import com.duallab.busschedule.model.BusSchedule;
import com.google.common.annotations.VisibleForTesting;

import java.io.IOException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BusScheduleFileWriter implements Writer<BusSchedule> {

    @Override
    public void writeAll(List<BusSchedule> list) {
        List<String> outputList = createListForFileSave(list);
        Charset utf8 = StandardCharsets.UTF_8;

        try {
            Files.write(Paths.get("app.log"), outputList, utf8);
        } catch (IOException x) {
            System.err.format("IOException: %s%n", x);
        }
    }

    @VisibleForTesting
    private List<String> createListForFileSave(List<BusSchedule> list) {
        long poshCount = list.stream().filter(item -> item.getCompanyName().equalsIgnoreCase("posh")).count();
        List<String> outputList = new ArrayList<>();
        for (int i = 0; i < list.size(); i++) {
            if (poshCount == i)
                outputList.add("");
            outputList.add(BusSchedule.busScheduleToString.apply(list.get(i)));
        }
        return outputList;
    }
}
