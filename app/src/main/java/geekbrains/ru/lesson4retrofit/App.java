package geekbrains.ru.lesson4retrofit;

import android.app.Application;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.component.ApplicationComponent;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.component.DaggerApplicationComponent;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.module.ApplicationModule;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.module.ConnectiveModule;
import geekbrains.ru.lesson4retrofit.dbbasecode.di.module.DatabaseModule;


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
                .build();
        mApplicationComponent.inject(this);
    }

    public static ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
