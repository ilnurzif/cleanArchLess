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
        if (presenter==null)
            presenter=new Presenter();
        return presenter;
    }

    private Presenter() {
      model=new Model(this);
    }

    public void bindMainView(MainView mainView) {
        this.mainView = mainView;
    }

    public void unBindMainView() {
        mainView=null;
    }

    public void startLoadUsers() {
        model.loadAllUsers();
    }

    public void startLoadRepos(String userName) {
        model.loadRepos(userName);
        model.loadAvatar(userName);
    }

    @Override
    public void callUsers(List<GitHubUser> users) {
        if (mainView!=null)
        mainView.callUsers(users);
    }

    @Override
    public void callUser(GitHubUser user) {
     if (mainView!=null)
      mainView.callUser(user);
    }

    @Override
    public void callRepos(List<UserRepo> repos) {
        if (detailedView!=null)
            detailedView.callRepos(repos);
    }

    @Override
    public void callAvatar(Bitmap bitmap) {
        detailedView.callAvatar(bitmap);
    }

    public void bindDetailedView(DetailedView detailedView) {
        this.detailedView=detailedView;
    }

    public void unBindDetailedView() {
        detailedView=null;
    }
}
