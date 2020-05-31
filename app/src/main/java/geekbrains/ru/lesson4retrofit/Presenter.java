package geekbrains.ru.lesson4retrofit;

import android.graphics.Bitmap;

import java.util.List;

import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import geekbrains.ru.lesson4retrofit.model.Model;
import geekbrains.ru.lesson4retrofit.model.PresenterCallBack;
import geekbrains.ru.lesson4retrofit.model.UserRepo;
import geekbrains.ru.lesson4retrofit.ui.detailed.DetailedView;
import geekbrains.ru.lesson4retrofit.ui.main.MainView;

public class Presenter implements PresenterCallBack {
    private static Presenter presenter;
    private MainView mainView;
    private DetailedView detailedView;
    private Model model;

    public static Presenter getInstance() {
        if (presenter == null)
            presenter = new Presenter();
        return presenter;
    }

    private Presenter() {
        model = new Model(this);
    }

    public void bindMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void unBindMainView() {
        mainView = null;
    }

    public void startLoadUsers() {
        model.getUsersCount();
    }

    public void startLoadRepos(String userName) {
        model.loadReposFromDB(userName);
        model.loadAvatar(userName);
    }

    @Override
    public void callUsers(List<GitHubUser> users) {
        callRecCount(users.size());
    }

    @Override
    public void callUser(GitHubUser user) {
        if (mainView != null)
            mainView.callUser(user);
    }

    @Override
    public void callRepos(List<UserRepo> repos) {
        if (detailedView != null)
            detailedView.callRepos(repos);
    }

    @Override
    public void callAvatar(Bitmap bitmap) {
        detailedView.callAvatar(bitmap);
    }

    @Override
    public void callUsersFromDB(List<GitHubUser> usersList) {
        if (mainView != null)
            mainView.callUsers(usersList);
    }

    @Override
    // Если таблица users не пустая загружаем данные из БД
    public void callRecCount(Integer count) {
        if (count > 0) {
            model.loadAllUsersFromDB(); //загружаем данные из Room
            model.loadAllUsersFromRealmDB(); //загружаем данные из Realme
        } else
            model.loadAllUsersFromRest();
    }

    @Override
    public void callReposFromDB(List<UserRepo> repos, String userName) {
        if (repos == null || repos.size() == 0)
            model.loadRepos(userName);
        else
            callRepos(repos);
    }

    @Override
    public void callRealmDatLoadTime(long res, long recCount) {
        mainView.callRealmDatLoadTime(res, recCount);
    }

    @Override
    public void callRoomDatLoadTime(long res, int recCount) {
        mainView.callRoomDatLoadTime(res, recCount);
    }

    public void bindDetailedView(DetailedView detailedView) {
        this.detailedView = detailedView;
    }

    public void unBindDetailedView() {
        detailedView = null;
    }
}
