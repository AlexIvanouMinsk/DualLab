package com.duallab.busschedule.writer;


import com.duallab.busschedule.model.BusSchedule;
import com.duallab.busschedule.writer.exception.WriteResultException;

import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class BusScheduleFileWriter implements Writer<BusSchedule> {

    @Override
    public void writeAll(List<BusSchedule> list) throws WriteResultException {
        List<String> outputList = processToRightFormat(list);
        Charset utf8 = StandardCharsets.UTF_8;
        try {
            Files.write(Paths.get("result.txt"), outputList, utf8);
        } catch (Exception ex) {
            throw new WriteResultException("Can't save the result", ex);
        }
    }

    private List<String> processToRightFormat(List<BusSchedule> list) {
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
