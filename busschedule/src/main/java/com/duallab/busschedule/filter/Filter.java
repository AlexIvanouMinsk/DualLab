package com.duallab.busschedule.filter;

import java.util.List;

public interface Filter <T> {
    List<T> filter(List<T> list);
}
