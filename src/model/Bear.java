package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Bear extends Wild {
    public Bear(Scene scene, Pane root) {
        super(ProductType.BEAR, scene, root);
        strength = 4;
        freedom = strength;
        velocity = 1;
    }
}
