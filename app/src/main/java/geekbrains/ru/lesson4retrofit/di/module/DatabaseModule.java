package geekbrains.ru.lesson4retrofit.di.module;

import android.content.Context;
import androidx.room.Room;
import java.util.List;
import javax.inject.Singleton;

import dagger.Module;
import dagger.Provides;
import geekbrains.ru.lesson4retrofit.dbbasecode.UserDao;
import geekbrains.ru.lesson4retrofit.dbbasecode.UsersDB;
import geekbrains.ru.lesson4retrofit.di.ApplicationContext;
import geekbrains.ru.lesson4retrofit.di.DatabaseInfo;
import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import io.reactivex.Single;


@Module
public class DatabaseModule {
    @ApplicationContext
    private final Context mContext;

   @DatabaseInfo
    private final String mDBName = "gitusers14";

    public DatabaseModule (@ApplicationContext Context context) {
        mContext = context;
    }

    @Singleton
    @Provides
    UsersDB provideDatabase () {
        return Room.databaseBuilder(
                mContext,
                UsersDB.class,
                mDBName)
                .allowMainThreadQueries()
                .build();
      }

    @Provides
    Single<List<GitHubUser>> getUsers(UsersDB db) {
        return db.getUserDao().getAllUsers();
    }

    @Provides
    Single<Integer> getUsersCount(UserDao userDao) {
        return userDao.getUsersCount();
    }

    @Singleton
    @Provides
    UserDao provideUsersDao(UsersDB db) { return db.getUserDao(); }
}
