package model;

import java.util.ArrayList;
import java.util.Arrays;

public class Farm {
    final int ROW = 6;
    final int COL = 6;
    ArrayList<Domestic> domestics = new ArrayList<>();
    ArrayList<Wild> wilds = new ArrayList<>();
    ArrayList<Cat> cats = new ArrayList<>();
    ArrayList<Dog> dogs = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    int[][] grass = new int[ROW][COL];
    ArrayList<Workshop> workshops = new ArrayList<>();
    Warehouse warehouse;
    int money;

    public void clear(){
        domestics.clear();
        wilds.clear();
        cats.clear();
        dogs.clear();
        products.clear();
        for (int i = 0; i < ROW; i++) {
            for (int j = 0; j < COL; j++) {
                grass[i][j] = 0;
            }
        }
        workshops.clear();
        Warehouse.getWarehouse().clear();
        money = 0;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getNumberOfDomestics(String domesticName) {
        int cnt = 0;
        for (Domestic domestic1 : domestics) {
            if (domestic1.NAME.equals(domesticName))
                cnt++;

        }
        return cnt;
    }

    public int getMoney() {
        return money;
    }

    public Workshop getWorkshop(String name) {
        for (Workshop workshop : workshops) {
            if (workshop.equal(name))
                return workshop;
        }
        return null;
    }

    public Object getObject(String name) {
        for (Product product : warehouse.products) {
            if (product.equal(name))
                return product;
        }
        for (Domestic domestic : domestics) {
            if (domestic.equal(name))
                return domestic;
        }
        return null;
    }

    public void truckLoad(String name) {
        Object o = null;
        for (Product product : warehouse.products) {
            if (product.equal(name)) {
                o = product;
                break;
            }
        }
        if (o != null) {
            warehouse.products.remove(o);
            return;
        }
        for (Domestic domestic : domestics) {
            if (domestic.equal(name)) {
                o = domestic;
                break;
            }
        }
        if (o != null)
            domestics.remove(o);
    }

    public void truckUnload(Object o) {
        if ("cow buffalo chicken ostrich".contains(((Sellable) o).getName())) {
            domestics.add((Domestic) o);
        } else warehouse.products.add((Product) o);
    }

    public ArrayList<Wild> getWilds() {
        return wilds;
    }

    public ArrayList<Product> getProducts() {
        return products;
    }

    public boolean insidePage(int x, int y) {
        return x <= COL && x >= 1 && y <= ROW && y >= 1;
    }

    public String toString() {
        String s = "\n";
        for (int i = 0; i < ROW; i++) {
            s += Arrays.toString(grass[i]) + "\n";
        }
        if (!(domestics.isEmpty() && wilds.isEmpty() && dogs.isEmpty() && cats.isEmpty()))
            s += "\nAnimals : \n";
        for (Domestic domestic : domestics) {
            s += domestic.toString();
        }
        for (Wild wild : wilds) {
            s += wild.toString();
        }
        for (Dog dog : dogs) {
            s += dog.toString();
        }
        for (Cat cat : cats) {
            s += cat.toString();
        }
        if (!products.isEmpty())
            s += "\nproducts : \n";
        for (Product product : products) {
            s += product.toString();
        }
        if (!workshops.isEmpty())
            s += "\nfactories : \n";
        for (Workshop workshop : workshops) {
            s += workshop.toString();
        }
        return s;
    }

    static private Farm farm;

    private Farm() {
        warehouse = Warehouse.getWarehouse();
        money = 1000;
        // TODO: ۲۹/۰۵/۲۰۲۱
    }

    public static Farm getFarm() {
        if (farm == null)
            farm = new Farm();
        return farm;
    }

}
