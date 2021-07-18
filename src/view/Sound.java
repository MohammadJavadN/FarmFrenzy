package view;

import javafx.scene.media.AudioClip;
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
