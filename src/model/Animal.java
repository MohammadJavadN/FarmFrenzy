package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.Random;

abstract class Animal implements Destroyable {
    IntegerProperty xPosition = new SimpleIntegerProperty();
    IntegerProperty yPosition = new SimpleIntegerProperty();
    int xVelocity, yVelocity;
    Random random = new Random();
    final String NAME;
    String imagePath;
    ImageView imageView;
    Scene scene;
    public Animal(String name, Scene scene, Pane root,String imagePath) {
        this.scene = scene;
        this.xPosition.set(random.nextInt(6));
        this.yPosition.set(random.nextInt(6));
        this.imagePath = imagePath;
        this.NAME = name;
        Image image = null;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);

        imageView.xProperty().bind(scene.widthProperty().multiply(xPosition.multiply(0.063).add(0.33)));
        imageView.yProperty().bind(scene.heightProperty().multiply(yPosition.multiply(0.072).add(0.33)));
        imageView.setPreserveRatio(true);

        root.getChildren().add(imageView);
        imageView.setFitHeight(scene.getWidth() / 10);
        imageView.setFitWidth(scene.getHeight() / 10);

    }

    void addXP(int count){
        xPosition.set(xPosition.get()+count);
    }
    void addYP(int count){
        yPosition.set(yPosition.get()+count);
    }

    abstract void move();
    void setVelocity(int velocity) {
        if (random.nextBoolean()) {
            yVelocity = 0;
            xVelocity = velocity - (2 * (random.nextInt(2))) * velocity;
        } else {
            xVelocity = 0;
            yVelocity = velocity - (2 * (random.nextInt(2))) * velocity;
        }
        if ((xPosition.get() + xVelocity) < 0 || (xPosition.get() + xVelocity) >= Farm.getFarm().COL)
            xVelocity *= -1;
        if ((yPosition.get() + yVelocity) < 0 || (yPosition.get() + yVelocity) >= Farm.getFarm().ROW)
            yVelocity *= -1;
    }
}
