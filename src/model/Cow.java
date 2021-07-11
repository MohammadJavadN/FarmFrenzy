package model;

public class Cow extends Domestic {
    static int price=400;
    private Cow() {
        super(ProductType.COW_MILK, 5, 400, 4, "cow");
    }
    public static boolean buy() {
        if (Farm.getFarm().getMoney() >= price) {
            new Cow();
            return true;
        }
        return false;
    }

}
