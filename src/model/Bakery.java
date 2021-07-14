package model;

import javafx.scene.Scene;

public class Bakery extends Workshop {
    static int price = 250;
    static String imagePath = "C:\\Users\\User\\Desktop\\HelloFX\\img\\bakery2.png";

    public Bakery(Scene scene) {
        super("Bakery", ProductType.FLOUR, ProductType.BREAD, 250, 5,scene,imagePath);
        imageView.setLayoutX(scene.getWidth()*0.12);
        imageView.setLayoutY(scene.getHeight()*0.33);
    }

    public static int buy(Scene scene) {
        if (Farm.getFarm().getWorkshop("Bakery") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Bakery(scene);
                return 1;
            }
        return 2;
    }
}
