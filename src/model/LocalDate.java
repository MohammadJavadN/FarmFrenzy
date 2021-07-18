package model;

import controller.Logger;
import javafx.beans.property.LongProperty;
import javafx.beans.property.SimpleLongProperty;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LocalDate {
    static private LocalDate instance;


    public HashMap<Long, Object> event = new HashMap<>();
    ArrayList<Long> eventsArrangement = new ArrayList<>();

    private void sortEvent() {
        long[] e = new long[event.size() + 1];
        e[0] = 0;
        int i = 0;
        for (Long date : event.keySet()) {
            for (int j = 0; j <= i; j++) {
                if (date < e[j] || e[j] == 0) {
                    if (i + 1 - j >= 0) System.arraycopy(e, j, e, j + 1, i + 1 - j);
                    e[j] = date;
                }
            }
            i++;
        }
        eventsArrangement.clear();
        for (int j = 0; j < i; j++) {
            eventsArrangement.add(j, e[j]);
        }
    }

    public void turn(int n) {
        for (int i = 0; i < n; i++) {
            turn();
        }
    }
    public LongProperty currentTime = new SimpleLongProperty();
    public LongProperty currentUnitTime = new SimpleLongProperty();

    private LocalDate() {
        currentTime.set((new Date()).getTime());
        currentUnitTime.bind(currentTime.divide(100000000L).add(-(new Date()).getTime() / 100000000L));
    }

    static public void resetTime() {
        getInstance().currentTime.set((new Date()).getTime());
    }

    private void turn() {
        Logger.getLogger(User.getCurrentUser()).log("in turn method ", Logger.LogType.Alarm);
        move();
        sortEvent();
        currentTime.set(currentTime.get() / (100000000L));
        currentTime.set(currentTime.get() * 100000000L + (new Date()).getTime() % 100000000L + 100000000L);
        for (Wild wild : Farm.getFarm().wilds) {
            wild.unCage();
        }
        while (eventsArrangement.size() > 0) {
            if (eventsArrangement.get(0) <= currentTime.get()) {
                Logger.getLogger(User.getCurrentUser()).log("checkAfterChangeDate " + event.get(eventsArrangement.get(0)), Logger.LogType.Alarm);
                ((Changeable) event.get(eventsArrangement.get(0))).checkAfterChangeDate();
                sortEvent();
            } else break;
        }
    }

    public void move() {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.addAll(Farm.getFarm().wilds);
        animals.addAll(Farm.getFarm().dogs);
        animals.addAll(Farm.getFarm().domestics);
        animals.addAll(Farm.getFarm().cats);
        for (Animal animal : animals) {
            animal.move();
        }
    }

    public long getCurrentTime() {
        return (currentTime.get() / (100000000L)) * (100000000L) + ((new Date()).getTime() % 100000000L);
    }

    static public LocalDate getInstance() {
        if (instance == null)
            instance = new LocalDate();
        return instance;
    }
}
