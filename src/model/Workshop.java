package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.FarmMenu;
import view.Sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;

public class Workshop implements Changeable {
    String name;
    ProductType input;
    ProductType output;
    int cost;
    int processTime;
    long processDate;
    boolean isWorking = false;
    int x, y;
    Scene scene;
    String imagePath;
    ImageView imageView;
    Label workingLabel = new Label();


    Workshop(String name, ProductType input, ProductType output, int cost, int processTime, Scene scene,String imagePath) {
        this.scene = scene;
        this.name = name;
        this.input = input;
        this.output = output;
        this.cost = cost;
        this.processTime = processTime;
        Farm.getFarm().addMoney(-cost);
        Farm.getFarm().workshops.add(this);
        this.imagePath = imagePath;
        Image image = null;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        Sound.playSound("sounds\\build.wav");
        imageView = new ImageView(image);

        imageView.setPreserveRatio(true);
        ((Pane) scene.getRoot()).getChildren().add(imageView);
        imageView.setFitHeight(scene.getWidth() / 4);
        imageView.setFitWidth(scene.getHeight() / 4);
        workingLabel.getStyleClass().add("label-well");
        workingLabel.layoutXProperty().bind(imageView.layoutXProperty());
        workingLabel.layoutYProperty().bind(imageView.layoutYProperty());
        workingLabel.setText("Is Working");
        imageView.setOnMouseClicked(e -> {
            if (Work()){
                if (FarmMenu.r.getChildren().contains(workingLabel))
                    workingLabel.setVisible(true);
                else {
                    FarmMenu.r.getChildren().add(workingLabel);
                    workingLabel.setVisible(true);
                }
            }
        });
    }

    public boolean Work() {
        if (Warehouse.getWarehouse().getProduct(input) != null && !isWorking) {
            processDate = LocalDate.getInstance().getCurrentTime() + this.processTime*100000000L;
            LocalDate.getInstance().event.put(processDate, this);
            isWorking = true;
            Warehouse.getWarehouse().removeProduct(Warehouse.getWarehouse().getProduct(input));
            Sound.playSound("sounds\\work.wav");
            return true;
        }
        Sound.playSound("sounds\\error.wav");
        return false;
    }
    public boolean equal(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    public void checkAfterChangeDate() {
        new Product(output, x, y, scene);
        isWorking = false;
        workingLabel.setVisible(false);
        long produceDate = 0;
        for (Long aLong : LocalDate.getInstance().event.keySet()) {
            if (LocalDate.getInstance().event.get(aLong) == this)
                produceDate = aLong;
        }
        LocalDate.getInstance().event.remove(produceDate);
        Logger.getLogger(User.getCurrentUser()).log(output.name() + " in [" + (x + 1) + "," + (y + 1) + "]" + "from " + name + "is produced", Logger.LogType.Info);
    }

    @Override
    public String toString() {
        return  name +
                " : input = " + input +
                ", output = " + output +
                ", processTime = " + processTime +
                ", isWorking = " + isWorking +
                '\n';
    }
}
