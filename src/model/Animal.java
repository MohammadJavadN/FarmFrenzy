package model;

import java.util.Random;

abstract class Animal implements Destroyable {
    int xPosition, yPosition;
    int xVelocity, yVelocity;
    Random random = new Random();
    final String NAME;
    public Animal(String name) {
        this.xPosition = random.nextInt(6);
        this.yPosition = random.nextInt(6);
        this.NAME = name;
    }

    abstract String move();
    void setVelocity(int velocity) {
        if (random.nextBoolean()) {
            yVelocity = 0;
            xVelocity = velocity - (2 * (random.nextInt(2))) * velocity;
        } else {
            xVelocity = 0;
            yVelocity = velocity - (2 * (random.nextInt(2))) * velocity;
        }
        if ((xPosition + xVelocity) < 0 || (xPosition + xVelocity) >= Farm.getFarm().COL)
            xVelocity *= -1;
        if ((yPosition + yVelocity) < 0 || (yPosition + yVelocity) >= Farm.getFarm().ROW)
            yVelocity *= -1;
    }
}
