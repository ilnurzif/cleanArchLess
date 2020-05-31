package geekbrains.ru.lesson4retrofit.model.realm;

import io.realm.RealmObject;
import io.realm.annotations.PrimaryKey;

public class GitHubUserRealme  extends RealmObject  {
    @PrimaryKey()
    private String login;
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }
}
