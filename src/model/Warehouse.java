package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;

import java.util.ArrayList;
import java.util.HashMap;

public class Warehouse {
    final int CAPACITY;
    //int emptySpace;
    IntegerProperty emptySpace ;

    public IntegerProperty warehouseFillPercent = new SimpleIntegerProperty();

    public ArrayList<Product> products = new ArrayList<>();
   // public HashMap<String,Integer> productCount = new HashMap<>();
    static private Warehouse warehouse;

    public void clear(){
        emptySpace.set(CAPACITY);
        products.clear();
        productNum.clear();
    //    productCount.clear();
    }


    public HashMap<String, IntegerProperty> productNum = new HashMap<>();
    IntegerProperty getProductNum(String name){
        if (productNum.containsKey(name)) {
            return productNum.get(name);
        } else {
            productNum.put(name, new SimpleIntegerProperty(0));
            return productNum.get(name);
        }
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
//        if (!productCount.containsKey(product.name))
//            productCount.put(product.name,1);
//        else productCount.replace(product.name,productCount.get(product.name)+1);
        if (productNum.containsKey(product.name)) {
            productNum.get(product.name).set(productNum.get(product.name).get() + 1);
        } else {
            productNum.put(product.name, new SimpleIntegerProperty(1));
        }
    }

    public void removeProduct(Product product) {
//        if (productCount.get(product.name)<2)
//            productCount.remove(product.name);
//        else
//            productCount.replace(product.name,productCount.get(product.name)-1);
        products.remove(product);
        emptySpace.set(emptySpace.get() + product.space);
        if (productNum.containsKey(product.name)) {
            productNum.get(product.name).set(productNum.get(product.name).get() - 1);
        }
    }

    public Product getProduct(ProductType productType) {
        for (Product product : products) {
            if (product.name.equals(productType.name())) {
                return product;
            }
        }
        return null;
    }
    public Product getProduct(String name) {
        for (Product product : products) {
            if (product.name.equals(name)) {
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
