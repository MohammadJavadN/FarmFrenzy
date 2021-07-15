package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class MilkPackaging extends Workshop {
    static int price = 400;
    static String imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\featherF.png";
    static Button buy = new Button("MilkPackaging\n" + price);

    public MilkPackaging(Scene scene) {
        super("MilkPackaging", ProductType.COW_MILK, ProductType.MILK, 400, 6, scene, imagePath);
        imageView.setLayoutX(scene.getWidth() * 0.72);
        imageView.setLayoutY(scene.getHeight() * 0.35);
        x = 5;
        y = 3;
    }

    public static void setBuy(Scene scene) {
        buy.setLayoutX(scene.getWidth() * 0.72);
        buy.setLayoutY(scene.getHeight() * 0.35);
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
        if (Farm.getFarm().getWorkshop("MilkPackaging") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new MilkPackaging(scene);
                return 1;
            }
        return 2;
    }
}
