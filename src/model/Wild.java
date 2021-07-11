package model;

import controller.Logger;
import model.User;

import java.util.ArrayList;
import java.util.Locale;

public class Wild extends Animal implements Changeable {

    ProductType wildType;
    int velocity;
    int strength;
    int freedom;
    long lastTrapTime;

    //    int sellPrice;
    public Wild(ProductType wildType) {
        super(wildType.name().toLowerCase(Locale.ROOT));
        this.wildType = wildType;
    }

    public boolean checkCoordinates(int x, int y) {//1-6
        return x - 1 == this.xPosition && y - 1 == this.yPosition;
    }

    public boolean trap() {
        if (freedom != strength) {
            if ((LocalDate.getInstance().getCurrentTime() - lastTrapTime) / 100000000L - 1 > 0)
                freedom += (LocalDate.getInstance().getCurrentTime() - lastTrapTime) / 100000000L - 1;
            freedom = Math.min(freedom, strength);
        }
        freedom--;
        lastTrapTime = LocalDate.getInstance().getCurrentTime();
        if (freedom == 0) {
            new Product(wildType, xPosition, yPosition);
            destroying();
            return true;
        }
        return false;
    }

    public String kill() {//this method must call after check [if (free)]
        String s = "";
        for (int i = 0; i <= xVelocity; i++) {
            for (int j = 0; j <= yVelocity; j++) {
                if ((s += kill(xPosition + i, yPosition + j)).contains("hound"))
                    return s;
            }
        }
        return s;
    }

    private String kill(int x, int y) {// if face dog return false else return true
        String s = "";
        ArrayList<Dog> dogs = new ArrayList<>();
        for (Dog dog : Farm.getFarm().dogs) {
            if (dog.xPosition == x && dog.yPosition == y) {
                dogs.add(dog);
                this.destroying();
                s += "The wild animal got involved with a hound in [" + (dog.xPosition + 1) + "," + (dog.yPosition + 1) + "] \n";
                return s;
            }
        }
        for (Dog dog : dogs) {
            dog.destroying();
        }
        ArrayList<Cat> cats = new ArrayList<>();
        for (Cat cat : Farm.getFarm().cats) {
            if (cat.xPosition == x && cat.yPosition == y) {
                cats.add(cat);
                s += "The wild animal killed cat in [" + (cat.xPosition + 1) + "," + (cat.yPosition + 1) + "] \n";
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
            if (domestic.xPosition == x && domestic.yPosition == y) {
                domestics.add(domestic);
                s += "The wild animal killed " + domestic.NAME + "in [" + (domestic.xPosition + 1) + "," + (domestic.yPosition + 1) + "] \n";
            }
        }
        for (Domestic domestic : domestics) {
            domestic.destroying();
        }
        return s;
    }

    String move() {//this method must call after check [if (free)]
        String s = "";
        setVelocity(velocity);
        s += kill();
        xPosition += xVelocity;
        yPosition += yVelocity;
        Logger.getLogger(User.getCurrentUser()).log(wildType.name() + " move ", Logger.LogType.Info);
        Logger.getLogger(User.getCurrentUser()).log(s, Logger.LogType.Info );
        System.out.println(s);
        return "";
       // return s;
    }

    @Override
    public void destroying() {
        Farm.getFarm().wilds.remove(this);
    }

    public String toString() {
        return NAME + " " + freedom + " [" + (xPosition + 1) + "," + (yPosition + 1) + "]\n";
    }

    @Override
    public String checkAfterChangeDate() {
        Farm.getFarm().wilds.add(this);
        long comingDate = 0;
        for (Long aLong : LocalDate.getInstance().event.keySet()) {
            if (LocalDate.getInstance().event.get(aLong) == this)
                comingDate = aLong;
        }
        LocalDate.getInstance().event.remove(comingDate);
        Logger.getLogger(User.getCurrentUser()).log("The "+wildType.name() +" entered the farm ", Logger.LogType.Info);
        System.out.println("The "+wildType.name() +" entered the farm ");
        return null;
    }
}
