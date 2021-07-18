package model;

import javafx.beans.property.IntegerProperty;
import javafx.beans.property.SimpleIntegerProperty;
import javafx.beans.property.SimpleStringProperty;
import javafx.beans.property.StringProperty;
import javafx.beans.value.ObservableValue;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Mission {
    File missionFile = new File("mission.txt");
    Scanner scanner;
    int levels;
    int[] coins;
    ArrayList<HashMap<String, IntegerProperty>> tasks = new ArrayList<>();
    ArrayList<HashMap<String, Long>> wildComing = new ArrayList<>();
    ArrayList<HashMap<Integer, Long>> awards = new ArrayList<>();
    int currentLevel = 1;

    public Mission() {
        {
            try {
                scanner = new Scanner(missionFile);
            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }
        levels = Integer.parseInt(scanner.nextLine());
        coins = new int[levels];
        for (int i = 0; i < levels; i++) {
            tasks.add(new HashMap<>());
            wildComing.add(new HashMap<>());
        }
        for (int i = 0; i < 3; i++) {
            awards.add(new HashMap<>());
        }
        for (int i = 0; i < levels; i++) {
            String string = scanner.nextLine();
            String[] split = string.split(",");
            coins[i] = Integer.parseInt(split[1]);
            String[] tasksString = split[2].split("\\.");
            for (int j = 0; j < tasksString.length; j++) {
                tasks.get(i).put(tasksString[j].split(" ")[0], new SimpleIntegerProperty(Integer.parseInt(tasksString[j].split(" ")[1])));
            }
            String[] wildComingString = split[3].split("\\.");
            for (int j = 0; j < wildComingString.length; j++) {
                wildComing.get(i).put(wildComingString[j].split(" ")[0], Integer.parseInt(wildComingString[j].split(" ")[1]) * 100000000L);
            }
            String[] awardsString = split[4].split("\\.");
            for (int j = 0; j < awardsString.length; j++) {
                awards.get(i).put(Integer.parseInt(awardsString[j].split(" ")[0]),
                        Integer.parseInt(awardsString[j].split(" ")[1]) * 100000000L + LocalDate.getInstance().getCurrentTime());
            }
        }
    }

    public void setCurrentLevel(int currentLevel) {
        this.currentLevel = currentLevel;
    }

    public int getLevels() {
        return levels;
    }

    public int[] getCoins() {
        return coins;
    }

    public ArrayList<HashMap<String, IntegerProperty>> getTasks() {
        return tasks;
    }

    public ArrayList<HashMap<String, Long>> getWildComing() {
        return wildComing;
    }

    public ArrayList<HashMap<Integer, Long>> getAwards() {
        return awards;
    }

    public int getCurrentLevel() {
        return currentLevel;
    }

    @Override
    public String toString() {
        String s = "tasks : \n";
        int i = 1;
        if (tasks.get(currentLevel - 1).containsKey("coins"))
            s += "      " + (i++) + "- " + "coins : " + tasks.get(currentLevel - 1).get("coins") + "/" + tasks.get(currentLevel - 1).get(coins) + "\n";

        for (String s1 : tasks.get(currentLevel - 1).keySet()) {
            if (s1.equals("chicken") || s1.equals("ostrich") || s1.equals("cow")) {
                s += "      " + (i++) + "- " + s1 + " : " + Farm.getFarm().getNumberOfDomestics(s1) + "/" + tasks.get(currentLevel - 1).get(s1) + "\n";
            } else if ("EGG FEATHER COW_MILK FLOUR CLOTH MILK BREAD SHIRT ICE_CREAM LION BEAR TIGER".contains(s1.toUpperCase())) {
                s += "      " + (i++) + "- " + s1 + " : " + Warehouse.getWarehouse().getNumberOfProducts(s1.toUpperCase()) + "/" + tasks.get(currentLevel - 1).get(s1) + "\n";
            }
        }
        return s;
    }

public SimpleStringProperty[] toString1() {
    StringProperty[] s1 = new SimpleStringProperty[15];
    for (int i = 0; i < 15; i++) {
        s1[i] = new SimpleStringProperty();
    }
    int m = 0;
    if (tasks.get(currentLevel - 1).containsKey("coins")) {
        s1[m++].set("coins ");
        s1[m++].bind( tasks.get(currentLevel - 1).get("coins").asString() );
        s1[m++].set("/" + tasks.get(currentLevel - 1).get("coins").get() + "    ");
    }
    for (String s : tasks.get(currentLevel - 1).keySet()) {
        if (s.equals("chicken") || s.equals("ostrich") || s.equals("cow")) {
            s1[m++].set( s + " ");
            s1[m++].bind(Farm.getFarm().getDomesticNum(s).asString() );
            s1[m].set("/" + tasks.get(currentLevel - 1).get(s).get() + " ");
            for (int i = 3; i < s.length(); i++) {
                s1[m].set(s1[m].get() + "  ");
            }
            m++;
        } else if ("EGG FEATHER COW_MILK FLOUR CLOTH MILK BREAD SHIRT ICE_CREAM LION BEAR TIGER".contains(s.toUpperCase())) {
            s1[m++].set( s + " ");
            s1[m++].bind( Warehouse.getWarehouse().getProductNum(s.toUpperCase()).asString());// TODO: ۱۲/۰۷/۲۰۲۱ upper?
            s1[m].set("/" + tasks.get(currentLevel - 1).get(s).get() + " ");
            for (int i = 3; i < s.length(); i++) {
                s1[m].set(s1[m].get() + "  ");
            }
            m++;
        }
    }
    return (SimpleStringProperty[]) s1;
}

    static private Mission mission;

    public static Mission getMission() {
        if (mission == null)
            mission = new Mission();
        return mission;
    }
}
