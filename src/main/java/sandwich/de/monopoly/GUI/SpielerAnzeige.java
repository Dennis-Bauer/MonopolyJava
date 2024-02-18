package sandwich.de.monopoly.GUI;

import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Utilities;


/*
 *  ACHTUNG:
 *  Im moment sind das einfach vorgefertigte Spieler, wie die Spieler
 *  daten gelesen werden oder ähnliches ist nicht mit ein Implementiert!
 */


public class SpielerAnzeige {

    private static double borderWidth;

    public static Pane buildPlayerDisplay(double width, double height, int playerAmount, Color backgroundColor) {
        Pane root = new Pane();
        root.setId("gameScene_PlayerDisplay");
        root.setMaxSize(width, height);
        borderWidth = width * 0.005;

        Rectangle background = Utilities.buildRectangle("gameScene_playerDisplay_Background", width, height, backgroundColor, true, Color.WHITE, borderWidth);
        Label header = Utilities.buildLabel("gameScene_playerDisplay_Header", "Spieler", Font.font(Main.textFont, FontWeight.BOLD, width / 15), TextAlignment.CENTER, Color.WHITE);

        Utilities.centeringChildInPane(header, root);

        root.getChildren().addAll(background, header);

        final double playerBoxWidth = width * 0.225;
        final double playerBoxHeight = height * 0.40;

        if (!(playerAmount > 5)) {
            if (playerAmount == 5) {
                Pane playerOne = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb(33, 203, 85), "Sandwich898", 2000);
                playerOne.setLayoutX(((width / 2) - (playerBoxWidth / 2)) - (playerBoxWidth + width * 0.05));
                playerOne.setLayoutY(width * 0.10);

                Pane playerTwo = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX((width / 2) - (playerBoxWidth / 2));
                playerTwo.setLayoutY(width * 0.10);

                Pane playerThree = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb( 255, 97, 0), "NAME", 898);
                playerThree.setLayoutX(((width / 2) - (playerBoxWidth / 2)) + playerBoxWidth + width * 0.05);
                playerThree.setLayoutY(width * 0.10);

                Pane playerFour = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb( 140, 82, 255), "NAME", 8928);
                playerFour.setLayoutX((width / 2 - playerBoxWidth) - width * 0.025);
                playerFour.setLayoutY(width * 0.10 + playerBoxHeight + height * 0.025);

                Pane playerFive = buildPlayer(playerBoxWidth, height * 0.40, Color.rgb( 56, 182, 255), "NAME", 89438);
                playerFive.setLayoutX((width / 2) + width * 0.025);
                playerFive.setLayoutY(width * 0.10 + playerBoxHeight + height * 0.025);

                root.getChildren().addAll(playerOne, playerTwo, playerThree, playerFour, playerFive);
            } else if (playerAmount == 4) {
                Pane playerOne = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb(33, 203, 85), "Sandwich898", 2000);
                playerOne.setLayoutX((width / 2 - playerBoxWidth) - width * 0.0125);
                playerOne.setLayoutY(width * 0.10);

                Pane playerTwo = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX((width / 2) + width * 0.0125);
                playerTwo.setLayoutY(width * 0.10);

                Pane playerThree = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb( 255, 97, 0), "NAME", 898);
                playerThree.setLayoutX((width / 2 - playerBoxWidth) - width * 0.0125);
                playerThree.setLayoutY(width * 0.10 + playerBoxHeight + width * 0.025);

                Pane playerFour = buildPlayer(playerBoxWidth, playerBoxHeight, Color.rgb( 140, 82, 255), "NAME", 8928);
                playerFour.setLayoutX((width / 2) + width * 0.0125);
                playerFour.setLayoutY(width * 0.10 + playerBoxHeight +  width * 0.025);

                root.getChildren().addAll(playerOne, playerTwo, playerThree, playerFour);
            } else if (playerAmount == 3) {
                final double bigger = 1.3;
                Pane playerOne = buildPlayer(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(33, 203, 85), "Sandwich898", 2000);
                playerOne.setLayoutX(((width / 2) - ((playerBoxWidth * bigger) / 2)) - ((playerBoxWidth * bigger) + width * 0.025));
                playerOne.setLayoutY(height * 0.03);

                Pane playerTwo = buildPlayer(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX((width / 2) - ((playerBoxWidth * bigger) / 2));
                playerTwo.setLayoutY(height * 0.03 + (playerBoxWidth * bigger));

                Pane playerThree = buildPlayer(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb( 255, 97, 0), "NAME", 898);
                playerThree.setLayoutX(((width / 2) - ((playerBoxWidth * bigger) / 2)) + (playerBoxWidth * bigger) + width * 0.025);
                playerThree.setLayoutY(height * 0.03);

                root.getChildren().addAll(playerOne, playerTwo, playerThree);
            } else if (playerAmount == 2) {
                final double bigger = 1.7;
                Pane playerOne = buildPlayer(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(33, 203, 85), "Sandwich898", 2000);
                playerOne.setLayoutX(width / 2 - playerBoxWidth * bigger - width * 0.09);
                playerOne.setLayoutY(height * 0.20);

                Label vs = Utilities.buildLabel("gameScene_playerDisplay_playerBoxes_VS", "VS", Font.font(Main.textFont, FontWeight.BOLD, width / 10), TextAlignment.CENTER, Color.WHITE);
                Utilities.centeringChildInPane(vs, root);
                vs.setLayoutY(height * 0.375);

                Pane playerTwo = buildPlayer(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX(width / 2 + width * 0.09);
                playerTwo.setLayoutY(height * 0.20);


                root.getChildren().addAll(playerOne, vs, playerTwo);
            } else throw new RuntimeException("Too many/little Players");
        } else throw new RuntimeException("Too many Players");

        return root;
    }

    private static Pane buildPlayer(double width, double height, Color backgroundColor, String playerName, int playerKontoStand) {
        Pane playerShowBox = new Pane();
        playerShowBox.setId("gameScene_playerDisplay_PlayerBox");
        playerShowBox.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("gameScene_playerDisplay_playerBox_Background", width, height, backgroundColor, true, Color.WHITE, borderWidth);

        Label headerName = Utilities.buildLabel("gameScene_playerDisplay_playerBox_NameHeader", playerName, Font.font(Main.textFont, FontWeight.BOLD, width / 8), TextAlignment.CENTER, Color.WHITE);
        Utilities.centeringChildInPane(headerName, playerShowBox);

        Line headerSeparatingline = Utilities.buildLine("gameScene_playerDisplay_playerBox_NameHeaderSeparatingLine", new Point2D(0, height * 0.15), new Point2D(width, height * 0.15), borderWidth, Color.WHITE);

        Label displayAccountBalance = Utilities.buildLabel("gameScene_playerDisplay_playerBox_DisplayAccountBalance", ("Kontostand: " + playerKontoStand), Font.font(Main.textFont, FontWeight.BOLD, width / 12), TextAlignment.CENTER, Color.WHITE, 0, height * 0.17);
        Utilities.centeringChildInPane(displayAccountBalance, playerShowBox);

        Line accountBalanceSeparatingline = Utilities.buildLine("gameScene_playerDisplay_playerBox_AccountBalanceSeparatingLine", new Point2D(0, height * 0.30), new Point2D(width, height * 0.30), borderWidth, Color.WHITE);

        Rectangle[] streets = buildStreetInventar(width, height);

        playerShowBox.getChildren().addAll( background, headerName, headerSeparatingline, displayAccountBalance, accountBalanceSeparatingline);

        for (Rectangle street : streets) {playerShowBox.getChildren().addAll(street);}

        return playerShowBox;
    }

    private static Rectangle[] buildStreetInventar(double w, double h) {
        Rectangle[] streets = new Rectangle[28];


        final double space = w * 0.04;
        final double width = w * 0.08;
        final double height = h * 0.10;
        final double startY = h * 0.4;
        final double startX = w * 0.04;
        for (int i = 0; i < streets.length; i++) {
            streets[i] = Utilities.buildRectangle("gameScene_playerDisplay_playerBox_playerInventar_Street" + i, width, height, Color.LIGHTGRAY, true, Color.WHITE, borderWidth / 2);
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
            streets[i].setFill(Color.rgb(173,173,	173));
        }
        return streets;
    }


}
