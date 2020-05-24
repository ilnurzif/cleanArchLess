package geekbrains.ru.lesson4retrofit.model;

import com.google.gson.annotations.SerializedName;

public class UserRepo {
    @SerializedName("html_url")
    private String html_url;

    public String getUrl() {
        return html_url;
    }
}
