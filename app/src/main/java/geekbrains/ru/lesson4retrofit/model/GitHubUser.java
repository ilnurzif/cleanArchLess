package geekbrains.ru.lesson4retrofit.model;

import androidx.annotation.NonNull;
import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.Index;
import androidx.room.PrimaryKey;
import com.google.gson.annotations.SerializedName;

@Entity(tableName = "users", indices = {@Index(value = {"login"})})
public class GitHubUser {
    @PrimaryKey()
    @ColumnInfo(name = "login")
    @SerializedName("login")
    @NonNull
    private String login;

    @ColumnInfo(name = "avatar_url")
    @SerializedName("avatar_url")
    private String avatar_url;

    public void setLogin(String login) {
        this.login = login;
    }

    public void setAvatar_url(String avatar_url) {
        this.avatar_url = avatar_url;
    }

    public String getLogin() {
        return login;
    }

    public String getAvatar_url() {
        return avatar_url;
    }
}
