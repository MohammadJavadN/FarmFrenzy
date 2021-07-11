package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;

public class Warehouse {
    final int CAPACITY;
    //int emptySpace;
    IntegerProperty emptySpace ;

    public IntegerProperty warehouseFillPercent = new SimpleIntegerProperty();

    public ArrayList<Product> products = new ArrayList<>();
    static private Warehouse warehouse;

    public void clear(){
        emptySpace.set(CAPACITY);
        products.clear();
    }

    public int getNumberOfProducts(String productName) {
        int cnt = 0;
        for (Product product : products) {
            if (product.name.equals(productName))
                cnt++;
        }
        return cnt;
    }

    public void addProduct(Product product) {
        products.add(product);
        emptySpace.set(emptySpace.get() - product.space);
    }

    public void removeProduct(Product product) {
        products.remove(product);
        emptySpace.set(emptySpace.get() + product.space);
    }

    public Product getProduct(ProductType productType) {
        for (Product product : products) {
            if (product.name.equals(productType.name())) {
                return product;
            }
        }
        return null;
    }

    private Warehouse() {
        CAPACITY = 30;
        emptySpace = new SimpleIntegerProperty(CAPACITY);
        warehouseFillPercent.bind(emptySpace.multiply(-100).divide(CAPACITY).add(100));
    }

    public String toString() {
        String s = "";
        for (Product product : products) {
            s += product.name + "\n";
        }
        return s;
    }

    public static Warehouse getWarehouse() {
        if (warehouse == null)
            warehouse = new Warehouse();
        return warehouse;
    }
}
