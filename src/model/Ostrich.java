package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.Sound;

public class Ostrich extends Domestic {
    static int price = 200;

    private Ostrich(Scene scene, Pane root) {
        super(ProductType.FEATHER, 3, 200, 2, "ostrich", scene, root, "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\ostrich.png");
        Sound.playSoundAC("sounds\\chicken.wav");
    }

    public static boolean buy(Scene scene, Pane root) {
        if (Farm.getFarm().getMoney() >= price) {
            new Ostrich(scene, root);
            return true;
        }
        return false;
    }

}
