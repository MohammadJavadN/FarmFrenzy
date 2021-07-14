package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class IceCreamShop extends Workshop {
    static int price = 550;
    static String imagePath = "C:\\Users\\User\\Desktop\\HelloFX\\img\\featherF.png";
    static Button buy = new Button("IceCreamShop\n" + price);

    public IceCreamShop(Scene scene) {
        super("IceCreamShop", ProductType.MILK, ProductType.ICE_CREAM, 550, 7, scene, imagePath);
        imageView.setLayoutX(scene.getWidth() * 0.13);
        imageView.setLayoutY(scene.getHeight() * 0.58);
        x = 0;
        y = 4;
    }

    public static void setBuy(Scene scene) {
        buy.setLayoutX(scene.getWidth() * 0.13);
        buy.setLayoutY(scene.getHeight() * 0.58);
        buy.getStyleClass().add("button-buy");
        buy.setOnAction(event -> {
            if (buy(scene) == 1) {
                buy.setVisible(false);
            }
        });
        if (!((Pane) scene.getRoot()).getChildren().contains(buy))
            ((Pane) scene.getRoot()).getChildren().add(buy);
        buy.setVisible(true);

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
