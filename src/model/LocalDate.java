package model;

import controller.Logger;
import model.User;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

public class LocalDate {
    //Date date;
    private long currentTime;
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

    private String turn() {
        Logger.getLogger(User.getCurrentUser()).log("in turn method ", Logger.LogType.Alarm);
        String s ;
        s = move();
        sortEvent();
        currentTime /= 100000000L;
        currentTime = currentTime *100000000L + (new Date()).getTime()%100000000L+100000000L;
        while (eventsArrangement.size() > 0) {
            if (eventsArrangement.get(0) <= currentTime) {
                Logger.getLogger(User.getCurrentUser()).log("checkAfterChangeDate "+event.get(eventsArrangement.get(0)), Logger.LogType.Alarm);
                s+=((Changeable) event.get(eventsArrangement.get(0))).checkAfterChangeDate();
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
            s+=animal.move();
        }
        return s;
    }

    public long getCurrentTime() {
        return (currentTime/100000000L) *100000000L + (new Date()).getTime()%100000000L;
    }

    public long getCurrentUnitTime(){
        return (currentTime / 100000000L) -(new Date()).getTime()/100000000L;
    }

    private LocalDate() {
        //date = new Date();
        currentTime = (new Date()).getTime();//date.getTime();
    }

    static private LocalDate instance;

    static public void resetTime() {
        getInstance().currentTime = (new Date()).getTime();
    }

    static public LocalDate getInstance() {
        if (instance == null)
            instance = new LocalDate();
        return instance;
    }
}
