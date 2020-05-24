package geekbrains.ru.lesson4retrofit.model;

import android.graphics.Bitmap;

import java.util.List;

public interface PresenterCallBack {
    void callUsers(List<GitHubUser> users);
    void callUser(GitHubUser user);
    void callRepos(List<UserRepo> repos);

    void callAvatar(Bitmap bitmap);
}
