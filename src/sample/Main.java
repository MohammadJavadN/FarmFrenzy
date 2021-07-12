package sample;

import javafx.application.Application;
import javafx.stage.Stage;
import view.LoginMenu;


public class Main extends Application {
    public static void main(String[] args) {
        launch(args);
    }

    @Override
    public void start(Stage window) throws Exception {

        window.setResizable(false);
        LoginMenu.getLoginMenu(window).run();
//        root = new GridPane();
//        root.setId("m1");
//        scene = new Scene(root, 1000, 600);
//        window.setTitle("FarmFrenzy");
//        int c = 0;
//        int r = 25;
//
//
//
//        //GridPane with 10px padding around edge
//        root.setPadding(new Insets(10, 10, 10, 10));
//        root.setAlignment(Pos.CENTER);
//        root.setVgap(8);
//        root.setHgap(10);
//         //Name Label - constrains use (child, column, row)
//        Label nameLabel = new Label("Username:");
//        nameLabel.setId("bold-label");
//        GridPane.setConstraints(nameLabel, c +0, r+0);
//
//        //Name Input
//        TextField nameInput = new TextField();
//        nameInput.setPromptText("username");
//        GridPane.setConstraints(nameInput, c +1, r+0);
//
//        //Password Label
//        Label passLabel = new Label("Password:");
//        GridPane.setConstraints(passLabel, c +0, r+1);
//
//        //Password Input
//        TextField passInput = new TextField();
//        passInput.setPromptText("password");
//        GridPane.setConstraints(passInput, c +1, r+1);
//
//        //Login
//        Button loginButton = new Button("Log In");
//        loginButton.setOnAction(e -> System.out.println("login"));
//        GridPane.setConstraints(loginButton, c +1, r+2);
//
//        //Sign up
//        Button signUpButton = new Button("Sign Up");
//        signUpButton.getStyleClass().add("button-blue");
//        GridPane.setConstraints(signUpButton, c +1, r+3);
//
//        //Add everything to grid
//        root.getChildren().addAll(nameLabel, nameInput, passLabel, passInput, loginButton, signUpButton);
//
//        scene.getStylesheets().add("Viper.css");
//
//        window.setScene(scene);
//        window.show();
    }

}
