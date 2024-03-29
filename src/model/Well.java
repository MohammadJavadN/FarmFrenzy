package model;

import controller.Logger;
import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.control.Label;
import javafx.scene.media.AudioClip;
import view.FarmMenu;
import view.Sound;

import java.io.File;

public class Well implements Changeable {
    Label fillingLabel = new Label("filling...");
    IntegerProperty CAPACITY = new SimpleIntegerProperty(3);
    public IntegerProperty remaining = new SimpleIntegerProperty(0);
    public IntegerProperty wellFillPercent = new SimpleIntegerProperty();

    final int FILLING_TIME = 3;
    long fillingDate;
    boolean isFilling = false;

    private Well() {
        wellFillPercent.bind(remaining.multiply(100).divide(CAPACITY));
        fillingLabel.getStyleClass().add("label-well");
        fillingLabel.setLayoutX(0.46 * FarmMenu.r.getScene().getWidth());
        fillingLabel.setLayoutY(0.15 * FarmMenu.r.getScene().getHeight());
    }

    void restart() {
        isFilling = false;
        remaining.set(0);
        fillingLabel.setVisible(false);
    }
    static boolean isNull(){
        return well == null;
    }
    public boolean fill() {
        if (remaining.get() != 0 || isFilling) {
            Sound.playSound("sounds\\error.wav");
            return false;
        }
        String well = "sounds\\well.wav";
        Sound.playSound(well);
        isFilling = true;
        if (!FarmMenu.r.getChildren().contains(fillingLabel))
            FarmMenu.r.getChildren().add(fillingLabel);
        fillingLabel.setVisible(true);
        fillingDate = FILLING_TIME * 100000000L + LocalDate.getInstance().getCurrentTime();
        LocalDate.getInstance().event.put(fillingDate, this);
        return true;
    }

    public boolean plantingGrass(int x, int y) {
        if (remaining.get() == 0)
            return false;
        Farm.getFarm().grass[x][y]++;
        remaining.set(remaining.get() - 1);
        return true;
    }

    public void checkAfterChangeDate() {
        Sound.playSound("sounds\\well.wav");
        remaining.set(CAPACITY.get());
        isFilling = false;
        fillingLabel.setVisible(false);
        LocalDate.getInstance().event.remove(fillingDate, this);
        Logger.getLogger(User.getCurrentUser()).log("The Well is filled", Logger.LogType.Info);
    }

    static private Well well;

    public static Well getWell() {
        if (well == null)
            well = new Well();
        return well;
    }
}
