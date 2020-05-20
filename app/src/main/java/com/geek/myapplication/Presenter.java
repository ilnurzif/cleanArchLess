package com.geek.myapplication;

import android.net.Uri;

import io.reactivex.Single;
import io.reactivex.disposables.Disposable;

public class Presenter {
    private static final String TAG = "Debug";
    private Model model;
    private ICallBackView callBackView;
    private Disposable disposable;

    public Presenter(ICallBackView callBackView) {
        this.callBackView=callBackView;
        model = new Model(callBackView);
    }

    public void startLoad() {
        model.loadImage();
    }

    public void doDisposable(Single single) {
       disposable=single.subscribe(
                (res)->{callBackView.okResult();},
                (error)->{callBackView.errorResult();});
    }


    public boolean isImageConverted() {
        try {
            Uri uri = model.getJpgUrl();
            if (uri == null) return false;
            model.jpegToPng(uri);
        } catch (Exception e) {
            return false;
        }
        return true;
    }
}
