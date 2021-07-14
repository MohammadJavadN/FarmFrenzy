package view;

import controller.Manager;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class ShipProductMenu extends Menu {
    private static ShipProductMenu shipProductMenu;
    Manager manager = Manager.getInstance();
    Warehouse warehouse = Warehouse.getWarehouse();
    Truck truck = Truck.getTruck();
    Farm farm = Farm.getFarm();
    boolean load = false;
    ArrayList<Product> products = new ArrayList<>();
    ArrayList<Domestic> domestics = new ArrayList<>();
    public static ShipProductMenu getInstance() {
        if (shipProductMenu == null)
            shipProductMenu = new ShipProductMenu();
        return shipProductMenu;
    }
    ArrayList<Object> objects = new ArrayList<>();

    @Override
    public void run() {
        window = new Stage();
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle("Ship Products");
        window.setMinWidth(500);
        show();
    }

    @Override
    public void show() {
        Pane root = new Pane();
        root.setId("ship");
        scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("Viper.css");

        //Block events to other windows

        Button cancelButton = new Button("Cancel");
        cancelButton.getStyleClass().add("button-blue");
        cancelButton.setOnAction(e -> {
            truck.unload();
            window.close();// TODO: ۱۴/۰۷/۲۰۲۱
        });

        Button okButton = new Button("OK");
        okButton.getStyleClass().add("button-green");
        okButton.setOnAction(e -> {
            manager.truckGo();
            okButton.setVisible(false);
            window.close();
        });
        okButton.setVisible(load);
        products.clear();
        for (String s : warehouse.productCount.keySet()) {
            products.add(warehouse.getProduct(s));
        }
        VBox vBox = new VBox(3.5);
        vBox.setLayoutX(0.16 * scene.getWidth());
        vBox.setLayoutY(0.18 * scene.getHeight());
        Button[] addButton = new Button[warehouse.productCount.size()];
        Label[] labels = new Label[warehouse.productCount.size()];
        HBox[] hBox = new HBox[warehouse.productCount.size()];
        ImageView[] imageView = new ImageView[warehouse.productCount.size()];
        int i = 0;
        for (HBox box : hBox) {
            box = new HBox(15);
            addToHBoxGL(box, i, labels[i], addButton[i], imageView[i]);
            vBox.getChildren().add(box);
            i++;
        }
        domestics.clear();
        for (String s : farm.getDomesticsNum().keySet()) {
            domestics.add((Domestic) farm.getObject(s));
        }
        i =0;
        Button[] addButtonA = new Button[farm.getDomesticsNum().size()];
        Label[] labelsA = new Label[farm.getDomesticsNum().size()];
        HBox[] hBoxA = new HBox[farm.getDomesticsNum().size()];
        ImageView[] imageViewA = new ImageView[farm.getDomesticsNum().size()];
        if (farm.getDomesticsNum().size()>0)
        for (HBox box : hBoxA) {
            box = new HBox(15);
            addToHBoxAL(box, i, labelsA[i], addButtonA[i], imageViewA[i]);
            vBox.getChildren().add(box);
            i++;
        }
        objects.clear();
        for (String s : truck.productCount.keySet()) {
            objects.add(truck.getObject(s));
        }
        i = 0;
        VBox vBox2 = new VBox(3.5);
        vBox2.setLayoutX(0.41 * scene.getWidth());
        vBox2.setLayoutY(0.18 * scene.getHeight());
        Button[] remButton = new Button[truck.getProductCount().size()];
        Label[] labels2 = new Label[truck.getProductCount().size()];
        HBox[] hBox2 = new HBox[truck.getProductCount().size()];
        ImageView[] imageView2 = new ImageView[truck.getProductCount().size()];
        for (HBox box : hBox2) {
            box = new HBox(15);
            addToHBoxUnL(box, i, labels2[i], remButton[i], imageView2[i]);
            vBox2.getChildren().add(box);
            i++;
        }
        HBox layout = new HBox(10);
        layout.getChildren().addAll(okButton, cancelButton);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        layout.setLayoutX(0.45 * scene.getWidth());
        layout.setLayoutY(0.9 * scene.getHeight());

        Label coin = new Label(String.valueOf(truck.getTransportPrice()));
        coin.setLayoutX(0.72*scene.getWidth());
        coin.setLayoutY(0.8*scene.getHeight());
        coin.getStyleClass().add("label-well");
        root.getChildren().addAll(layout, vBox,vBox2,coin);

        window.setScene(scene);
        window.setResizable(false);
        window.show();

    }
    void addToHBoxAL(HBox box, int i, Label label, Button addButton, ImageView imageView) {
        Domestic d = domestics.get(i);
        if (d == null)
            return;
        label = new Label(" * " + farm.getNumberOfDomestics(d.getName()) + "       " + d.getPrice() + "          ");
        addButton = new Button("Load");
        addButton.setOnAction(e -> {
            manager.truckLoad(d.getName());
            load = true;
            show();
        });// TODO: ۱۴/۰۷/۲۰۲۱

        Image image = null;
        try {
            image = new Image(new FileInputStream(d.getImagePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);
        imageView.setFitHeight(scene.getWidth() / 45);
        imageView.setFitWidth(scene.getHeight() / 30);

        box.getChildren().addAll(imageView, label, addButton);
    }

    void addToHBoxGL(HBox box, int i, Label label, Button addButton, ImageView imageView) {
        Product p = products.get(i);
        if (p == null)
            return;
        label = new Label(" * " + warehouse.productCount.get(p.getName()) + "       " + p.getPrice() + "          ");
        addButton = new Button("Load");
        addButton.setOnAction(e -> {
            manager.truckLoad(p.getName());
            load = true;
            show();
        });// TODO: ۱۴/۰۷/۲۰۲۱

        Image image = null;
        try {
            image = new Image(new FileInputStream(p.getImagePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);
        imageView.setFitHeight(scene.getWidth() / 45);
        imageView.setFitWidth(scene.getHeight() / 30);

        box.getChildren().addAll(imageView, label, addButton);
    }

    void addToHBoxUnL(HBox box, int i, Label label, Button addButton, ImageView imageView) {
        Object p =objects.get(i);
        if (p == null)
            return;
        label = new Label(" * " + truck.productCount.get(((Sellable)p).getName()) + "       " + ((Sellable)p).getPrice() + "          ");
        addButton = new Button("Unload");
        addButton.setOnAction(e -> {
            manager.truckUnload(((Sellable)p).getName());
            if (truck.getLoad() < 1) load = false;
            show();
        });// TODO: ۱۴/۰۷/۲۰۲۱

        Image image = null;
        try {
            image = new Image(new FileInputStream(((Sellable)p).getImagePath()));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        imageView = new ImageView(image);
        imageView.setFitHeight(scene.getWidth() / 45);
        imageView.setFitWidth(scene.getHeight() / 30);

        box.getChildren().addAll(imageView, label, addButton);
    }

}
