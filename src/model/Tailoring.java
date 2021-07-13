package model;

import javafx.scene.Scene;

public class Tailoring extends Workshop {
    static int price = 400;

    public Tailoring(Scene scene) {
        super("Tailoring", ProductType.CLOTH, ProductType.SHIRT, 400, 6,scene);
    }

    public static int buy(Scene scene) {
        if (Farm.getFarm().getWorkshop("Tailoring") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Tailoring(scene);
                return 1;
            }
        return 2;
    }
}
