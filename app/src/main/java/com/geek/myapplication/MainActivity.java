package com.geek.myapplication;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.content.ContentResolver;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.schedulers.Schedulers;

import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {
    private static final int READ_WRITE_REQUEST_CODE = 111;
 //   private static final int READ_REQUEST_CODE = 42;
    private static final String TAG = "Debug";
    private Presenter presenter;
    private ActivityCallBack activityCallBack;
    private AlertDialog alertDialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        requestPermissions();
        initPresenter();
        initView();
    }

    private void initPresenter() {
        activityCallBack = new ActivityCallBack();
        presenter = new Presenter(activityCallBack);
    }

    private void initView() {
        ((Button) findViewById(R.id.button)).setOnClickListener(v -> {
            presenter.startLoad();
        });
    }

    private void requestPermissions() {
        ActivityCompat.requestPermissions(this,
                new String[]{Manifest.permission.READ_EXTERNAL_STORAGE, Manifest.permission.WRITE_EXTERNAL_STORAGE},
                READ_WRITE_REQUEST_CODE);
    }



    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == RequestConst.READ_REQUEST_CODE && resultCode == Activity.RESULT_OK) {

            Single singleObservable = Single.create(emitter -> {
                runOnUiThread(() -> {
                   alertDialog = new AlertDialog.Builder(
                            MainActivity.this).setMessage(R.string.cancelMsg)
                            .setNegativeButton(R.string.cancel_button_title,
                                    (dialog, which) -> emitter.onSuccess(false)
                            ).create();
                    alertDialog.show();
                });
                activityCallBack.setContentResolver(getContentResolver());
                activityCallBack.setData(data);
                sleep(5000);
                emitter.onSuccess(presenter.isImageConverted());
            }).subscribeOn((Schedulers.io()))
                    .observeOn(AndroidSchedulers.mainThread());
            presenter.doDisposable(singleObservable);
        }
    }

    class ActivityCallBack implements ICallBackView {
        private Intent data;
        private ContentResolver contentResolver;

        public ActivityCallBack() {

        }

        @Override
        public void loadImage(Intent intent, int responseCode) {
            startActivityForResult(intent, responseCode);
        }

        @Override
        public boolean checkPermission() {
            return ContextCompat.checkSelfPermission(MainActivity.this,
                    Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED &&
                    ContextCompat.checkSelfPermission(MainActivity.this,
                            Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
        }

        @Override
        public Intent getData() {
            return data;
        }

        @Override
        public ContentResolver getContentResolver() {
            return contentResolver;
        }

        @Override
        public void okResult() {
            if (alertDialog!=null)   alertDialog.hide();
        }

        @Override
        public void errorResult() {
            Toast.makeText(MainActivity.this, R.string.error_msg,Toast.LENGTH_LONG).show();
        }

        public void setData(Intent intent) {
            data = intent;
        }

        public void setContentResolver(ContentResolver contentResolver) {
            this.contentResolver = contentResolver;
        }
    }
}
