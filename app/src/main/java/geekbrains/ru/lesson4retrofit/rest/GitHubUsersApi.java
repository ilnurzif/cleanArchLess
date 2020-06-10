package geekbrains.ru.lesson4retrofit.rest;

import java.util.List;

import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import geekbrains.ru.lesson4retrofit.model.UserRepo;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface GitHubUsersApi {
    @GET("users")
    Single<List<GitHubUser>> getUsers();

    @GET("users/{user}")
    Single<GitHubUser> getUsers(@Path("user") String user);

    @GET("users/{user}/repos")
    Single<List<UserRepo>> getRepos(@Path("user") String user);
}
