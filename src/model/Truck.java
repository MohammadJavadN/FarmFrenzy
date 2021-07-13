package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.FarmMenu;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class Truck implements Changeable {
    int transportPrice;
    final int TRANSPORT_TIME = 10;
    ArrayList<Object> objects = new ArrayList<>();
    int capacity;
    int load = 0;
    public boolean present;
    long returningTime;
    ImageView imageView;
    Scene scene;

    private Truck() {
        transportPrice = 0;//
        capacity = 15;
        present = true;
        scene = FarmMenu.r.getScene();
        creatImage();
    }

    void backHome() {
        //Farm.getFarm().money.set(Farm.getFarm().money.get()+transportPrice);
        Farm.getFarm().addMoney(transportPrice);
        transportPrice = 0;
        present = true;
        load = 0;
        objects.clear();
        setImage();
    }

    public boolean load(Object object) {
        if (((Sellable) object).getSpace() > capacity - load)
            return false;
        else {
            objects.add(object);
            load += ((Sellable) object).getSpace();
            Farm.getFarm().truckLoad(((Sellable) object).getName());
        }
        return true;
    }

    public boolean unload(Object object) {
        if (objects.contains(object)) {
            load -= ((Sellable) object).getSpace();
            objects.remove(object);
            Farm.getFarm().truckUnload(object);
            return true;
        }
        return false;
    }

    public String transport() {
        if (!present)
            return "The truck has not yet returned from the previous trip";
        if (objects.isEmpty())
            return "You must first load the truck";
        transportPrice = 0;
        for (Object object : objects) {
            transportPrice += ((Sellable) object).sell();
        }
        returningTime = LocalDate.getInstance().getCurrentTime() + TRANSPORT_TIME * 100000000L;
        LocalDate.getInstance().event.put(returningTime, this);
        present = false;
        setImage();
        return "The truck started its journey";
    }

    static Truck truck;

    private void setImage() {
        if (present) {
            imageView.setX(0.26 * scene.getWidth());
            imageView.setY(0.86 * scene.getHeight());
        } else {
            imageView.setX(0.75 * scene.getWidth());
            imageView.setY(0.1 * scene.getHeight());
        }
    }
    private void creatImage() {
        Image image = null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\HelloFX\\img\\truck1.gif"));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);
        setImage();
        imageView.setPreserveRatio(true);

        ((Pane) scene.getRoot()).getChildren().add(imageView);
        imageView.setFitHeight(scene.getWidth() / 5);
        imageView.setFitWidth(scene.getHeight() / 5);

    }

    public static Truck getTruck() {
        if (truck == null)
            truck = new Truck();
        return truck;
    }

    @Override
    public String checkAfterChangeDate() {
        String s = "truck back home with $ " + transportPrice + "\n";
        LocalDate.getInstance().event.remove(returningTime, this);
        backHome();
        Logger.getLogger(User.getCurrentUser()).log(s, Logger.LogType.Info);
        System.out.println(s);
        return "";
        //return s;
    }

    @Override
    public String toString() {
        String s = "\ntruck status :";
        if (present) {
            s += "present\n";
            if (!objects.isEmpty())
                s += "truck load : \n";
            for (Object object : objects) {
                s+=((Sellable)object).getName() +", ";
            }
            s+="\n";
        }
        else {
            s += "in trip with $ "+transportPrice+".\n";
        }
        return s;
    }
}
