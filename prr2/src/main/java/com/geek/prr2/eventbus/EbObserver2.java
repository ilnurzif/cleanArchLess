package com.geek.prr2.eventbus;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EbObserver2 extends EbObserver {
    private static String TAG = "debug";

    public EbObserver2() {
        super();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MsgEvent event) {
        String log = "EbObserver2= " + event.getMsg();
        Log.d(TAG, log);
    }
}
