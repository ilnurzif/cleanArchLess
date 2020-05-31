package geekbrains.ru.lesson4retrofit.ui.detailed;

import android.graphics.Bitmap;

import java.util.List;

import geekbrains.ru.lesson4retrofit.model.UserRepo;

public interface DetailedView {
    void callRepos(List<UserRepo> repos);

    void callAvatar(Bitmap bitmap);
}
