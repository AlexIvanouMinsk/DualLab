package com.duallab.busschedule.writer;

import com.duallab.busschedule.writer.exception.WriteResultException;

import java.util.List;

public interface Writer <T> {
    void writeAll(List<T> list) throws WriteResultException;
}
