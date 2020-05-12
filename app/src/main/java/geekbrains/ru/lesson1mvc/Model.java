package geekbrains.ru.lesson1mvc;

import java.util.Arrays;
import java.util.List;

public class Model {
    private List<Integer> mList = Arrays.asList(0,0,0);

    public int getElementValueAtIndex(int _index){
        return mList.get(_index);
    }

    public void setElementValueAtIndex(int _index){
        Integer currentValue = mList.get(_index);
        mList.set(_index, ++currentValue);
    }

    public List<Integer> getValList() {
        return mList;
    }
}

