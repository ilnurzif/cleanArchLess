package geekbrains.ru.lesson4retrofit.model;

import android.graphics.Bitmap;

import java.util.List;

public interface PresenterCallBack {
    void callUser(GitHubUser user);
    void callUsers(List<GitHubUser> users);
    void callRepos(List<UserRepo> repos);
    void callAvatar(Bitmap bitmap);
    void callUsersFromDB(List<GitHubUser> usersList);
    void callRecCount(Integer count);
    void callReposFromDB(List<UserRepo> repos, String userName);
    void callError(String methodName, String errorMsg);
}
