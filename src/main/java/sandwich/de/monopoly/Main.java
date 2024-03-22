package sandwich.de.monopoly;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.GUI.Menu.StartMenu;

import java.util.HashMap;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

public class Main extends Application {

    //Important and final variables
    public static final String TEXT_FONT = "Clear Sans";
    public static final boolean CONSOLE_OUT_PUT = true;
    public static final String CONSOLE_OUT_PUT_LINEBREAK = "---------------";
    public static final double WINDOW_WIDTH = 1800;
    public static final double WINDOW_HEIGHT = 950;

    //Game variables
    public static HashMap<Integer, Color> streetColor = new HashMap<>();

    private static Game gameOperator;

    private static Stage primaryStage;
    @Override
    public void start(Stage stage) {

        primaryStage = stage;
        stage.setTitle("-M---o-----n----o---p----o---l----y");
        stage.setResizable(true);
        stage.setScene(new Scene(new StartMenu(WINDOW_WIDTH, WINDOW_HEIGHT)));
        stage.show();
    }

    public static void main(String[] args) {

        ConsoleUtilities.colors startInfoColor = ConsoleUtilities.colors.CYAN;

        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.REGULAR, "Monopoly made by ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Charles Darrow");
        System.out.println();
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.REGULAR, "Programmed by ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Dennis Bauer");
        System.out.println();
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.REGULAR, "Graphics by ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Rafael LAST_NAME");
        System.out.println();
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.REGULAR, "Ideas from ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Rafael LAST_NAME ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.REGULAR, "and ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Dennis Bauer");
        System.out.println();

        if (CONSOLE_OUT_PUT)
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Infos über die Konsole sind eingeschaltet, und auf Deutsch.");
        else
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Information about the program via the console is out");

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

        launch();
    }

    public static Game getGameOperator() {
        if (gameOperator != null)
            return gameOperator;
        else throw new NullPointerException("The game operator is null!");
    }

    public static void setGameOperator(Game gameOperator) {
        if (Main.gameOperator == null)
            Main.gameOperator = gameOperator;
        else throw new NullPointerException("The game operator is already created!");
    }

    public static Stage getPrimaryStage() {
        return primaryStage;
    }
}

/*
   Fehler 001: 2 oder mehre Spieler haben die gleiche Figure ausgewählt!
   Fehler 002: 2 oder mehre Spieler haben den gleichen Namen ausgewählt!
   Fehler 003: Es ist im moment nur 1 Spieler erstellt!
   Fehler 004: Es sind im moment keine Spieler erstellt!
 */