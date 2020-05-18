package com.geek.prr2.rx;

import com.geek.prr2.eventbus.MsgEvent;

import io.reactivex.Observable;
import io.reactivex.Observer;

public class RxManager {
    public static void sendEvent(Observer observer, MsgEvent[] msgEvents) {
        Observable<MsgEvent> msgEventObservable = Observable.fromArray(msgEvents);
        msgEventObservable.subscribe(observer);
    }
}
