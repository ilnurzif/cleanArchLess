package geekbrains.ru.lesson4retrofit.dbbasecode;

import androidx.room.Dao;
import androidx.room.Insert;
import androidx.room.OnConflictStrategy;
import androidx.room.Query;

import java.util.List;

import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import geekbrains.ru.lesson4retrofit.model.UserRepo;
import io.reactivex.Single;

@Dao
public interface UserDao {
    @Query("SELECT * FROM users")
    Single<List<GitHubUser>> getAllUsers();

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long> insertUser(GitHubUser gitHubUser);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long[]> insertUsers(List<GitHubUser> gitHubUserList);

    @Query("SELECT count(*) FROM users")
    Single<Integer> getUsersCount();

    @Query("SELECT * FROM userrepo")
    Single<List<UserRepo>> getAllRepos();

    @Query("SELECT * FROM userrepo where login=:login")
    Single<List<UserRepo>> getUserRepos(String login);

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    Single<Long[]> insertRepos(List<UserRepo> repoList);
}
