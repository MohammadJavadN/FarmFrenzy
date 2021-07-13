package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Chicken extends Domestic {
    static int price = 100;

    Chicken(Scene scene, Pane root) {
        super(ProductType.EGG, 2, 100, 1, "chicken", scene, root);
    }

    public static boolean buy( Scene scene, Pane root) {
        if (Farm.getFarm().getMoney() >= price) {
            new Chicken(scene,root);
            return true;
        }
        return false;
    }

}
