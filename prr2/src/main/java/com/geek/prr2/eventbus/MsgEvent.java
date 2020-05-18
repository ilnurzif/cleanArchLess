package com.geek.prr2.eventbus;

public class MsgEvent {
    private final String message;

    public MsgEvent(String message) {
        this.message = message;
    }

    public String getMsg() {
        return message;
    }
}
