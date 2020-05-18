package com.geek.prr2.eventbus;

import org.greenrobot.eventbus.EventBus;

import io.reactivex.disposables.Disposable;
import io.reactivex.Observer;

public class EventBusManager implements Observer {
    public static final String TAG = "Debug";
    private static EventBusManager eventBusManager;
    private EbObserver[] ebObservers;

    public static EventBusManager getInstance() {
        if (eventBusManager == null)
            eventBusManager = new EventBusManager();
        return eventBusManager;
    }

    private EventBusManager() {
        ebObservers = new EbObserver[]{new EbObserver1(), new EbObserver2()};
    }

    public void destroy() {
        for (int i = 0; i < ebObservers.length; i++) {
            ebObservers[i].destroy();
        }
    }

    @Override
    public void onSubscribe(Disposable d) {
    }

    @Override
    public void onNext(Object value) {
        sendEvent((MsgEvent) value);
    }

    public void sendEvent(MsgEvent event) {
        EventBus.getDefault().post(event);
    }

    @Override
    public void onError(Throwable e) {
        //  Log.d(TAG, "onError: ");
    }

    @Override
    public void onComplete() {
        // Log.d(TAG, "onComplete: ");
    }
}
