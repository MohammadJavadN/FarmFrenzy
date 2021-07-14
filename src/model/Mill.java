package model;

import javafx.scene.Scene;

public class Mill extends Workshop {
    static int price = 150;
    static String imagePath = "C:\\Users\\User\\Desktop\\HelloFX\\img\\mill2.png";

    public Mill(Scene scene) {
        super("Mill", ProductType.EGG, ProductType.FLOUR, 150, 4,scene,imagePath);
        imageView.setLayoutX(scene.getWidth()*0.21);
        imageView.setLayoutY(scene.getHeight()*0.18);
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
