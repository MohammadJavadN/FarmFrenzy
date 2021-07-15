package model;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;

public class Mill extends Workshop {
    static int price = 150;
    static String imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\mill2.png";
    static Button buy = new Button("Mill\n" + price);

    public Mill(Scene scene) {
        super("Mill", ProductType.EGG, ProductType.FLOUR, 150, 4, scene, imagePath);
        imageView.setLayoutX(scene.getWidth() * 0.21);
        imageView.setLayoutY(scene.getHeight() * 0.18);
        x = 0;
        y = 0;
    }

    public static void setBuy(Scene scene) {
        buy.setLayoutX(scene.getWidth() * 0.21);
        buy.setLayoutY(scene.getHeight() * 0.18);
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
        if (Farm.getFarm().getWorkshop("Mill") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Mill(scene);
                return 1;
            }
        return 2;
    }
}
