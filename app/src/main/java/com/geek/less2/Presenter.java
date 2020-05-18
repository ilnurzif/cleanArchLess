package com.geek.less2;

public class Presenter {
    private MainView mainView;

    public Presenter(MainView mainView) {
        this.mainView = mainView;
    }

    public void setText(String text) {
        mainView.updateText(text);
    }
}
