package geekbrains.ru.lesson4retrofit.di.component;

import android.content.Context;

import java.util.List;

import javax.inject.Singleton;
import dagger.Component;
import geekbrains.ru.lesson4retrofit.App;
import geekbrains.ru.lesson4retrofit.dbbasecode.UserDao;
import geekbrains.ru.lesson4retrofit.di.ApplicationContext;
import geekbrains.ru.lesson4retrofit.di.module.ApplicationModule;
import geekbrains.ru.lesson4retrofit.di.module.ConnectiveModule;
import geekbrains.ru.lesson4retrofit.di.module.DatabaseModule;
import geekbrains.ru.lesson4retrofit.di.module.RetrofitModule;
import geekbrains.ru.lesson4retrofit.model.Model;
import geekbrains.ru.lesson4retrofit.model.UserRepo;
import geekbrains.ru.lesson4retrofit.rest.GitHubUsersApi;
import io.reactivex.Single;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class,
        ConnectiveModule.class,
        RetrofitModule.class
})
public interface ApplicationComponent {
    void inject(App application);
    void inject(Model model);

   @ApplicationContext
    Context getContext();
    UserDao getUserDao();
}
