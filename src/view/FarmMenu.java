package view;

import controller.Logger;
import controller.Manager;
import javafx.beans.property.SimpleStringProperty;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.stage.Modality;
import javafx.stage.Stage;
import model.*;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
public class FarmMenu extends Menu {
    Manager manager = Manager.getInstance();
    Farm farm = Farm.getFarm();
    public static String rootId = "farm0";

    @Override
    public void run() {
        LocalDate.resetTime();
        show();
        mission();
        manager.setWildComing(scene, ((Pane) scene.getRoot()));
        Farm.getFarm().addMoney(0);
        scene.setOnMousePressed(event -> {
            handleMouseEvent(event);
            System.out.println("mouse click detected! " + event.getSource());
            System.out.println("x = " + event.getX());
            System.out.println("y = " + event.getY());
        });
    }

    void handleMouseEvent(MouseEvent e) {
        System.out.println(inFarm(e));
        if (inBuy(e))
            buy(e);
        else if (inWell(e))
            well();
        else if (inFarm(e))
            plant(e);

    }

    void buy(MouseEvent e) {
        if (e.getX() < 0.180 * scene.getWidth()) {
            manager.buy("chicken", scene, (Pane) scene.getRoot());
        }else if (e.getX() > 0.187 * scene.getWidth() && e.getX() < 0.235 * scene.getWidth()) {
            manager.buy("ostrich", scene, (Pane) scene.getRoot());
        } else if (e.getX() > 0.240 * scene.getWidth() && e.getX() < 0.288 * scene.getWidth()) {
            manager.buy("cow", scene, (Pane) scene.getRoot());
        } else if (e.getX() > 0.295 * scene.getWidth() && e.getX() < 0.343 * scene.getWidth()) {
            manager.buy("dog", scene, (Pane) scene.getRoot());
        } else if (e.getX() > 0.350 * scene.getWidth() && e.getX() < 0.398 * scene.getWidth()) {
            manager.buy("cat", scene, (Pane) scene.getRoot());
        }

    }

    void plant(MouseEvent e) {
        int y = (int) ((e.getX() - 0.33 * scene.getWidth()) / (0.056 * scene.getWidth())) + 1;
        int x = (int) ((e.getY() - 195 * scene.getHeight()/600) / (44 * scene.getHeight() / 600)) + 1;

        System.out.println("x =  " + x + " & y = " + y);
        System.out.println(farm.insidePage(x, y));
        if (farm.insidePage(x, y))
            if (manager.plant(x, y)) {
                Image image = null;
                try {
                    image = new Image(new FileInputStream("C:\\Users\\User\\Desktop\\HelloFX\\img\\grase.png"));
                } catch (FileNotFoundException event) {
                    event.printStackTrace();
                }

                ImageView imageView = new ImageView(image);

                imageView.setX(e.getX() - scene.getWidth() / 40);
                imageView.setY(e.getY() - scene.getHeight() / 20);
                imageView.setPreserveRatio(true);

                ((Pane) scene.getRoot()).getChildren().add(imageView);
                imageView.setFitHeight(scene.getWidth() / 10);
                imageView.setFitWidth(scene.getHeight() / 10);
                farm.grassImage.put(10*x+y+farm.grass[x-1][y-1]*100,imageView);
            }
    }

    boolean inBuy(MouseEvent e) {
        return e.getX() > 0.129 * scene.getWidth() && e.getX() < 0.4 * scene.getWidth() && e.getY() < 0.1 * scene.getHeight();
    }

    boolean inWell(MouseEvent e) {
        return e.getY() > 92 * scene.getHeight() / 600 && e.getY() < 15 * scene.getHeight() / 60
                && e.getX() > 0.468 * scene.getWidth() && e.getX() < 0.552 * scene.getWidth();
    }

    boolean inFarm(MouseEvent e) {

        System.out.println("in 108 farm menu");
        System.out.println(e.getX() + " > " + (0.33 * scene.getWidth()) + " && " + e.getX() + " < " + (0.670 * scene.getWidth()) + " && " +
                e.getY() + " > " + (195 * scene.getHeight() / 600) + " && " + e.getY() + " < " + (460 * scene.getHeight() / 600));
        return e.getX() > 0.33 * scene.getWidth() && e.getX() < 0.670 * scene.getWidth() &&
                e.getY() > 195 * scene.getHeight() / 600 && e.getY() < 460 * scene.getHeight() / 600;
    }
    public static Pane r;
    @Override
    public void show() {
        Pane root = new Pane();
        r = root;
        root.setId(rootId);
        root.setPadding(new Insets(10, 10, 10, 10));
        scene = new Scene(root, 1000, 600);
        scene.getStylesheets().add("Viper.css");
        window.setScene(scene);

        Button menu = new Button("menu");
        menu.setOnAction(e -> menu());// TODO: ۱۱/۰۷/۲۰۲۱
        menu.getStyleClass().add("button-blue");
        menu.setLayoutX(0.14 * window.getWidth());
        menu.setLayoutY(0.88 * window.getHeight());
        menu.setMinWidth(50);
        root.getChildren().add(menu);

        Button turn = new Button("turn");
        turn.setOnAction(e -> {
            LocalDate.getInstance().turn(1);
            if (manager.giveAward(manager.check())) {
                if (Mission.getMission().getLevels() != User.getCurrentUser().getOpenedLevel())
                    User.getCurrentUser().setOpenedLevel(User.getCurrentUser().getOpenedLevel() + 1);

            }
            System.out.println(Farm.getFarm().toString());
        });// TODO: ۱۱/۰۷/۲۰۲۱
        turn.getStyleClass().add("button-blue");
        turn.setLayoutX(0.47 * window.getWidth());
        turn.setLayoutY(0.01 * window.getHeight());
        turn.setMinWidth(50);
        root.getChildren().add(turn);

        Label wellFillPercent = new Label();
        wellFillPercent.getStyleClass().add("label-well");
        wellFillPercent.textProperty().bind(Well.getWell().wellFillPercent.asString());
        wellFillPercent.setLayoutX(0.5 * scene.getWidth());
        wellFillPercent.setLayoutY(0.23 * scene.getHeight());

        Label warehouseCap = new Label();
        warehouseCap.getStyleClass().add("label-well");
        warehouseCap.textProperty().bind(Warehouse.getWarehouse().warehouseFillPercent.asString());
        warehouseCap.setLayoutX(0.45 * scene.getWidth());
        warehouseCap.setLayoutY(0.94 * scene.getHeight());

        Label[] mission = setMissionLabel();

        Label time = new Label();
        time.getStyleClass().add("label-white");
        time.textProperty().bind(LocalDate.getInstance().currentUnitTime.asString());
        time.setLayoutX(0.76 * scene.getWidth());
        time.setLayoutY(0.94 * scene.getHeight());

        Label coin = new Label();
        coin.getStyleClass().add("label-white");
        coin.textProperty().bind(Farm.getFarm().money.asString());
        coin.setLayoutX(0.63 * scene.getWidth());
        coin.setLayoutY(0.04 * scene.getHeight());
        for (Label label : mission) {
            root.getChildren().add(label);
        }
        root.getChildren().addAll(wellFillPercent, warehouseCap, coin, time);
      //  addAnimal(root);
        window.show();
    }

    private Label[] setMissionLabel() {
        Label[] mission = new Label[12];
        //mission.getStyleClass().add("label-white");
        for (int i = 0; i < 12; i++) {
            mission[i] = new Label();
        }
        SimpleStringProperty[] s1 = Mission.getMission().toString1();
        for (int i = 0; i < 4; i++) {
            if (!s1[3 * i].isNull().get()) {
                mission[3 * i].setText(s1[3 * i].get() + " ");
                mission[3 * i].setLayoutX(0.73 * scene.getWidth() + i * scene.getWidth() * 0.05);
                mission[3 * i].setLayoutY(0.87 * scene.getHeight());
            }
        }
        for (int i = 0; i < 5; i++) {
            if (!s1[3 * i + 2].isNull().get()) {
                mission[3 * i + 2].setText(s1[3 * i + 2].get() + " ");
                mission[3 * i + 2].setLayoutX(0.75 * scene.getWidth() + i * scene.getWidth() * 0.04);
                mission[3 * i + 2].setLayoutY(0.91 * scene.getHeight());
            }
        }
        for (int i = 0; i < 5; i++) {
            if (!s1[3 * i + 1].isNull().get()) {
                mission[3 * i + 1].textProperty().bind(s1[3 * i + 1]);
                mission[3 * i + 1].setLayoutX(0.75 * scene.getWidth() + i * scene.getWidth() * 0.04 - scene.getWidth() * 0.01);
                mission[3 * i + 1].setLayoutY(0.91 * scene.getHeight());
            }
        }

        //mission[1].textProperty().bind();
        return mission;

    }

    public void mission() {
        Stage window1 = new Stage();
        Pane root1 = new Pane();
        root1.setId("mission");
        Scene scene1 = new Scene(root1, 500, 500);
        scene1.getStylesheets().add("Viper.css");

        //Block events to other windows
        window1.initModality(Modality.APPLICATION_MODAL);
        window1.setTitle("mission");
        window1.setMinWidth(500);

        Button closeButton = new Button("OK");
        closeButton.getStyleClass().add("button-blue");
        closeButton.setOnAction(e -> window1.close());

        Mission m = Mission.getMission();
        //Name Label - constrains use (child, column, row)
        int i = 0;
        int pT = 210, pR1 = 40, vGap1 = 56, pR2 = 293, vGap2 = 72, hGap = 105;
        Label[] collect = new Label[2 * m.getTasks().get(m.getCurrentLevel() - 1).size()];
        for (String product : m.getTasks().get(m.getCurrentLevel() - 1).keySet()) {
            collect[i] = new Label(product);
            collect[i].getStyleClass().add("label-white");
            collect[i].setLayoutX(pR1);
            collect[i].setLayoutY(pT + vGap1 * i);

            collect[i + 1] = new Label(String.valueOf(m.getTasks().get(m.getCurrentLevel() - 1).get(product).get()));
            collect[i + 1].getStyleClass().add("label-white");
            collect[i + 1].setLayoutX(1.1 * pR1 + collect[i].getText().length() * collect[i].getFont().getSize());
            collect[i + 1].setLayoutY(pT + vGap1 * i);
            root1.getChildren().addAll(collect[i], collect[i + 1]);
            i++;
        }

        Label[] star = new Label[5];
        star[0] = new Label(String.valueOf(m.getCoins()[m.getCurrentLevel() - 1] / 10));
        star[0].getStyleClass().add("label-white");
        star[0].setLayoutX(pR2);
        star[0].setLayoutY(pT);

        star[1] = new Label(String.valueOf((m.getAwards().get(m.getCurrentLevel() - 1).get(1) - LocalDate.getInstance().getCurrentTime()) / 100000000L));
        star[1].getStyleClass().add("label-white");
        star[1].setLayoutX(pR2);
        star[1].setLayoutY(pT+ vGap2);

        star[2] = new Label(String.valueOf(m.getCoins()[m.getCurrentLevel() - 1] / 2));
        star[2].getStyleClass().add("label-white");
        star[2].setLayoutX(pR2+hGap);
        star[2].setLayoutY(pT+ 0.8*vGap2);

        star[3] = new Label(String.valueOf(((m.getAwards().get(m.getCurrentLevel() - 1).get(2) - LocalDate.getInstance().getCurrentTime()) / 100000000L)));
        star[3].getStyleClass().add("label-white");
        star[3].setLayoutX(pR2);
        star[3].setLayoutY(pT+2.1* vGap2);

        star[4] = new Label(String.valueOf(m.getCoins()[m.getCurrentLevel() - 1] * 3 / 10));
        star[4].getStyleClass().add("label-white");
        star[4].setLayoutX(pR2+hGap);
        star[4].setLayoutY(pT+1.9* vGap2);

        for (int j = 0; j < 5; j++) {
            root1.getChildren().add(star[j]);
        }

        VBox layout = new VBox(10);
        layout.getChildren().addAll(closeButton);
        layout.setAlignment(Pos.BOTTOM_CENTER);
        layout.setLayoutX(0.45 * scene1.getWidth());
        layout.setLayoutY(0.9 * scene1.getHeight());

        root1.getChildren().add(layout);

        window1.setScene(scene1);
        window1.setResizable(false);
        window1.showAndWait();
    }

    private void build() {
        Logger.getLogger(user).log("build command", Logger.LogType.Command);
        if ("bakery icecreamshop milkpackaging mill tailoring textile".contains(matcher.group(1))) {
            int result = manager.build(matcher.group(1),scene);
            if (result == 0) {
                System.out.println(matcher.group(1) + " has already build ");
                Logger.getLogger(user).log(matcher.group(1) + "  has already build", Logger.LogType.Info);
            } else if (result == 1) {
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
            if (manager.buy(matcher.group(1),scene, (Pane) scene.getRoot())) {
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
