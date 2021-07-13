package model;

import javafx.scene.Scene;

public class Mill extends Workshop {
    static int price = 150;

    public Mill(Scene scene) {
        super("Mill", ProductType.EGG, ProductType.FLOUR, 150, 4,scene);
    }

    public static int buy(Scene scene) {
        if (Farm.getFarm().getWorkshop("Mill") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Mill(scene);
                return 1;
            }
        return 2;
    }
}
