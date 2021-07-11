package view;

import controller.Logger;
import controller.Manager;
import model.User;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Menu {
    User user;

    @Override
    public void run() {
        Manager.getInstance().loadUser();
        Logger.getLogger().log("load user file", Logger.LogType.Info);
        setSubMenu(scanner);
        show();
        while (true) {
            user = null;
            input = scanner.nextLine();
            if (Commands.LOGIN.getMatcher(input.toLowerCase()).matches()) {
                login();
            } else if (Commands.SIGNUP.getMatcher(input.toLowerCase()).matches()) {
                signup();
            } else if (Commands.EXIT.getMatcher(input.toLowerCase()).matches()) {
                Logger.getLogger(user).log("EXIT : <" + input + ">", Logger.LogType.Command);
                System.exit(-1);
            } else {
                System.out.println("please try again!");
                Logger.getLogger(user).log("Invalid command in LoginMenu: <" + input + ">", Logger.LogType.Alarm);
            }
        }
    }

    private void login() {
        Logger.getLogger().log("Login command entered: <" + input + ">", Logger.LogType.Command);
        System.out.println("please enter your username : ");
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Commands.USERNAME.getMatcher(input.toLowerCase())).matches()) {
                usernameL();
            } else if (Commands.Back.getMatcher(input.toLowerCase()).matches()) {
                show();
                break;
            } else {
                System.out.println("Please write in the form below :\nusername javad\n");
                Logger.getLogger(user).log("Invalid form username : <" + input + ">", Logger.LogType.Alarm);
            }
        }
    }

    private void usernameL() {
        Logger.getLogger().log("entered username : <" + input + ">", Logger.LogType.Command);
        if (User.getUserHashMap().containsKey(matcher.group(1))) {
            user = User.getUserHashMap().get(matcher.group(1));
            Logger.getLogger(user).log("username accepted", Logger.LogType.Info);
            System.out.println("Please enter password : ");
            while (true) {
                input = scanner.nextLine();
                if ((matcher = Commands.PASSWORD.getMatcher(input.toLowerCase())).matches()) {
                    passwordL();
                } else if (Commands.Back.getMatcher(input.toLowerCase()).matches()) {
                    System.out.println("please enter your username : ");
                    break;
                } else {
                    System.out.println("Please write in the form below :\npassword dfv@7d8\n");
                    Logger.getLogger(user).log("Invalid form password : <" + input + ">", Logger.LogType.Alarm);
                }
            }
        } else {
            System.out.println("Invalid username");
            Logger.getLogger(user).log("Invalid username : <" + input + ">", Logger.LogType.Alarm);
        }
    }

    private void passwordL() {
        Logger.getLogger(user).log("entered password : <" + input + ">", Logger.LogType.Command);
        if (user.correctPass(matcher.group(1))) {
            Logger.getLogger(user).log("Login successfully", Logger.LogType.Replay);
            System.out.println("\nLogin successfully");
            User.setCurrentUser(user);
            subMenu.setUser(user);
            subMenu.run();
        } else {
            System.out.println("Invalid password!");
            Logger.getLogger(user).log("Wrong password : <" + input + ">", Logger.LogType.Error);
        }
    }

    private void signup() {
        Logger.getLogger().log("Signup command entered: <" + input + ">", Logger.LogType.Command);
        System.out.println("please enter your username : ");
        while (true) {
            input = scanner.nextLine();
            if ((matcher = Commands.USERNAME.getMatcher(input.toLowerCase())).matches()) {
                usernameS();
            } else if (Commands.Back.getMatcher(input.toLowerCase()).matches()) {
                show();
                break;
            } else {
                System.out.println("Please write in the form below :\nusername javad\n");
                Logger.getLogger(user).log("Invalid form username : <" + input + ">", Logger.LogType.Alarm);
            }
        }
    }

    private void usernameS() {
        Logger.getLogger().log("entered username : <" + input + ">", Logger.LogType.Command);
        if (!User.getUserHashMap().containsKey(matcher.group(1))) {
            user = new User(matcher.group(1));
            Logger.getLogger(user).log("username accepted", Logger.LogType.Info);
            System.out.println("Please enter password : ");
            while (true) {
                input = scanner.nextLine();
                if ((matcher = Commands.PASSWORD.getMatcher(input.toLowerCase())).matches()) {
                    passwordS();
                } else if (Commands.Back.getMatcher(input.toLowerCase()).matches()) {
                    System.out.println("please enter your username : ");
                    break;
                } else {
                    System.out.println("Please write in the form below :\npassword dfv@7d8\n");
                    Logger.getLogger(user).log("Invalid form password : <" + input + ">", Logger.LogType.Alarm);
                }
            }
        } else {
            System.out.println("This username is already registered");
            Logger.getLogger(user).log("username is already registered : <" + input + ">", Logger.LogType.Replay);
        }
    }

    private void passwordS() {
        Logger.getLogger(user).log("entered password : <" + input + ">", Logger.LogType.Command);
        user.setPassword(matcher.group(1));
        Logger.getLogger(user).log("Signup successfully", Logger.LogType.Replay);
        System.out.println("\nSignup successfully");
        Manager.getInstance().saveUsers();
        Logger.getLogger().log("save user file", Logger.LogType.Info);
        User.setCurrentUser(user);
        subMenu.setUser(user);
        subMenu.run();
    }

    @Override
    void setCommands() {
        commands.add("LOG IN");
        commands.add("SIGNUP");
        commands.add("EXIT");
    }

    static private LoginMenu loginMenu;

    private LoginMenu() {
        this.scanner = new Scanner(System.in);
    }

    private void setSubMenu(Scanner scanner) {
        subMenu = MainMenu.getMainMenu(scanner);
    }

    public static LoginMenu getLoginMenu() {
        if (loginMenu == null)
            loginMenu = new LoginMenu();
        return loginMenu;
    }

    enum Commands {
        USERNAME("\\s*^username\\s+(\\w+)\\s*$"),
        PASSWORD("\\s*^password\\s+(\\S+)\\s*$"),
        LOGIN("\\s*^log\\s*in\\s*$"),
        SIGNUP("\\s*^signup\\s*$"),
        EXIT("\\s*^exit\\s*$"),
        Back("\\s*^back\\s*$");
        Pattern commandPattern;

        Commands(String s) {
            this.commandPattern = Pattern.compile(s);
        }

        public Matcher getMatcher(String input) {
            return this.commandPattern.matcher(input);
        }
    }
}
