package model;

import java.util.ArrayList;

public class Warehouse {
    final int CAPACITY;
    int emptySpace;
    public ArrayList<Product> products = new ArrayList<>();
    static private Warehouse warehouse;

    public void clear(){
        emptySpace = CAPACITY;
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
        emptySpace -= product.space;
    }

    public void removeProduct(Product product) {
        products.remove(product);
        emptySpace += product.space;
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
        emptySpace = CAPACITY;
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
