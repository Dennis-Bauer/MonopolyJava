package de.sandwich;

import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;

import de.sandwich.GUI.Menu.StartMenu;


import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.stage.Stage;
public class Main extends Application {

    //Important and final variables
    public static final String TEXT_FONT = "Clear Sans";
    public static final boolean CONSOLE_OUT_PUT = true;
    public static final String CONSOLE_OUT_PUT_LINEBREAK = "---------------";
    public static final double WINDOW_WIDTH = 1300;
    public static final double WINDOW_HEIGHT = 650;

    //Game variables
    private static Game gameOperator;

    private static Stage primaryStage;
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) {

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
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Rafael Mikaeljan");
        System.out.println();
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.REGULAR, "Ideas from ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Rafael Mikaeljan ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.REGULAR, "and ");
        consoleOutPut(startInfoColor, ConsoleUtilities.textStyle.BOLD, "Dennis Bauer");
        System.out.println();

        if (CONSOLE_OUT_PUT)
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Infos über die Konsole sind eingeschaltet, und auf Deutsch.");
        else
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Information about the program via the console is out");

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

    @SuppressWarnings("exports")
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