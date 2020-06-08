package geekbrains.ru.lesson4retrofit;


import org.junit.Test;
import org.mockito.Mockito;
import geekbrains.ru.lesson4retrofit.Presenter;


import static org.junit.Assert.*;
import static org.mockito.Mockito.verify;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void presenter_correct_rest_err_result() {
        Presenter presenter = Presenter.getInstance();
        String res=presenter.errMsg("loadAllUsersFromRest","");
        assertEquals("Ошибка загрузки пользователей", res);
    }

    @Test
    public void presenter_correct_dbinsert_err_result() {
        Presenter presenter = Presenter.getInstance();
        String res=presenter.errMsg("saveUserReposInDb","");
        assertEquals("Не удалось сохранить пользователей в БД", res);
    }

    @Test
    public void presenter_correct_loadrepos_err_result() {
        Presenter presenter = Presenter.getInstance();
        String res=presenter.errMsg("loadRepos","");
        assertEquals("Ошибка загрузки репозитория пользователя", res);
    }

}