package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Ostrich extends Domestic {
    static int price = 200;

    private Ostrich(Scene scene, Pane root) {
        super(ProductType.FEATHER, 3, 200, 2, "ostrich", scene, root, "C:\\Users\\User\\Desktop\\HelloFX\\img\\ostrich.png");
    }

    public static boolean buy(Scene scene, Pane root) {
        if (Farm.getFarm().getMoney() >= price) {
            new Ostrich(scene, root);
            return true;
        }
        return false;
    }

}
