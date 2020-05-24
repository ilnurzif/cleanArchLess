package geekbrains.ru.lesson4retrofit.model;

import java.util.List;

import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import io.reactivex.Single;
import retrofit2.http.GET;
import retrofit2.http.Path;

interface GitHubUsersApi {
    @GET("/users")
    Single<List<GitHubUser>> getUsers();

    @GET("/users/{user}")
    Single<GitHubUser> getUsers(@Path("user") String user);

    @GET("/users/{user}/repos")
    Single<List<UserRepo>> getRepos(@Path("user") String user);
}
