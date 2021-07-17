package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.Sound;

public class Cow extends Domestic {
    static int price = 400;

    private Cow(Scene scene, Pane root) {
        super(ProductType.COW_MILK, 5, 400, 4, "cow", scene, root, "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cow1.png");
        Sound.playSoundM("sounds/cow.mp3");
    }

    public static boolean buy(Scene scene, Pane root) {
        if (Farm.getFarm().getMoney() >= price) {
            new Cow(scene, root);
            return true;
        }
        return false;
    }

}
