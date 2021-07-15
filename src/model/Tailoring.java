package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Background;
import javafx.scene.layout.Pane;

public class Tailoring extends Workshop {
    static int price = 400;
    static String imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\tailoring.png";
    static Button buy = new Button("Tailoring\n" + price);

    public Tailoring(Scene scene) {
        super("Tailoring", ProductType.CLOTH, ProductType.SHIRT, 400, 6, scene, imagePath);
        imageView.setLayoutX(scene.getWidth() * 0.72);
        imageView.setLayoutY(scene.getHeight() * 0.6);
        x = 5;
        y = 5;
    }

    public static void setBuy(Scene scene) {
        buy.setLayoutX(scene.getWidth() * 0.72);
        buy.setLayoutY(scene.getHeight() * 0.6);
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
        if (Farm.getFarm().getWorkshop("Tailoring") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Tailoring(scene);
                return 1;
            }
        return 2;
    }
}
