package geekbrains.ru.lesson4retrofit;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import java.util.List;
import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import geekbrains.ru.lesson4retrofit.ui.main.MainView;
import geekbrains.ru.lesson4retrofit.ui.main.UsersAdapter;
import geekbrains.ru.lesson4retrofit.utility.Utility;

public class MainActivity extends AppCompatActivity implements MainView {
    private Presenter presenter;
    private RecyclerView usersRW;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initView();
        initPresenter();
    }

    private void initView() {
        usersRW = findViewById(R.id.usersRW);
    }

    private void initPresenter() {
        presenter = Presenter.getInstance();
        presenter.createModel();
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
}


