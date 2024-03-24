package sandwich.de.monopoly.GUI.Game.DisplayController;

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
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;
import sandwich.de.monopoly.Fields.Field;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayOne.PlayerDisplay;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayOne.TradingDisplay;
import sandwich.de.monopoly.Game;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Player;
import sandwich.de.monopoly.Fields.Street;

import java.util.ArrayList;
import java.util.HashMap;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class GameDisplayControllerOne {

    private static Pane display;
    private static PlayerDisplay playerDisplay;
    private static ArrayList<Player> lastPlayerSave;
    private static TradingDisplay tradingDisplay;

    public static void createDisplayOne(double width, double height) {
        if (display == null) {
            display = new Pane();
            display.setId("gameScene_DisplayOne");
            display.setMaxSize(width, height);
            display.getChildren().add(buildRectangle("gameScene_playerDisplay_Background", width, height, Color.rgb(97, 220, 43), true, Color.WHITE, width * 0.005));

            playerDisplay = new PlayerDisplay(width, height);
            tradingDisplay = new TradingDisplay(width, height, Color.RED);
            tradingDisplay.setVisible(false);
            playerDisplay.setVisible(false);

            display.getChildren().addAll(playerDisplay, tradingDisplay);
        } else throw new RuntimeException("Display One is already created!");
    }

    public static Pane getDisplay() {
        if (display != null)
            return display;
        else
            throw new RuntimeException("Display One is not yet created!");
    }

    public static void updateDisplay() throws PlayerNotFoundExceptions {
        if (playerDisplay.isVisible()) {
            if (lastPlayerSave != null)
                playerDisplay.createPlayers(lastPlayerSave);
            else throw new PlayerNotFoundExceptions();
        } else {
            System.out.println("ACHTUNG, DIE UPDATE METHODE FÜR DAS TRADING DISPLAY IST NOCH NICHT GEMACHT!!!");
        }
    }

    public static void displayPlayers(ArrayList<Player> players) {
        if (display != null) {
            playerDisplay.setVisible(true);
            tradingDisplay.setVisible(false);
            playerDisplay.createPlayers(players);
            lastPlayerSave = players;
        } else throw new NullPointerException("Display one is not yet created!");
    }

    public static void displayTradingMenu(/*Player One und Player Two Objekte werden hier übergeben*/) {
        if (display != null) {
            tradingDisplay.setVisible(true);
            playerDisplay.setVisible(false);
            tradingDisplay.startTrading();
        } else throw new NullPointerException("Display one is not yet created!");
    }

    public static void displayPlayerDisplay() {
        if (display != null) {
            if (playerDisplay.arePlayersGenerated() && !playerDisplay.isVisible()) {
                tradingDisplay.setVisible(false);

                playerDisplay.setVisible(true);
            }
        } else throw new NullPointerException("Display one is not yet created!");
    }

    //Creates the base from the player boxes
    public static Pane buildPlayer(double width, double height, Color backgroundColor, Player player) {
        Pane playerBox = new Pane();
        playerBox.setId("gameScene_playerDisplay_PlayerBox");
        playerBox.setMaxSize(width, height);

        Rectangle background = buildRectangle("gameScene_playerDisplay_playerBox_Background", width, height, backgroundColor, true, Color.WHITE, width * 0.005);

        Label headerName = buildLabel("gameScene_playerDisplay_playerBox_NameHeader", player.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 8), TextAlignment.CENTER, Color.WHITE);
        centeringChildInPane(headerName, playerBox);

        ImageView figureDisplay = createImageView("gameScene_playerDisplay_playerBox_FigureDisplay", player.getFigur().getFigureImage(), (width / 3.725) / 2, (width / 3.725) / 2, width - (width / 3.725) / 2 - (width * 0.001), height * 0.025);

        Line headerSeparatingline = buildLine("gameScene_playerDisplay_playerBox_NameHeaderSeparatingLine", new Point2D(0, height * 0.15), new Point2D(width, height * 0.15), width * 0.005, Color.WHITE);

        Label displayAccountBalance = buildLabel("gameScene_playerDisplay_playerBox_DisplayAccountBalance", ("Kontostand: " + player.getBankAccount()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 12), TextAlignment.CENTER, Color.WHITE, 0, height * 0.17);
        centeringChildInPane(displayAccountBalance, playerBox);

        Line accountBalanceSeparatingline = buildLine("gameScene_playerDisplay_playerBox_AccountBalanceSeparatingLine", new Point2D(0, height * 0.30), new Point2D(width, height * 0.30), width * 0.005, Color.WHITE);

        playerBox.getChildren().addAll( background, headerName, figureDisplay, headerSeparatingline, displayAccountBalance, accountBalanceSeparatingline);

        return playerBox;
    }

    public static Rectangle[] buildStreetInventar(double w, double h, Player p) {
        Rectangle[] streetsR = new Rectangle[28];
        HashMap<Integer, Field> fields = Game.getFields();

        //ÄDNEREN!!!
        System.out.println("HIER MUSS WAS IM BUILD STREET INVENTAR GEÄNDERT WERDEN");
        Color ANLAGEN = Color.GRAY;
        Color BAHNHOEFE = Color.BLACK;

        final double space = w * 0.04;
        final double width = w * 0.08;
        final double height = h * 0.10;
        final double startY = h * 0.40;
        final double startX = w * 0.04;

        for (int i = 0, j = 0; i < 46; i++) {

            if (streetsR[j] == null)
                streetsR[j] = buildRectangle("street:" + j + ", isOwner:", width, height, Color.rgb(173,173,173), true, Color.WHITE, (w * 0.005) / 2);

            if (j == 22 || j == 23) {
                streetsR[j].setFill(ANLAGEN);
                streetsR[j].setY(startY + height + space);
                streetsR[j].setX(startX + (j - 16) * (width + space));

                j++;

            } else if (j >= 24) {
                streetsR[j].setFill(BAHNHOEFE);

                if (j == 24 || j == 25) {
                    streetsR[j].setY(startY + 2 * (height + space));
                    streetsR[j].setX(startX + (j - 18) * (width + space));
                } else {
                    streetsR[j].setY(startY + 3 * (height + space));
                    streetsR[j].setX(startX + (j - 20) * (width + space));
                }

                j++;

            } else {
                if (fields.get(i) instanceof Street street) {

                    if (street.isOwned()) {
                        if (street.getOwner() == p) {
                            streetsR[j].setId(streetsR[j].getId() + true);
                            streetsR[j].setFill(street.getColor());
                        }
                    } else {
                        streetsR[j].setId(streetsR[j].getId() + false);
                        streetsR[j].setFill(Color.rgb(173,173,173));
                    }

                    switch (j) {
                        case 0, 1 -> {
                            streetsR[j].setY(startY);
                            streetsR[j].setX(startX + j * (width + space));
                        }
                        case 20, 21 -> {
                            streetsR[j].setY(startY);
                            streetsR[j].setX(startX + 4 * (width + space) + (j - 18) * (width + space));
                        }
                        case 2, 3, 4 -> {
                            streetsR[j].setY(startY + (j - 1) * (height + space));
                            streetsR[j].setX(startX);
                        }
                        case 5, 6, 7 -> {
                            streetsR[j].setY(startY + (j - 4) * (height + space));
                            streetsR[j].setX(startX + width + space);
                        }
                        case 8, 9, 10 -> {
                            streetsR[j].setY(startY + (j - 7) * (height + space));
                            streetsR[j].setX(startX + 2 * (width + space));
                        }
                        case 11, 12, 13 -> {
                            streetsR[j].setY(startY + (j - 10) * (height + space));
                            streetsR[j].setX(startX + 3 * (width + space));
                        }
                        case 14, 15, 16 -> {
                            streetsR[j].setY(startY + (j - 13) * (height + space));
                            streetsR[j].setX(startX + 4 * (width + space));
                        }
                        case 17, 18, 19 -> {
                            streetsR[j].setY(startY + (j - 16) * (height + space));
                            streetsR[j].setX(startX + 5 * (width + space));
                        }
                    }

                    j++;
                }
            }
        }
        return streetsR;
    }

}
