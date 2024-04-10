package de.sandwich.GUI.Game.Displays.DisplayOne;

import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne;

import java.util.ArrayList;

import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.ToManyPlayersExceptions;
import de.sandwich.Exceptions.WrongNodeException;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne.buildPlayer;
import static de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne.buildStreetInventar;

/*
 *  ACHTUNG:
 *  Im moment sind das einfach vorgefertigte Spieler, wie die Spieler
 *  daten gelesen werden oder ähnliches ist nicht mit ein Implementiert!
 */


public class PlayerDisplay extends Pane{

    //Variables
    private final GameDisplayControllerOne rootDisplay;
    private final double width, height;
    private boolean arePlayerGenerated = false;
    private final ImageView[] playerOrderFigures = new ImageView[5];

    public PlayerDisplay(double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_PlayerDisplay");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        //Creates the player Displays without any images
        double playerSize = (((width * 0.225) / 3.725) / 2);
        for (int i = 0; i != 5; i++) {
            playerOrderFigures[i] = new ImageView();

            playerOrderFigures[i].setFitWidth(playerSize);
            playerOrderFigures[i].setFitHeight(playerSize);

            playerOrderFigures[i].setY(height - playerSize);

            playerOrderFigures[i].setX((playerSize + width * 0.005) * i);

            getChildren().add(playerOrderFigures[i]);
        }

        this.width = width;
        this.height = height;
    }

    public void createPlayers(ArrayList<Player> players) {
        Pane display = new Pane();
        display.setId("gameScene_playerDisplay_Players");

        //Sets the player figure images that displayed
        for (int i = 0; i != players.size(); i++) {
            if (players.get(i) != null)
                playerOrderFigures[i].setImage(players.get(i).getFigur().getFigureImage());
        }

        arePlayerGenerated = true;

        final double playerBoxWidth = width * 0.225;
        final double playerBoxHeight = height * 0.40;

        Label header = buildLabel("gameScene_playerDisplay_Header", "Spieler", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 15), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());

        Pane playerOne = null;
        Pane playerTwo = null;
        Pane playerThree = null;
        Pane playerFour = null;
        Pane playerFive = null;

        //Generates the Player boxes that are displayed in game
        for (int i = 0; i != players.size(); i++) {
            double bigger;
            if (players.size() > 3) {
                switch (i) {
                    case 0 -> playerOne = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, ProgramColor.PLAYER_ONE_BACKGROUND.getColor(), players.get(0));
                    case 1 -> playerTwo = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, ProgramColor.PLAYER_TWO_BACKGROUND.getColor(), players.get(1));
                    case 2 -> playerThree = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, ProgramColor.PLAYER_THREE_BACKGROUND.getColor(), players.get(2));
                    case 3 -> playerFour = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, ProgramColor.PLAYER_FOUR_BACKGROUND.getColor(), players.get(3));
                    case 4 -> playerFive = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, ProgramColor.PLAYER_FIVE_BACKGROUND.getColor(), players.get(4));
                }
            } else {
                if (players.size() == 3)
                    bigger = 1.3;
                else
                    bigger = 1.7;

                switch (i) {
                    case 0 -> playerOne = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, ProgramColor.PLAYER_ONE_BACKGROUND.getColor(), players.get(0));
                    case 1 -> playerTwo = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, ProgramColor.PLAYER_TWO_BACKGROUND.getColor(), players.get(1));
                    case 2 -> playerThree = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, ProgramColor.PLAYER_THREE_BACKGROUND.getColor(), players.get(2));
                }
            }
        }

        //Set the postions from the Player boxes that are displayed in game
        if (!(players.size() > 5)) {
            if (players.size() == 5) {
                playerOne.setLayoutX((width / 2 - playerBoxWidth / 2) - (playerBoxWidth + width * 0.05));
                playerOne.setLayoutY(width * 0.10);

                assert playerTwo != null;
                playerTwo.setLayoutX((width / 2) - (playerBoxWidth / 2));
                playerTwo.setLayoutY(width * 0.10);

                assert playerThree != null;
                playerThree.setLayoutX((width / 2 - playerBoxWidth / 2) + playerBoxWidth + width * 0.05);
                playerThree.setLayoutY(width * 0.10);

                assert playerFour != null;
                playerFour.setLayoutX((width / 2 - playerBoxWidth) - width * 0.025);
                playerFour.setLayoutY(width * 0.10 + playerBoxHeight + height * 0.025);

                assert playerFive != null;
                playerFive.setLayoutX((width / 2) + width * 0.025);
                playerFive.setLayoutY(width * 0.10 + playerBoxHeight + height * 0.025);

                display.getChildren().addAll(playerOne, playerTwo, playerThree, playerFour, playerFive);
            } else if (players.size() == 4) {
                playerOne.setLayoutX((width / 2 - playerBoxWidth) - width * 0.0125);
                playerOne.setLayoutY(width * 0.10);

                assert playerTwo != null;
                playerTwo.setLayoutX((width / 2) + width * 0.0125);
                playerTwo.setLayoutY(width * 0.10);

                assert playerThree != null;
                playerThree.setLayoutX((width / 2 - playerBoxWidth) - width * 0.0125);
                playerThree.setLayoutY(width * 0.10 + playerBoxHeight + width * 0.025);

                assert playerFour != null;
                playerFour.setLayoutX((width / 2) + width * 0.0125);
                playerFour.setLayoutY(width * 0.10 + playerBoxHeight +  width * 0.025);

                display.getChildren().addAll(playerOne, playerTwo, playerThree, playerFour);
            } else if (players.size() == 3) {
                final double bigger = 1.3;

                playerOne.setLayoutX(((width / 2) - ((playerBoxWidth * bigger) / 2)) - ((playerBoxWidth * bigger) + width * 0.025));
                playerOne.setLayoutY(height * 0.03);

                assert playerTwo != null;
                playerTwo.setLayoutX((width / 2) - ((playerBoxWidth * bigger) / 2));
                playerTwo.setLayoutY(height * 0.03 + (playerBoxWidth * bigger));

                assert playerThree != null;
                playerThree.setLayoutX(((width / 2) - ((playerBoxWidth * bigger) / 2)) + (playerBoxWidth * bigger) + width * 0.025);
                playerThree.setLayoutY(height * 0.03);

                display.getChildren().addAll(playerOne, playerTwo, playerThree);
            } else if (players.size() == 2) {
                final double bigger = 1.7;

                playerOne.setLayoutX(width / 2 - playerBoxWidth * bigger - width * 0.09);
                playerOne.setLayoutY(height * 0.20);

                Label vs = buildLabel("gameScene_playerDisplay_playerBoxes_VS", "VS", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 10), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
                centeringChildInPane(vs, this);
                vs.setLayoutY(height * 0.375);

                assert playerTwo != null;
                playerTwo.setLayoutX(width / 2 + width * 0.09);
                playerTwo.setLayoutY(height * 0.20);


                display.getChildren().addAll(playerOne, vs, playerTwo);
            } else throw new ToManyPlayersExceptions();
        } else throw new ToManyPlayersExceptions();

        display.getChildren().add(header);

        centeringChildInPane(header, this);

        getChildren().add(display);
    }

    public void deletePlayers() {
        getChildren().clear();
        arePlayerGenerated = false;
    }

    public boolean arePlayersGenerated() {
        return arePlayerGenerated;
    }

    private Pane buildPlayerShowBox(double width, double height, Color backgroundColor, Player player) {
        Pane playerShowBox = buildPlayer(width, height, backgroundColor, player);

        if (Main.isGameOperatorCreated()) {
            if (Main.getGameOperator().getTurnPlayer().getOrderNumber() == player.getOrderNumber()) {
                if (playerShowBox.getChildren().get(0) instanceof Rectangle r) {
                    r.setStrokeWidth(r.getStrokeWidth() * 6);
                } else throw new WrongNodeException("PlayerShowBox");
            }
        } else {
            System.out.println("Die Nummer des Spielers ist: " + player.getOrderNumber());
            if (player.getOrderNumber() == 0) {
                if (playerShowBox.getChildren().get(0) instanceof Rectangle r) {    
                    r.setStrokeWidth(r.getStrokeWidth() * 6);
                }
            }
        }

        StackPane tradingButton = new StackPane();

        Rectangle tradingButtonBackground = buildRectangle("gameScene_playerDisplay_tradingStartButton_Background", width * 0.36, height * 0.09, backgroundColor.desaturate(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), width * 0.015);
        tradingButtonBackground.setArcHeight(width * 0.12);
        tradingButtonBackground.setArcWidth(width * 0.12);

        Label tradingButtonLabel = buildLabel("gameScene_playerDisplay_tradingStartButton_Label", "Trading", Font.font(Main.TEXT_FONT,  FontWeight.BOLD, width * 0.08), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());

        tradingButton.getChildren().setAll(tradingButtonBackground, tradingButtonLabel);
        tradingButton.setLayoutY(height * 0.40);
        tradingButton.setLayoutX(width * 0.32);

        tradingButton.setOnMouseClicked(event -> rootDisplay.displayTradingMenu());

        Rectangle[] streets = buildStreetInventar(width, height, player);



        playerShowBox.getChildren().add(tradingButton);

        for (Rectangle street : streets) 
            playerShowBox.getChildren().add(street);

        return playerShowBox;
    }


}
