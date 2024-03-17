package sandwich.de.monopoly.GUI.Game;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.GUI.Game.Displays.PlayerDisplay;
import sandwich.de.monopoly.GUI.Game.Displays.TradingDisplay;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Spieler;

import java.util.ArrayList;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.createImageView;

public class GameDisplayControllerOne {

    private static Pane displayOne;
    private static PlayerDisplay playerDisplay;
    private static TradingDisplay tradingDisplay;

    public static void createDisplayOne(double width, double height) {
        displayOne = new Pane();
        displayOne.setId("gameScene_DisplayOne");
        displayOne.setMaxSize(width, height);

        Rectangle background = buildRectangle("gameScene_playerDisplay_Background", width, height, Color.rgb(97, 220, 43), true, Color.WHITE, width * 0.005);

        playerDisplay = new PlayerDisplay(width, height);
        tradingDisplay = new TradingDisplay(width, height, Color.RED);
        tradingDisplay.setVisible(false);
        playerDisplay.setVisible(false);

        displayOne.getChildren().addAll(background, playerDisplay, tradingDisplay);
    }

    public static Pane getDisplayOne() {
        if (displayOne != null)
            return displayOne;
        else
            throw new RuntimeException("Display One is not yet created");

    }

    public static void displayPlayers(ArrayList<Spieler> players) {
        playerDisplay.setVisible(true);
        tradingDisplay.setVisible(false);
        playerDisplay.createPlayers(players);
    }

    public static void displayTradingMenu(/*Player One und Player Two Objekte werden hier übergeben*/) {
        tradingDisplay.setVisible(true);
        playerDisplay.setVisible(false);
        tradingDisplay.startTrading();
    }

    public static void displayPlayerDisplay() {
        if (playerDisplay.arePlayerGenerated() && !playerDisplay.isVisible()) {
            tradingDisplay.setVisible(false);

            playerDisplay.setVisible(true);
        }
    }

    //Erstellt die Basis der Spieler anzeige
    public static Pane buildPlayer(double width, double height, Color backgroundColor, Spieler player) {
        Pane playerBox = new Pane();
        playerBox.setId("gameScene_playerDisplay_PlayerBox");
        playerBox.setMaxSize(width, height);

        Rectangle background = buildRectangle("gameScene_playerDisplay_playerBox_Background", width, height, backgroundColor, true, Color.WHITE, width * 0.005);

        Label headerName = buildLabel("gameScene_playerDisplay_playerBox_NameHeader", player.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 8), TextAlignment.CENTER, Color.WHITE);
        centeringChildInPane(headerName, playerBox);

        ImageView figureDisplay = createImageView("gameScene_playerDisplay_playerBox_FigureDisplay", player.getFigur().getFigureImage(), (width / 3.725) / 2, (width / 3.725) / 2, width - (width / 3.725) / 2 - (width * 0.001), height * 0.025);

        Line headerSeparatingline = buildLine("gameScene_playerDisplay_playerBox_NameHeaderSeparatingLine", new Point2D(0, height * 0.15), new Point2D(width, height * 0.15), width * 0.005, Color.WHITE);

        Label displayAccountBalance = buildLabel("gameScene_playerDisplay_playerBox_DisplayAccountBalance", ("Kontostand: " + player.getKontoStand()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 12), TextAlignment.CENTER, Color.WHITE, 0, height * 0.17);
        centeringChildInPane(displayAccountBalance, playerBox);

        Line accountBalanceSeparatingline = buildLine("gameScene_playerDisplay_playerBox_AccountBalanceSeparatingLine", new Point2D(0, height * 0.30), new Point2D(width, height * 0.30), width * 0.005, Color.WHITE);

        playerBox.getChildren().addAll( background, headerName, figureDisplay, headerSeparatingline, displayAccountBalance, accountBalanceSeparatingline);

        return playerBox;
    }

    public static Rectangle[] buildStreetInventar(double w, double h) {
        Rectangle[] streets = new Rectangle[28];


        final double space = w * 0.04;
        final double width = w * 0.08;
        final double height = h * 0.10;
        final double startY = h * 0.40;
        final double startX = w * 0.04;
        for (int i = 0; i < streets.length; i++) {
            streets[i] = buildRectangle("street:" + i + ", isOwner:", width, height, Color.LIGHTGRAY, true, Color.WHITE, (w * 0.005) / 2);
            streets[i].setFill(Main.streetColor.get(i));

            switch (i) {
                case 0, 1 -> {
                    streets[i].setY(startY);
                    streets[i].setX(startX + i * (width + space));
                }
                case 20, 21 -> {
                    streets[i].setY(startY);
                    streets[i].setX(startX + 4 * (width + space) + (i - 18) * (width + space));
                }
                case 2, 3, 4 -> {
                    streets[i].setY(startY + (i - 1) * (height + space));
                    streets[i].setX(startX);
                }
                case 5, 6, 7 -> {
                    streets[i].setY(startY + (i - 4) * (height + space));
                    streets[i].setX(startX + width + space);
                }
                case 8, 9, 10 -> {
                    streets[i].setY(startY + (i - 7) * (height + space));
                    streets[i].setX(startX + 2 * (width + space));
                }
                case 11, 12, 13 -> {
                    streets[i].setY(startY + (i - 10) * (height + space));
                    streets[i].setX(startX + 3 * (width + space));
                }
                case 14, 15, 16 -> {
                    streets[i].setY(startY + (i - 13) * (height + space));
                    streets[i].setX(startX + 4 * (width + space));
                }
                case 17, 18, 19 -> {
                    streets[i].setId(streets[i].getId() + true);
                    streets[i].setY(startY + (i - 16) * (height + space));
                    streets[i].setX(startX + 5 * (width + space));
                }
                case 22, 23 -> {
                    streets[i].setY(startY + height + space);
                    streets[i].setX(startX + (i - 16) * (width + space));

                    streets[i].setFill(Main.streetColor.get(22));
                }
                case 24, 25 -> {
                    streets[i].setY(startY + 2 * (height + space));
                    streets[i].setX(startX + (i - 18) * (width + space));

                    streets[i].setFill(Main.streetColor.get(23));
                }
                case 26, 27 -> {
                    streets[i].setY(startY + 3 * (height + space));
                    streets[i].setX(startX + (i - 20) * (width + space));

                    streets[i].setFill(Main.streetColor.get(23));
                }
            }
            if (!streets[i].getId().endsWith("true")) {
                streets[i].setFill(Color.rgb(173,173,	173));
                streets[i].setId(streets[i].getId() + false);
            }
        }
        return streets;
    }

}
