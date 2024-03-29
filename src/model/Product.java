package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import view.Sound;

import java.io.FileInputStream;
import java.io.FileNotFoundException;


public class Product implements Sellable, Destroyable, Changeable {
    String name;
    int space;
    int price;

    public Product(ProductType type, int x, int y, Scene scene) {
        this.x = x;
        this.y = y;
        switch (type) {
            case EGG:
                price = 15;
                space = 1;
                expirationTime = 4;
                name = "EGG";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\egg.png";
                break;
            case FEATHER:
                price = 20;
                space = 1;
                expirationTime = 4;
                name = "FEATHER";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\feather.png";
                break;
            case COW_MILK:
                price = 25;
                space = 1;
                expirationTime = 4;
                name = "COW_MILK";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cow_milk1.png";
                break;
            case FLOUR:
                price = 40;
                space = 2;
                expirationTime = 5;
                name = "FLOUR";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\flour.png";
                break;
            case CLOTH:
                price = 50;
                space = 2;
                expirationTime = 5;
                name = "CLOTH";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cloth.png";
                break;
            case MILK:
                price = 60;
                space = 2;
                expirationTime = 5;
                name = "MILK";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\milk.png";
                break;
            case BREAD:
                price = 80;
                space = 4;
                expirationTime = 6;
                name = "BREAD";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\bread.png";
                break;
            case SHIRT:
                price = 100;
                space = 4;
                expirationTime = 6;
                name = "SHIRT";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\shirt.png";
                break;
            case ICE_CREAM:
                price = 120;
                space = 4;
                expirationTime = 6;
                name = "ICE_CREAM";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\ice_cream1.png";
                break;
            case LION:
                price = 300;
                space = 15;
                expirationTime = 5;
                name = "LION";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cage_lion.png";
                break;
            case BEAR:
                price = 400;
                space = 15;
                expirationTime = 5;
                name = "BEAR";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cage_bear.png";
                break;
            case TIGER:
                price = 500;
                space = 15;
                expirationTime = 5;
                name = "TIGER";
                imagePath = "C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cage_tiger.png";
                break;
            default:
                break;
        }
        expirationDate = expirationTime * 100000000L + LocalDate.getInstance().getCurrentTime();
        LocalDate.getInstance().event.put(expirationDate, this);
        Farm.getFarm().products.add(this);
        creatProductImage(scene);
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getPrice() {
        return price;
    }

    boolean catComeFor = false;
    int expirationTime;
    long expirationDate;
    int x, y; // 0-5

    public String getImagePath() {
        return imagePath;
    }

    public ImageView getImageView() {
        return imageView;
    }

    ImageView imageView;
    String imagePath;

    public void setPrice(int price) {
        this.price = price;
    }

    private void creatProductImage(Scene scene) {
        Image image = null;
        try {
            image = new Image(new FileInputStream(imagePath));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        imageView = new ImageView(image);

        imageView.setX(scene.getWidth() * (x * (0.065) + 0.33));
        imageView.setY(scene.getHeight() * (y * 0.075 + 0.33));
        imageView.setPreserveRatio(true);
        imageView.setOnMouseClicked(e -> {
            if (collect())
                Sound.playSound("sounds\\add.wav");
            else
                Sound.playSound("sounds\\error.wav");
        });
        ((Pane) scene.getRoot()).getChildren().add(imageView);
        imageView.setFitHeight(scene.getWidth() / 15);
        imageView.setFitWidth(scene.getHeight() / 15);
        if (name.equalsIgnoreCase("EGG")) {
            imageView.setFitHeight(scene.getWidth() / 30);
            imageView.setFitWidth(scene.getHeight() / 30);
        }

        if (name.equals("LION") || name.equals("BEAR") || name.equals("TIGER")) {
            imageView.setFitHeight(scene.getWidth() / 10);
            imageView.setFitWidth(scene.getHeight() / 10);
        }
    }

    @Override
    public int sell() {
        imageView.setVisible(false);
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean collect() {
        if (Farm.getFarm().products.contains(this) && Warehouse.getWarehouse().emptySpace.get() >= space) {
            destroying();
            Warehouse.getWarehouse().addProduct(this);
            return true;
        }
        return false;
    }

    @Override
    public int getSpace() {
        return space;
    }

    @Override
    public void destroying() {
        LocalDate.getInstance().event.remove(expirationDate, this);
        Farm.getFarm().products.remove(this);
    }

    public boolean equal(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    @Override
    public void checkAfterChangeDate() {
        destroying();
        imageView.setVisible(false);
        Logger.getLogger(User.getCurrentUser()).log(name + " in [" + (x + 1) + "," + (y + 1) + "] was destroyed", Logger.LogType.Info );
    }
    public String toString(){
        return name + " [" + (x + 1) + "," + (y + 1) + "]\n";
    }
}

