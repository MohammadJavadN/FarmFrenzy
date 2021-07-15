package sample;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseButton;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Background;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
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
//        AlertBox.display("title","message");
        LoginMenu.getLoginMenu(window).run();
        
    }
}
