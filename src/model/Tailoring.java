package model;

import javafx.scene.Scene;

public class Tailoring extends Workshop {
    static int price = 400;
    static String imagePath = "C:\\Users\\User\\Desktop\\HelloFX\\img\\tailoring.png";
    public Tailoring(Scene scene) {
        super("Tailoring", ProductType.CLOTH, ProductType.SHIRT, 400, 6,scene,imagePath);
        imageView.setLayoutX(scene.getWidth()*0.72);
        imageView.setLayoutY(scene.getHeight()*0.6);
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
