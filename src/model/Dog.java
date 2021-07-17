package model;

import controller.Logger;
import javafx.scene.Scene;
import javafx.scene.layout.Pane;
import view.Sound;

public class Dog extends Animal {
    final static int price = 100;
    Wild wild;

    private Dog(Scene scene, Pane root) {
        super("hound", scene, root,"C:\\Users\\User\\Desktop\\FarmFrenzy\\img\\hound.png");
        Farm.getFarm().addMoney(-price);
        Farm.getFarm().dogs.add(this);
        Sound.playSound("sounds\\dog.wav");
    }

    public static boolean buy(Scene scene, Pane root) {
        if (Farm.getFarm().getMoney() >= price) {
            new Dog(scene, root);
            return true;
        }
        return false;
    }

    String move() {
        setVelocity();
        addXP(xVelocity);
        addYP(yVelocity);
        Logger.getLogger(User.getCurrentUser()).log("hound move ", Logger.LogType.Info);

        if (wild == null)
            return "";
        System.out.println("The hound followed the wild animal in [" + (wild.xPosition.get() + 1) + "," + (wild.yPosition.get() + 1) + "] \n");
        return "";
        //return "The hound followed the wild animal in [" + (wild.xPosition + 1) + "," + (wild.yPosition + 1) + "] \n";
    }

    void setVelocity() {
        int distance = -1;
        if (wild == null) {
            for (Wild wildF : Farm.getFarm().wilds) {
                if ((distance == -1 || distance > (Math.abs(xPosition.get() - wildF.xPosition.get()) + Math.abs(yPosition.get() - wildF.yPosition.get())))) {
                    distance = Math.abs(xPosition.get() - wildF.xPosition.get()) + Math.abs(yPosition.get() - wildF.yPosition.get());
                    wild = wildF;
                }
            }
        }
        if (wild != null && Farm.getFarm().wilds.contains(wild)) {
            int velocity = 1;
            ///////////increase speed for arrive food
            if (distance > 3)
                velocity = 2;

            if (Math.abs(xPosition.get() - wild.xPosition.get()) > Math.abs(yPosition.get() - wild.yPosition.get())) {
                xVelocity = velocity * Integer.signum(wild.xPosition.get() - xPosition.get());
                yVelocity = 0;
            } else {
                yVelocity = velocity * Integer.signum(wild.yPosition.get() - yPosition.get());
                xVelocity = 0;
            }
        } else setVelocity(1);
    }

    @Override
    public void destroying() {
        Farm.getFarm().dogs.remove(this);
        imageView.setVisible(false);
    }
    public String toString(){
        return NAME + " [" + (xPosition.get() + 1) + "," + (yPosition.get() + 1) + "]\n";
    }
}
