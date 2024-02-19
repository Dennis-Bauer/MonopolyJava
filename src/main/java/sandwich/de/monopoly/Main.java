package sandwich.de.monopoly;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.GUI.Spielfeld;
import sandwich.de.monopoly.GUI.StartMenu;

import java.util.HashMap;
import java.util.Objects;

public class Main extends Application {

    public static final String textFont = "Clear Sans";

    public static final Image[] playerFigures = new Image[5];

    public static HashMap<Integer, Color> streetColor = new HashMap<>();

    private static final double stageWidth = 1800, stageHeight = 950;

    private static Scene game;

    private static Scene menu;

    private static Stage primaryStage;
    @Override
    public void start(Stage stage) {

        primaryStage = stage;

        stage.setTitle("-M---o-----n----o---p----o---l----y");
        stage.setResizable(false);
        stage.setScene(game);
        stage.show();
    }

    public static void main(String[] args) {

        playerFigures[0] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/affe.png")));
        playerFigures[1] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/burger.png")));
        playerFigures[2] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/doener.png")));
        playerFigures[3] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/pinguin.png")));
        playerFigures[4] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/sandwich.png")));

        streetColor.put(0, Color.DARKBLUE); //Straße
        streetColor.put(1, Color.DARKBLUE); //Straße
        streetColor.put(2, Color.AQUA);     //Straße
        streetColor.put(3, Color.AQUA);     //Straße
        streetColor.put(4, Color.AQUA);     //Straße
        streetColor.put(5, Color.PURPLE);   //Straße
        streetColor.put(6, Color.PURPLE);   //Straße
        streetColor.put(7, Color.PURPLE);   //Straße
        streetColor.put(8, Color.ORANGE);   //Straße
        streetColor.put(9, Color.ORANGE);   //Straße
        streetColor.put(10, Color.ORANGE);  //Straße
        streetColor.put(11, Color.RED);     //Straße
        streetColor.put(12, Color.RED);     //Straße
        streetColor.put(13, Color.RED);     //Straße
        streetColor.put(14, Color.YELLOW);  //Straße
        streetColor.put(15, Color.YELLOW);  //Straße
        streetColor.put(16, Color.YELLOW);  //Straße
        streetColor.put(17, Color.LIME);    //Straße
        streetColor.put(18, Color.LIME);    //Straße
        streetColor.put(19, Color.LIME);    //Straße
        streetColor.put(20, Color.rgb(112, 40, 0));    //Straße
        streetColor.put(21, Color.rgb(112, 40, 0));    //Straße
        streetColor.put(22, Color.GRAY);    //Anlagen
        streetColor.put(23, Color.BLACK);   //Bahnhöfe

        Spielfeld gameRoot = new Spielfeld(0, stageWidth, stageHeight, Color.rgb(204, 227, 199));
        game = new Scene(gameRoot, stageWidth, stageHeight, Color.BLACK);
        StartMenu menuRoot = new StartMenu(stageWidth, stageHeight);
        menu = new Scene(menuRoot, stageWidth, stageHeight, Color.BLACK);

        launch();


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