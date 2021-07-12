package model;

public class Chicken extends Domestic {
    static int price =100;
    Chicken() {
        super(ProductType.EGG, 2, 100, 1, "chicken");
    }

    public static boolean buy() {
        if (Farm.getFarm().getMoney() >= price) {
            new Chicken();
            return true;
        }
        return false;
    }

}
