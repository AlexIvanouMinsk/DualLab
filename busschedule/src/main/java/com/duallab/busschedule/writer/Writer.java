package com.duallab.busschedule.writer;

import java.util.List;

public interface Writer <T> {
    void writeAll(List<T> list);
}
