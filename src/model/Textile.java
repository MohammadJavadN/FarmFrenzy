package model;

import javafx.scene.Scene;

public class Textile extends Workshop {
    static int price = 250;

    public Textile(Scene scene) {
        super("Textile", ProductType.FEATHER, ProductType.CLOTH, 250, 4,scene);
    }

    public static int buy(Scene scene) {
        if (Farm.getFarm().getWorkshop("Textile") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Textile(scene);
                return 1;
            }
        return 2;
    }
}
