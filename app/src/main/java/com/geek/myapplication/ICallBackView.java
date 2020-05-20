package com.geek.myapplication;

import android.content.ContentResolver;
import android.content.Intent;

interface ICallBackView {
    void loadImage(Intent intent, int responseCode);
    boolean checkPermission();
    Intent getData();
    ContentResolver getContentResolver();
    void okResult();
    void errorResult();
}
