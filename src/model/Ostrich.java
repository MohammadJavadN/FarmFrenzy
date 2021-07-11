package model;

public class Ostrich extends Domestic {
    static int price =200;
    private Ostrich() {
        super(ProductType.FEATHER, 3, 200, 2, "ostrich");
    }
    public static boolean buy() {
        if (Farm.getFarm().getMoney() >= price) {
            new Ostrich();
            return true;
        }
        return false;
    }

}
