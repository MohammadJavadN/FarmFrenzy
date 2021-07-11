package model;

public class MilkPackaging extends Workshop {
    static int price = 400;

    public MilkPackaging() {
        super("MilkPackaging", ProductType.COW_MILK, ProductType.MILK, 400, 6);
    }

    public static int buy() {
        if (Farm.getFarm().getWorkshop("MilkPackaging") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new MilkPackaging();
                return 1;
            }
        return 2;
    }
}
