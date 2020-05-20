package com.geek.myapplication;

import android.content.ContentResolver;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.os.ParcelFileDescriptor;
import android.util.Log;

import java.io.File;
import java.io.FileDescriptor;
import java.io.FileOutputStream;
import java.io.IOException;

public class Model {
    private ICallBackView callBack;
    private Bitmap bitmap_ = null;

    public Model(ICallBackView callBack) {
        this.callBack = callBack;
    }

    public void loadImage() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        callBack.loadImage(intent, RequestConst.READ_REQUEST_CODE);
    }

    public Uri getJpgUrl() {
        Log.d("Debug", "getJpgUrl start");
        Intent data = callBack.getData();
        if (data == null)
            return null;
        Uri uri = null;
        if (data != null) {
            uri = data.getData();
        } else
            return null;
        return uri;
    }

    public boolean jpegToPng(Uri uri) {
        Bitmap bm = null;
        try {
            Log.d("Debug", "jpegToPng start");
            bm = getBitmapFromUri(uri);
            String path = Environment.getExternalStorageDirectory().toString();
            if (callBack.checkPermission()) {
                Log.d("Debug", "checkPermission true");
                FileOutputStream os = new FileOutputStream(new File(path + "/res555.png"));
                bm.compress(Bitmap.CompressFormat.PNG, 100, os);
                os.flush();
                os.close();
            }
        } catch (IOException e) {
            Log.d("Debug", "jpegToPng error");
            e.printStackTrace();
        }
        return true;
    }


    private Bitmap getBitmapFromUri(Uri uri) throws IOException {
        ContentResolver contentResolver = callBack.getContentResolver();
        ParcelFileDescriptor parcelFileDescriptor = contentResolver.openFileDescriptor(uri, "r");
        FileDescriptor fileDescriptor = parcelFileDescriptor.getFileDescriptor();
        Bitmap image = BitmapFactory.decodeFileDescriptor(fileDescriptor);
        parcelFileDescriptor.close();
        return image;
    }
}
