package model;

import javafx.scene.Scene;

public class IceCreamShop extends Workshop {
    static int price = 550;
    static String imagePath = "C:\\Users\\User\\Desktop\\HelloFX\\img\\featherF.png";

    public IceCreamShop(Scene scene) {
        super("IceCreamShop", ProductType.MILK, ProductType.ICE_CREAM, 550, 7,scene,imagePath);
        imageView.setLayoutX(scene.getWidth()*0.13);
        imageView.setLayoutY(scene.getHeight()*0.58);
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
