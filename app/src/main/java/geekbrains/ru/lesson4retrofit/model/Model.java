package geekbrains.ru.lesson4retrofit.model;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.util.Log;

import com.squareup.picasso.Picasso;
import com.squareup.picasso.Target;

import java.util.Date;
import java.util.List;

import geekbrains.ru.lesson4retrofit.App;
import geekbrains.ru.lesson4retrofit.dbbasecode.UserDao;
import geekbrains.ru.lesson4retrofit.model.realm.GitHubUserRealme;
import geekbrains.ru.lesson4retrofit.model.rest.GitHubUsersApi;
import io.reactivex.Single;
import io.reactivex.SingleOnSubscribe;
import io.reactivex.android.schedulers.AndroidSchedulers;
import io.reactivex.disposables.Disposable;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.schedulers.Schedulers;
import io.realm.Realm;
import io.realm.RealmResults;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

public class Model {
    private static String baseUrl = "https://api.github.com";
    private GitHubUsersApi gitHubUsersApi;
    private Retrofit retrofit;
    private PresenterCallBack presenterCallBack;
    private UserDao userDao;

    public Model(PresenterCallBack presenterCallBack) {
        this.presenterCallBack = presenterCallBack;
        initRetrofit();
        userDao = App.getInstance().getUserDao();
    }

    private void initRetrofit() {
        retrofit = new Retrofit.Builder().
                baseUrl(baseUrl).
                addCallAdapterFactory(RxJava2CallAdapterFactory.create()).
                addConverterFactory(GsonConverterFactory.create()).
                build();
        gitHubUsersApi = retrofit.create(GitHubUsersApi.class);
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
    private void saveRealm(final List<GitHubUser> userList) {
        Realm realm = Realm.getDefaultInstance();
        realm.beginTransaction();
        realm.deleteAll();
        realm.commitTransaction();
        for (int i = 0; i < userList.size(); i++) {
            realm.beginTransaction();
            GitHubUserRealme gitHubUserRealme = new GitHubUserRealme();
            gitHubUserRealme.setLogin(userList.get(i).getLogin());
            gitHubUserRealme.setAvatar_url(userList.get(i).getAvatar_url());
            realm.copyToRealm(gitHubUserRealme);
            realm.commitTransaction();
        }
    }

    ;

    @SuppressLint("CheckResult")
    public void getUsersCount() {
        userDao.getUsersCount()
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
        Disposable disposable = gitHubUsersApi.getUsers().retry(2)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribeWith(new DisposableSingleObserver<List<GitHubUser>>() {
                    @Override
                    public void onSuccess(List<GitHubUser> users) {
                        saveUsersInDb(users);
                        saveRealm(users);
                        if (presenterCallBack != null) presenterCallBack.callUsers(users);
                    }

                    @Override
                    public void onError(Throwable e) {
                    }
                });
    }

    public void loadAllUsersFromDB() {
        Date first = new Date();
        Disposable subscribe = userDao.getAllUsers()
                .subscribeOn(AndroidSchedulers.mainThread())  // для чистоты эксперимента замера скорости загрузку данных делаем в mainThread
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(usersList -> {
                    Date second = new Date();
                    long res = second.getTime() - first.getTime();
                    presenterCallBack.callRoomDatLoadTime(res, usersList.size());
                    if (presenterCallBack != null)
                        presenterCallBack.callUsersFromDB(usersList);
                });
    }

    @SuppressLint("CheckResult")
    public void loadAllUsersFromRealmDB() {
        Single<RealmResults<GitHubUserRealme>> singleUserList = Single.create((SingleOnSubscribe<RealmResults<GitHubUserRealme>>) emitter -> {
            Realm realm = Realm.getDefaultInstance();
            Date first = new Date();
            realm.beginTransaction();
            RealmResults<GitHubUserRealme> realmeRealmResults = realm.where(GitHubUserRealme.class).findAll();
            realm.commitTransaction();
            Date second = new Date();
            long res = second.getTime() - first.getTime();
            presenterCallBack.callRealmDatLoadTime(res, realmeRealmResults.size());
            emitter.onSuccess(realmeRealmResults);
        }).subscribeOn(AndroidSchedulers.mainThread())   // для чистоты эксперимента замера скорости загрузку данных делаем в mainThread
                .observeOn(AndroidSchedulers.mainThread());

        singleUserList.subscribeWith(new DisposableSingleObserver<RealmResults<GitHubUserRealme>>() {
            @Override
            public void onSuccess(RealmResults<GitHubUserRealme> gitHubUserRealmes) {
                Log.d("TAG", "onSuccess: ");
            }

            @SuppressLint("CheckResult")
            @Override
            public void onError(Throwable e) {
            }
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
                    }

                    @SuppressLint("CheckResult")
                    @Override
                    public void onError(Throwable e) {
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

    // map сдесь использую для учебных целей лучше сразу в запросе соединить таблицы с помощью Left join
    public void loadRepos(final String userName) {
        Single<List<UserRepo>> listSingle = gitHubUsersApi.getRepos(userName);
        Disposable disposable = listSingle.retry(2)
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
