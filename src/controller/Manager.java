package controller;

import model.*;
import model.User;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Scanner;

public class Manager {
    int star;
    Farm farm = Farm.getFarm();

    public int getStar() {
        return star;
    }

    public void loadUser() {
        String strF = "";
        File file = new File("users.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            User.getUserHashMap().clear();
            Scanner scanner = new Scanner(file);
            while (scanner.hasNextLine()) {
                if ((strF = scanner.nextLine()).length() < 1)
                    break;
                User.getUserHashMap().put(strF, new User(strF, scanner.nextLine()
                        , Integer.parseInt(scanner.nextLine()), Integer.parseInt(scanner.nextLine()),Integer.parseInt(scanner.nextLine())));
            }
            scanner.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveUsers() {
        File file = new File("users.txt");
        try {
            if (!file.exists()) {
                file.createNewFile();
            }
            FileWriter writer = new FileWriter(file);
            HashMap<String, User> u = User.getUserHashMap();
            int i = 0;
            for (String s : u.keySet()) {
                writer.append(s).append("\n").append(u.get(s).getPassword()).append("\n")
                        .append(String.valueOf(u.get(s).gamePlayed)).append("\n").append(String.valueOf(u.get(s).savedCoin)).append("\n").append(String.valueOf(u.get(s).openedLevel));
                if ((i++) < u.size())
                    writer.append("\n");
            }
            writer.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

    public int build(String name) {
        // "bakery icecreamshop milkpackaging mill tailoring textile"
        // return :
        // 0 --> build before
        // 1 --> add in farm
        // 2 --> not enough money
        // 3 --> invalid name
        switch (name.toLowerCase()) {
            case "bakery":
                return Bakery.buy();
            case "icecreamshop":
                return IceCreamShop.buy();
            case "milkpackaging":
                return MilkPackaging.buy();
            case "mill":
                return Mill.buy();
            case "tailoring":
                return Tailoring.buy();
            case "textile":
                return Textile.buy();
            default:
                return 3;
        }
    }

    public boolean buy(String name) {
        switch (name.toLowerCase()) {
            case "cat":
                return Cat.buy();
            case "dog":
            case "hound":
                return Dog.buy();
            case "cow":
            case "buffalo":
                return Cow.buy();
            case "chicken":
                return Chicken.buy();
            case "ostrich":
                return Ostrich.buy();
            default:
                return false;
        }
    }

    public boolean pickup(int x, int y) {//1-6
        ArrayList<Product> collectedProduct = new ArrayList<>();
        for (Product product : farm.getProducts()) {
            if (product.checkCoordinates(x, y))
                collectedProduct.add(product);
        }
        if (collectedProduct.isEmpty())
            return false;
        for (Product product : collectedProduct) {
            product.collect();
        }
        return true;
    }

    public boolean well() {
        return Well.getWell().fill();
    }

    public boolean plant(int x, int y) {//1-6
        return Well.getWell().plantingGrass(x - 1, y - 1);
    }

    public boolean work(String name) {
        Workshop workshop = Farm.getFarm().getWorkshop(name);
        if (workshop == null)
            return false;
        return workshop.Work();
        // TODO: ۰۴/۰۶/۲۰۲۱
    }

    public int trap(int x, int y) {
        // 0 -> not exist in farm
        // 1 -> not exist in this point
        // 2 -> exist but not trapped
        // 3 -> exist and trapped
        if (farm.getWilds().isEmpty())
            return 0;
        for (Wild wild : farm.getWilds()) {
            if (wild.checkCoordinates(x, y)) {
                if (wild.trap()) {
                    return 3;
                } else return 2;
            }
        }
        return 1;
    }

    public String inquiry() {
        String s = "The number of units of time elapsed since the beginning of the stage: " + LocalDate.getInstance().getCurrentUnitTime() + "\n";
        s += Mission.getMission().toString();
        s+=Truck.getTruck().toString();
        if (Warehouse.getWarehouse().products!=null)
            if (!Warehouse.getWarehouse().products.isEmpty())
                s+="Inventory : \n";
        s += Warehouse.getWarehouse().toString();
        s += Farm.getFarm().toString();
        // TODO: ۰۴/۰۶/۲۰۲۱ tasks
        s += "coin : $" + Farm.getFarm().getMoney() + " \n";
        return s;
    }

    public String turn(int n) {
        String s = inquiry();
        s += LocalDate.getInstance().turn(n);
        s += inquiry();
        return s;
    }

    public String truckLoad(String name) {
        if (!Truck.getTruck().present)
            return "The truck has not yet returned from the previous trip";
        Object o = Farm.getFarm().getObject(name);
        if (o == null)
            return "item dos not exist";
        if (Truck.getTruck().load(o)) {
            return name + " load successfully";
        }
        return "The truck does not have enough space";
    }

    public String truckUnload(String name) {
        if (!Truck.getTruck().present)
            return "The truck has not yet returned from the previous trip";
        if (Truck.getTruck().unload(Farm.getFarm().getObject(name)))
            return " unload successfully";
        return "item dos not exist";
    }

    public String truckGo() {
        return Truck.getTruck().transport();
    }

    private Manager() {
    }

    private static Manager managerInstance;

    public static Manager getInstance() {
        if (managerInstance == null)
            managerInstance = new Manager();
        return managerInstance;
    }

    public boolean start(int level) {
        if (level > User.getCurrentUser().getOpenedLevel())
            return false;
        farm.clear();
        LocalDate.getInstance().event.clear();
        LocalDate.resetTime();
        Mission.getMission().setCurrentLevel(level);
        farm.setMoney(Mission.getMission().getCoins()[Mission.getMission().getCurrentLevel() - 1] + User.getCurrentUser().savedCoin);
        Logger.getLogger(User.getCurrentUser()).log("start game ", Logger.LogType.Alarm);
        return true;
    }

    public boolean check() {
        Logger.getLogger(User.getCurrentUser()).log("in check method ", Logger.LogType.Alarm);
        boolean output = true;
        boolean dinamic;
        boolean coinsBoolean = false;
        if (Mission.getMission().getTasks().get(Mission.getMission().getCurrentLevel() - 1).containsKey("coins")
                && !coinsBoolean)
            coinsBoolean = (Mission.getMission().getTasks().get(Mission.getMission().getCurrentLevel() - 1).get("coins").get()
                    <= farm.getMoney());
        for (String s : Mission.getMission().getTasks().get(Mission.getMission().getCurrentLevel() - 1).keySet()) {
            if (s.equals("chicken") || s.equals("ostrich") || s.equals("cow")) {
                dinamic = (farm.getNumberOfDomestics(s)) >= Mission.getMission().getTasks().get(Mission.getMission().
                        getCurrentLevel() - 1).get(s).get();
            } else if ("EGG FEATHER COW_MILK FLOUR CLOTH MILK BREAD SHIRT ICE_CREAM LION BEAR TIGER".contains(s.toUpperCase())) {
                dinamic = Mission.getMission().getTasks().get(Mission.getMission().getCurrentLevel() - 1).get(s).get() <=
                        Warehouse.getWarehouse().getNumberOfProducts(s.toUpperCase());
            } else continue;
            output = (output && dinamic);
        }
        Logger.getLogger(User.getCurrentUser()).log("check result : " + (output || coinsBoolean), Logger.LogType.Alarm);
        return output || coinsBoolean;
    }

    public boolean giveAward(boolean check) {
        if (check) {
            if (Mission.getMission().getAwards().get(Mission.getMission().getCurrentLevel() - 1).get(1)
                    >= LocalDate.getInstance().getCurrentTime()) {
                star = 3;
                User.getCurrentUser().savedCoin = Mission.getMission().getCoins()[Mission.getMission().getCurrentLevel() - 1] / 2;
            } else if (Mission.getMission().getAwards().get(Mission.getMission().getCurrentLevel() - 1).get(2)
                    >= LocalDate.getInstance().getCurrentTime()) {
                star = 2;
                User.getCurrentUser().savedCoin = Mission.getMission().getCoins()[Mission.getMission().getCurrentLevel() - 1] * 3 / 10;
            } else if (Mission.getMission().getAwards().get(Mission.getMission().getCurrentLevel() - 1).get(3)
                    >= LocalDate.getInstance().getCurrentTime()) {
                star = 1;
                User.getCurrentUser().savedCoin = Mission.getMission().getCoins()[Mission.getMission().getCurrentLevel() - 1] / 10;
            } else star = 0;
            return true;
        }
        return false;
    }

    public void setWildComing() {
        Logger.getLogger(User.getCurrentUser()).log("in setWildComing method ", Logger.LogType.Alarm);
        for (String s : Mission.getMission().getWildComing().get(Mission.getMission().getCurrentLevel() - 1).keySet()) {
            if (s.toUpperCase().equals(ProductType.BEAR.name())) {
                Logger.getLogger(User.getCurrentUser()).log(" set Bear coming ", Logger.LogType.Alarm);
                LocalDate.getInstance().event.put(Mission.getMission().getWildComing()
                        .get(Mission.getMission().getCurrentLevel() - 1).get(s) + LocalDate.getInstance()
                        .getCurrentTime(), new Bear());
            } else if (s.toUpperCase().equals(ProductType.TIGER.name())) {
                Logger.getLogger(User.getCurrentUser()).log(" set Tiger coming ", Logger.LogType.Alarm);
                LocalDate.getInstance().event.put(Mission.getMission().getWildComing()
                        .get(Mission.getMission().getCurrentLevel() - 1).get(s) + LocalDate.getInstance()
                        .getCurrentTime(), new Tiger());
            } else if (s.toUpperCase().equals(ProductType.LION.name())) {
                Logger.getLogger(User.getCurrentUser()).log(" set Lion coming ", Logger.LogType.Alarm);
                LocalDate.getInstance().event.put(Mission.getMission().getWildComing()
                        .get(Mission.getMission().getCurrentLevel() - 1).get(s) + LocalDate.getInstance()
                        .getCurrentTime(), new Lion());
            }
        }
    }

}
