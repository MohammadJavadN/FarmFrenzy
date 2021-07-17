package view;

import javafx.scene.media.AudioClip;
import javafx.scene.media.Media;
import javafx.scene.media.MediaPlayer;
import sample.Main;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import java.io.File;

public class Sound {
    public static synchronized void playSound(final String url) {
        new Thread(() -> {
            try {
                Clip clip = AudioSystem.getClip();
                AudioInputStream inputStream = AudioSystem.getAudioInputStream(new File(url));
                clip.open(inputStream);
                clip.start();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }
    public static synchronized void playSoundM(final String url) {
        new Thread(() -> {
            try {
                Media media = new Media(Sound.class.getResource(url).toURI().toString()); //replace /Movies/test.mp3 with your file
                MediaPlayer player = new MediaPlayer(media);
                player.play();
                player.play();
            } catch (Exception e) {
                try {
                    AudioClip audioClip = new AudioClip(new File(url).toURI().toString());
                    audioClip.play();
                }catch (Exception ignored){

                }
                System.err.println(e.getMessage());
            }
        }).start();
    }
    public static synchronized void playSoundAC(final String url) {
        new Thread(() -> {
            try {
                AudioClip audioClip;
                audioClip = new AudioClip(new File(url).toURI().toString());
                audioClip.play();
            } catch (Exception e) {
                System.err.println(e.getMessage());
            }
        }).start();
    }

}
