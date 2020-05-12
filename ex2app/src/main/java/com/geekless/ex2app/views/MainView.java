package com.geekless.ex2app.views;

import com.geekless.ex2app.models.Counter;

import java.util.List;

public interface MainView {
    void showCounters(List<Counter> counters);

    void showLoading();

    void showEmpty();
}
