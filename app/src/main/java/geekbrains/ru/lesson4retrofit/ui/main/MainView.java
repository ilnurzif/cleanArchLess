package geekbrains.ru.lesson4retrofit.ui.main;

import java.util.List;

import geekbrains.ru.lesson4retrofit.model.GitHubUser;

public interface MainView {
    void callUsers(List<GitHubUser> users);
    void callUser(GitHubUser user);
}
