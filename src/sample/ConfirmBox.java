package sample;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class ConfirmBox {

    //Create variable
    static boolean answer;

    public static boolean display(String title, String message) {
        Stage window = new Stage();
        Pane root = new Pane();
        root.setId("alert");
        Scene scene = new Scene(root, 500, 300);
        scene.getStylesheets().add("Viper.css");

        //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);

        Label label = new Label();
        label.setText(message);
        label.getStyleClass().add("label-alert");
        label.setLayoutX(0.1 * scene.getWidth());
        label.setLayoutY(0.3 * scene.getHeight());
        label.setMaxWidth(scene.getWidth() * 0.8);

        Button okButton = new Button("OK");
        okButton.setOnAction(e -> {
            window.close();
            answer = true;
        });
        okButton.setLayoutX(scene.getWidth() * 0.4);
        okButton.setLayoutY(0.8 * scene.getHeight());
        okButton.getStyleClass().add("button-green");
        okButton.setMinWidth(0.1 * scene.getWidth());

        Button cancelButton = new Button("Cancel");
        cancelButton.setOnAction(e -> {
            window.close();
            answer = false;
        });
        cancelButton.setLayoutX(scene.getWidth() * 0.5);
        cancelButton.setLayoutY(0.8 * scene.getHeight());
        cancelButton.getStyleClass().add("button-blue");
        cancelButton.setMinWidth(0.1 * scene.getWidth());
        root.getChildren().addAll(label, okButton, cancelButton);
        //Display window and wait for it to be closed before returning
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();

        //Make sure to return answer
        return answer;
    }
}
