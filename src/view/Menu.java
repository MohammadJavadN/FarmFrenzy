package view;

import java.util.ArrayList;
import java.util.Scanner;
import java.util.regex.Matcher;
import model.User;

public abstract class Menu {
    protected Menu parentMenu;
    protected Menu subMenu;
    protected Scanner scanner;
    User user;
    String input;
    Matcher matcher;
    ArrayList<String> commands = new ArrayList<>();

    abstract public void run();

    public void show() {
        int i = 1;
        if (commands.isEmpty()) setCommands();
        System.out.println("\ncommands :");
        for (String command : commands) {
            System.out.print("          " + (i++) + " - ");
            System.out.println(command);
        }
        //System.out.println("---------------");
        System.out.println("please enter your command:");
    }

    abstract void setCommands();

    public void setUser(User user) {
        this.user = user;
    }
}
