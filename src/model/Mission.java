package model;

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
    ArrayList<HashMap<String, Integer>> tasks = new ArrayList<>();
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
                tasks.get(i).put(tasksString[j].split(" ")[0], Integer.parseInt(tasksString[j].split(" ")[1]));
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
        /*tasks.get(0).put("egg", 2);
        tasks.get(0).put("chicken", 3);
        tasks.get(1).put("cow", 3);
        tasks.get(1).put("cow_milk", 2);
        //wildComing.get(0).put("bear", 2 * 100000000L);
        //wildComing.get(0).put("lion", 3 * 100000000L);
        //wildComing.get(1).put("tiger" , 100000000L);
        awards.get(0).put(1, 5 * 100000000L + LocalDate.getInstance().getCurrentTime());
        awards.get(0).put(2, 10 * 100000000L + LocalDate.getInstance().getCurrentTime());
        awards.get(0).put(3, 15 * 100000000L + LocalDate.getInstance().getCurrentTime());
        awards.get(1).put(1, 5 * 100000000L + LocalDate.getInstance().getCurrentTime());
        awards.get(1).put(2, 10 * 100000000L + LocalDate.getInstance().getCurrentTime());
        awards.get(1).put(3, 15 * 100000000L + LocalDate.getInstance().getCurrentTime());*/
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

    public ArrayList<HashMap<String, Integer>> getTasks() {
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

    static private Mission mission;

    public static Mission getMission() {
        if (mission == null)
            mission = new Mission();
        return mission;
    }
}
