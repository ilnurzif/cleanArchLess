package geekbrains.ru.lesson4retrofit;

import android.app.Application;

import androidx.room.Room;
import geekbrains.ru.lesson4retrofit.dbbasecode.UserDao;
import geekbrains.ru.lesson4retrofit.dbbasecode.UsersDB;
import io.realm.Realm;


public class App extends Application {
    private static App singleApp;
    private UsersDB db;

    public static App getInstance() {
        return singleApp;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        singleApp = this;

        db=Room.databaseBuilder(
                getApplicationContext(),
                UsersDB.class,
                "gitusers")
                .allowMainThreadQueries() //для  замера скорости загрузки данных включаем синхронный режим.
                .build();

     Realm.init(this);
    }

    public UserDao getUserDao() {
        return db.getUserDao();
    }
}
