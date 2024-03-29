package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.Sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Locale;

public class Wild extends Animal implements Changeable {

    ProductType wildType;
    int velocity;
    int strength;
    int freedom;
    long lastTrapTime;
    ImageView[] cage;

    public Wild(ProductType wildType, Scene scene, Pane root, String path) {
        super(wildType.name().toLowerCase(Locale.ROOT), scene, root, path);
        imageView.setVisible(false);
        this.wildType = wildType;
        cage = new ImageView[5];
    }

    public void trap() {
        cage();
        freedom--;
        lastTrapTime = LocalDate.getInstance().getCurrentTime();
        if (freedom == 0) {
            new Product(wildType, xPosition.get(), yPosition.get(), scene);
            destroying();
        }
    }

    void unCage() {
        if ((LocalDate.getInstance().getCurrentTime() - lastTrapTime) / 100000000L - 1 > 0)
            if (freedom < strength) {
                freedom++;
                cage[strength - freedom].setVisible(false);
            }
    }

    void cage() {
        Image image = null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cage.png"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        cage[strength - freedom] = new ImageView(image);

        cage[strength - freedom].xProperty().bind(scene.widthProperty().multiply(xPosition.multiply(0.063).add(0.33)).add(3 * (strength - freedom)));
        cage[strength - freedom].yProperty().bind(scene.heightProperty().multiply(yPosition.multiply(0.072).add(0.33)));
        cage[strength - freedom].setPreserveRatio(true);
        cage[strength - freedom].setOnMouseClicked(e->trap());
        ((Pane) scene.getRoot()).getChildren().add(cage[strength - freedom]);
        cage[strength - freedom].setFitHeight(scene.getWidth() / 10);
        cage[strength - freedom].setFitWidth(scene.getHeight() / 10);
        cage[strength - freedom].setVisible(true);


    }

    public String kill() {//this method must call after check [if (free)]
        String s = "";
        for (int i = 0; i <= xVelocity; i++) {
            for (int j = 0; j <= yVelocity; j++) {
                if ((s += kill(xPosition.get() + i, yPosition.get() + j)).contains("hound"))
                    return s;
            }
        }
        return s;
    }

    private String kill(int x, int y) {// if face dog return false else return true
        String s = "";
        ArrayList<Dog> dogs = new ArrayList<>();
        for (Dog dog : Farm.getFarm().dogs) {
            if (dog.xPosition.get() == x && dog.yPosition.get() == y) {
                dogs.add(dog);
                this.destroying();
                s += "The wild animal got involved with a hound in [" + (dog.xPosition.get() + 1) + "," + (dog.yPosition.get() + 1) + "] \n";
                break;
            }
        }
        for (Dog dog : dogs) {
            dog.destroying();
            return s;
        }
        ArrayList<Cat> cats = new ArrayList<>();
        for (Cat cat : Farm.getFarm().cats) {
            if (cat.xPosition.get() == x && cat.yPosition.get() == y) {
                cats.add(cat);
                s += "The wild animal killed cat in [" + (cat.xPosition.get() + 1) + "," + (cat.yPosition.get() + 1) + "] \n";
            }
        }
        for (Cat cat : cats) {
            cat.destroying();
        }
        ArrayList<Product> products = new ArrayList<>();
        for (Product product : Farm.getFarm().products) {
            if (product.x == x && product.y == y) {
                products.add(product);
                s += "The wild animal destroyed " + product.name + " in [" + (product.x + 1) + "," + (product.y + 1) + "] \n";
            }
        }
        for (Product product : products) {
            product.destroying();
        }
        ArrayList<Domestic> domestics = new ArrayList<>();
        for (Domestic domestic : Farm.getFarm().domestics) {
            if (domestic.xPosition.get() == x && domestic.yPosition.get() == y) {
                domestics.add(domestic);
                s += "The wild animal killed " + domestic.NAME + "in [" + (domestic.xPosition.get() + 1) + "," + (domestic.yPosition.get() + 1) + "] \n";
            }
        }
        for (Domestic domestic : domestics) {
            domestic.destroying();
        }
        return s;
    }

    void move() {//this method must call after check [if (free)]
        String s = "";
        setVelocity(velocity);
        s += kill();
        addXP(xVelocity);
        addYP(yVelocity);
        Logger.getLogger(User.getCurrentUser()).log(wildType.name() + " move ", Logger.LogType.Info);
        Logger.getLogger(User.getCurrentUser()).log(s, Logger.LogType.Info);
    }

    @Override
    public void destroying() {
        Farm.getFarm().wilds.remove(this);
        imageView.setVisible(false);
        for (ImageView cage : this.cage) {
            if (cage!=null)cage.setVisible(false);
        }
    }

    public String toString() {
        return NAME + " " + freedom + " [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "]\n";
    }

    @Override
    public void checkAfterChangeDate() {
        Farm.getFarm().wilds.add(this);
        imageView.setVisible(true);
        Sound.playSoundAC("sounds\\roar.wav");
        imageView.setOnMouseClicked(e->trap());
        long comingDate = 0;
        for (Long aLong : LocalDate.getInstance().event.keySet()) {
            if (LocalDate.getInstance().event.get(aLong) == this)
                comingDate = aLong;
        }
        LocalDate.getInstance().event.remove(comingDate);
        Logger.getLogger(User.getCurrentUser()).log("The "+wildType.name() +" entered the farm ", Logger.LogType.Info);
    }
}
