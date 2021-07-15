package view;

import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.stage.Modality;
import javafx.stage.Stage;

public class AlertBox {

    public static void display(String title, String message) {
        Stage window = new Stage();
        Pane root = new Pane();
        root.setId("alert");
        Scene scene = new Scene(root, 500,350);
        scene.getStylesheets().add("Viper.css");

            //Block events to other windows
        window.initModality(Modality.APPLICATION_MODAL);
        window.setTitle(title);
        window.setMinWidth(400);

        Label label = new Label();
        label.setText(message);
        label.getStyleClass().add("label-alert");
        label.setLayoutX(0.1*scene.getWidth());
        label.setLayoutY(0.3*scene.getHeight());
        label.setMaxWidth(scene.getWidth()*0.8);

        Button closeButton = new Button("OK");
        closeButton.setOnAction(e -> window.close());
        closeButton.setLayoutX(scene.getWidth()*0.45);
        closeButton.setLayoutY(0.8*scene.getHeight());;
        closeButton.getStyleClass().add("button-green");
        closeButton.setMinWidth(0.1*scene.getWidth());

        root.getChildren().addAll(label, closeButton);
        //Display window and wait for it to be closed before returning
        window.setScene(scene);
        window.setResizable(false);
        window.showAndWait();
    }

}
