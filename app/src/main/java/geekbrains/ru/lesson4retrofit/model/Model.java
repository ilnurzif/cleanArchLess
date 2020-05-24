package geekbrains.ru.lesson4retrofit.model;

import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {
    private static String baseUrl = "https://api.github.com";
    private GitHubUsersApi gitHubUsersApi;
    private Retrofit retrofit;
    private PresenterCallBack presenterCallBack;

    public Model(PresenterCallBack presenterCallBack) {
        this.presenterCallBack = presenterCallBack;
        initRetrofit();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        gitHubUsersApi = retrofit.create(GitHubUsersApi.class);
    }

    public void loadAllUsers() {
        Single<List<GitHubUser>> single = gitHubUsersApi.getUsers();
        Disposable disposable = single.retry(2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<GitHubUser>>() {
            @Override
            public void onSuccess(List<GitHubUser> users) {
                if (presenterCallBack != null) presenterCallBack.callUsers(users);
            }

            @Override
            public void onError(Throwable e) {

            }
        });
    }

    public void loadRepos(String userName) {
        Single<List<UserRepo>> listSingle = gitHubUsersApi.getRepos(userName);
        Disposable disposable = listSingle.retry(2).subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<List<UserRepo>>() {
            @Override
            public void onSuccess(List<UserRepo> repos) {
                if (presenterCallBack != null) presenterCallBack.callRepos(repos);
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }

    public void loadAvatar(String userName) {
        Target mTarget = new Target() {
            @Override
            public void onBitmapLoaded(final Bitmap bitmap, Picasso.LoadedFrom from) {
                presenterCallBack.callAvatar(bitmap);
            }

            @Override
            public void onBitmapFailed(Exception e, Drawable errorDrawable) {
            }

            @Override
            public void onPrepareLoad(Drawable placeHolderDrawable) {
            }
        };

        Single<GitHubUser> single = gitHubUsersApi.getUsers(userName);
        Disposable disposable = single.subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribeWith(new DisposableSingleObserver<GitHubUser>() {
            @Override
            public void onSuccess(GitHubUser user) {
                Picasso.get()
                        .load(user.getAvatar_url())
                        //  .transform(new CircleTransformation())
                        .into(mTarget);
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }
}
