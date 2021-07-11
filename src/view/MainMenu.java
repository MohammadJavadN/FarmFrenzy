package view;

import controller.Logger;
import controller.Manager;
import model.Mission;
import model.User;

import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu extends Menu {
    User user;

    @Override
    public void run() {
        setParentMenu();
        setSubMenu(scanner);
        subMenu.setUser(user);
        show();
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Commands.START.getMatcher(input.toLowerCase())).matches()) {
                if (start(matcher.group(1))) {
                    Manager.getInstance().setWildComing();
                    subMenu.run();
                } else System.out.println("invalid Number for level");
            } else if (Commands.LOGOUT.getMatcher(input.toLowerCase()).matches()) {
                Logger.getLogger(user).log("Logout : <" + input + ">", Logger.LogType.Command);
                parentMenu.run();
            } else if (Commands.SETTINGS.getMatcher(input.toLowerCase()).matches()) {
                Logger.getLogger(user).log("setting command : <" + input + ">", Logger.LogType.Command);
                System.out.println("This option is added in the second phase.");
            } else if (Commands.EXIT.getMatcher(input.toLowerCase()).matches()) {
                Logger.getLogger(user).log("EXIT : <" + input + ">", Logger.LogType.Command);
                System.exit(-1);
            } else {
                System.out.println("please try again!");
                Logger.getLogger(user).log("Invalid command Main menu : <" + input + ">", Logger.LogType.Alarm);
            }
        }
    }

    boolean start(String level){
        int levelNum = Integer.parseInt(level);
        return Manager.getInstance().start(levelNum);
    }

    @Override
    void setCommands() {
        commands.add("START [level]");
        commands.add("LOG OUT");
        commands.add("SETTINGS");
        commands.add("EXIT");
    }

    private MainMenu(Scanner scanner) {
        this.scanner = scanner;
        parentMenu = LoginMenu.getLoginMenu();
    }
    private void setSubMenu(Scanner scanner){
        subMenu = FarmMenu.getFarmMenu(scanner);
    }
    private void setParentMenu(){
        parentMenu = LoginMenu.getLoginMenu();
    }
    private static MainMenu mainMenu;
    public static MainMenu getMainMenu(Scanner scanner){
        if (mainMenu==null)
            mainMenu=new MainMenu(scanner);
        return mainMenu;
    }

    enum Commands {
        START("\\s*^start\\s+(\\d+)\\s*$"),
        LOGOUT("\\s*^log\\s*out\\s*$"),
        SETTINGS("\\s*^setting\\s*$"),
        EXIT("\\s*^exit\\s*$");

        Pattern commandPattern;

        Commands(String s) {
            this.commandPattern = java.util.regex.Pattern.compile(s);
        }

        public Matcher getMatcher(String input) {
            return this.commandPattern.matcher(input);
        }
    }
}
