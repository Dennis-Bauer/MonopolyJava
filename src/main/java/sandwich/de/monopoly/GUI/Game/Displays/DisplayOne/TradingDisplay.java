package sandwich.de.monopoly.GUI.Game.Displays.DisplayOne;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.Enums.ProgramColor;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Player;

import java.util.concurrent.atomic.AtomicInteger;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.buildPlus;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne.buildPlayer;
import static sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne.buildStreetInventar;

public class TradingDisplay extends Pane {

    private final GameDisplayControllerOne rootDisplay;
    private final double WIDTH, HEIGHT, BORDER_WIDTH;

    public TradingDisplay(double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_playerDisplay_TradingMenu");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        this.WIDTH = width;
        this.HEIGHT = height;
        this.BORDER_WIDTH = width * 0.005;
    }

    public void startTrading(/*später werden hier die beiden Spieler übergeben*/) {
        resetTrading();

        // ACHTUNG!!! Temporäre Variablen!!!
        int kontoPlayerOne = 207;
        int kontoPlayerTwo = 5463;

        AtomicInteger tradeOfferLeft = new AtomicInteger();
        AtomicInteger tradeOfferRight = new AtomicInteger();

        //Left
        Pane playerTradeBoxLeft = buildPlayerTradeBox(WIDTH * 0.306, HEIGHT * 0.6035, ProgramColor.PLAYER_TWO_BACKGROUND.getColor(), new Player("NAME", Figuren.AFFE, 0));
        playerTradeBoxLeft.setLayoutX(WIDTH * 0.014);
        playerTradeBoxLeft.setLayoutY(HEIGHT * 0.20);

        //Buttons
        createButtons((WIDTH * 0.306) * 0.35, HEIGHT * 0.075, WIDTH / 2 + WIDTH * 0.18, HEIGHT * 0.825, "left");

        //Left Money transfer
        Pane setLeftCash = new Pane();
        setLeftCash.setMaxSize((WIDTH * 0.306) * 0.36, (HEIGHT * 0.6035) * 0.10);

        StackPane addLeftCash = buildPlus("gameScene_playerDisplay_tradingMenu_plusCashLeft", (WIDTH * 0.306) * 0.015, (WIDTH * 0.306) * 0.10, 0, 0, null, ProgramColor.SYMBOLE_COLOR.getColor(), 0, ((WIDTH * 0.306) * 0.08) / 2);
        addLeftCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);

        Label leftCashLabel = buildLabel("gameScene_playerDisplay_tradingMenu_CashLabelLeft", Integer.toString(tradeOfferLeft.get()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.025), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(leftCashLabel, setLeftCash);
        leftCashLabel.setLayoutY((HEIGHT * 0.6035) * 0.0125);

        StackPane removeLeftCash = new StackPane();

        Rectangle removeLeftCashSymbole = buildRectangle("gameScene_playerDisplay_tradingMenu_minusCashLeft", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.015, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0);
        Rectangle removeLeftCashClickBox = buildRectangle("gameScene_playerDisplay_tradingMenu_clickBoxLeft", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.08, ProgramColor.SYMBOLE_COLOR.getColor(), false, null, 0);

        removeLeftCash.getChildren().addAll(removeLeftCashSymbole, removeLeftCashClickBox);
        removeLeftCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);
        removeLeftCash.setLayoutX((WIDTH * 0.306) * 0.32);

        setLeftCash.setLayoutX(WIDTH * 0.014 + (WIDTH * 0.306) * 0.32);
        setLeftCash.setLayoutY(HEIGHT * 0.20 + (HEIGHT * 0.6035) * 0.40);
        setLeftCash.getChildren().addAll(addLeftCash, leftCashLabel, removeLeftCash);


        //Right
        Pane playerTradeBoxRight = buildPlayerTradeBox(WIDTH * 0.306, HEIGHT * 0.6035, ProgramColor.PLAYER_THREE_BACKGROUND.getColor(), new Player("NAME", Figuren.AFFE, 0));
        playerTradeBoxRight.setLayoutX(WIDTH / 2 + WIDTH * 0.18);
        playerTradeBoxRight.setLayoutY(HEIGHT * 0.20);

        //Buttons
        createButtons((WIDTH * 0.306) * 0.35, HEIGHT * 0.075, WIDTH / 2 - WIDTH * 0.306 - WIDTH * 0.18, HEIGHT * 0.825, "right");

        //Right Money transfer
        Pane setRightCash = new Pane();
        setRightCash.setMaxSize((WIDTH * 0.306) * 0.36, (HEIGHT * 0.6035) * 0.10);

        StackPane addRightCash = buildPlus("gameScene_playerDisplay_tradingMenu_plusCashRight", (WIDTH * 0.306) * 0.015, (WIDTH * 0.306) * 0.10, 0, 0, null, ProgramColor.SYMBOLE_COLOR.getColor(), 0, ((WIDTH * 0.306) * 0.08) / 2);
        addRightCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);

        Label rightCashLabel = buildLabel("gameScene_playerDisplay_tradingMenu_CashLabelRight", Integer.toString(tradeOfferRight.get()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.025), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(rightCashLabel, setRightCash);
        rightCashLabel.setLayoutY((HEIGHT * 0.6035) * 0.0125);

        StackPane removeRightCash = new StackPane();

        Rectangle removeRightCashSymbole = buildRectangle("gameScene_playerDisplay_tradingMenu_minusCashRight", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.015, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0);
        Rectangle removeRightCashClickBox = buildRectangle("gameScene_playerDisplay_tradingMenu_clickBoxRight", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.08, ProgramColor.SYMBOLE_COLOR.getColor(), false, null, 0);


        removeRightCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);
        removeRightCash.setLayoutX((WIDTH * 0.306) * 0.32);
        removeRightCash.getChildren().addAll(removeRightCashSymbole, removeRightCashClickBox);

        setRightCash.setLayoutX(WIDTH / 2 + WIDTH * 0.18 + WIDTH * 0.014 + (WIDTH * 0.306) * 0.28);
        setRightCash.setLayoutY(HEIGHT * 0.20 + (HEIGHT * 0.6035) * 0.40);
        setRightCash.getChildren().addAll(addRightCash, rightCashLabel, removeRightCash);

        //Buttons
        addLeftCash.setOnMouseClicked(mouseEvent -> {
            if (tradeOfferLeft.get() + 50 < kontoPlayerOne)
                tradeOfferLeft.addAndGet(50);
            else
                tradeOfferLeft.set(kontoPlayerOne);

            leftCashLabel.setText(tradeOfferLeft.toString());
        });

        removeLeftCash.setOnMouseClicked(mouseEvent -> {
            if ((tradeOfferLeft.get() - 50) <= 0)
                tradeOfferLeft.set(0);
            else if (tradeOfferLeft.get() % 50 != 0)
                tradeOfferLeft.addAndGet(-(tradeOfferLeft.get() % 50));
            else
                tradeOfferLeft.addAndGet(-50);

            leftCashLabel.setText(tradeOfferLeft.toString());
        });

        addRightCash.setOnMouseClicked(mouseEvent -> {
            if (tradeOfferRight.get() + 50 < kontoPlayerTwo)
                tradeOfferRight.addAndGet(50);
            else
                tradeOfferRight.set(kontoPlayerTwo);

            rightCashLabel.setText(tradeOfferRight.toString());
        });

        removeRightCash.setOnMouseClicked(mouseEvent -> {
            if ((tradeOfferRight.get() - 50) <= 0)
                tradeOfferRight.set(0);
            else if (tradeOfferRight.get() % 50 != 0)
                tradeOfferRight.addAndGet(-(tradeOfferRight.get() % 50));
            else
                tradeOfferRight.addAndGet(-50);

            rightCashLabel.setText(tradeOfferRight.toString());
        });


        getChildren().addAll(playerTradeBoxLeft, setLeftCash, playerTradeBoxRight, setRightCash);
    }

    private void createButtons(double buttonWidth, double buttonHeight, double x, double y, String id) {

        //Confirm Button
        StackPane confirmButton = new StackPane();

        Rectangle backgroundConfirmButton = buildRectangle("gameScene_playerDisplay_tradingMenu_" + id + "ConfirmButton_Background", buttonWidth, buttonHeight, ProgramColor.NULL_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), BORDER_WIDTH);
        backgroundConfirmButton.setArcWidth(backgroundConfirmButton.getHeight());
        backgroundConfirmButton.setArcHeight(backgroundConfirmButton.getHeight());

        Label confirmButtonLabel = buildLabel("gameScene_playerDisplay_tradingMenu_" + id + "ConfirmButton_Label", "Fertig", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.025), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());

        confirmButton.getChildren().addAll(backgroundConfirmButton, confirmButtonLabel);
        confirmButton.setLayoutX(x);
        confirmButton.setLayoutY(y);

        //Cancel Button
        StackPane cancelButton = new StackPane();

        Rectangle backgroundCancelButton = buildRectangle("gameScene_playerDisplay_tradingMenu_" + id + "CancelButton_Background", buttonWidth, buttonHeight, ProgramColor.NULL_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), BORDER_WIDTH);
        backgroundCancelButton.setArcWidth(backgroundCancelButton.getHeight());
        backgroundCancelButton.setArcHeight(backgroundCancelButton.getHeight());

        Label cancelCancelLabel = buildLabel("gameScene_playerDisplay_tradingMenu_" + id + "CancelButton_Label", "Abbruch", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.024), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());

        cancelButton.getChildren().addAll(backgroundCancelButton, cancelCancelLabel);
        cancelButton.setLayoutX(x + WIDTH * 0.306 - buttonWidth);
        cancelButton.setLayoutY(y);

        cancelButton.setOnMouseClicked(event -> Main.getGameOperator().getDisplayControllerOne().displayPlayerDisplay());

        getChildren().addAll(confirmButton, cancelButton);
    }

    private void resetTrading() {
        getChildren().clear();

        //Farben der Buttons und des Pfeiles werden vom Spieler her gegeben, Rafa fragen

        //Header
        Label header = buildLabel("gameScene_playerDisplay_tradingMenu_Header", "Trading", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH / 15), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(header, this);

        //Arrow
        HBox arrow = new HBox(-1);

        Polygon arrowLeftTop = buildTriangle("gameScene_playerDisplay_tradingMenu_arrow_LeftTop", new Point2D(WIDTH * 0.005, 0), new Point2D(WIDTH * 0.065, HEIGHT * 0.05), new Point2D(WIDTH * 0.065, -(HEIGHT * 0.05)), ProgramColor.TRADING_ARROW.getColor(), null);
        Rectangle arrowMiddlePart = buildRectangle("gameScene_playerDisplay_tradingMenu_arrow_MiddlePart", WIDTH * 0.23, HEIGHT * 0.05, ProgramColor.TRADING_ARROW.getColor(), true, null, 0);
        Polygon arrowRightTop = buildTriangle("gameScene_playerDisplay_tradingMenu_arrow_RightTop", new Point2D( WIDTH * 0.295 + WIDTH * 0.065 - WIDTH * 0.005, 0), new Point2D(WIDTH * 0.295, HEIGHT * 0.05), new Point2D(WIDTH * 0.295, -(HEIGHT * 0.05)), ProgramColor.TRADING_ARROW.getColor(), null);

        HBox.setMargin(arrowMiddlePart, new Insets(HEIGHT * 0.025, 0, 0, 0));

        arrow.getChildren().addAll(arrowLeftTop, arrowMiddlePart, arrowRightTop);
        arrow.setLayoutX(WIDTH * 0.32 + WIDTH * 0.005);
        arrow.setLayoutY(HEIGHT * 0.15 + (HEIGHT * 0.6035) / 2);

        getChildren().addAll(header, arrow);
    }

    private Pane buildPlayerTradeBox(double width, double height, Color backgroundColor, Player player) {
        Pane playerTradingBox = buildPlayer(width, height, backgroundColor, player);

        Rectangle[] streets = buildStreetInventar(width, height, player);

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

}
