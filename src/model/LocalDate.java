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
                    for (int k = i; k >= j; k--) {
                        e[k + 1] = e[k];
                    }
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

    public String turn(int n) {
        String s ="";
        for (int i = 0; i < n; i++) {
            s+=turn();
        }
        return s;
    }
    //Date date;
    //private long currentTime;
    public LongProperty currentTime = new SimpleLongProperty();
    public LongProperty currentUnitTime = new SimpleLongProperty();

    private LocalDate() {
        //date = new Date();
        currentTime.set((new Date()).getTime());//date.getTime();
        currentUnitTime.bind(currentTime.divide(100000000L).add(-(new Date()).getTime() / 100000000L));
    }

    static public void resetTime() {
        getInstance().currentTime.set((new Date()).getTime());
    }

    private String turn() {
        Logger.getLogger(User.getCurrentUser()).log("in turn method ", Logger.LogType.Alarm);
        String s;
        s = move();
        sortEvent();
        currentTime.set(currentTime.get() / (100000000L));
        currentTime.set(currentTime.get() * 100000000L + (new Date()).getTime() % 100000000L + 100000000L);
        while (eventsArrangement.size() > 0) {
            if (eventsArrangement.get(0) <= currentTime.get()) {
                Logger.getLogger(User.getCurrentUser()).log("checkAfterChangeDate " + event.get(eventsArrangement.get(0)), Logger.LogType.Alarm);
                s += ((Changeable) event.get(eventsArrangement.get(0))).checkAfterChangeDate();
                sortEvent();
            } else break;
        }
        return s;
    }

    public String move() {
        ArrayList<Animal> animals = new ArrayList<>();
        animals.addAll(Farm.getFarm().wilds);
        animals.addAll(Farm.getFarm().dogs);
        animals.addAll(Farm.getFarm().domestics);
        animals.addAll(Farm.getFarm().cats);
        String s = "";
        for (Animal animal : animals) {
            s += animal.move();
        }
        return s;
    }

    public long getCurrentUnitTime() {
        return currentTime.get() / (100000000L) - ((new Date()).getTime() / 100000000L);
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
