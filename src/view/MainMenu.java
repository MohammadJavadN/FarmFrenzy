package view;

import controller.Manager;
import javafx.geometry.Insets;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.Pane;
import javafx.stage.Stage;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class MainMenu extends Menu {
    private static MainMenu mainMenu;
    final int levelsCount = 10;

    private MainMenu(Stage window) {
        this.window = window;
        parentMenu = LoginMenu.getLoginMenu(window);
    }

    public static MainMenu getMainMenu(Stage window) {
        if (mainMenu == null)
            mainMenu = new MainMenu(window);
        return mainMenu;
    }

    @Override
    public void run() {
        setParentMenu();
        setSubMenu(window);
        subMenu.setUser(user);
        show();
        /*
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
        }*/
    }

    @Override
    public void show() {
        double xCenter = 0.7 * window.getWidth();
        double yCenter = 0.71 * window.getHeight();
        double radius = 0.1 * window.getWidth();

        Pane root = new Pane();
        root.setId("m2");
        scene = new Scene(root, 1000, 600);
        root.setPadding(new Insets(10, 10, 10, 10));
        scene.getStylesheets().add("Viper.css");
        Button[] level = new Button[levelsCount];
        for (int i = 0; i < user.openedLevel; i++) {
            level[i] = new Button(String.valueOf(i + 1));
            int finalI = i;
            level[i].setOnAction(e -> start(finalI + 1));
            level[i].getStyleClass().add("button-greenLevel");
        }
        for (int i = user.openedLevel; i < levelsCount; i++) {
            level[i] = new Button(String.valueOf(i + 1));
            level[i].getStyleClass().add("button-blueLevel");
        }
        for (int i = 0; i < levelsCount; i++) {
            level[i].setLayoutX(xCenter - radius * Math.cos(((double) i) / ((double) levelsCount*1.3) * 2 * Math.PI - Math.PI/4));
            level[i].setLayoutY(yCenter + radius * Math.sin(((double) i) / ((double) levelsCount*1.3) * 2 * Math.PI- Math.PI/4));
            root.getChildren().add(level[i]);
        }
        Button logout = new Button("Logout");
        logout.setOnAction(e -> parentMenu.run());
        logout.getStyleClass().add("button-blue");
        logout.setLayoutX(0.8*window.getWidth());
        logout.setLayoutY(0.88*window.getHeight());
        logout.setMinWidth(50);
        root.getChildren().add(logout);

        Button exit = new Button("exit");
        exit.setOnAction(e -> System.exit(-1));
        exit.getStyleClass().add("button-red");
        exit.setLayoutX(0.14*window.getWidth());
        exit.setLayoutY(0.88*window.getHeight());
        exit.setMinWidth(50);
        root.getChildren().add(exit);

        window.setScene(scene);
        window.show();
    }

    void start(int level) {
        if (Manager.getInstance().start(level)){
            subMenu.run();
        }else {
            // TODO: ۱۱/۰۷/۲۰۲۱  
        }
    }

    private void setSubMenu(Stage window) {
        subMenu = FarmMenu.getFarmMenu(window);
    }

    private void setParentMenu() {
        parentMenu = LoginMenu.getLoginMenu(window);
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
