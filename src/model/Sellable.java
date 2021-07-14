package model;

public interface Sellable {
    //    int getPrice();
    int sell();// return sellPrice

    String getName();
    int getPrice();
    String getImagePath();
    int getSpace();
}
