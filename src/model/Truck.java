package model;

import controller.Logger;
import model.User;

import java.util.ArrayList;

public class Truck implements Changeable {
    int transportPrice;
    final int TRANSPORT_TIME = 10;
    ArrayList<Object> objects = new ArrayList<>();
    int capacity;
    int load = 0;
    public boolean present;
    long returningTime;

    public String transport() {
        if (!present)
            return "The truck has not yet returned from the previous trip";
        if (objects.isEmpty())
            return "You must first load the truck";
        transportPrice = 0;
        for (Object object : objects) {
            transportPrice += ((Sellable) object).sell();
        }
        returningTime = LocalDate.getInstance().getCurrentTime() + TRANSPORT_TIME*100000000L;
        LocalDate.getInstance().event.put(returningTime, this);
        present = false;
        return "The truck started its journey";
    }

    void backHome() {
        Farm.getFarm().money.set(Farm.getFarm().money.get()+transportPrice);
        transportPrice = 0;
        present = true;
        load = 0;
        objects.clear();
    }

    public void upgrade() {

    }

    public boolean load(Object object) {
        if (((Sellable) object).getSpace() > capacity - load)
            return false;
        else {
            objects.add(object);
            load += ((Sellable) object).getSpace();
            Farm.getFarm().truckLoad(((Sellable) object).getName());
        }
        return true;
    }

    public boolean unload(Object object) {
        if (objects.contains(object)) {
            load -= ((Sellable) object).getSpace();
            objects.remove(object);
            Farm.getFarm().truckUnload(object);
            return true;
        }
        return false;
    }


    static Truck truck;

    private Truck() {
        transportPrice = 0;//
        capacity = 15;
        present = true;
    }

    public static Truck getTruck() {
        if (truck == null)
            truck = new Truck();
        return truck;
    }

    @Override
    public String checkAfterChangeDate() {
        String s = "truck back home with $ " + transportPrice + "\n";
        LocalDate.getInstance().event.remove(returningTime, this);
        backHome();
        Logger.getLogger(User.getCurrentUser()).log(s, Logger.LogType.Info);
        System.out.println(s);
        return "";
        //return s;
    }

    @Override
    public String toString() {
        String s = "\ntruck status :";
        if (present) {
            s += "present\n";
            if (!objects.isEmpty())
                s += "truck load : \n";
            for (Object object : objects) {
                s+=((Sellable)object).getName() +", ";
            }
            s+="\n";
        }
        else {
            s += "in trip with $ "+transportPrice+".\n";
        }
        return s;
    }
}
