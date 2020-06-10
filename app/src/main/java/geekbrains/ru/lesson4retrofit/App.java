package geekbrains.ru.lesson4retrofit;

import android.app.Application;

import com.facebook.stetho.Stetho;
import com.facebook.stetho.okhttp3.StethoInterceptor;

import geekbrains.ru.lesson4retrofit.di.component.ApplicationComponent;
import geekbrains.ru.lesson4retrofit.di.component.DaggerApplicationComponent;
import geekbrains.ru.lesson4retrofit.di.module.ApplicationModule;
import geekbrains.ru.lesson4retrofit.di.module.ConnectiveModule;
import geekbrains.ru.lesson4retrofit.di.module.DatabaseModule;
import geekbrains.ru.lesson4retrofit.di.module.RetrofitModule;
import okhttp3.OkHttpClient;


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

        Stetho.InitializerBuilder initializerBuilder =
                Stetho.newInitializerBuilder(this);

        // Enable Chrome DevTools
        initializerBuilder.enableWebKitInspector(
                Stetho.defaultInspectorModulesProvider(this));

        // Use the InitializerBuilder to generate an Initializer
        Stetho.Initializer initializer = initializerBuilder.build();

        // Initialize Stetho with the Initializer
        Stetho.initialize(initializer);

        new OkHttpClient.Builder()
                .addNetworkInterceptor(new StethoInterceptor())
                .build();

    }

    public static ApplicationComponent getComponent() {
        return mApplicationComponent;
    }
}
