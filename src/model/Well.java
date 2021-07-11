package model;

import controller.Logger;
import model.User;

public class Well implements Changeable {
    final int CAPACITY = 5;
    int remaining = 0;
    final int FILLING_TIME = 3;
    long fillingDate;
    boolean isFilling = false;

    public boolean fill() {
        if (remaining != 0 || isFilling)
            return false;
        isFilling = true;
        fillingDate = FILLING_TIME*100000000L + LocalDate.getInstance().getCurrentTime();
        LocalDate.getInstance().event.put(fillingDate, this);
        return true;
    }

    public boolean plantingGrass(int x, int y) {
        if (remaining == 0)
            return false;
        Farm.getFarm().grass[x][y]++;
        remaining--;
        return true;
    }

    public String checkAfterChangeDate() {
        remaining = CAPACITY;
        isFilling = false;
        LocalDate.getInstance().event.remove(fillingDate, this);
        Logger.getLogger(User.getCurrentUser()).log("The Well is filled", Logger.LogType.Info);
        System.out.println("The Well is filled");
        return "";
        //return "The Well is filled\n";
    }

    static private Well well;

    public static Well getWell() {
        if (well == null)
            well = new Well();
        return well;
    }
}
