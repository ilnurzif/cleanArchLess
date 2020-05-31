package geekbrains.ru.lesson4retrofit.dbbasecode;

import androidx.room.Database;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;

import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import geekbrains.ru.lesson4retrofit.model.UserRepo;

@Database(entities = {GitHubUser.class, UserRepo.class}, version = 2)
@TypeConverters(DateConverter.class)
public abstract class UsersDB extends RoomDatabase {
    public abstract UserDao getUserDao();
}
