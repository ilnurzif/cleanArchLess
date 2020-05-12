package com.geekless.ex2app.views;

import com.geekless.ex2app.models.Counter;

public interface CounterView {
    void setCounterName(String name);

    void setCounterValue(int value);

    void setMinusButtonEnabled(boolean enabled);

    void setPlusButtonEnabled(boolean enabled);

    void goToDetailView(Counter counter);
}
