package model;

public class IceCreamShop extends Workshop {
    static int price = 550;

    public IceCreamShop() {
        super("IceCreamShop", ProductType.MILK, ProductType.ICE_CREAM, 550, 7);
    }

    public static int buy() {
        if (Farm.getFarm().getWorkshop("IceCreamShop") != null)
            return 0;
            if (Farm.getFarm().getMoney() >= price) {
                new IceCreamShop();
                return 1;
            }
        return 2;
    }
}
