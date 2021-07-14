package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.scene.image.ImageView;
import view.FarmMenu;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;

public class Farm {
    final int ROW = 6;
    final int COL = 6;

    public ArrayList<Domestic> getDomestics() {
        return domestics;
    }

    ArrayList<Domestic> domestics = new ArrayList<>();
    ArrayList<Wild> wilds = new ArrayList<>();
    ArrayList<Cat> cats = new ArrayList<>();
    ArrayList<Dog> dogs = new ArrayList<>();
    ArrayList<Product> products = new ArrayList<>();
    public int[][] grass = new int[ROW][COL];
    ArrayList<Workshop> workshops = new ArrayList<>();
    public HashMap<Integer, ImageView> grassImage = new HashMap<>();
    public void remGrass(int i,int j){
        grassImage.get(10 * (i + 1) + (j + 1) +grass[i][j] * 100).setVisible(false);
        grassImage.remove(10 * (i + 1) + (j + 1) +grass[i][j] * 100);
        grass[i][j]--;
    }
    Warehouse warehouse;
    //int money;
    public IntegerProperty money = new SimpleIntegerProperty();

    public void addMoney(int count) {
        money.set(money.get() + count);
        if (FarmMenu.r != null) {
            if (money.get() < 100)
                FarmMenu.r.setId("farm0");
            else if (money.get() < 150)
                FarmMenu.r.setId("farm100");
            else if (money.get() < 200)
                FarmMenu.r.setId("farm150");
            else if (money.get() < 400)
                FarmMenu.r.setId("farm200");
            else
                FarmMenu.r.setId("farm400");
        }else {
            if (money.get() < 100)
            FarmMenu.rootId ="farm0";
            else if (money.get() < 150)
            FarmMenu.rootId ="farm100";
            else if (money.get() < 200)
            FarmMenu.rootId ="farm150";
            else if (money.get() < 400)
            FarmMenu.rootId ="farm200";
            else
            FarmMenu.rootId ="farm400";
        }
    }
    public void clear() {
        domesticsNum.clear();
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
        //money.set(0);
        addMoney(-money.get());
        Truck.truck = null;
    }

    public void setMoney(int money) {
        this.money.set(money);
        addMoney(0);
    }


    public HashMap<String, IntegerProperty> getDomesticsNum() {
        return domesticsNum;
    }

    HashMap<String, IntegerProperty> domesticsNum = new HashMap<>();
    IntegerProperty getDomesticNum(String name){
        if (domesticsNum.containsKey(name)) {
            return domesticsNum.get(name);
        } else {
            domesticsNum.put(name, new SimpleIntegerProperty(0));
            return domesticsNum.get(name);
        }
    }

    public void addDomestic(Domestic domestic) {
        if (domesticsNum.containsKey(domestic.NAME)) {
            domesticsNum.get(domestic.NAME).set(domesticsNum.get(domestic.NAME).get() + 1);
        } else {
            domesticsNum.put(domestic.NAME, new SimpleIntegerProperty(1));
        }
        domestics.add(domestic);
    }

    public void remDomestics(Domestic domestic) {
        if (domesticsNum.containsKey(domestic.NAME)) {
            domesticsNum.get(domestic.NAME).set(domesticsNum.get(domestic.NAME).get() - 1);
            domestics.remove(domestic);
        }
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
        return money.get();
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
            warehouse.removeProduct((Product) o);
//            warehouse.products.remove(o);
            return;
        }
        for (Domestic domestic : domestics) {
            if (domestic.equal(name)) {
                o = domestic;
                break;
            }
        }
        if (o != null)
            remDomestics((Domestic) o);
            //domestics.remove(o);
    }

    public void truckUnload(Object o) {
        if ("cow buffalo chicken ostrich".contains(((Sellable) o).getName())) {
            addDomestic((Domestic) o);
            //domestics.add((Domestic) o);
        } else
            warehouse.addProduct((Product) o);
//            warehouse.products.add((Product) o);
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
        money.set(1000);
        addMoney(0);
        // TODO: ۲۹/۰۵/۲۰۲۱
    }

    public static Farm getFarm() {
        if (farm == null)
            farm = new Farm();
        return farm;
    }

}
