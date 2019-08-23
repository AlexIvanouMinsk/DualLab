package com.duallab.busschedule.reader;

import java.util.List;

public interface Reader <T> {
    public List<T> readAll();
}
