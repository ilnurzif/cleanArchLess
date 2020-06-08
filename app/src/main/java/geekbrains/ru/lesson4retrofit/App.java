package geekbrains.ru.lesson4retrofit;

import android.app.Application;
import geekbrains.ru.lesson4retrofit.di.component.ApplicationComponent;
import geekbrains.ru.lesson4retrofit.di.component.DaggerApplicationComponent;
import geekbrains.ru.lesson4retrofit.di.module.ApplicationModule;
import geekbrains.ru.lesson4retrofit.di.module.ConnectiveModule;
import geekbrains.ru.lesson4retrofit.di.module.DatabaseModule;
import geekbrains.ru.lesson4retrofit.di.module.RetrofitModule;


public class App extends Application {
    private static App singleApp;
    protected static ApplicationComponent mApplicationComponent;

   public static App getInstance() {
        return singleApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
         singleApp = this;

         mApplicationComponent = DaggerApplicationComponent
                .builder()
                .applicationModule(new ApplicationModule(this))
                .databaseModule(new DatabaseModule(this))
                .connectiveModule(new ConnectiveModule(this))
                .retrofitModule(new RetrofitModule())
                .build();
        mApplicationComponent.inject(this);
    }

    public static ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
