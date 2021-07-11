package model;

public class Tailoring extends Workshop {
    static int price = 400;

    public Tailoring() {
        super("Tailoring", ProductType.CLOTH, ProductType.SHIRT, 400, 6);
    }

    public static int buy() {
        if (Farm.getFarm().getWorkshop("Tailoring") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new Tailoring();
                return 1;
            }
        return 2;
    }
}
