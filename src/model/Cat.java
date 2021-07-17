package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.Sound;

import java.util.ArrayList;

public class Cat extends Animal {
    static final int price = 150;
    Product product;

    private Cat(Scene scene, Pane root) {
        super("cat", scene, root,"C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\cat.png");
        Farm.getFarm().addMoney(-price);
        Farm.getFarm().cats.add(this);
        Sound.playSound("sounds\\cat.wav");
    }

    public static boolean buy(Scene scene, Pane root) {
        if (Farm.getFarm().getMoney() >= price) {
            new Cat(scene, root);
            return true;
        }
        return false;
    }

    public String collectProduct() {
        String s = "";
        ArrayList<Product> collectedProducts = new ArrayList<>();
        if (product.x == xPosition.get() + xVelocity && product.y == yPosition.get() + yVelocity) {
            if (Farm.getFarm().products.contains(product))
                collectedProducts.add(product);
            product = null;
            for (Product productF : Farm.getFarm().products) {
                if (productF.y == yPosition.get() + yVelocity && productF.x == xPosition.get() + xVelocity) {
                    if (!productF.catComeFor)
                        collectedProducts.add(productF);
                }
            }
            for (Product collectedProduct : collectedProducts) {
                s += collectedProduct.name + ", ";
                collectedProduct.collect();
            }
        }
        return s;
    }

    @Override
    public void destroying() {
        if (product != null)
            product.catComeFor = false;
        Farm.getFarm().cats.remove(this);
        imageView.setVisible(false);
    }

    String move() {
        String s = "";
        setVelocity();
        if (product != null) s += collectProduct();
        addXP(xVelocity);
        addYP(yVelocity);
        Logger.getLogger(User.getCurrentUser()).log("Cat move ", Logger.LogType.Info);
        if (s.length() > 0) {
            s = s.substring(0, s.length() - 2);
            System.out.println("cat collect this product : [" + s + "]\n");
            //return "cat collect this product : [" + s + "]\n";
        }
        return "";
    }

    void setVelocity() {
        int distance = -1;
        if (product == null) {
            for (Product productF : Farm.getFarm().products) {
                if ((distance == -1 || distance > (Math.abs(xPosition.get() - productF.x) + Math.abs(yPosition.get() - productF.y)))) {
                    distance = Math.abs(xPosition.get() - productF.x) + Math.abs(yPosition.get() - productF.y);
                    product = productF;
                }
            }
        }
        if (product != null && Farm.getFarm().products.contains(product)) {
            product.catComeFor = true;
            int velocity = 1;
            ///////////increase speed for arrive food
            if (distance > 3)
                velocity = 2;

            if (Math.abs(xPosition.get() - product.x) > Math.abs(yPosition.get() - product.y)) {
                xVelocity = velocity * Integer.signum(product.x - xPosition.get());
                yVelocity = 0;
            } else {
                yVelocity = velocity * Integer.signum(product.y - yPosition.get());
                xVelocity = 0;
            }
        } else setVelocity(1);
    }
    public String toString(){
        return NAME + " [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "]\n";
    }
}
