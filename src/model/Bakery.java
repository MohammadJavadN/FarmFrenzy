package model;

public class Bakery extends Workshop {
    static int price = 250;

    public Bakery() {
        super("Bakery", ProductType.FLOUR, ProductType.BREAD, 250, 5);
    }

    public static int buy() {
        if (Farm.getFarm().getWorkshop("Bakery") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Bakery();
                return 1;
            }
        return 2;
    }
}
