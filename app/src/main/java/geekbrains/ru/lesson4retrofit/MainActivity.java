package geekbrains.ru.lesson4retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import java.util.List;

import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import geekbrains.ru.lesson4retrofit.ui.main.MainView;
import geekbrains.ru.lesson4retrofit.ui.main.UsersAdapter;
import geekbrains.ru.lesson4retrofit.utility.Utility;

public class MainActivity extends AppCompatActivity implements MainView {
    private Presenter presenter;
    private RecyclerView usersRW;
    private TextView realmeLoadTimeTV;
    private TextView roomLoadTimeTV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPresenter();
    }

    private void initView() {
        usersRW = findViewById(R.id.usersRW);
        realmeLoadTimeTV = findViewById(R.id.realmeLoadTimeTV);
        roomLoadTimeTV = findViewById(R.id.roomLoadTimeTV);
    }

    private void initPresenter() {
        presenter = Presenter.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindMainView(this);
        presenter.startLoadUsers();
    }

    @Override
    protected void onPause() {
        presenter.unBindMainView();
        super.onPause();
    }

    @Override
    public void callUsers(List<GitHubUser> users) {
        Utility.initReciclerViewAdapter(MainActivity.this, new UsersAdapter(users), usersRW);
    }

    @Override
    public void callUser(GitHubUser user) {
    }

    @Override
    public void callRealmDatLoadTime(long res, long recCount) {
        String msg = "Время загрузки данных realme=" + res + " количество записей:" + recCount;
        realmeLoadTimeTV.setText(msg);
    }

    @Override
    public void callRoomDatLoadTime(long res, int recCount) {
        String msg = "Время загрузки данных room=" + res + " количество записей:" + recCount;
        roomLoadTimeTV.setText(msg);
    }
}


