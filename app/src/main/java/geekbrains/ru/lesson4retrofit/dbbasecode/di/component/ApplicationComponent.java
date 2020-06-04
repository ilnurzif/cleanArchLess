package geekbrains.ru.lesson4retrofit.dbbasecode.di.component;

import android.app.Application;
import android.content.Context;
import javax.inject.Singleton;
import dagger.Component;
import geekbrains.ru.lesson4retrofit.App;
import geekbrains.ru.lesson4retrofit.dbbasecode.UserDao;
import geekbrains.ru.lesson4retrofit.dbbasecode.UsersDB;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.ApplicationContext;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.module.ApplicationModule;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.module.ConnectiveModule;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.module.DatabaseModule;
import geekbrains.ru.lesson4retrofit.model.Model;

@Singleton
@Component(modules = {
        ApplicationModule.class,
        DatabaseModule.class,
        ConnectiveModule.class
})
public interface ApplicationComponent {
    void inject(App application);
    void inject(Model model);

   @ApplicationContext
    Context getContext();
   // Application getApplication();
    UserDao getUserDao();
}
