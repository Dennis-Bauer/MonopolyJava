package sandwich.de.monopoly.GUI.Game.Displays;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.GUI.Game.GameDisplayControllerOne;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Spieler;
import sandwich.de.monopoly.Utilities;

import java.security.SecurityPermission;
import java.util.ArrayList;

import static sandwich.de.monopoly.GUI.Game.GameDisplayControllerOne.buildPlayer;
import static sandwich.de.monopoly.GUI.Game.GameDisplayControllerOne.buildStreetInventar;


/*
 *  ACHTUNG:
 *  Im moment sind das einfach vorgefertigte Spieler, wie die Spieler
 *  daten gelesen werden oder ähnliches ist nicht mit ein Implementiert!
 */


public class PlayerDisplay extends Pane{

    //Variables
    private final double borderWidth;
    private final double width, height;
    private boolean arePlayerGenerated = false;

    public PlayerDisplay(double width, double height) {
        setId("gameScene_PlayerDisplay");
        setMaxSize(width, height);
        this.width = width;
        this.height = height;
        borderWidth = width * 0.005;
    }

    public void createPlayers(ArrayList<Spieler> players) {
        Pane display = new Pane();
        display.setId("gameScene_playerDisplay_Players");

        arePlayerGenerated = true;

        final double playerBoxWidth = width * 0.225;
        final double playerBoxHeight = height * 0.40;

        Label header = Utilities.buildLabel("gameScene_playerDisplay_Header", "Spieler", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 15), TextAlignment.CENTER, Color.WHITE);

        Pane playerOne = null;
        Pane playerTwo = null;
        Pane playerThree = null;
        Pane playerFour = null;
        Pane playerFive = null;

        for (int i = 0; i != players.size(); i++) {
            double bigger;
            if (players.size() > 3) {
                switch (i) {
                    case 0 -> playerOne = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb(33, 203, 85), players.get(0));
                    case 1 -> playerTwo = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb(255, 49, 49), players.get(1));
                    case 2 -> playerThree = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb( 255, 97, 0), players.get(2));
                    case 3 -> playerFour = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb( 140, 82, 255), players.get(3));
                    case 4 -> playerFive = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb( 56, 182, 255), players.get(4));
                }
            } else {
                if (players.size() == 3)
                    bigger = 1.3;
                else
                    bigger = 1.7;

                switch (i) {
                    case 0 -> playerOne = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(33, 203, 85), players.get(0));
                    case 1 -> playerTwo = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(255, 49, 49), players.get(1));
                    case 2 -> playerThree = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(255, 97, 0), players.get(2));
                }
            }
        }

        if (!(players.size() > 5)) {
            if (players.size() == 5) {
                playerOne.setLayoutX(((width / 2) - (playerBoxWidth / 2)) - (playerBoxWidth + width * 0.05));
                playerOne.setLayoutY(width * 0.10);

                assert playerTwo != null;
                playerTwo.setLayoutX((width / 2) - (playerBoxWidth / 2));
                playerTwo.setLayoutY(width * 0.10);

                assert playerThree != null;
                playerThree.setLayoutX(((width / 2) - (playerBoxWidth / 2)) + playerBoxWidth + width * 0.05);
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

                Label vs = Utilities.buildLabel("gameScene_playerDisplay_playerBoxes_VS", "VS", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 10), TextAlignment.CENTER, Color.WHITE);
                Utilities.centeringChildInPane(vs, this);
                vs.setLayoutY(height * 0.375);

                assert playerTwo != null;
                playerTwo.setLayoutX(width / 2 + width * 0.09);
                playerTwo.setLayoutY(height * 0.20);


                display.getChildren().addAll(playerOne, vs, playerTwo);
            } else throw new RuntimeException("Too many/little Players");
        } else throw new RuntimeException("Too many Players");

        display.getChildren().add(header);

        Utilities.centeringChildInPane(header, this);

        getChildren().add(display);
    }

    public void deletePlayers() {
        getChildren().clear();
        arePlayerGenerated = false;
    }

    public boolean arePlayerGenerated() {
        return arePlayerGenerated;
    }

    private Pane buildPlayerShowBox(double width, double height, Color backgroundColor, Spieler player) {
        Pane playerShowBox = buildPlayer(width, height, backgroundColor, player);

        StackPane tradingButton = new StackPane();

        Rectangle tradingButtonBackground = Utilities.buildRectangle("gameScene_playerDisplay_tradingStartButton_Background", width * 0.36, height * 0.09, backgroundColor.desaturate(), true, Color.WHITE, width * 0.015);
        tradingButtonBackground.setArcHeight(width * 0.12);
        tradingButtonBackground.setArcWidth(width * 0.12);

        Label tradingButtonLabel = Utilities.buildLabel("gameScene_playerDisplay_tradingStartButton_Label", "Trading", Font.font(Main.TEXT_FONT,  FontWeight.BOLD, width * 0.08), TextAlignment.CENTER, Color.WHITE);

        tradingButton.getChildren().setAll(tradingButtonBackground, tradingButtonLabel);
        tradingButton.setLayoutY(height * 0.40);
        tradingButton.setLayoutX(width * 0.32);

        tradingButton.setOnMouseClicked(event -> GameDisplayControllerOne.displayTradingMenu());

        Rectangle[] streets = buildStreetInventar(width, height);

        playerShowBox.getChildren().add(tradingButton);

        for (Rectangle street : streets) {playerShowBox.getChildren().addAll(street);}

        return playerShowBox;
    }


}
