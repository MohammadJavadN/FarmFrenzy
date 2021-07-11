package model;

import controller.Logger;
import model.User;

public class Dog extends Animal {
    final static int price = 100;
    Wild wild;

    private Dog() {
        super("hound");
        Farm.getFarm().dogs.add(this);
    }

    public static boolean buy() {
        if (Farm.getFarm().getMoney() >= price) {
            new Dog();
            return true;
        }
        return false;
    }

    String move() {
        setVelocity();
        xPosition += xVelocity;
        yPosition += yVelocity;
        Logger.getLogger(User.getCurrentUser()).log("hound move ", Logger.LogType.Info);

        if (wild == null)
            return "";
        System.out.println("The hound followed the wild animal in [" + (wild.xPosition + 1) + "," + (wild.yPosition + 1) + "] \n");
        return "";
        //return "The hound followed the wild animal in [" + (wild.xPosition + 1) + "," + (wild.yPosition + 1) + "] \n";
    }

    void setVelocity() {
        int distance = -1;
        if (wild == null) {
            for (Wild wildF : Farm.getFarm().wilds) {
                if ((distance == -1 || distance > (Math.abs(xPosition - wildF.xPosition) + Math.abs(yPosition - wildF.yPosition)))) {
                    distance = Math.abs(xPosition - wildF.xPosition) + Math.abs(yPosition - wildF.yPosition);
                    wild = wildF;
                }
            }
        }
        if (wild != null && Farm.getFarm().wilds.contains(wild)) {
            int velocity = 1;
            ///////////increase speed for arrive food
            if (distance > 3)
                velocity = 2;

            if (Math.abs(xPosition - wild.xPosition) > Math.abs(yPosition - wild.yPosition)) {
                xVelocity = velocity * Integer.signum(wild.xPosition - xPosition);
                yVelocity = 0;
            } else {
                yVelocity = velocity * Integer.signum(wild.yPosition - yPosition);
                xVelocity = 0;
            }
        } else setVelocity(1);
    }

    @Override
    public void destroying() {
        Farm.getFarm().dogs.remove(this);
    }
    public String toString(){
        return NAME + " [" + (xPosition + 1) + "," + (yPosition + 1) + "]\n";
    }
}
