package geekbrains.ru.lesson4retrofit.model;

import com.google.gson.annotations.SerializedName;

public class GitHubUser {
    @SerializedName("login")
    private String login;

    @SerializedName("avatar_url")
    private String avatar_url;

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
}
