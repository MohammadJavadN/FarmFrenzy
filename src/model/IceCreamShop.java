package model;

import javafx.scene.Scene;

public class IceCreamShop extends Workshop {
    static int price = 550;

    public IceCreamShop(Scene scene) {
        super("IceCreamShop", ProductType.MILK, ProductType.ICE_CREAM, 550, 7,scene);
    }

    public static int buy(Scene scene) {
        if (Farm.getFarm().getWorkshop("IceCreamShop") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new IceCreamShop(scene);
                return 1;
            }
        return 2;
    }
}
