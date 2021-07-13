package model;

import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Tiger extends Wild {
    public Tiger(Scene scene, Pane root) {
        super(ProductType.TIGER, scene, root,"C:\\Users\\User\\Desktop\\HelloFX\\img\\tiger.png");
        strength = 4;
        freedom = strength;
        velocity = 2;
    }
}
