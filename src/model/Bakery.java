package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Bakery extends Workshop {
    static int price = 250;
    static String imagePath = "C:\\Users\\User\\Desktop\\HelloFX\\img\\bakery2.png";
    static Button buy = new Button("Bakery\n" + price);

    public static void setBuy(Scene scene) {
        buy.setLayoutX(scene.getWidth() * 0.12);
        buy.setLayoutY(scene.getHeight() * 0.33);
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

    public Bakery(Scene scene) {
        super("Bakery", ProductType.FLOUR, ProductType.BREAD, 250, 5, scene, imagePath);
        imageView.setLayoutX(scene.getWidth() * 0.12);
        imageView.setLayoutY(scene.getHeight() * 0.33);
        x = 0;
        y = 2;
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
