package model;

public class Lion extends Wild {
    public Lion() {
        super(ProductType.LION);
        strength = 3;
        freedom = strength;
        velocity = 1;
    }
}
