package model;

public class Textile extends Workshop {
    static int price = 250;

    public Textile() {
        super("Textile", ProductType.FEATHER, ProductType.CLOTH, 250, 4);
    }

    public static int buy() {
        if (Farm.getFarm().getWorkshop("Textile") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Textile();
                return 1;
            }
        return 2;
    }
}
