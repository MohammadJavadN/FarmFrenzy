package model;

import controller.Logger;
import model.User;

public class Product implements Sellable, Destroyable, Changeable {
    String name;
    int space;
    int price;
    boolean catComeFor = false;
    int expirationTime;
    long expirationDate;
    int x, y; // 0-5

    public Product(ProductType type, int x, int y) {
        this.x = x;
        this.y = y;
        switch (type) {
            case EGG:
                price = 15;
                space = 1;
                expirationTime = 4;
                name = "EGG";
                break;
            case FEATHER:
                price = 20;
                space = 1;
                expirationTime = 4;
                name = "FEATHER";
                break;
            case COW_MILK:
                price = 25;
                space = 1;
                expirationTime = 4;
                name = "COW_MILK";
                break;
            case FLOUR:
                price = 40;
                space = 2;
                expirationTime = 5;
                name = "FLOUR";
                break;
            case CLOTH:
                price = 50;
                space = 2;
                expirationTime = 5;
                name = "CLOTH";
                break;
            case MILK:
                price = 60;
                space = 2;
                expirationTime = 5;
                name = "MILK";
                break;
            case BREAD:
                price = 80;
                space = 4;
                expirationTime = 6;
                name = "BREAD";
                break;
            case SHIRT:
                price = 100;
                space = 4;
                expirationTime = 6;
                name = "SHIRT";
                break;
            case ICE_CREAM:
                price = 120;
                space = 4;
                expirationTime = 6;
                name = "ICE_CREAM";
                break;
            case LION:
                price = 300;
                space = 15;
                expirationTime = 5;
                name = "LION";
                break;
            case BEAR:
                price = 400;
                space = 15;
                expirationTime = 5;
                name = "BEAR";
                break;
            case TIGER:
                price = 500;
                space = 15;
                expirationTime = 5;
                name = "TIGER";
                break;
            default:
                break;
        }
        expirationDate = expirationTime*100000000L + LocalDate.getInstance().getCurrentTime();
        LocalDate.getInstance().event.put(expirationDate, this);
        Farm.getFarm().products.add(this);
    }

    public boolean checkCoordinates(int x, int y) {//1-6
        return x - 1 == this.x && y - 1 == this.y;
    }

    @Override
    public int sell() {
        Warehouse.getWarehouse().removeProduct(this);
        return price;
    }

    @Override
    public String getName() {
        return name;
    }

    public boolean collect() {
        if (Farm.getFarm().products.contains(this) && Warehouse.getWarehouse().emptySpace.get() >= space) {
            destroying();
            Warehouse.getWarehouse().addProduct(this);
            return true;
        }
        return false;
    }

    @Override
    public int getSpace() {
        return space;
    }

    @Override
    public void destroying() {
        LocalDate.getInstance().event.remove(expirationDate, this);
        Farm.getFarm().products.remove(this);
    }

    public boolean equal(String name) {
        return this.name.equalsIgnoreCase(name);
    }

    @Override
    public String checkAfterChangeDate() {
        destroying();
        Logger.getLogger(User.getCurrentUser()).log(name + " in [" + (x + 1) + "," + (y + 1) + "] was destroyed", Logger.LogType.Info );
        System.out.println( name + " in [" + (x + 1) + "," + (y + 1) + "] was destroyed");
        return "";
        //return name + " in [" + (x + 1) + "," + (y + 1) + "] was destroyed\n";
    }
    public String toString(){
        return name + " [" + (x + 1) + "," + (y + 1) + "]\n";
    }
}

