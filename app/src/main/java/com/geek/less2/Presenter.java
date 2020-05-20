package com.geek.less2;

import io.reactivex.Observable;
import io.reactivex.functions.Consumer;
import io.reactivex.disposables.Disposable;
import io.reactivex.subjects.PublishSubject;

public class Presenter {
    private static String lastVal = "";
    Disposable disposable;

    public void bindView(Observable<String> src, Consumer<String> dst) {
        PublishSubject<String> s = PublishSubject.create();
        Disposable dis = s.subscribe(dst);
        s.onNext(lastVal);
        src.subscribe(dst);
        dis.dispose();
        disposable = src.map(value -> lastVal = value).subscribe(dst);
    }

    public void unBindView() {
        if (!disposable.isDisposed()) disposable.dispose();
    }
}
