package de.sandwich.GUI.Game.Displays.DisplayOne;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildTriangle;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.WrongNodeException;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.Fields.Utilitie;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne;
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

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.buildPlus;
import static de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne.buildPlayer;
import static de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne.buildStreetInventar;

public class TradingDisplay extends Pane {

    //Default variables
    private final double PLAYER_BOX_WIDTH, PLAYER_BOX_HEIGHT;
    private final double WIDTH, HEIGHT, BORDER_WIDTH;

    private final GameDisplayControllerOne rootDisplay;

    private ArrayList<Field> chosenFields = new ArrayList<>();

    public TradingDisplay(double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_playerDisplay_TradingMenu");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        this.WIDTH = width;
        this.HEIGHT = height;
        this.BORDER_WIDTH = width * 0.005;
        this.PLAYER_BOX_WIDTH = WIDTH * 0.306;
        this.PLAYER_BOX_HEIGHT = HEIGHT * 0.6035;
    }


    public void startTrading(Player playerOne, Player playerTwo) {
        resetTrading();

        final int MONEY_STEPS = 100;
        final double BUTTON_Y = HEIGHT * 0.825;
        final double BUTTON_HEIGHT = HEIGHT * 0.075;

        AtomicInteger tradeOfferLeft = new AtomicInteger();
        AtomicInteger tradeOfferRight = new AtomicInteger();

        //Left
        Pane playerTradeBoxLeft = buildPlayerTradeBox(PLAYER_BOX_WIDTH, PLAYER_BOX_HEIGHT, ProgramColor.TRADING_PLAYER_ONE_BACKGROUND.getColor(), playerOne);
        playerTradeBoxLeft.setLayoutX(WIDTH * 0.014);
        playerTradeBoxLeft.setLayoutY(HEIGHT * 0.20);

        //Buttons
        createCancelButton((WIDTH * 0.306) * 0.35, BUTTON_HEIGHT, WIDTH / 2 + WIDTH * 0.18, BUTTON_Y, "left");

        //Left Money transfer
        Pane setLeftCash = new Pane();
        setLeftCash.setMaxSize((WIDTH * 0.306) * 0.36, (HEIGHT * 0.6035) * 0.10);

        StackPane addLeftCash = buildPlus("gameScene_playerDisplay_tradingMenu_plusCashLeft", (WIDTH * 0.306) * 0.015, (WIDTH * 0.306) * 0.10, 0, 0, null, ProgramColor.SYMBOLE_COLOR.getColor(), 0, ((WIDTH * 0.306) * 0.08) / 2);
        addLeftCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);

        Label leftCashLabel = buildLabel("gameScene_playerDisplay_tradingMenu_CashLabelLeft", Integer.toString(tradeOfferLeft.get()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.025), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(leftCashLabel, setLeftCash);
        leftCashLabel.setLayoutY((HEIGHT * 0.6035) * 0.0125);

        StackPane removeLeftCash = new StackPane(
            buildRectangle("gameScene_playerDisplay_tradingMenu_minusCashLeft", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.015, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0),
            buildRectangle("gameScene_playerDisplay_tradingMenu_clickBoxLeft", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.08, ProgramColor.SYMBOLE_COLOR.getColor(), false, null, 0)
        );

        removeLeftCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);
        removeLeftCash.setLayoutX((WIDTH * 0.306) * 0.32);

        setLeftCash.setLayoutX(WIDTH * 0.014 + (WIDTH * 0.306) * 0.32);
        setLeftCash.setLayoutY(HEIGHT * 0.20 + (HEIGHT * 0.6035) * 0.40);
        setLeftCash.getChildren().addAll(addLeftCash, leftCashLabel, removeLeftCash);


        //Right
        Pane playerTradeBoxRight = buildPlayerTradeBox(WIDTH * 0.306, HEIGHT * 0.6035, ProgramColor.TRADING_PLAYER_TWO_BACKGROUND.getColor(), playerTwo);
        playerTradeBoxRight.setLayoutX(WIDTH / 2 + WIDTH * 0.18);
        playerTradeBoxRight.setLayoutY(HEIGHT * 0.20);

        //Button
        createCancelButton((WIDTH * 0.306) * 0.35, BUTTON_HEIGHT, WIDTH / 2 - WIDTH * 0.306 - WIDTH * 0.18, BUTTON_Y, "right");

        //Right Money transfer
        Pane setRightCash = new Pane();
        setRightCash.setMaxSize((WIDTH * 0.306) * 0.36, (HEIGHT * 0.6035) * 0.10);

        StackPane addRightCash = buildPlus("gameScene_playerDisplay_tradingMenu_plusCashRight", (WIDTH * 0.306) * 0.015, (WIDTH * 0.306) * 0.10, 0, 0, null, ProgramColor.SYMBOLE_COLOR.getColor(), 0, ((WIDTH * 0.306) * 0.08) / 2);
        addRightCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);

        Label rightCashLabel = buildLabel("gameScene_playerDisplay_tradingMenu_CashLabelRight", Integer.toString(tradeOfferRight.get()), Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.025), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(rightCashLabel, setRightCash);
        rightCashLabel.setLayoutY((HEIGHT * 0.6035) * 0.0125);

        StackPane removeRightCash = new StackPane(
            buildRectangle("gameScene_playerDisplay_tradingMenu_minusCashRight", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.015, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0),
            buildRectangle("gameScene_playerDisplay_tradingMenu_clickBoxRight", (WIDTH * 0.306) * 0.08, (WIDTH * 0.306) * 0.08, ProgramColor.SYMBOLE_COLOR.getColor(), false, null, 0)
        );
     
        removeRightCash.setLayoutY(((WIDTH * 0.306) * 0.08) / 2);
        removeRightCash.setLayoutX((WIDTH * 0.306) * 0.32);

        setRightCash.setLayoutX(WIDTH / 2 + WIDTH * 0.18 + WIDTH * 0.014 + (WIDTH * 0.306) * 0.28);
        setRightCash.setLayoutY(HEIGHT * 0.20 + (HEIGHT * 0.6035) * 0.40);
        setRightCash.getChildren().addAll(addRightCash, rightCashLabel, removeRightCash);

        //Trade button
        StackPane tradeButton = new StackPane(buildLabel("gameScene_playerDisplay_tradingMenu_tradeButton_Text", "Traden", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.024), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()));

        Rectangle backgroundCancelButton = buildRectangle("gameScene_playerDisplay_tradingMenu_tradeButton_Background", WIDTH * 0.30, BUTTON_HEIGHT, ProgramColor.TRADING_BUTTON_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), BORDER_WIDTH);
        backgroundCancelButton.setArcWidth(backgroundCancelButton.getHeight());
        backgroundCancelButton.setArcHeight(backgroundCancelButton.getHeight());

        tradeButton.getChildren().add(backgroundCancelButton);
        backgroundCancelButton.toBack();
        tradeButton.setLayoutY(BUTTON_Y);
        tradeButton.layoutXProperty().bind(widthProperty().divide(2).subtract(tradeButton.widthProperty().divide(2)));

        //Buttons
        addLeftCash.setOnMouseClicked(mouseEvent -> {
            if (tradeOfferLeft.get() + MONEY_STEPS < playerOne.getBankAccount())
                tradeOfferLeft.addAndGet(MONEY_STEPS);
            else
                tradeOfferLeft.set(playerOne.getBankAccount());

            leftCashLabel.setText(tradeOfferLeft.toString());
        });

        removeLeftCash.setOnMouseClicked(mouseEvent -> {
            if ((tradeOfferLeft.get() - MONEY_STEPS) <= 0)
                tradeOfferLeft.set(0);
            else if (tradeOfferLeft.get() % MONEY_STEPS != 0)
                tradeOfferLeft.addAndGet(-(tradeOfferLeft.get() % MONEY_STEPS));
            else
                tradeOfferLeft.addAndGet(-MONEY_STEPS);

            leftCashLabel.setText(tradeOfferLeft.toString());
        });

        addRightCash.setOnMouseClicked(mouseEvent -> {
            if (tradeOfferRight.get() + MONEY_STEPS < playerTwo.getBankAccount())
                tradeOfferRight.addAndGet(MONEY_STEPS);
            else
                tradeOfferRight.set(playerTwo.getBankAccount());

            rightCashLabel.setText(tradeOfferRight.toString());
        });

        removeRightCash.setOnMouseClicked(mouseEvent -> {
            if ((tradeOfferRight.get() - MONEY_STEPS) <= 0)
                tradeOfferRight.set(0);
            else if (tradeOfferRight.get() % MONEY_STEPS != 0)
                tradeOfferRight.addAndGet(-(tradeOfferRight.get() % MONEY_STEPS));
            else
                tradeOfferRight.addAndGet(-MONEY_STEPS);

            rightCashLabel.setText(tradeOfferRight.toString());
        });

        tradeButton.setOnMouseClicked(mouseEvent -> {
            playerOne.transferMoneyToBankAccount(-(tradeOfferLeft.get()));
            playerTwo.transferMoneyToBankAccount(tradeOfferLeft.get());

            playerOne.transferMoneyToBankAccount(tradeOfferRight.get());
            playerTwo.transferMoneyToBankAccount(-(tradeOfferRight.get()));

            for (int i = 0; i < chosenFields.size(); i++) {
                if (chosenFields.get(i) instanceof Street f) {
                    if (f.getOwner() == playerOne) {
                        f.setOwner(playerTwo);
                    } else {
                        f.setOwner(playerOne);
                    }
                } else if (chosenFields.get(i) instanceof Station f) {
                    if (f.getOwner() == playerOne) {
                        f.setOwner(playerTwo);
                    } else {
                        f.setOwner(playerOne);
                    }
                } else if (chosenFields.get(i) instanceof Utilitie f) {
                    if (f.getOwner() == playerOne) {
                        f.setOwner(playerTwo);
                    } else {
                        f.setOwner(playerOne);
                    }
                } else throw new WrongNodeException("chosenFields");
            }

            rootDisplay.displayPlayers(Main.getGameOperator().getPlayers());

        });

        getChildren().addAll(playerTradeBoxLeft, setLeftCash, playerTradeBoxRight, setRightCash, tradeButton);
    }

    private void createCancelButton(double buttonWidth, double buttonHeight, double x, double y, String id) {

        //Cancel Button
        StackPane cancelButton = new StackPane();

        Rectangle backgroundCancelButton = buildRectangle("gameScene_playerDisplay_tradingMenu_" + id + "CancelButton_Background", buttonWidth, buttonHeight, ProgramColor.TRADING_BUTTON_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), BORDER_WIDTH);
        backgroundCancelButton.setArcWidth(backgroundCancelButton.getHeight());
        backgroundCancelButton.setArcHeight(backgroundCancelButton.getHeight());

        Label cancelCancelLabel = buildLabel("gameScene_playerDisplay_tradingMenu_" + id + "CancelButton_Label", "Abbruch", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.024), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());

        cancelButton.getChildren().addAll(backgroundCancelButton, cancelCancelLabel);
        cancelButton.layoutXProperty().bind(widthProperty().multiply(0.153).subtract(cancelButton.widthProperty().divide(2)).add(x));
        cancelButton.setLayoutY(y);

        cancelButton.setOnMouseClicked(event -> rootDisplay.displayPlayerDisplay());

        getChildren().add(cancelButton);
    }

    private void resetTrading() {
        getChildren().clear();

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

        Main.getGameOperator();
        HashMap<Integer, Field> fields = Game.getFields();

        for (Rectangle street : streets) {

            int fieldNumber = Integer.parseInt(street.getId().substring(12, 14));
            
            street.setOnMouseClicked(event -> {
                if (street.getId().endsWith("true")) {
                    street.setId(street.getId() + "C");
                    street.setStrokeWidth(street.getStrokeWidth() * 3);

                    if (fields.get(fieldNumber) instanceof Street || fields.get(fieldNumber) instanceof Station || fields.get(fieldNumber) instanceof Utilitie) 
                        chosenFields.add(fields.get(fieldNumber));
                    else throw new WrongNodeException("Fields");

                } else if (street.getId().endsWith("trueC")) {
                    street.setId(street.getId().replace("C", ""));
                    street.setStrokeWidth(street.getStrokeWidth() / 3);

                    if (fields.get(fieldNumber) instanceof Street || fields.get(fieldNumber) instanceof Station || fields.get(fieldNumber) instanceof Utilitie) 
                        chosenFields.remove(fields.get(fieldNumber));
                    else throw new WrongNodeException("Fields");

                }
            });
            playerTradingBox.getChildren().addAll(street);
        }

        return playerTradingBox;
    }

}
