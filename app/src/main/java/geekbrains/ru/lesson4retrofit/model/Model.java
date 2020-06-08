package geekbrains.ru.lesson4retrofit.model;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import geekbrains.ru.lesson4retrofit.App;
import geekbrains.ru.lesson4retrofit.dbbasecode.UserDao;
import geekbrains.ru.lesson4retrofit.rest.GitHubUsersApi;
import io.reactivex.Single;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {
    private PresenterCallBack presenterCallBack;

    UserDao userDao;

    @Inject
    Single<List<GitHubUser>> allUsers;

    @Inject
    Single<Integer> userCount;

    @Inject
    Boolean isNetworkConnect;

    @Inject
    Retrofit retrofit;

    @Inject
    GitHubUsersApi gitHubUsersApi;

    @Inject
    @Named("RetrofitUsers")
    Single<List<GitHubUser>> allRestUsers;


    public Model(PresenterCallBack presenterCallBack) {
        this.presenterCallBack = presenterCallBack;
        App.getComponent().inject(this);
        userDao=App.getComponent().getUserDao();
   }

    @SuppressLint("CheckResult")
    private void saveUsersInDb(final List<GitHubUser> userList) {
        userDao.insertUsers(userList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long[]>() {
                    @Override
                    public void onSuccess(Long[] insertedIDs) {
                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void getUsersCount() {
             userCount
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Integer>() {
                    @Override
                    public void onSuccess(Integer count) {
                        presenterCallBack.callRecCount(count);
                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void loadAllUsersFromRest() {
        if  (isNetworkConnect) {
            Disposable disposable = allRestUsers
                    .subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .subscribeWith(new DisposableSingleObserver<List<GitHubUser>>() {
                        @Override
                        public void onSuccess(List<GitHubUser> users) {
                            saveUsersInDb(users);
                            if (presenterCallBack != null) presenterCallBack.callUsers(users);
                        }

                        @Override
                        public void onError(Throwable e) {
                            presenterCallBack.callError("loadAllUsersFromRest",e.getMessage());
                        }
                    });
        }
    }

    @SuppressLint("CheckResult")
    public void loadAllUsersFromDB() {
          allUsers
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(usersList -> {
                    if (presenterCallBack != null)
                        presenterCallBack.callUsersFromDB(usersList);
                });
    }

    @SuppressLint("CheckResult")
    private void saveUserReposInDb(final List<UserRepo> repoList) {
         userDao.insertRepos(repoList)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<Long[]>() {
                    @Override
                    public void onSuccess(Long[] insertedIDs) {
                        Log.d("TAG","inseted true");
                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void onError(Throwable e) {
                        presenterCallBack.callError("saveUserReposInDb",e.getMessage());
                    }
                });
    }

    @SuppressLint("CheckResult")
    public void loadReposFromDB(final String userName) {
        userDao.getUserRepos(userName)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<UserRepo>>() {
                    @Override
                    public void onSuccess(List<UserRepo> repos) {
                        if (presenterCallBack != null)
                            presenterCallBack.callReposFromDB(repos, userName);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void loadRepos(final String userName) {
        Disposable disposable = gitHubUsersApi.getRepos(userName)
                .map(userRepos -> {
                    for (int i = 0; i < userRepos.size(); i++) {
                        userRepos.get(i).setLogin(userName);
                    }
                    return userRepos;
                })
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<UserRepo>>() {
                    @Override
                    public void onSuccess(List<UserRepo> repos) {
                        saveUserReposInDb(repos);
                        if (presenterCallBack != null) presenterCallBack.callRepos(repos);
                    }

                    @Override
                    public void onError(Throwable e) {
                        presenterCallBack.callError("loadRepos",e.getMessage());
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
                        .into(mTarget);
            }

            @Override
            public void onError(Throwable e) {
            }
        });
    }
}
