package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;

public class Domestic extends Animal implements Sellable, Changeable {
    // Farm farm;
    ProductType productType;
    int live;

    public int getPrice() {
        return price;
    }

    int price;
    int processTime;
    long produceDate;
    int space;
    static int i = 0;
    int id;

    public String getImagePath() {
        return imagePath;
    }

    String imagePath;

    public Domestic(ProductType productType, int processTime, int price, int space, String name, Scene scene, Pane root, String path) {
        super(name, scene, root, path);
        this.productType = productType;
        this.price = price;
        this.processTime = processTime;
        this.space = space;
        id = i;
        i++;
        live = 100;
        produceDate = LocalDate.getInstance().getCurrentTime() + processTime * 100000000L;
        LocalDate.getInstance().event.put(produceDate, this);
        //Farm.getFarm().money.set(Farm.getFarm().money.get()-price);
        Farm.getFarm().addMoney(-price);
        Farm.getFarm().addDomestic(this);
        //Farm.getFarm().domestics.add(this);
        this.imagePath = path;
    }

    public void destroying() {
        if (produceDate / 100000000L != LocalDate.getInstance().getCurrentTime() / 100000000L)
            LocalDate.getInstance().event.remove(produceDate, this);
        Farm.getFarm().remDomestics(this);
        imageView.setVisible(false);
//        Farm.getFarm().domestics.remove(this);
    }

    public int sell() {
        destroying();
        //Farm.getFarm().domestics.remove(this);
        return price;
    }

    @Override
    public String getName() {
        return NAME;
    }

    @Override
    public int getSpace() {
        return space;
    }

    String move() {
        String s = setVelocity();
        addXP(xVelocity);
        addYP(yVelocity);
        Logger.getLogger(User.getCurrentUser()).log(NAME + " move ", Logger.LogType.Info);
        Logger.getLogger(User.getCurrentUser()).log(s, Logger.LogType.Info);
        System.out.println(s);
        System.out.println("65 domestic "+ xPosition.get() + " " + yPosition.get());
        return "";
        //return s;
    }

    public void produce() {
        new Product(productType, xPosition.get(), yPosition.get(),scene);
    }

    public boolean equal(String name) {
        return this.NAME.equalsIgnoreCase(name);
    }

    String setVelocity() {// setVelocity & eat & manage live
        String s ="";
        int I = random.nextInt(6), J = random.nextInt(6);
        int distance = -1;
        if (live < 50) {
            s += NAME + " in [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "] looked for food \n";
            for (int i = 0; i < Farm.getFarm().ROW; i++) {
                for (int j = 0; j < Farm.getFarm().COL; j++) {
                    if (Farm.getFarm().grass[i][j] > 0 && (distance == -1 || distance > (Math.abs(xPosition.get() - j) + Math.abs(yPosition.get() - i)))) {
                        distance = Math.abs(xPosition.get() - j) + Math.abs(yPosition.get() - i);
                        I = i;
                        J = j;
                    }
                }
            }

            int velocity = 1;
            ///////////increase speed for arrive food
            if (distance == 0) {
                eat(I, J);
                return s + NAME + " eat grass in [" + (I + 1) + "," + (J + 1) + "]\n";
            }
            if (distance > 3)
                velocity = 2;

            if (Math.abs(xPosition.get() - J) > Math.abs(yPosition.get() - I)) {
                xVelocity = velocity * Integer.signum(J - xPosition.get());
                yVelocity = 0;
            } else {
                yVelocity = velocity * Integer.signum(I - yPosition.get());
                xVelocity = 0;
            }
        } else setVelocity(1);
        live -= 10;
        if (live <= 0) {
            destroying();
            s += NAME + " in [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "] died of malnutrition\n";
        }
        return s;
    }

    private void eat(int i, int j) {
        Farm.getFarm().remGrass(i,j);
        live = 100;
    }

    @Override
    public String checkAfterChangeDate() {
        LocalDate.getInstance().event.remove(produceDate, this);
        produce();
        produceDate += processTime * 100000000L;
        LocalDate.getInstance().event.put(produceDate, this);
        Logger.getLogger(User.getCurrentUser()).log(NAME + " in [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "] produce " + productType.name(), Logger.LogType.Info);
        System.out.println(NAME + " in [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "] produce " + productType.name());
        return "";
        //return NAME + " in [" + (xPosition + 1) + "," + (yPosition + 1) + "] produce " + productType.name() + "\n";
    }

    public String toString() {
        return NAME + " " + live + "% [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "]\n";
    }
}
