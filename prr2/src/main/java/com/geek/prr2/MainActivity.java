package com.geek.prr2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.geek.prr2.eventbus.EventBusManager;
import com.geek.prr2.eventbus.MsgEvent;
import com.geek.prr2.rx.RxManager;

import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity {
    private EventBusManager eventBusManager;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    private void initView() {
        eventBusManager = EventBusManager.getInstance();
        MsgEvent[] msgEvents = {new MsgEvent("1"), new MsgEvent("2"), new MsgEvent("3")};
        RxManager.sendEvent(eventBusManager, msgEvents);
    }

    @Override
    protected void onStart() {
        super.onStart();
        initView();
    }

    @Override
    protected void onStop() {
        eventBusManager.destroy();
        super.onStop();
    }
}
