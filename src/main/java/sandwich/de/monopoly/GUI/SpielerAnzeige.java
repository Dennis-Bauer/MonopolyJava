package sandwich.de.monopoly.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Polygon;
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


public class SpielerAnzeige extends Pane{

    //Variables
    private final double borderWidth;
    private final double width, height;

    //Scenes
    private Pane players;

    private final Pane tradingMenu;

    public SpielerAnzeige(double width, double height, int playerAmount, Color backgroundColor) {
        setId("gameScene_PlayerDisplay");
        setMaxSize(width, height);
        this.width = width;
        this.height = height;
        borderWidth = width * 0.005;

        Rectangle background = Utilities.buildRectangle("gameScene_playerDisplay_Background", width, height, backgroundColor, true, Color.WHITE, borderWidth);

        players = placePlayerDisplays(playerAmount);
        tradingMenu = buildTradingMenu();
        tradingMenu.setVisible(false);



        getChildren().addAll(background, players, tradingMenu);
    }

    public void setPlayerAmount(int playerAmount) {
        players = placePlayerDisplays(playerAmount);
    }

    public void startTrading() {
        players.setVisible(false);
        tradingMenu.setVisible(true);
    }

    private Pane placePlayerDisplays(int playerAmount) {
        Pane display = new Pane();
        display.setId("gameScene_playerDisplay_Players");

        final double playerBoxWidth = width * 0.225;
        final double playerBoxHeight = height * 0.40;

        Label header = Utilities.buildLabel("gameScene_playerDisplay_Header", "Spieler", Font.font(Main.textFont, FontWeight.BOLD, width / 15), TextAlignment.CENTER, Color.WHITE);

        if (!(playerAmount > 5)) {
            if (playerAmount == 5) {
                Pane playerOne = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb(33, 203, 85), "NAME", 2000);
                playerOne.setLayoutX(((width / 2) - (playerBoxWidth / 2)) - (playerBoxWidth + width * 0.05));
                playerOne.setLayoutY(width * 0.10);

                Pane playerTwo = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX((width / 2) - (playerBoxWidth / 2));
                playerTwo.setLayoutY(width * 0.10);

                Pane playerThree = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb( 255, 97, 0), "NAME", 898);
                playerThree.setLayoutX(((width / 2) - (playerBoxWidth / 2)) + playerBoxWidth + width * 0.05);
                playerThree.setLayoutY(width * 0.10);

                Pane playerFour = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb( 140, 82, 255), "NAME", 8928);
                playerFour.setLayoutX((width / 2 - playerBoxWidth) - width * 0.025);
                playerFour.setLayoutY(width * 0.10 + playerBoxHeight + height * 0.025);

                Pane playerFive = buildPlayerShowBox(playerBoxWidth, height * 0.40, Color.rgb( 56, 182, 255), "NAME", 89438);
                playerFive.setLayoutX((width / 2) + width * 0.025);
                playerFive.setLayoutY(width * 0.10 + playerBoxHeight + height * 0.025);

                display.getChildren().addAll(playerOne, playerTwo, playerThree, playerFour, playerFive);
            } else if (playerAmount == 4) {
                Pane playerOne = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb(33, 203, 85), "NAME", 2000);
                playerOne.setLayoutX((width / 2 - playerBoxWidth) - width * 0.0125);
                playerOne.setLayoutY(width * 0.10);

                Pane playerTwo = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX((width / 2) + width * 0.0125);
                playerTwo.setLayoutY(width * 0.10);

                Pane playerThree = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb( 255, 97, 0), "NAME", 898);
                playerThree.setLayoutX((width / 2 - playerBoxWidth) - width * 0.0125);
                playerThree.setLayoutY(width * 0.10 + playerBoxHeight + width * 0.025);

                Pane playerFour = buildPlayerShowBox(playerBoxWidth, playerBoxHeight, Color.rgb( 140, 82, 255), "NAME", 8928);
                playerFour.setLayoutX((width / 2) + width * 0.0125);
                playerFour.setLayoutY(width * 0.10 + playerBoxHeight +  width * 0.025);

                display.getChildren().addAll(playerOne, playerTwo, playerThree, playerFour);
            } else if (playerAmount == 3) {
                final double bigger = 1.3;
                Pane playerOne = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(33, 203, 85), "NAME", 2000);
                playerOne.setLayoutX(((width / 2) - ((playerBoxWidth * bigger) / 2)) - ((playerBoxWidth * bigger) + width * 0.025));
                playerOne.setLayoutY(height * 0.03);

                Pane playerTwo = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX((width / 2) - ((playerBoxWidth * bigger) / 2));
                playerTwo.setLayoutY(height * 0.03 + (playerBoxWidth * bigger));

                Pane playerThree = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb( 255, 97, 0), "NAME", 898);
                playerThree.setLayoutX(((width / 2) - ((playerBoxWidth * bigger) / 2)) + (playerBoxWidth * bigger) + width * 0.025);
                playerThree.setLayoutY(height * 0.03);

                display.getChildren().addAll(playerOne, playerTwo, playerThree);
            } else if (playerAmount == 2) {
                final double bigger = 1.7;
                Pane playerOne = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(33, 203, 85), "NAME", 2000);
                playerOne.setLayoutX(width / 2 - playerBoxWidth * bigger - width * 0.09);
                playerOne.setLayoutY(height * 0.20);

                Label vs = Utilities.buildLabel("gameScene_playerDisplay_playerBoxes_VS", "VS", Font.font(Main.textFont, FontWeight.BOLD, width / 10), TextAlignment.CENTER, Color.WHITE);
                Utilities.centeringChildInPane(vs, this);
                vs.setLayoutY(height * 0.375);

                Pane playerTwo = buildPlayerShowBox(playerBoxWidth * bigger, playerBoxHeight * bigger, Color.rgb(255, 49, 49), "NAME", 1730);
                playerTwo.setLayoutX(width / 2 + width * 0.09);
                playerTwo.setLayoutY(height * 0.20);


                display.getChildren().addAll(playerOne, vs, playerTwo);
            } else throw new RuntimeException("Too many/little Players");
        } else throw new RuntimeException("Too many Players");

        display.getChildren().add(header);

        Utilities.centeringChildInPane(header, this);

        return display;
    }


    private Pane buildPlayerShowBox(double width, double height, Color backgroundColor, String playerName, int playerKontoStand) {
        Pane playerShowBox = buildPlayer(width, height, backgroundColor, playerName, playerKontoStand);

        StackPane tradingButton = new StackPane();

        Rectangle tradingButtonBackground = Utilities.buildRectangle("gameScene_playerDisplay_tradingStartButton_Background", width * 0.36, height * 0.09, backgroundColor.desaturate(), true, Color.WHITE, width * 0.015);
        tradingButtonBackground.setArcHeight(width * 0.12);
        tradingButtonBackground.setArcWidth(width * 0.12);

        Label tradingButtonLabel = Utilities.buildLabel("gameScene_playerDisplay_tradingStartButton_Label", "Trading", Font.font(Main.textFont,  FontWeight.BOLD, width * 0.08), TextAlignment.CENTER, Color.WHITE);

        tradingButton.getChildren().setAll(tradingButtonBackground, tradingButtonLabel);
        tradingButton.setLayoutY(height * 0.40);
        tradingButton.setLayoutX(width * 0.32);

        tradingButton.setOnMouseClicked(event -> startTrading());

        Rectangle[] streets = buildStreetInventar(width, height);

        playerShowBox.getChildren().add(tradingButton);

        for (Rectangle street : streets) {playerShowBox.getChildren().addAll(street);}

        return playerShowBox;
    }

    private Pane buildPlayerTradeBox(double width, double height, Color backgroundColor, String playerName, int playerKontoStand) {
        Pane playerTradingBox = buildPlayer(width, height, backgroundColor, playerName, playerKontoStand);

        Rectangle[] streets = buildStreetInventar(width, height);

        for (Rectangle street : streets) {
            street.setOnMouseClicked(event -> {
                if (street.getId().endsWith("true")) {
                    street.setId(street.getId() + "C");
                    street.setStrokeWidth(street.getStrokeWidth() * 3);
                } else if (street.getId().endsWith("trueC")) {
                    street.setId(street.getId().replace("C", ""));
                    street.setStrokeWidth(street.getStrokeWidth() / 3);
                }
            });
            playerTradingBox.getChildren().addAll(street);
        }


        return playerTradingBox;
    }

    private Pane buildPlayer(double width, double height, Color backgroundColor, String playerName, int playerKontoStand) {
        Pane playerBox = new Pane();
        playerBox.setId("gameScene_playerDisplay_PlayerBox");
        playerBox.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("gameScene_playerDisplay_playerBox_Background", width, height, backgroundColor, true, Color.WHITE, borderWidth);

        Label headerName = Utilities.buildLabel("gameScene_playerDisplay_playerBox_NameHeader", playerName, Font.font(Main.textFont, FontWeight.BOLD, width / 8), TextAlignment.CENTER, Color.WHITE);
        Utilities.centeringChildInPane(headerName, playerBox);

        Line headerSeparatingline = Utilities.buildLine("gameScene_playerDisplay_playerBox_NameHeaderSeparatingLine", new Point2D(0, height * 0.15), new Point2D(width, height * 0.15), borderWidth, Color.WHITE);

        Label displayAccountBalance = Utilities.buildLabel("gameScene_playerDisplay_playerBox_DisplayAccountBalance", ("Kontostand: " + playerKontoStand), Font.font(Main.textFont, FontWeight.BOLD, width / 12), TextAlignment.CENTER, Color.WHITE, 0, height * 0.17);
        Utilities.centeringChildInPane(displayAccountBalance, playerBox);

        Line accountBalanceSeparatingline = Utilities.buildLine("gameScene_playerDisplay_playerBox_AccountBalanceSeparatingLine", new Point2D(0, height * 0.30), new Point2D(width, height * 0.30), borderWidth, Color.WHITE);

        playerBox.getChildren().addAll( background, headerName, headerSeparatingline, displayAccountBalance, accountBalanceSeparatingline);

        return playerBox;
    }

    private Rectangle[] buildStreetInventar(double w, double h) {
        Rectangle[] streets = new Rectangle[28];


        final double space = w * 0.04;
        final double width = w * 0.08;
        final double height = h * 0.10;
        final double startY = h * 0.40;
        final double startX = w * 0.04;
        for (int i = 0; i < streets.length; i++) {
            streets[i] = Utilities.buildRectangle("street:" + i + ", isOwner:", width, height, Color.LIGHTGRAY, true, Color.WHITE, borderWidth / 2);
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

    private Pane buildTradingMenu(/*Später werden hier die beiden Spieler übergeben*/) {

        //Farben der Buttons und des Pfeiles werden vom Spieler her gegeben, Rafa fragen

        Pane tradingMenu = new Pane();
        tradingMenu.setId("gameScene_playerDisplay_TradingMenu");

        Label header = Utilities.buildLabel("gameScene_playerDisplay_tradingMenu_Header", "Trading", Font.font(Main.textFont, FontWeight.BOLD, width / 15), TextAlignment.CENTER, Color.WHITE);
        Utilities.centeringChildInPane(header, this);

        Pane playerTradeBoxOne = buildPlayerTradeBox(width * 0.306, height * 0.6035, Color.rgb(255, 49, 49), "NAME", 2000);
        playerTradeBoxOne.setLayoutX(width / 2 - width * 0.306 - width * 0.18);
        playerTradeBoxOne.setLayoutY(height * 0.20);


        Pane playerTradeBoxTwo = buildPlayerTradeBox(width * 0.306, height * 0.6035, Color.rgb( 255, 97, 0), "NAME", 2000);
        playerTradeBoxTwo.setLayoutX(width / 2 + width * 0.18);
        playerTradeBoxTwo.setLayoutY(height * 0.20);

        HBox arrow = new HBox(-1);

        Color arrowColor = Color.RED;
        Polygon arrowLeftTop = Utilities.buildTriangle("gameScene_playerDisplay_tradingMenu_arrow_LeftTop", new Point2D(width * 0.005, 0), new Point2D(width * 0.065, height * 0.05), new Point2D(width * 0.065, -(height * 0.05)), arrowColor, null);
        Rectangle arrowMiddlePart = Utilities.buildRectangle("gameScene_playerDisplay_tradingMenu_arrow_MiddlePart", width * 0.23, height * 0.05, arrowColor, true, null, 0);
        Polygon arrowRightTop = Utilities.buildTriangle("gameScene_playerDisplay_tradingMenu_arrow_RightTop", new Point2D( width * 0.295 + width * 0.065 - width * 0.005, 0), new Point2D(width * 0.295, height * 0.05), new Point2D(width * 0.295, -(height * 0.05)), arrowColor, null);

        HBox.setMargin(arrowMiddlePart, new Insets(height * 0.025, 0, 0, 0));

        arrow.getChildren().addAll(arrowLeftTop, arrowMiddlePart, arrowRightTop);
        arrow.setLayoutX(width * 0.32 + width * 0.005);
        arrow.setLayoutY(height * 0.15 + (height * 0.6035) / 2);

        //Left Buttons
        StackPane leftConfirmButton = new StackPane();

        //Left Confirm
        Rectangle backgroundLeftConfirmButton = Utilities.buildRectangle("gameScene_playerDisplay_tradingMenu_leftConfirmButton_Background", (width * 0.306) * 0.35, height * 0.075, arrowColor, true, Color.WHITE, width * 0.005);
        backgroundLeftConfirmButton.setArcWidth(backgroundLeftConfirmButton.getHeight());
        backgroundLeftConfirmButton.setArcHeight(backgroundLeftConfirmButton.getHeight());

        Label leftConfirmButtonLabel = Utilities.buildLabel("gameScene_playerDisplay_tradingMenu_leftConfirmButton_Label", "Fertig", Font.font(Main.textFont, FontWeight.BOLD, width * 0.025), TextAlignment.CENTER, Color.WHITE);

        leftConfirmButton.getChildren().addAll(backgroundLeftConfirmButton, leftConfirmButtonLabel);
        leftConfirmButton.setLayoutX(width / 2 + width * 0.18);
        leftConfirmButton.setLayoutY(height * 0.825);

        //Left Cancel
        StackPane leftCancelButton = new StackPane();

        Rectangle backgroundLeftCancelButton = Utilities.buildRectangle("gameScene_playerDisplay_tradingMenu_leftCancelButton_Background", (width * 0.306) * 0.35, height * 0.075, arrowColor, true, Color.WHITE, width * 0.005);
        backgroundLeftCancelButton.setArcWidth(backgroundLeftCancelButton.getHeight());
        backgroundLeftCancelButton.setArcHeight(backgroundLeftCancelButton.getHeight());

        Label leftConfirmCancelLabel = Utilities.buildLabel("gameScene_playerDisplay_tradingMenu_leftCancelButton_Label", "Abbruch", Font.font(Main.textFont, FontWeight.BOLD, width * 0.025), TextAlignment.CENTER, Color.WHITE);

        leftCancelButton.getChildren().addAll(backgroundLeftCancelButton, leftConfirmCancelLabel);
        leftCancelButton.setLayoutX(width / 2 + width * 0.18 + width * 0.306 - (width * 0.306) * 0.35);
        leftCancelButton.setLayoutY(height * 0.825);

        //Right Buttons
        StackPane rightConfirmButton = new StackPane();

        //Right Confrim
        Rectangle backgroundRightConfirmButton = Utilities.buildRectangle("gameScene_playerDisplay_tradingMenu_rightConfirmButton_Background", (width * 0.306) * 0.35, height * 0.075, arrowColor, true, Color.WHITE, width * 0.005);
        backgroundRightConfirmButton.setArcWidth(backgroundRightConfirmButton.getHeight());
        backgroundRightConfirmButton.setArcHeight(backgroundRightConfirmButton.getHeight());

        Label rightConfirmButtonLabel = Utilities.buildLabel("gameScene_playerDisplay_tradingMenu_rightConfirmButton_Label", "Fertig", Font.font(Main.textFont, FontWeight.BOLD, width * 0.025), TextAlignment.CENTER, Color.WHITE);

        rightConfirmButton.getChildren().addAll(backgroundRightConfirmButton, rightConfirmButtonLabel);
        rightConfirmButton.setLayoutX(width / 2 - width * 0.306 - width * 0.18);
        rightConfirmButton.setLayoutY(height * 0.825);

        StackPane rightCancelButton = new StackPane();

        Rectangle backgroundRightCancelButton = Utilities.buildRectangle("gameScene_playerDisplay_tradingMenu_rightCancelButton_Background", (width * 0.306) * 0.35, height * 0.075, arrowColor, true, Color.WHITE, width * 0.005);
        backgroundRightCancelButton.setArcWidth(backgroundRightCancelButton.getHeight());
        backgroundRightCancelButton.setArcHeight(backgroundRightCancelButton.getHeight());

        Label rightConfirmCancelLabel = Utilities.buildLabel("gameScene_playerDisplay_tradingMenu_rightCancelButton_Label", "Abbruch", Font.font(Main.textFont, FontWeight.BOLD, width * 0.025), TextAlignment.CENTER, Color.WHITE);

        rightCancelButton.getChildren().addAll(backgroundRightCancelButton, rightConfirmCancelLabel);
        rightCancelButton.setLayoutX((width / 2 - width * 0.18) - (width * 0.306) * 0.35);
        rightCancelButton.setLayoutY(height * 0.825);

        tradingMenu.getChildren().addAll(header, playerTradeBoxOne, playerTradeBoxTwo, arrow, leftConfirmButton, leftCancelButton, rightCancelButton, rightConfirmButton);
        return tradingMenu;
    }

}
