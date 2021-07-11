package model;

public class Bear extends Wild {
    public Bear() {
        super(ProductType.BEAR);
        strength = 4;
        freedom = strength;
        velocity = 1;
    }
}
