package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Bear extends Wild {
    public Bear(Scene scene, Pane root) {
        super(ProductType.BEAR, scene, root,"C:\\Users\\User\\Desktop\\HelloFX\\img\\bear.png");
        strength = 4;
        freedom = strength;
        velocity = 1;
    }
}
