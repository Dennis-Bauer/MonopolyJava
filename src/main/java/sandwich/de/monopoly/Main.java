package sandwich.de.monopoly;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.GUI.Spielfeld;
import sandwich.de.monopoly.GUI.StartMenu;

import java.util.Objects;

public class Main extends Application {

    public static final String textFont = "Verdana";

    public static final Image[] playerFigures = new Image[5];

    private static final double stageWidth = 1800, stageHeight = 950;

    private static final Scene game = new Scene(Spielfeld.buildGameBoard(0, stageHeight, Color.BEIGE), stageWidth, stageHeight, Color.BLACK);

    private static final Scene menu = new Scene(StartMenu.buildMenu(stageWidth, stageHeight), stageWidth, stageHeight, Color.BLACK);

    private static Stage primaryStage;
    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        stage.setTitle("-M---o-----n----o---p----o---l----y");
        stage.setResizable(false);
        stage.setScene(menu);
        stage.show();
    }

    public static void main(String[] args) {

        playerFigures[0] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/affe.png")));
        playerFigures[1] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/burger.png")));
        playerFigures[2] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/doener.png")));
        playerFigures[3] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/pinguin.png")));
        playerFigures[4] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/sandwich.png")));

        launch();


        Spieler sp = new Spieler(200, 2, "Paul", Figuren.BOOT);


    }

    public static void changeScene(scenes s) {
        switch (s) {
            case GAME -> primaryStage.setScene(game);
            case MENU -> primaryStage.setScene(menu);
        }
    }

    public enum scenes {
        MENU,
        GAME
    }
}