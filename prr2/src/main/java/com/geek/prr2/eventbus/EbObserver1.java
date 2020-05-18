package com.geek.prr2.eventbus;

import android.util.Log;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class EbObserver1 extends EbObserver {
    private static String TAG = "debug";

    public EbObserver1() {
        super();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessageEvent(MsgEvent event) {
        String log = "EbObserver1=" + event.getMsg();
        Log.d(TAG, log);
    }
}
