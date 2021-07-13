package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import view.LoginMenu;

import java.io.*;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage window;
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setResizable(false);
        LoginMenu.getLoginMenu(window).run();




    }
}

