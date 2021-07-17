package view;

import controller.Logger;
import controller.Manager;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.stage.Stage;
import model.User;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class LoginMenu extends Menu {
    GridPane root;

    @Override
    public void run() {
        Manager.getInstance().loadUser();
        Logger.getLogger().log("load user file", Logger.LogType.Info);
        setSubMenu(window);
        show();
    }

    @Override
    public void show() {
        root = new GridPane();
        root.setId("m1");
        scene = new Scene(root, 1000, 600);
        window.setTitle("FarmFrenzy");

        int c = 0;
        int r = 25;

        //GridPane with 10px padding around edge
        root.setPadding(new Insets(10, 10, 10, 10));
        root.setAlignment(Pos.CENTER);
        root.setVgap(8);
        root.setHgap(10);
        //Name Label - constrains use (child, column, row)
        Label nameLabel = new Label("Username:");
        nameLabel.setId("bold-label");
        GridPane.setConstraints(nameLabel, c , r);

        //Name Input
        TextField nameInput = new TextField();
        nameInput.setPromptText("username");
        GridPane.setConstraints(nameInput, c + 1, r );

        //Password Label
        Label passLabel = new Label("Password:");
        GridPane.setConstraints(passLabel, c , r + 1);

        //Password Input
        TextField passInput = new TextField();
        passInput.setPromptText("password");
        GridPane.setConstraints(passInput, c + 1, r + 1);

        //Login
        Button loginButton = new Button("Log In");
        loginButton.setOnAction(e ->{
            Sound.playSound("sounds\\click.wav");
            login(nameInput.getText(), passInput.getText());
        });
        GridPane.setConstraints(loginButton, c + 1, r + 2);

        //Sign up
        Button signUpButton = new Button("Sign Up");
        signUpButton.getStyleClass().add("button-blue");
        signUpButton.setOnAction(e -> {
            Sound.playSound("sounds\\click.wav");
            signup(nameInput.getText(), passInput.getText());
        });
        GridPane.setConstraints(signUpButton, c + 1, r + 3);

        //Add everything to grid
        root.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, signUpButton);

        scene.getStylesheets().add("Viper.css");

        window.setScene(scene);
        window.show();
    }

    private void login(String username, String pass) {
        Logger.getLogger().log("click login", Logger.LogType.Command);
        if ((matcher = Commands.USERNAME.getMatcher(username.toLowerCase())).matches()) {
            usernameL(username, pass);
        } else {
            // TODO: ۱۱/۰۷/۲۰۲۱
            System.out.println("Please write in the form below :\njavad\n");
            Logger.getLogger(user).log("Invalid form username : <" + username + ">", Logger.LogType.Alarm);
            AlertBox.display("Error","Invalid form username : < " + username + " >");
        }
    }

    private void usernameL(String username, String pass) {
        Logger.getLogger().log("entered username : <" + username + ">", Logger.LogType.Command);
        if (User.getUserHashMap().containsKey(matcher.group(1))) {
            user = User.getUserHashMap().get(matcher.group(1));
            Logger.getLogger(user).log("username accepted", Logger.LogType.Info);

            if ((matcher = Commands.PASSWORD.getMatcher(pass)).matches()) {
                passwordL(pass);
            }else {
                AlertBox.display("Error","Invalid password");
            }
        } else {
            AlertBox.display("Error","this username dose not exist!");
            Logger.getLogger(user).log("Invalid username : <" + username + ">", Logger.LogType.Alarm);
        }
    }

    private void passwordL(String pass) {
        Logger.getLogger(user).log("password : <" + pass + ">", Logger.LogType.Command);
        if (user.correctPass(matcher.group(1))) {
            Logger.getLogger(user).log("Login successfully", Logger.LogType.Replay);
            // TODO: ۱۱/۰۷/۲۰۲۱
          //  AlertBox.display("Welcome","Welcome!!!");
            System.out.println("\nLogin successfully");
            User.setCurrentUser(user);
            // TODO: ۱۱/۰۷/۲۰۲۱
              subMenu.setUser(user);
            // TODO: ۱۱/۰۷/۲۰۲۱
              subMenu.run();
        } else {
            // TODO: ۱۱/۰۷/۲۰۲۱   System.out.println("Invalid password!");
            Logger.getLogger(user).log("Wrong password : <" + pass + ">", Logger.LogType.Error);
            AlertBox.display("Error","Wrong password!");
        }
    }

    private void signup(String username, String pass) {
        Logger.getLogger().log("click Signup: <" + username + ">", Logger.LogType.Command);
        if ((matcher = Commands.USERNAME.getMatcher(username)).matches()) {
            usernameS(username, pass);
        }
    }

    private void usernameS(String username, String pass) {
        Logger.getLogger().log("username : <" + username + ">", Logger.LogType.Command);
        if (!User.getUserHashMap().containsKey(matcher.group(1))) {
            user = new User(matcher.group(1));
            Logger.getLogger(user).log("username accepted", Logger.LogType.Info);
                if ((matcher = Commands.PASSWORD.getMatcher(pass)).matches()) {
                    passwordS(pass);
                } else {
                    AlertBox.display("Error","Please write in the form below :\ndfv@7d8");
                    Logger.getLogger(user).log("Invalid form password : <" + pass + ">", Logger.LogType.Alarm);
                }
        } else {
            AlertBox.display("Error","This username is already registered");
            System.out.println("This username is already registered");
            Logger.getLogger(user).log("username is already registered : <" + pass + ">", Logger.LogType.Replay);
        }
    }

    private void passwordS(String pass) {
        Logger.getLogger(user).log("password : <" + pass + ">", Logger.LogType.Command);
        user.setPassword(matcher.group(1));
        Logger.getLogger(user).log("Signup successfully", Logger.LogType.Replay);
        Manager.getInstance().saveUsers();
        Logger.getLogger().log("save user file", Logger.LogType.Info);
        User.setCurrentUser(user);
        subMenu.setUser(user);
        subMenu.run();
    }

    static private LoginMenu loginMenu;

    private LoginMenu(Stage window) {
        this.window = window;
    }

    private void setSubMenu(Stage window) {
        subMenu = MainMenu.getMainMenu(window);
    }

    public static LoginMenu getLoginMenu(Stage window) {
        if (loginMenu == null)
            loginMenu = new LoginMenu(window);
        return loginMenu;
    }

    enum Commands {
        USERNAME("\\s*(\\w+)\\s*$"),
        PASSWORD("\\s*(\\S+)\\s*$");
        Pattern commandPattern;

        Commands(String s) {
            this.commandPattern = Pattern.compile(s);
        }

        public Matcher getMatcher(String input) {
            return this.commandPattern.matcher(input);
        }
    }
}
