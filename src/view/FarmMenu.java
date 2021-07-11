package view;

import controller.Logger;
import controller.Manager;
import javafx.stage.Stage;
import model.Farm;
import model.LocalDate;
import model.Mission;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import  model.User;
public class FarmMenu extends Menu {
    Manager manager = Manager.getInstance();
    Farm farm = Farm.getFarm();

    @Override
    public void run() {
        LocalDate.resetTime();
        show();
        while (true) {
            if (Manager.getInstance().giveAward(Manager.getInstance().check())) {
                System.err.println("to Bordi :) & Stars: " + Manager.getInstance().getStar());
                if (Mission.getMission().getLevels() != User.getCurrentUser().getOpenedLevel())
                    User.getCurrentUser().setOpenedLevel(User.getCurrentUser().getOpenedLevel() + 1);

                menu();
            }
            input = scanner.nextLine();
            if ((matcher = Commands.BUY.getMatcher(input.toLowerCase())).matches()) {
                buy();
            } else if ((matcher = Commands.BUILD.getMatcher(input.toLowerCase())).matches()) {
                build();
            } else if ((matcher = Commands.PICKUP.getMatcher(input.toLowerCase())).matches()) {
                pickup();
            } else if (Commands.WELL.getMatcher(input.toLowerCase()).matches()) {
                well();
            } else if ((matcher = Commands.PLANT.getMatcher(input.toLowerCase())).matches()) {
                plant();
            } else if ((matcher = Commands.WORK.getMatcher(input.toLowerCase())).matches()) {
                work();
            } else if ((matcher = Commands.CAGE.getMatcher(input.toLowerCase())).matches()) {
                cage();
            } else if ((matcher = Commands.TURN.getMatcher(input.toLowerCase())).matches()) {
                turn();
            } else if ((matcher = Commands.TRUCK_LOAD.getMatcher(input.toLowerCase())).matches()) {
                truckLoad();
            } else if ((matcher = Commands.TRUCK_UNLOAD.getMatcher(input.toLowerCase())).matches()) {
                truckUnload();
            } else if (Commands.TRUCK_GO.getMatcher(input.toLowerCase()).matches()) {
                truckGo();
            } else if (Commands.MENU.getMatcher(input.toLowerCase()).matches()) {
                menu();
            } else if (Commands.INQUIRY.getMatcher(input.toLowerCase()).matches()) {
                inquiry();
            } else if (Commands.COMMANDS.getMatcher(input.toLowerCase()).matches()) {
                Logger.getLogger(user).log("commands : <" + input + ">", Logger.LogType.Command);
                show();
            } else if (FarmMenu.Commands.EXIT.getMatcher(input.toLowerCase()).matches()) {
                Logger.getLogger(user).log("EXIT : <" + input + ">", Logger.LogType.Command);
                System.exit(-1);
            } else {
                System.out.println("please try again!");
                Logger.getLogger(user).log("Invalid command in FarmMenu : <" + input + ">", Logger.LogType.Alarm);
            }
        }
    }

    @Override
    public void show() {

    }

    private void build() {
        Logger.getLogger(user).log("build command", Logger.LogType.Command);
        if ("bakery icecreamshop milkpackaging mill tailoring textile".contains(matcher.group(1))) {
            int result = manager.build(matcher.group(1));
            if (result == 0){
                System.out.println(matcher.group(1) + " has already build ");
                Logger.getLogger(user).log(matcher.group(1) + "  has already build", Logger.LogType.Info);
            }else if (result == 1) {
                System.out.println(matcher.group(1) + " add in farm");
                Logger.getLogger(user).log(matcher.group(1) + " add in farm", Logger.LogType.Info);
            } else if (result == 2) {
                System.out.println("You do not have enough money to build this workshop!");
                Logger.getLogger(user).log("do not have enough money to buy " + matcher.group(1), Logger.LogType.Replay);
            } else if (result == 3) {
                System.out.println("Invalid workshop name!");
                Logger.getLogger(user).log("Invalid workshop name <" + input + ">", Logger.LogType.Alarm);
            }
        } else {
            System.out.println("Invalid workshop name!");
            Logger.getLogger(user).log("Invalid workshop name <" + input + ">", Logger.LogType.Alarm);
        }

    }

    private void buy() {
        Logger.getLogger(user).log("buy command", Logger.LogType.Command);
        if ("cat dog hound cow buffalo chicken ostrich".contains(matcher.group(1))) {
            if (manager.buy(matcher.group(1))) {
                System.out.println(matcher.group(1) + " add in farm");
                Logger.getLogger(user).log(matcher.group(1) + " add in farm", Logger.LogType.Info);
            } else {
                System.out.println("You do not have enough money to buy this animal!");
                Logger.getLogger(user).log("do not have enough money to buy " + matcher.group(1), Logger.LogType.Replay);
            }
        } else {
            System.out.println("Invalid animal name!");
            Logger.getLogger(user).log("Invalid animal name <" + input + ">", Logger.LogType.Alarm);
        }
    }

    private void pickup() {
        Logger.getLogger(user).log("pickup command", Logger.LogType.Command);
        if (farm.insidePage(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)))) {
            if (manager.pickup(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)))) {
                System.out.println("product in [" + matcher.group(1) + "," + matcher.group(2) + "] was pickup");
                Logger.getLogger(user).log("product in [" + matcher.group(1) + "," + matcher.group(2) + "] was pickup", Logger.LogType.Info);
            } else {
                System.out.println("There are no products in this house.");
                Logger.getLogger(user).log("There were no products in the selected house", Logger.LogType.Alarm);
            }
        } else {
            System.out.println("Invalid point");
            Logger.getLogger(user).log("Invalid point entered", Logger.LogType.Error);
        }
    }

    private void well() {
        Logger.getLogger(user).log("well command", Logger.LogType.Command);
        if (manager.well()) {
            System.out.println("The well began to drain");
            Logger.getLogger(user).log("The well began to drain", Logger.LogType.Replay);
        } else {
            System.out.println("The well does not need drainage at the moment");
            Logger.getLogger(user).log("The well does not need drainage at the moment", Logger.LogType.Replay);
        }
    }

    private void plant() {
        Logger.getLogger(user).log("plant command", Logger.LogType.Command);
        if (farm.insidePage(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)))) {
            if (manager.plant(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)))) {
                System.out.println("plant grass in [" + matcher.group(1) + "," + matcher.group(2) + "] ");
                Logger.getLogger(user).log("plant grass in [" + matcher.group(1) + "," + matcher.group(2) + "] ", Logger.LogType.Info);
            } else {
                System.out.println("The well has no water.");
                Logger.getLogger(user).log("The well has no water", Logger.LogType.Replay);
            }
        } else {
            System.out.println("Invalid point");
            Logger.getLogger(user).log("Invalid point entered", Logger.LogType.Error);
        }
    }

    private void work() {
        Logger.getLogger(user).log("work command", Logger.LogType.Command);
        if (manager.work(matcher.group(1))) {
            System.out.println("The " + matcher.group(1) + " started working");
            Logger.getLogger(user).log("The " + matcher.group(1) + " started working", Logger.LogType.Info);
        } else {
            System.out.println("Necessary materials are not available");
            Logger.getLogger(user).log("Necessary materials are not available", Logger.LogType.Replay);
        }
    }

    private void cage() {
        Logger.getLogger(user).log("cage command", Logger.LogType.Command);
        if (farm.insidePage(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)))) {
            int result = manager.trap(Integer.parseInt(matcher.group(1)), Integer.parseInt(matcher.group(2)));
            if (result == 0) {
                System.out.println("There are no wild animals in farm");
                Logger.getLogger(user).log("There are no wild animals in farm", Logger.LogType.Replay);
            } else if (result == 1) {
                System.out.println("There are no wild animals in this house");
                Logger.getLogger(user).log("Wrong coordinates", Logger.LogType.Error);
            } else if (result == 2) {
                System.out.println("The cage order was applied to the wild animal but it has not yet been trapped");
                Logger.getLogger(user).log("The cage order was applied to the wild animal", Logger.LogType.Info);
            } else if (result == 3) {
                System.out.println("wild animals in [" + matcher.group(1) + "," + matcher.group(2) + "] was trapped");
                Logger.getLogger(user).log("wild animals in [" + matcher.group(1) + "," + matcher.group(2) + "] was trapped", Logger.LogType.Info);
            }
        } else {
            System.out.println("Invalid point");
            Logger.getLogger(user).log("Invalid point entered", Logger.LogType.Error);
        }
    }

    private void turn() {
        Logger.getLogger(user).log("turn command", Logger.LogType.Command);
        String s = manager.turn(Integer.parseInt(matcher.group(1)));
        //Logger.getLogger(user).log(s, Logger.LogType.Info);
        System.out.println(s);
    }

    private void truckLoad() {
        Logger.getLogger(user).log("truck load command", Logger.LogType.Command);
        String s = manager.truckLoad(matcher.group(1));
        System.out.println(s);
        Logger.getLogger(user).log(s, Logger.LogType.Info);
    }

    private void truckUnload() {
        Logger.getLogger(user).log("truck unload command", Logger.LogType.Command);
        String s = manager.truckUnload(matcher.group(1));
        System.out.println(s);
        Logger.getLogger(user).log(s, Logger.LogType.Info);
    }

    private void truckGo() {
        Logger.getLogger(user).log("truck go command", Logger.LogType.Command);
        String s = manager.truckGo();
        System.out.println(s);
        Logger.getLogger(user).log(s, Logger.LogType.Info);
    }

    private void menu() {
        manager.saveUsers();
        Logger.getLogger(user).log("save user", Logger.LogType.Info);
        Logger.getLogger(user).log("menu command", Logger.LogType.Command);
        // TODO: ۰۴/۰۶/۲۰۲۱ check tasks
        parentMenu.run();
    }

    private void inquiry() {
        Logger.getLogger(user).log("inquiry command", Logger.LogType.Command);
        String s = manager.inquiry();
        System.out.println(s);
        //Logger.getLogger(user).log(s, Logger.LogType.Info);
    }


    private FarmMenu(Stage window) {
        this.window = window;
        parentMenu = MainMenu.getMainMenu(window);
    }

    private static FarmMenu farmMenu;

    public static FarmMenu getFarmMenu(Stage window) {
        if (farmMenu == null)
            farmMenu = new FarmMenu(window);
        return farmMenu;
    }

    enum Commands {
        BUY("\\s*^buy\\s+(\\w+)\\s*$"),
        PICKUP("\\s*^pickup\\s+(\\d)\\s+(\\d)\\s*$"),
        WELL("\\s*^well\\s*$"),
        PLANT("\\s*^plant\\s+(\\d)\\s+(\\d)\\s*$"),
        WORK("\\s*^work\\s+(\\w+)\\s*$"),
        CAGE("\\s*^cage\\s+(\\d)\\s+(\\d)\\s*$"),
        TURN("\\s*^turn\\s+(\\d+)\\s*$"),
        TRUCK_LOAD("\\s*^truck\\s*load\\s+(\\w+)\\s*$"),
        TRUCK_UNLOAD("\\s*^truck\\s*unload\\s+(\\w+)\\s*$"),
        TRUCK_GO("\\s*^truck\\s*go\\s*$"),
        MENU("\\s*^menu\\s*$"),
        INQUIRY("\\s*^inquiry\\s*$"),
        COMMANDS("\\s*^commands\\s*$"),
        EXIT("\\s*^exit\\s*$"),
        BUILD("\\s*^build\\s+(\\w+)\\s*$");

        Pattern commandPattern;

        Commands(String s) {
            this.commandPattern = java.util.regex.Pattern.compile(s);
        }

        public Matcher getMatcher(String input) {
            return this.commandPattern.matcher(input);
        }

    }
}
