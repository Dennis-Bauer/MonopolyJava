package de.sandwich.GUI.Game.DisplayController;

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

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLine;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import java.util.ArrayList;
import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.PlayerNotFoundExceptions;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.Fields.Utilitie;
import de.sandwich.GUI.Game.Displays.DisplayOne.BankDisplay;
import de.sandwich.GUI.Game.Displays.DisplayOne.BuildDisplay;
import de.sandwich.GUI.Game.Displays.DisplayOne.DestroyDisplay;
import de.sandwich.GUI.Game.Displays.DisplayOne.PlayerDisplay;
import de.sandwich.GUI.Game.Displays.DisplayOne.TradingDisplay;


public class GameDisplayControllerOne extends Pane {

    private final PlayerDisplay playerDisplay;
    private final TradingDisplay tradingDisplay;
    private final BankDisplay bankDisplay;
    private final BuildDisplay buildDisplay;
    private final DestroyDisplay destroyDisplay;

    private ArrayList<Player> lastPlayerSave;

    public GameDisplayControllerOne(double width, double height) {
        setId("gameScene_DisplayOne");
        setMaxSize(width, height);
        getChildren().add(buildRectangle("gameScene_playerDisplay_Background", width, height, ProgramColor.DISPLAY_ONE_BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), width * 0.005));

        playerDisplay = new PlayerDisplay(width, height, this);
        playerDisplay.setVisible(false);

        tradingDisplay = new TradingDisplay(width, height, this);
        tradingDisplay.setVisible(false);

        bankDisplay = new BankDisplay(width, height, this);
        bankDisplay.setVisible(false);

        buildDisplay = new BuildDisplay(width, height, this);
        buildDisplay.setVisible(false);

        destroyDisplay = new DestroyDisplay(width, height, this);
        destroyDisplay.setVisible(false);

        getChildren().addAll(playerDisplay, tradingDisplay, bankDisplay, buildDisplay, destroyDisplay);
    }

    public void updateDisplay() throws PlayerNotFoundExceptions {
        if (playerDisplay.isVisible()) {
            if (lastPlayerSave != null)
                playerDisplay.createPlayers(lastPlayerSave);
            else throw new PlayerNotFoundExceptions();
        } else if (bankDisplay.isVisible()) {
            bankDisplay.display(Main.getGameOperator().getTurnPlayer());
        } else if (buildDisplay.isVisible()) {
            buildDisplay.display(Main.getGameOperator().getTurnPlayer());
        }  else if (destroyDisplay.isVisible()) {
            destroyDisplay.display(Main.getGameOperator().getTurnPlayer());
        }
    }

    public void displayPlayers(ArrayList<Player> players) {
        resetDisplay();

        lastPlayerSave = players;
        
        playerDisplay.setVisible(true);
        playerDisplay.createPlayers(players);
    }

    public void displayTradingMenu(Player playerOne, Player playerTwo) {
        resetDisplay();

        tradingDisplay.setVisible(true);
        tradingDisplay.startTrading(playerOne, playerTwo);
    }

    public void displayPlayerDisplay() {
        if (playerDisplay.arePlayersGenerated() && !playerDisplay.isVisible()) {
            resetDisplay();

            playerDisplay.setVisible(true);
        }
    }

    public void displayBankDisplay(Player p) {
        resetDisplay();

        bankDisplay.display(p);
        bankDisplay.setVisible(true);
    }

    public void displayBuildDisplay(Player p) {
        resetDisplay();

        buildDisplay.display(p);
        buildDisplay.setVisible(true);
    }

    public void displayDestroyDisplay(Player p) {
        resetDisplay();

        destroyDisplay.display(p);
        destroyDisplay.setVisible(true);
    }

    private void resetDisplay() {
        bankDisplay.setVisible(false);
        tradingDisplay.setVisible(false);
        playerDisplay.setVisible(false);
        buildDisplay.setVisible(false);
        destroyDisplay.setVisible(false);
    }

    //Creates the base from the player boxes
    public static Pane buildPlayer(double w, double h, Color backgroundColor, Player p) {
        Pane playerBox = new Pane();
        playerBox.setId("gameScene_playerDisplay_PlayerBox");
        playerBox.setMaxSize(w, h);

        Rectangle background = buildRectangle("gameScene_playerDisplay_playerBox_Background", w, h, backgroundColor, true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), w * 0.005);

        Label headerName = buildLabel("gameScene_playerDisplay_playerBox_NameHeader", p.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w / 8), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(headerName, playerBox);

        ImageView freeJailDisplay = createImageView("gameScene_playerDisplay_playerBox_FreeJailImage", "/de/sandwich/monopoly/gameBoard/freeJailSymbole.png", w * 0.10, w * 0.10, w - (w * 0.18), h * 0.19);
        freeJailDisplay.setRotate(-45);

        Label freeJailCount = buildLabel("gameScene_palyerDisplay_playerBox_FreeJailCardsCount", p.getFreeJailCardNumber() + "X", Font.font(Main.TEXT_FONT, FontWeight.BOLD, w / 16), null, ProgramColor.TEXT_COLOR.getColor(), w - (w * 0.12), h * 0.23);

        ImageView figureDisplay = createImageView("gameScene_playerDisplay_playerBox_FigureDisplay", p.getFigur().getFigureImage(), (w / 3.725) / 2, (w / 3.725) / 2, w - (w / 3.725) / 2 - (w * 0.001), h * 0.025);

        Line headerSeparatingline = buildLine("gameScene_playerDisplay_playerBox_NameHeaderSeparatingLine", new Point2D(0, h * 0.15), new Point2D(w, h * 0.15), w * 0.005, ProgramColor.BORDER_COLOR_LIGHT.getColor());

        Label displayAccountBalance = buildLabel("gameScene_playerDisplay_playerBox_DisplayAccountBalance", ("Kontostand: " + p.getBankAccount()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w / 12), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), w * 0.01, h * 0.19);

        Line accountBalanceSeparatingline = buildLine("gameScene_playerDisplay_playerBox_AccountBalanceSeparatingLine", new Point2D(0, h * 0.30), new Point2D(w, h * 0.30), w * 0.005, ProgramColor.BORDER_COLOR_LIGHT.getColor());

        playerBox.getChildren().addAll( background, headerName, figureDisplay, headerSeparatingline, displayAccountBalance, accountBalanceSeparatingline, freeJailDisplay, freeJailCount);

        return playerBox;
    }

    public static Rectangle[] buildStreetInventar(double w, double h, Player p) {
        Rectangle[] streetsR = new Rectangle[28];
        HashMap<Integer, Field> fields = Game.getFields();

        final double space = w * 0.04;
        final double width = w * 0.08;
        final double height = h * 0.10;
        final double startY = h * 0.40;
        final double startX = w * 0.04;

        for (int i = 0, j = 0, b = 22; i < fields.size() + 2; i++) {

            if (fields.get(i) instanceof Street) {
                String id;
                if (i < 10)
                    id = "fieldNumber:0" + i + ", hasOwner:";
                else
                    id = "fieldNumber:" + i + ", hasOwner:";

                streetsR[j] = buildRectangle(id, width, height, ProgramColor.STREET_NOT_OWNED.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), (w * 0.01) / 2);
            } else if (fields.get(i) instanceof Station) {
                String id;
                if (i < 10)
                    id = "fieldNumber:0" + i + ", hasOwner:";
                else
                    id = "fieldNumber:" + i + ", hasOwner:";
                streetsR[b] = buildRectangle(id, width, height, ProgramColor.STREET_NOT_OWNED.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), (w * 0.01) / 2);
            }

            if (fields.get(i) instanceof Utilitie utilitie && i == 12) {
                streetsR[26] = buildRectangle("fieldNumber:" + i + ", hasOwner:", width, height, ProgramColor.STREET_NOT_OWNED.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), (w * 0.01) / 2);

                if (utilitie.getOwner() == p) {
                    streetsR[26].setFill(ProgramColor.UTILITIES_COLOR.getColor());
                    streetsR[26].setId(streetsR[26].getId() + true);   
                }

                streetsR[26].setY(startY + height + space);
                streetsR[26].setX(startX + (22 - 16) * (width + space));
            } else if (fields.get(i) instanceof Utilitie utilitie && i == 28) {
                streetsR[27] = buildRectangle("fieldNumber:" + i + ", hasOwner:", width, height, ProgramColor.STREET_NOT_OWNED.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), (w * 0.01) / 2);

                if (utilitie.getOwner() == p) {
                    streetsR[27].setFill(ProgramColor.UTILITIES_COLOR.getColor());
                    streetsR[27].setId(streetsR[27].getId() + true);   
                }
                streetsR[27].setY(startY + height + space);
                streetsR[27].setX(startX + (23 - 16) * (width + space));
            }
            else if (fields.get(i) instanceof Station s) {
                if (s.getOwner() == p) {
                    streetsR[b].setFill(ProgramColor.STATION_COLOR.getColor());
                    streetsR[b].setId(streetsR[b].getId() + true);   
                }

                if (b == 22 || b == 23) {
                    streetsR[b].setY(startY + 2 * (height + space));
                    streetsR[b].setX(startX + (b - 16) * (width + space));
                } else {
                    streetsR[b].setY(startY + 3 * (height + space));
                    streetsR[b].setX(startX + (b - 18) * (width + space));
                }
                b++;
            } else {
                if (fields.get(i) instanceof Street street) {

                    if (street.isOwned()) {
                        if (street.getOwner() == p) {
                            streetsR[j].setId(streetsR[j].getId() + true);
                            streetsR[j].setFill(street.getColor());

                            if (street.isInBank()) 
                                streetsR[j].setOpacity(0.4);
                            
                        }
                    } else {
                        streetsR[j].setId(streetsR[j].getId() + false);
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
