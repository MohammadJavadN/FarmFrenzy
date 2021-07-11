package model;

import controller.Logger;
import model.User;

import java.util.ArrayList;

public class Cat extends Animal {
    static final int price = 150;
    Product product;

    private Cat() {
        super("cat");

        Farm.getFarm().cats.add(this);
    }

    public static boolean buy() {
        if (Farm.getFarm().getMoney() >= price) {
            new Cat();
            return true;
        }
        return false;
    }

    public String collectProduct() {
        String s = "";
        ArrayList<Product> collectedProducts = new ArrayList<>();
        if (product.x == xPosition + xVelocity && product.y == yPosition + yVelocity) {
            if (Farm.getFarm().products.contains(product))
                collectedProducts.add(product);
            product = null;
            for (Product productF : Farm.getFarm().products) {
                if (productF.y == yPosition + yVelocity && productF.x == xPosition + xVelocity) {
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
    }

    String move() {
        String s = "";
        setVelocity();
        if (product != null) s += collectProduct();
        xPosition += xVelocity;
        yPosition += yVelocity;
        Logger.getLogger(User.getCurrentUser()).log("Cat move ", Logger.LogType.Info );
        if (s.length() > 0) {
            s = s.substring(0, s.length() - 2);
            System.out.println( "cat collect this product : [" + s + "]\n");
            //return "cat collect this product : [" + s + "]\n";
        }
        return "";
    }

    void setVelocity() {
        int distance = -1;
        if (product == null) {
            for (Product productF : Farm.getFarm().products) {
                if ((distance == -1 || distance > (Math.abs(xPosition - productF.x) + Math.abs(yPosition - productF.y)))) {
                    distance = Math.abs(xPosition - productF.x) + Math.abs(yPosition - productF.y);
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

            if (Math.abs(xPosition - product.x) > Math.abs(yPosition - product.y)) {
                xVelocity = velocity * Integer.signum(product.x - xPosition);
                yVelocity = 0;
            } else {
                yVelocity = velocity * Integer.signum(product.y - yPosition);
                xVelocity = 0;
            }
        } else setVelocity(1);
    }
    public String toString(){
        return NAME + " [" + (xPosition + 1) + "," + (yPosition + 1) + "]\n";
    }
}
