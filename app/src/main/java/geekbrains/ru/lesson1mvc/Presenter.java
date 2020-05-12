package geekbrains.ru.lesson1mvc;

class Presenter {
    private static Presenter presenter;
    private MainView view;
    private Model mModel;

    private Presenter(MainView view) {
        this.view = view;
        mModel = new Model();
    }

    public static Presenter getInstance(MainView view) {
        presenter = presenter == null ? new Presenter(view) : presenter;
        presenter.view = view;  // если осуществлен вызов метода значит был обновлен view
        view.updateVal(presenter.mModel.getValList()); // обновлем значения счетчиков на экране при повороте
        return presenter;
    }

    public void buttonClick(int id) {
        mModel.setElementValueAtIndex(id);
        int val = mModel.getElementValueAtIndex(id);
        view.updateVal(mModel.getValList());
    }
}
