package com.geek.less2;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.widget.EdgeEffect;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity implements MainView {
    private TextView textView;
    private EditText editText;
    private Presenter presenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
    }

    private void initView() {
        textView=findViewById(R.id.textView);
        editText=findViewById(R.id.editText);
        editText.addTextChangedListener(textWatcher);
        presenter=new Presenter(this);
    }

    private final TextWatcher textWatcher=new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {

        }

        @Override
        public void afterTextChanged(Editable s) {
            presenter.setText(editText.getText().toString());
        }
    };

    @Override
    public void updateText(String text) {
        textView.setText(editText.getText().toString());
    }
}
