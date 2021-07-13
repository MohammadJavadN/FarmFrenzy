package model;

import javafx.scene.Scene;

public class MilkPackaging extends Workshop {
    static int price = 400;

    public MilkPackaging(Scene scene) {
        super("MilkPackaging", ProductType.COW_MILK, ProductType.MILK, 400, 6,scene);
    }

    public static int buy(Scene scene) {
        if (Farm.getFarm().getWorkshop("MilkPackaging") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new MilkPackaging(scene);
                return 1;
            }
        return 2;
    }
}
