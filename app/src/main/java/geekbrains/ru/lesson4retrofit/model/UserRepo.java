package geekbrains.ru.lesson4retrofit.model;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.ForeignKey;
import androidx.room.Index;
import androidx.room.PrimaryKey;

import com.google.gson.annotations.SerializedName;
import static androidx.room.ForeignKey.CASCADE;

@Entity(tableName = "userrepo",
        indices = {@Index(value = {"id"})},
        foreignKeys = @ForeignKey(entity = GitHubUser.class, parentColumns = "login", childColumns = "login", onDelete = CASCADE))

public class UserRepo {
    @PrimaryKey(autoGenerate = true)
    @ColumnInfo(name = "id")
    private long id;

    @ColumnInfo(name = "login")
    private String login;

    @SerializedName("html_url")
    @ColumnInfo(name = "html_url")
    private String html_url;

    public String getUrl() {
        return html_url;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getLogin() {
        return login;
    }

    public void setLogin(String login) {
        this.login = login;
    }

    public String getHtml_url() {
        return html_url;
    }

    public void setHtml_url(String html_url) {
        this.html_url = html_url;
    }
}
