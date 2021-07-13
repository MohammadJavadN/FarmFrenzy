package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Lion extends Wild {
    public Lion(Scene scene, Pane root) {
        super(ProductType.LION, scene, root);
        strength = 3;
        freedom = strength;
        velocity = 1;
    }
}
