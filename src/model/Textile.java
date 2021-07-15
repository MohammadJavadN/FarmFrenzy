package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;

public class Textile extends Workshop {
    static int price = 250;
    static String imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\featherF.png";

    public Textile(Scene scene) {
        super("Textile", ProductType.FEATHER, ProductType.CLOTH, 250, 4, scene, imagePath);
        imageView.setLayoutX(scene.getWidth() * 0.65);
        imageView.setLayoutY(scene.getHeight() * 0.19);
        x = 5;
        y = 1;
    }
    static Button buy = new Button("Textile\n" + price);

    public static void setBuy(Scene scene) {
        buy.setLayoutX(scene.getWidth() * 0.65);
        buy.setLayoutY(scene.getHeight() * 0.19);
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
        if (Farm.getFarm().getWorkshop("Textile") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Textile(scene);
                return 1;
            }
        return 2;
    }
}
