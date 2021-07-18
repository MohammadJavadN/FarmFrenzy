package view;

import java.util.regex.Matcher;
import javafx.scene.Scene;
import javafx.stage.Stage;
import model.User;

public abstract class Menu {
    User user;
    Scene scene;
    Stage window;

    protected Menu parentMenu;
    protected Menu subMenu;
    Matcher matcher;

    abstract public void run();

    public abstract void show();

    public void setUser(User user) {
        this.user = user;
    }
}
