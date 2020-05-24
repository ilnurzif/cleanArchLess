package geekbrains.ru.lesson4retrofit.ui.detailed;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

import geekbrains.ru.lesson4retrofit.Presenter;
import geekbrains.ru.lesson4retrofit.R;
import geekbrains.ru.lesson4retrofit.model.UserRepo;
import geekbrains.ru.lesson4retrofit.utility.Utility;

public class DetailedActivity extends AppCompatActivity implements DetailedView {
    private TextView userNameTW;
    private Presenter presenter;
    private RecyclerView repoListRW;
    private String login;
    private ImageView avatarIV;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detailed);
        initPresenter();
        initView();
    }

    private void initView() {
        userNameTW=findViewById(R.id.itemTextTW);
        repoListRW=findViewById(R.id.repoListRW);
        login = getIntent().getExtras().getString("login");
        userNameTW.setText(login);
        avatarIV=findViewById(R.id.avatarIV);
    }

    private void initPresenter() {
        presenter = Presenter.getInstance();
    }

    @Override
    protected void onResume() {
        super.onResume();
        presenter.bindDetailedView(this);
        presenter.startLoadRepos(login);
    }

    @Override
    protected void onPause() {
        presenter.unBindDetailedView();
        super.onPause();
    }

    @Override
    public void callRepos(List<UserRepo> repos) {
        Utility.initReciclerViewAdapter(DetailedActivity.this, new ReposAdapter(repos), repoListRW);
    }

    @Override
    public void callAvatar(Bitmap bitmap) {
        avatarIV.setImageBitmap(bitmap);
    }
}
