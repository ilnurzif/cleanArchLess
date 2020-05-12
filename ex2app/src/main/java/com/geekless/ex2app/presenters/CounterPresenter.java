package com.geekless.ex2app.presenters;

import com.geekless.ex2app.CounterDatabase;
import com.geekless.ex2app.models.Counter;
import com.geekless.ex2app.views.CounterView;

public class CounterPresenter extends BasePresenter<Counter, CounterView> {
    private static final int MIN_VALUE = 0;
    private static final int MAX_VALUE = 99;

    @Override
    protected void updateView() {
        view().setCounterName(model.getName());
        int value = model.getValue();
        view().setCounterValue(value);
        view().setMinusButtonEnabled(value > MIN_VALUE);
        view().setPlusButtonEnabled(value < MAX_VALUE);
    }

    public void onMinusButtonClicked() {
        if (setupDone() && model.getValue() > MIN_VALUE) {
            model.setValue(model.getValue() - 1);
            CounterDatabase.getInstance().saveCounter(model);
            updateView();
        }
    }

    public void onPlusButtonClicked() {
        if (setupDone() && model.getValue() < MAX_VALUE) {
            model.setValue(model.getValue() + 1);
            CounterDatabase.getInstance().saveCounter(model);
            updateView();
        }
    }

    public void onCounterClicked() {
        if (setupDone()) {
            view().goToDetailView(model);
        }
    }
}
