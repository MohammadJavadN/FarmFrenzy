package model;

public interface Sellable {
    int sell();// return sellPrice

    String getName();
    int getPrice();
    String getImagePath();
    int getSpace();
}
