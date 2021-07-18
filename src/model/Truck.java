package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.FarmMenu;
import view.ShipProductMenu;
import view.Sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;

public class Truck implements Changeable {
    public HashMap<String, Integer> productCount = new HashMap<>();

    int transportPrice;
    final int TRANSPORT_TIME = 10;
    Label tPrice = new Label();

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
        tPrice.getStyleClass().add("label-well");
    }

    public int getTransportPrice() {
        int c = 0;
        for (Object object : objects) {
            c += ((Sellable) object).getPrice();
        }
        return c;
    }

    public ArrayList<Object> getObjects() {
        return objects;
    }

    public Object getObject(String name) {
        for (Object o : objects) {
            if (((Sellable) o).getName().equalsIgnoreCase(name))
                return o;
        }
        return null;
    }

    void backHome() {
        //Farm.getFarm().money.set(Farm.getFarm().money.get()+transportPrice);
        Farm.getFarm().addMoney(transportPrice);
        transportPrice = 0;
        present = true;
        load = 0;
        productCount.clear();
        objects.clear();
        setImage();
    }

    public boolean load(Object object) {
        if (((Sellable) object).getSpace() > capacity - load)
            return false;
        else {
            if (!productCount.containsKey(((Sellable) object).getName()))
                productCount.put(((Sellable) object).getName(), 1);
            else
                productCount.replace(((Sellable) object).getName(), productCount.get(((Sellable) object).getName()) + 1);
            objects.add(object);
            load += ((Sellable) object).getSpace();
            Farm.getFarm().truckLoad(((Sellable) object).getName());
        }
        return true;
    }

    public boolean unload(String name) {
        for (Object o : objects) {
            if (((Sellable) o).getName().equalsIgnoreCase(name)) {
                return unload(o);

            }
        }
        return false;
    }

    public HashMap<String, Integer> getProductCount() {
        return productCount;
    }

    public void unload() {
        while (objects.size() > 0)
            unload(objects.get(0));
    }

    public boolean unload(Object object) {
        if (objects.contains(object)) {
            load -= ((Sellable) object).getSpace();
            if (productCount.get(((Sellable) object).getName()) < 2)
                productCount.remove(((Sellable) object).getName());
            else
                productCount.replace(((Sellable) object).getName(), productCount.get(((Sellable) object).getName()) - 1);
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
        Warehouse.getWarehouse().resetProductsImage();
        Sound.playSoundAC("sounds\\truck.wav");
        return "The truck started its journey";
    }

    static Truck truck;

    public int getLoad() {
        return load;
    }

    private void setImage() {
        if (present) {
            imageView.setX(0.26 * scene.getWidth());
            imageView.setY(0.86 * scene.getHeight());
            tPrice.setVisible(false);
            imageView.setOnMouseClicked(e -> (new ShipProductMenu()).run());
        } else {
            imageView.setX(0.75 * scene.getWidth());
            imageView.setY(0.02 * scene.getHeight());
            tPrice.setText("$ " + transportPrice);
            tPrice.setVisible(true);
            tPrice.setLayoutX(imageView.getX());
            tPrice.setLayoutY(imageView.getY());
            if (!((Pane) scene.getRoot()).getChildren().contains(tPrice))
                ((Pane) scene.getRoot()).getChildren().add(tPrice);
            imageView.setOnMouseClicked(e -> System.out.print(""));
        }
    }
    private void creatImage() {
        Image image = null;
        try {
            image = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\truck1.gif"));
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
