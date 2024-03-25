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
import sandwich.de.monopoly.GUI.Game.Displays.DisplayOne.BankDisplay;
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

    private static final NullPointerException DISPLAY_NOT_CREATED = new NullPointerException("Display one is not yet created!");
    private static Pane display;
    private static PlayerDisplay playerDisplay;
    private static TradingDisplay tradingDisplay;
    private static BankDisplay bankDisplay;
    private static ArrayList<Player> lastPlayerSave;

    public static void createDisplayOne(double width, double height) {
        if (display == null) {
            display = new Pane();
            display.setId("gameScene_DisplayOne");
            display.setMaxSize(width, height);
            display.getChildren().add(buildRectangle("gameScene_playerDisplay_Background", width, height, Color.rgb(97, 220, 43), true, Color.WHITE, width * 0.005));

            playerDisplay = new PlayerDisplay(width, height);
            playerDisplay.setVisible(false);

            tradingDisplay = new TradingDisplay(width, height, Color.RED);
            tradingDisplay.setVisible(false);

            bankDisplay = new BankDisplay(width, height, Color.rgb(78, 138, 186));
            bankDisplay.setVisible(false);

            display.getChildren().addAll(playerDisplay, tradingDisplay, bankDisplay);
        } else throw new RuntimeException("Display One is already created!");
    }

    public static Pane getDisplay() {
        if (display != null)
            return display;
        else
            throw DISPLAY_NOT_CREATED;
    }

    public static void updateDisplay() throws PlayerNotFoundExceptions {
        if (playerDisplay.isVisible()) {
            if (lastPlayerSave != null)
                playerDisplay.createPlayers(lastPlayerSave);
            else throw new PlayerNotFoundExceptions();
        } else if (bankDisplay.isVisible())
            bankDisplay.displayPlayer(Main.getGameOperator().getTurnPlayer());
        else {
            System.out.println("ACHTUNG, DIE UPDATE METHODE FÜR DAS TRADING DISPLAY IST NOCH NICHT GEMACHT!!!");
        }
    }

    public static void displayPlayers(ArrayList<Player> players) {
        if (display != null) {
            clearDisplay();

            playerDisplay.setVisible(true);

            playerDisplay.createPlayers(players);
            lastPlayerSave = players;
        } else throw DISPLAY_NOT_CREATED;
    }

    public static void displayTradingMenu(/*Player One und Player Two Objekte werden hier übergeben*/) {
        if (display != null) {

            clearDisplay();

            tradingDisplay.setVisible(true);

            tradingDisplay.startTrading();
        } else throw DISPLAY_NOT_CREATED;
    }

    public static void displayPlayerDisplay() {
        if (display != null) {
            if (playerDisplay.arePlayersGenerated() && !playerDisplay.isVisible()) {

                clearDisplay();

                playerDisplay.setVisible(true);
            }
        } else throw DISPLAY_NOT_CREATED;
    }

    public static void displayBankDisplay(Player p) {
        if (display != null) {

            clearDisplay();

            bankDisplay.displayPlayer(p);
            bankDisplay.setVisible(true);

        } else throw DISPLAY_NOT_CREATED;
    }

    private static void clearDisplay() {
        bankDisplay.setVisible(false);
        tradingDisplay.setVisible(false);
        playerDisplay.setVisible(false);
    }

    //Creates the base from the player boxes
    public static Pane buildPlayer(double w, double h, Color backgroundColor, Player p) {
        Pane playerBox = new Pane();
        playerBox.setId("gameScene_playerDisplay_PlayerBox");
        playerBox.setMaxSize(w, h);

        Rectangle background = buildRectangle("gameScene_playerDisplay_playerBox_Background", w, h, backgroundColor, true, Color.WHITE, w * 0.005);

        Label headerName = buildLabel("gameScene_playerDisplay_playerBox_NameHeader", p.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w / 8), TextAlignment.CENTER, Color.WHITE);
        centeringChildInPane(headerName, playerBox);

        ImageView figureDisplay = createImageView("gameScene_playerDisplay_playerBox_FigureDisplay", p.getFigur().getFigureImage(), (w / 3.725) / 2, (w / 3.725) / 2, w - (w / 3.725) / 2 - (w * 0.001), h * 0.025);

        Line headerSeparatingline = buildLine("gameScene_playerDisplay_playerBox_NameHeaderSeparatingLine", new Point2D(0, h * 0.15), new Point2D(w, h * 0.15), w * 0.005, Color.WHITE);

        Label displayAccountBalance = buildLabel("gameScene_playerDisplay_playerBox_DisplayAccountBalance", ("Kontostand: " + p.getBankAccount()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w / 12), TextAlignment.CENTER, Color.WHITE, 0, h * 0.17);
        centeringChildInPane(displayAccountBalance, playerBox);

        Line accountBalanceSeparatingline = buildLine("gameScene_playerDisplay_playerBox_AccountBalanceSeparatingLine", new Point2D(0, h * 0.30), new Point2D(w, h * 0.30), w * 0.005, Color.WHITE);

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
