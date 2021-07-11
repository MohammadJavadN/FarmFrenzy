package model;

public class Mill extends Workshop {
    static int price = 150;

    public Mill() {
        super("Mill", ProductType.EGG, ProductType.FLOUR, 150, 4);
    }

    public static int buy() {
        if (Farm.getFarm().getWorkshop("Mill") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Mill();
                return 1;
            }
        return 2;
    }
}
