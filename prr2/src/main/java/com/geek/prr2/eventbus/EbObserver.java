package com.geek.prr2.eventbus;

import org.greenrobot.eventbus.EventBus;

public class EbObserver {
    public void destroy() {
        EventBus.getDefault().unregister(this);
    }

    public EbObserver() {
        EventBus.getDefault().register(this);
    }
}
