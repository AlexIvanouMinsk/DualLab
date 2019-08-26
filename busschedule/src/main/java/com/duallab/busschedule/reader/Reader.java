package com.duallab.busschedule.reader;

import com.duallab.busschedule.reader.exceptions.ExtractObjectException;

import java.util.List;

public interface Reader <T> {
    public List<T> readAll() throws ExtractObjectException;
}
