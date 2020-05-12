package geekbrains.ru.lesson1mvc;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import java.util.ArrayList;
import java.util.List;
import java.util.Observable;
import java.util.Observer;

public class MainActivity extends AppCompatActivity implements MainView, View.OnClickListener {
    private List<Button> buttonList;
    private Presenter mPresenter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        buttonList = new ArrayList<>();
        Button btnCounter1 = (Button) findViewById(R.id.btnCounter1);
        btnCounter1.setTag(0);
        buttonList.add(btnCounter1);
        Button btnCounter2 = (Button) findViewById(R.id.btnCounter2);
        btnCounter2.setTag(1);
        buttonList.add(btnCounter2);
        Button btnCounter3 = (Button) findViewById(R.id.btnCounter3);
        btnCounter3.setTag(2);
        buttonList.add(btnCounter3);
        for (int i = 0; i < buttonList.size(); i++)
            buttonList.get(i).setOnClickListener(this);
        // испльзуем синглтон, что бы не терять значения счетчиков при повороте и т.п.
        mPresenter = Presenter.getInstance(this);
    }

    @Override
    public void onClick(View v) {
        Integer tag = (Integer) v.getTag();
        mPresenter.buttonClick(tag);
    }

    @Override
    public void updateVal(List<Integer> list) {
        for (int i = 0; i < list.size(); i++) {
            String text = "Количество = " + list.get(i);
            buttonList.get(i).setText(text);
        }
    }
}

