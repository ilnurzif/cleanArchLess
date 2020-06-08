package geekbrains.ru.lesson4retrofit;

import androidx.test.runner.AndroidJUnit4;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.reactivestreams.Subscriber;

import java.io.IOException;
import java.util.List;

import javax.inject.Inject;
import javax.inject.Named;

import dagger.Component;
import geekbrains.ru.lesson4retrofit.di.module.RetrofitModule;
import geekbrains.ru.lesson4retrofit.model.GitHubUser;
import io.reactivex.Flowable;
import io.reactivex.Single;
import io.reactivex.observers.DisposableSingleObserver;
import io.reactivex.subscribers.TestSubscriber;
import okhttp3.mockwebserver.MockResponse;
import okhttp3.mockwebserver.MockWebServer;

@Component(modules = RetrofitModule.class)
interface RetrofitModelTestComponent {
    void inject(MockServerTest test);
}

class TestDaggerRetrofitModule extends RetrofitModule {
    MockWebServer server;

    TestDaggerRetrofitModule(MockWebServer server) {
        this.server = server;
    }

    @Override
    public String provideEndpoint() {
        return server.url("/").toString();
    }
}

@RunWith(AndroidJUnit4.class)
public class MockServerTest {
    private MockWebServer mockWebServer;

    @Inject
    @Named("RetrofitUsers")
    Single<List<GitHubUser>> request;

    private static final String login = "mojombo";
    private static final String avatarUrl = "test_url";


    @Before
    public void prepare() throws IOException {
        mockWebServer = new MockWebServer();
        mockWebServer.start();

        DaggerRetrofitModelTestComponent.builder()
                .retrofitModule(new TestDaggerRetrofitModule(mockWebServer))
                .build()
                .inject(this);
    }

    @Test
    public void usersLoadCheck() {
        mockWebServer.enqueue(createMockResponse(login, avatarUrl));
        TestSubscriber<Boolean> testsubscriber = TestSubscriber.create();
        request.subscribeWith(new DisposableSingleObserver<List<GitHubUser>>() {
            @Override
            public void onSuccess(List<GitHubUser> retrofitModels) {
                if (retrofitModels.size() == 0) {
                    testsubscriber.onNext(false);
                    testsubscriber.onComplete();
                    return;
                }
                GitHubUser model = retrofitModels.get(0);
                boolean equal = model.getLogin().equals(login) &&
                        model.getAvatar_url().equals(avatarUrl);
                testsubscriber.onNext(equal);
                testsubscriber.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                testsubscriber.onNext(false);
                testsubscriber.onComplete();
            }
        });
        testsubscriber.awaitTerminalEvent();
        testsubscriber.assertValue(true);
        testsubscriber.dispose();
    }

    @Test
    public void badJSONCheck() {
        mockWebServer.enqueue(createErrorMockResponse(login, avatarUrl));
        TestSubscriber<Boolean> testsubscriber = TestSubscriber.create();

        request.subscribeWith(new DisposableSingleObserver<List<GitHubUser>>() {
            @Override
            public void onSuccess(List<GitHubUser> retrofitModels) {
                testsubscriber.onNext(false);
                testsubscriber.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                testsubscriber.onNext(true);
                testsubscriber.onComplete();
                testsubscriber.onError(e);
            }
        });
        testsubscriber.awaitTerminalEvent();
        testsubscriber.assertValue(true);
        testsubscriber.dispose();
    }

    @Test
    public void badResponceCheck() {
        mockWebServer.enqueue(createErrorCodeMockResponse(login, avatarUrl));
        TestSubscriber<Boolean> testsubscriber = TestSubscriber.create();

        request.subscribeWith(new DisposableSingleObserver<List<GitHubUser>>() {
            @Override
            public void onSuccess(List<GitHubUser> retrofitModels) {
                testsubscriber.onNext(false);
                testsubscriber.onComplete();
            }

            @Override
            public void onError(Throwable e) {
                testsubscriber.onNext(true);
                testsubscriber.onComplete();
            }
        });
        testsubscriber.awaitTerminalEvent();
        testsubscriber.assertValue(true);
        testsubscriber.dispose();
    }

    private MockResponse createMockResponse(String login, String avatarUrl) {
        int id = 2;
        return new MockResponse().setBody("[{\n" +
                "\"login\": \"" + login + "\",\n" +
                "\"avatar_url\": \"" + avatarUrl + "\",\n" +
                "\"id\": \"" + id + "\"\n" +
                "}]");
    }

    private MockResponse createErrorMockResponse(String login, String avatarUrl) {
        return new MockResponse().setBody("error body");
    }

    private MockResponse createErrorCodeMockResponse(String login, String avatarUrl) {
        return new MockResponse().setResponseCode(500);
    }

    @After
    public void shutdownServer() throws IOException {
        mockWebServer.shutdown();
    }
}