package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;

import javafx.scene.Scene;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

public abstract class Menu {
    User user;
    Scene scene;
    Stage window;

    protected Menu parentMenu;
    protected Menu subMenu;
    protected Scanner scanner;
    String input;
    Matcher matcher;
    ArrayList<String> commands = new ArrayList<>();

    abstract public void run();

    public abstract void show();

    public void setUser(User user) {
        this.user = user;
    }
}
