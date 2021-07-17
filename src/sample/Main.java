package sample;

import javafx.application.Application;
import javafx.scene.media.AudioClip;
import javafx.stage.Stage;
import view.LoginMenu;
import view.Sound;

import java.io.File;

public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    public static Stage window;
    @Override
    public void start(Stage stage) throws Exception {
        window = stage;
        window.setResizable(false);
        AudioClip audioClip = new AudioClip(new File("sounds\\back sound.wav").toURI().toString());
        audioClip.play(0.5);
        LoginMenu.getLoginMenu(window).run();
        
    }
}
