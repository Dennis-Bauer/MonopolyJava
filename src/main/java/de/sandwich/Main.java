package de.sandwich;

import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Enums.Values;
import de.sandwich.GUI.Menu.StartMenu;

import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.creatImage;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.stage.Stage;

/**
 *  <h2>MonopolyFX</h2>
 *  <p>
 *  This started as a small school project, but then became the first big 
 *  project for two young computer scientists. Right from the start they had 
 *  so much fun that they decided to create a complete version of Monopoly. 
 *  Now 97 days later the first full version is available. Every graphic was 
 *  created with love, every code was typed with love and every animation was 
 *  thought out with love. Even if it's not perfect, in the end it's not just 
 *  Monopoly for both of us, it's a part of our lives. Decades from now we will 
 *  show our children this and say "That's how it all began..."
 *  </p>
 *  <h3>Infos you need:</h3>
 *  <p>
 *  There are 1 changes to this version that make it very special.
 *  1. A street/station/... has no mortgage value, this is always automatically the price of this field.
 *  </p>
 *  <p>
 *      <h3>A few numbers:</h3>
 *      <ul>
 *      <li>Lines of code: 9000</li>
 *      <li>Amount of classes: 50</li>
 *      <li>Time needed: 97 days</li>
 *      <li>Amount of images: 31 pngs
 *      <li>Amount of commits 137</li>
 *      <li>Help from teacher: 0</li>
 *      </ul>
 *  </p>
 * @author bauer, mikaeljan
 * @version 1.0
 * @since 30.04.2024
 */


public class Main extends Application {

    //Important and final variables
    public static final String TEXT_FONT = "Arial";
    public static final boolean CONSOLE_OUT_PUT = true;
    public static final String CONSOLE_OUT_PUT_LINEBREAK = "---------------";
    public static final double WINDOW_WIDTH = Values.MAIN_WINDOW_WIDTH.getValue();
    public static final double WINDOW_HEIGHT = Values.MAIN_WINDOW_HEIGHT.getValue();

    //Game variables
    private static Game gameOperator;

    private static Stage primaryStage;
    @Override
    public void start(@SuppressWarnings("exports") Stage stage) {
        primaryStage = stage;
        stage.getIcons().add(creatImage("/de/sandwich/monopoly/icon.png"));
        stage.setTitle("-M---o---n---o---p---o---l---y-");
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
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Infos über die Konsole sind eingeschaltet.");
        else
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Information about the program via the console is off");

        launch();
    }

    public static Game getGameOperator() {
        if (gameOperator != null)
            return gameOperator;
        else throw new NullPointerException("The game operator is null!");
    }

    public static Boolean isGameOperatorCreated() {
        return gameOperator != null;
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

    public static void showWinner(String playerName) {
        StackPane root = new StackPane(
            buildLabel("winner_Name", buildLongText("Der Spieler", playerName, "hat gewonnen!!!"), Font.font(TEXT_FONT, FontWeight.BOLD, WINDOW_WIDTH * 0.05), null, ProgramColor.BORDER_COLOR_DARK.getColor())
        );

        Scene winnScene = new Scene(root);
        primaryStage.setScene(winnScene);
    }
}

/*
   Fehler 001: 2 oder mehre Spieler haben die gleiche Figure ausgewählt!
   Fehler 002: 2 oder mehre Spieler haben den gleichen Namen ausgewählt!
   Fehler 003: Es ist im moment nur 1 Spieler erstellt!
   Fehler 004: Es sind im moment keine Spieler erstellt!
 */