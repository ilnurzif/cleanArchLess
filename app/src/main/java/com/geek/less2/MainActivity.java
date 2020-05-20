package com.geek.less2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EditText;
import android.widget.TextView;
import io.reactivex.Observable;

public class MainActivity extends AppCompatActivity{
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        presenter = new Presenter();
    }

    @Override
    protected void onResume() {
        super.onResume();
        initPresenter();
    }

    @Override
    protected void onPause() {
        presenter.unBindView();
        super.onPause();
    }

    private void initPresenter() {
        Observable<String> observable = Observable.create(emmitter -> ((EditText) findViewById(R.id.editText))
                .addTextChangedListener(((SimpleTextWather) editable ->
                        emmitter.onNext(editable.toString()))));
        presenter.bindView(observable, val -> ((TextView)findViewById(R.id.textView)).setText(val.toString()));
    }

    interface SimpleTextWather extends TextWatcher {
        @Override
        default void beforeTextChanged(CharSequence s, int start, int count, int after) {};
        @Override
        default void onTextChanged(CharSequence s, int start, int before, int count) {};
        @Override
        void afterTextChanged(Editable s);
    }

}
