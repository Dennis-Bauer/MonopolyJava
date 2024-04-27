package de.sandwich;

import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import java.util.ArrayList;
import java.util.HashMap;

import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;
import de.sandwich.Enums.ChanceCards;
import de.sandwich.Enums.CommunityCards;
import de.sandwich.Enums.CornerTyp;
import de.sandwich.Enums.ExtraFields;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.PlayerNotFoundExceptions;
import de.sandwich.Exceptions.ToManyPlayersExceptions;
import de.sandwich.Fields.Corner;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.GetCard;
import de.sandwich.Fields.PayExtra;
import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.GUI.Game.GameBoard;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerTwo;
import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import de.sandwich.GUI.Game.Displays.DisplayTwo.DiceDisplay;

import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

public class Game {

    //Players
    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;
    private final ArrayList<Player> playerList = new ArrayList<>();

    //Displays
    private final GameDisplayControllerOne displayControllerOne;
    private final GameDisplayControllerTwo displayControllerTwo;
    private final MiddleGameDisplayController middleDisplayController;

    //Gameboard
    private static final HashMap<Integer, Field> FIELDS = createFields();
    private final GameBoard gameBoard = new GameBoard(Main.WINDOW_HEIGHT * 0.98, FIELDS);
    private final StackPane turnFinishButton = new StackPane();
    private final Label errorMessage = buildLabel("gameBoard_ErrorMessage", "NULL", Font.font( Main.TEXT_FONT, FontWeight.BOLD, Main.WINDOW_HEIGHT * 0.015), TextAlignment.CENTER, ProgramColor.ERROR_MESSAGES.getColor(), 0, 0);
    private final Scene gameScene;
    private final Pane root;

    //Game variables
    private int freeParkingMoney = 999;
    private int playerInGame = 0;

    private Player turnPlayer;
    private Player nextPlayer;


    public Game(Player[] players) {

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, "Game Controller, Konstruktor:");
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        //Randomize Cards
        GetCard.randomizeCards();

        //Displays
        displayControllerOne = new GameDisplayControllerOne((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.60);
        displayControllerTwo = new GameDisplayControllerTwo((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.38);
        middleDisplayController = new MiddleGameDisplayController(Main.WINDOW_HEIGHT * 0.40, Main.WINDOW_HEIGHT * 0.25, Main.WINDOW_HEIGHT * 0.18);   

        VBox sideDisplays = new VBox(Main.WINDOW_HEIGHT * 0.02);

        sideDisplays.getChildren().addAll(
            displayControllerOne,
            displayControllerTwo
        );
        sideDisplays.setLayoutX(Main.WINDOW_HEIGHT + (((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 2) - ((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1) / 2));
        sideDisplays.setLayoutY(0);

        errorMessage.layoutXProperty().bind(gameBoard.widthProperty().subtract(gameBoard.widthProperty().subtract(gameBoard.widthProperty().divide(1.4)).divide(2)).divide(2).add(gameBoard.widthProperty().subtract(gameBoard.widthProperty().divide(1.4)).divide(4)).subtract(errorMessage.widthProperty().divide(2)));
        errorMessage.setLayoutY(Main.WINDOW_HEIGHT * 0.81);
        errorMessage.setVisible(false);

        turnFinishButton.getChildren().addAll(
            buildRectangle("gameScene_turnFinishButton_Background", Main.WINDOW_WIDTH * 0.18, Main.WINDOW_HEIGHT * 0.08, ProgramColor.SELECT_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), Main.WINDOW_WIDTH * 0.005),
            buildLabel("gameScene_turnFinishButton_Text", "Beende Zug", Font.font(Main.TEXT_FONT, FontWeight.BOLD ,Main.WINDOW_WIDTH * 0.02), null, ProgramColor.TEXT_COLOR.getColor())
        );
        turnFinishButton.layoutXProperty().bind(gameBoard.widthProperty().subtract(gameBoard.widthProperty().subtract(gameBoard.widthProperty().divide(1.4)).divide(2)).divide(2).add(gameBoard.widthProperty().subtract(gameBoard.widthProperty().divide(1.4)).divide(4)).subtract(turnFinishButton.widthProperty().divide(2)));
        turnFinishButton.setLayoutY(Main.WINDOW_HEIGHT - Main.WINDOW_HEIGHT * 0.285);

        turnFinishButton.setOnMouseClicked(mouseEvent -> {
            turnFinish();
        });

        root = new Pane(
            buildRectangle("gameScene_Background", Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, ProgramColor.BACKGROUND.getColor(), true, null, 0, 0, 0),
            gameBoard,
            sideDisplays,
            middleDisplayController,
            errorMessage,
            turnFinishButton
        );

        gameScene = new Scene(root);


        for (int i = 0, j = 1; i != 5; i++) {
            if (players[i] != null) {
                playerInGame++;

                assert false;
                playerList.add(players[i]);

                switch (j) {
                    case 1 -> playerOne = players[i];
                    case 2 -> playerTwo = players[i];
                    case 3 -> playerThree = players[i];
                    case 4 -> playerFour = players[i];
                    case 5 -> playerFive = players[i];
                }
                j++;

            }
        }
        turnPlayer = playerOne;

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "Game Operator wurde erstellt!");
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerInGame + " Player Objects: ");

            for (int i = 0; i != 5; i++) {
                switch (i) {
                    case 0 -> {
                        if (playerOne != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerOne: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerOne.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 1 -> {
                        if (playerTwo != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerTwo: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerTwo.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 2 -> {
                        if (playerThree != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerThree: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerThree.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 3 -> {
                        if (playerFour != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFour: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerFour.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    case 4 -> {
                        if (playerFive != null) {
                            consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFive: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, playerFive.toString());
                        } else
                            consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    default -> throw new ToManyPlayersExceptions();
                }
                System.out.println();
            }
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        //Zeigt die Spieler an
        gameBoard.addPlayers(playerList);
        displayControllerOne.displayPlayers(playerList);
        displayControllerTwo.displayDice();
        turnFinishButton.setVisible(false);

        //Zeigt das Game Board an
        Main.getPrimaryStage().setScene(gameScene);
    }

    //Starts the Animation and controls the postion from the player.
    public void playerRolledDice(int diceOne, int diceTwo) {

        DiceDisplay.lockeDices();
        setNextPlayer();

        if (turnPlayer.isInJail()) {
            if (diceOne == diceTwo) {
                turnPlayer.removePlayerFromJail();

                try { 
                    gameBoard.movePlayer(turnPlayer, diceOne + diceTwo); 
                    turnPlayer.moveFieldPostion(diceOne + diceTwo);
                }catch (NullPointerException notUse) {

                    gameBoard.getPlayerMoveTransition().setOnFinished(actionEvent -> {
                        try {gameBoard.movePlayer(turnPlayer, diceOne + diceTwo);} catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }
                        turnPlayer.moveFieldPostion(diceOne + diceTwo);
                    });

                } catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }
            } else {
                turnPlayer.removeOnInJailRemain();
                middleDisplayController.displayInJailDisplay(turnPlayer, false);
            }

        } else {
            try { gameBoard.movePlayer(turnPlayer, diceOne + diceTwo); } catch (PlayerNotFoundExceptions ignored) {}
            turnPlayer.moveFieldPostion(diceOne + diceTwo);

            if (diceOne == diceTwo)
                nextPlayer = turnPlayer;

            if (Main.CONSOLE_OUT_PUT) {
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
            }
        }
    }

    //Detects the field where the player is standing on, and then carries out the function of the field.
    public void playerHasMoved() {
        displayControllerTwo.displayPlayerAction();

        if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Street street) { //Is the player on a Street
            if (!street.isOwned()) {
                if (street.getSalePrice() <= turnPlayer.getBankAccount()) {
                    middleDisplayController.displayBuyStreetDisplay(street);
                } else {
                    setVisibilityTurnFinButton(true);
                }
            } else {
                middleDisplayController.displayPayDisplay(buildLongText("Du schuldest den Spieler", street.getOwner().getName() + " " + (street.getPlayerRent() + "€")), -(street.getPlayerRent()));
            }
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Corner corner) { //Is the player on a Corner
            switch (corner.getTyp()) {
                case FREE_PARKING -> {
                    turnPlayer.transferMoneyToBankAccount(freeParkingMoney);
                    setVisibilityTurnFinButton(true);
                }
                case GO_TO_JAIL -> {
                    try {gameBoard.setPlayerInJail(turnPlayer);} catch(Exception e) {System.out.println(e);}
                    setVisibilityTurnFinButton(true);
                }
                default -> setVisibilityTurnFinButton(true);
            }
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof PayExtra payField) { //Is the player on a pay extra field
            middleDisplayController.displayPayDisplay(payField.getTyp().getMessage(), payField.getPrice());
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Station station) {

            if (!station.isOwned()) {
                if (station.getPrice() <= turnPlayer.getBankAccount()) {
                    middleDisplayController.displayBuyStationDisplay(station);
                } else {
                    setVisibilityTurnFinButton(true);
                }
            } else {
                middleDisplayController.displayPayDisplay(buildLongText("Du schuldest den Spieler", station.getOwner().getName() + " " + (station.getRent() + "€")), -(station.getRent()));
            }

        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof GetCard getCardField) {
            if (getCardField.isFieldChance()) {
                //Field ist eine Chance Card
                ChanceCards card = GetCard.getChanceCard();

                //Spieler bewegt sich zu einem Feld das nicht der Start ist
                if (card.getMoneyTransfer() <= -101 && card.getMoneyTransfer() >= -105) {
                    switch (card.getMoneyTransfer()) {
                        case -101 -> {

                        }
                        case -102 -> {

                        }
                        case -103 -> {

                        }
                        case -104 -> {

                        }
                        case -105 -> {

                        }
                    }
                } else if (card.getMoneyTransfer() <= -1 && card.getMoneyTransfer() >= -6) {
                    switch (card.getMoneyTransfer()) {
                        case -1 -> {
                            //Überprüfen
                            //Give player a free jail card
                            turnPlayer.addFreeJailCard();
                            middleDisplayController.displayInfoDisplay(card.getMessage());
                        }
                        case -2 -> {
                            //Set player to jail
                            try {gameBoard.setPlayerInJail(turnPlayer);} catch(Exception e) {System.out.println(e);}
                                                        
                            middleDisplayController.displayInfoDisplay(card.getMessage());
                        }
                        case -3 -> {
                            //Move 3 steps back
                        }
                        case -4 -> {
                            //Got to start
                            if (turnPlayer.getFieldPostion() != 0) {
                                try { gameBoard.movePlayer(turnPlayer, 40 - turnPlayer.getFieldPostion()); } catch (PlayerNotFoundExceptions ignored) {}
                                turnPlayer.moveFieldPostion(40 - turnPlayer.getFieldPostion());

                                if (Main.CONSOLE_OUT_PUT) {
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                }
                            }

                            middleDisplayController.displayInfoDisplay(card.getMessage());
                        }
                        case -5 -> {
                            //Go to next Station
                        }
                        case -6 -> {
                            //Player pay for each hous 500 and for each hotel 2000
                            int payment = 0;
                            for (int i = 0; i < FIELDS.size(); i++) {
                                if (FIELDS.get(i) instanceof Street s) {
                                    if (s.getOwner() == turnPlayer) {
                                        if (s.getHouseNumber() == -1) {
                                            payment = payment + 2000;
                                        } else {
                                            payment = payment + (s.getHouseNumber() * 500);
                                        }
                                    }
                                }
                            }

                            if (payment != 0) {
                                middleDisplayController.displayPayDisplay(card.getMessage(), -payment);
                            } else {
                                middleDisplayController.displayInfoDisplay(card.getMessage());;
                            }
                        }
                    }
                }

            } else {
                //Field ist eine Comunitty
                CommunityCards card = GetCard.getCommunityCard();

                if (card.getMoneyTransfer() <= -1 && card.getMoneyTransfer() >= -5) {

                    switch (card.getMoneyTransfer()) {
                        case -1 -> {
                            //Überprüfen
                            //Give player a free jail card
                            turnPlayer.addFreeJailCard();
                            middleDisplayController.displayInfoDisplay(card.getMessage());
                        }
                        case -2 -> {
                            //Got to start
                            if (turnPlayer.getFieldPostion() != 0) {
                                try { gameBoard.movePlayer(turnPlayer, 40 - turnPlayer.getFieldPostion()); } catch (PlayerNotFoundExceptions ignored) {}
                                turnPlayer.moveFieldPostion(40 - turnPlayer.getFieldPostion());

                                if (Main.CONSOLE_OUT_PUT) {
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                }
                            }

                            middleDisplayController.displayInfoDisplay(card.getMessage());
                        }
                        case -3 -> {
                            //Set player to jail
                            try {gameBoard.setPlayerInJail(turnPlayer);} catch(Exception e) {System.out.println(e);}
                            
                            middleDisplayController.displayInfoDisplay(card.getMessage());
                        }
                        case -4 -> {
                            //Get from every player 1000
                            turnPlayer.transferMoneyToBankAccount(playerInGame * 1000);

                            for (Player p : playerList) {
                                p.transferMoneyToBankAccount(-1000);
                            }

                            middleDisplayController.displayInfoDisplay(card.getMessage());
                        }
                        case -5 -> {
                            //Player pay for each hous 800 and for each hotel 2300
                            int payment = 0;
                            for (int i = 0; i < FIELDS.size(); i++) {
                                if (FIELDS.get(i) instanceof Street s) {
                                    if (s.getOwner() == turnPlayer) {
                                        if (s.getHouseNumber() == -1) {
                                            payment = payment + 2300;
                                        } else {
                                            payment = payment + (s.getHouseNumber() * 800);
                                        }
                                    }
                                }
                            }

                            if (payment != 0) {
                                middleDisplayController.displayPayDisplay(card.getMessage(), -payment);
                            } else {
                                middleDisplayController.displayInfoDisplay(card.getMessage());;
                            }

                        }
                    }

                } else {
                    middleDisplayController.displayPayDisplay(card.getMessage(), card.getMoneyTransfer());
                }

            }
        }
         {
            setVisibilityTurnFinButton(true);
        }
    }

    //Starts the new Round for a new Player
    public void turnFinish() {

        turnPlayer = nextPlayer;
        nextPlayer = null;

        DiceDisplay.unlockDices();

        middleDisplayController.removeDisplay();
        setVisibilityTurnFinButton(false);
        displayControllerOne.displayPlayers(playerList);
        
        displayControllerTwo.displayDice();

        if (!(turnPlayer.getBankAccount() < 0)) {
            if (turnPlayer.isInJail()) {
                middleDisplayController.displayInJailDisplay(turnPlayer, true);
            }
        } else {
            DiceDisplay.lockeDices();

            middleDisplayController.displayPlayerIsInMinusDisplay();
        }

    }

    public void transferMoney(int money) {
        if (!(money <= 0)) {

            if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Street street) {
                if (street.isOwned())
                    street.getOwner().transferMoneyToBankAccount(money);
            } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof PayExtra) {
                freeParkingMoney = freeParkingMoney + money;

                if (Main.CONSOLE_OUT_PUT) {
                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Auf das frei-parken Konto wurden ");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, money + "€");
                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, " hinzugefügt!");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Damit sind jetzt ");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, freeParkingMoney + "€");
                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, " auf frei-parken!");
                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                }
            }

            setVisibilityTurnFinButton(true);
        }
        else throw new IllegalArgumentException("No negative amounts can be transfer!");
    }

    public void displayErrorMessage(String message) {
        if (errorMessage.getText().equals("NULL")) {
            errorMessage.setText(message);
            errorMessage.setVisible(true);
            errorMessage.toFront();

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), errorMessage);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            ScaleTransition waitTransition = new ScaleTransition(Duration.seconds(1), errorMessage);
            waitTransition.setCycleCount(3);
            waitTransition.setByX(Main.WINDOW_WIDTH * 0.00003);
            waitTransition.setByY(Main.WINDOW_WIDTH * 0.00003);
            waitTransition.setAutoReverse(true);
            waitTransition.play();


            waitTransition.setOnFinished(actionEvent -> fadeTransition.play());

            fadeTransition.setOnFinished(actionEvent -> {
                if (fadeTransition.getToValue() == 1) {
                    waitTransition.play();

                    fadeTransition.setToValue(0);
                    fadeTransition.setFromValue(1);
                } else {
                    errorMessage.setVisible(false);
                    errorMessage.setText("NULL");
                }
            });
        }
    }

    //Getter
    public Player getTurnPlayer() {
        return turnPlayer;
    }

    @SuppressWarnings("exports")
    public GameDisplayControllerOne getDisplayControllerOne() {
        return displayControllerOne;
    }

    @SuppressWarnings("exports")
    public GameDisplayControllerTwo getDisplayControllerTwo() {
        return displayControllerTwo;
    }

    @SuppressWarnings("exports")
    public MiddleGameDisplayController getMiddleDisplayController() {
        return middleDisplayController;
    }

    @SuppressWarnings("exports")
    public GameBoard getBoard() {
        return gameBoard;
    }

    public ArrayList<Player> getPlayers() {
        ArrayList<Player> ps = new ArrayList<>();

        if (playerOne != null)
            ps.add(playerOne);
        if (playerTwo != null)
            ps.add(playerTwo);
        if (playerThree != null)
            ps.add(playerThree);
        if (playerFour != null)
            ps.add(playerFour);
        if (playerFive != null)
            ps.add(playerFive);

        return ps;
    }

    @SuppressWarnings("exports")
    public static HashMap<Integer, Field> getFields() {
        return FIELDS;
    }

    public void setVisibilityTurnFinButton(boolean visible) {
        double animationLenght = 1;
        if (visible) {
            if (!turnFinishButton.isVisible()) {
                System.out.println("Der Finish button wird visible");
                turnFinishButton.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(animationLenght), turnFinishButton);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);

                fadeTransition.play();
            }
        } else {
            if (turnFinishButton.isVisible()) {
                System.out.println("Der Finish button wird nicht mehr Visible");
                turnFinishButton.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(animationLenght), turnFinishButton);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);

                fadeTransition.play();

                fadeTransition.setOnFinished(event -> turnFinishButton.setVisible(false));
            }

        }
        turnFinishButton.setVisible(visible);
    }

    private void setNextPlayer() {

        

        switch (turnPlayer.getOrderNumber() + 1) {
            case 1 -> nextPlayer = playerTwo;
            case 2 -> nextPlayer = playerThree;
            case 3 -> nextPlayer = playerFour;
            case 4 -> nextPlayer = playerFive;
            case 5 -> nextPlayer = playerOne;
            default -> nextPlayer = playerOne;
        }

        if (nextPlayer == null)
            nextPlayer = playerOne;

    } 

    //Private
    private static HashMap<Integer, Field> createFields() {
        HashMap<Integer, Field> s = new HashMap<>();

        s.put(0, new Corner(CornerTyp.START, 0));
        s.put(1, new Street("Turmstrasse", 1200 ,80, new int[]{400, 1200, 3600, 6400}, 9000, 1000, 1000, Color.rgb(112, 40, 0), 0, 1));
        s.put(2, new GetCard(2));
        s.put(3, new Street("Badstrasse", 1200 ,40, new int[]{200, 600, 1800, 3200}, 5000, 1000, 1000,  Color.rgb(112, 40, 0), 0, 3));
        s.put(4, new PayExtra(200, ExtraFields.SPOTIFY_PREMIUM, 4));
        s.put(5, new Station("Südbahnhof", 5));
        s.put(6, new Street("Chausseestrasse", 2000 ,120, new int[]{600, 1800, 5400, 8000}, 11000, 1000, 1000, Color.AQUA, 1, 6));
        s.put(7, new GetCard(GetCard.ChanceColors.GREEN, 7));
        s.put(8, new Street("Elisenstrasse", 2000 ,120, new int[]{600, 1800, 5400, 8000}, 11000, 1000, 1000, Color.AQUA, 1, 8));
        s.put(9, new Street("Poststrasse", 2400 ,160, new int[]{800, 2000, 6000, 9000}, 12000, 1000, 1000, Color.AQUA, 1, 9));
        s.put(10, new Corner(CornerTyp.JAIL, 10));
        s.put(11, new Street("Seestrasse", 2800 ,200, new int[]{1000, 3000, 9000, 12500}, 15000, 2000, 2000, Color.PURPLE, 2, 11));
        s.put(12, new PayExtra(200, ExtraFields.HESSLER_SCHULDEN, 12));
        s.put(13, new Street("Hafenstrasse", 2800 ,200, new int[]{1000, 3000, 9000, 12500}, 15000, 2000, 2000, Color.PURPLE, 2, 13));
        s.put(14, new Street("Neue Strasse", 3200 ,240, new int[]{1200, 3600, 10000, 14000}, 18000, 2000, 2000, Color.PURPLE, 2, 14));
        s.put(15, new Station("Westbahnhof", 15));
        s.put(16, new Street("Müncher Strasse", 3600 ,280, new int[]{1400, 4000, 11000, 15000}, 19000, 2000, 2000, Color.ORANGE, 3, 16));
        s.put(17, new Street("Wiener Strasse", 3600 ,280, new int[]{1400, 4000, 11000, 15000}, 19000, 2000, 2000, Color.ORANGE, 3, 17));
        s.put(18, new GetCard(18));
        s.put(19, new Street("Berliner Strasse", 4000 ,320, new int[]{1600, 4400, 12000, 16000}, 20000, 2000, 2000, Color.ORANGE, 3, 19));
        s.put(20, new Corner(CornerTyp.FREE_PARKING, 20));
        s.put(21, new Street("Theaterstrasse", 4400 ,360, new int[]{1800, 5000, 14000, 17500}, 21000, 3000, 3000, Color.RED, 4, 21));
        s.put(22, new Street("Museumstrasse", 4400 ,360, new int[]{1800, 5000, 14000, 17500}, 21000, 3000, 3000, Color.RED, 4, 22));
        s.put(23, new GetCard(GetCard.ChanceColors.BLUE, 23));
        s.put(24, new Street("Opernplatz", 4800 ,400, new int[]{2000, 6000, 15000, 18500}, 22000, 3000, 3000, Color.RED, 4, 24));
        s.put(25, new Station("Nordbahnhof", 25));
        s.put(26, new Street("Lessingstrasse", 5200 ,480, new int[]{2200, 6600, 16000, 19500}, 23000, 3000, 3000, Color.YELLOW, 5, 26));
        s.put(27, new Street("Schillerstrasse", 5200 ,480, new int[]{2200, 6600, 16000, 19500}, 23000, 3000, 3000, Color.YELLOW, 5, 27));
        s.put(28, new PayExtra(200, ExtraFields.NAME_THREE, 28));
        s.put(29, new Street("Goethestrasse", 5600 ,580, new int[]{2400, 7200, 17000, 20500}, 24000, 3000, 3000, Color.YELLOW, 5, 29));
        s.put(30, new Corner(CornerTyp.GO_TO_JAIL, 30));
        s.put(31, new Street("Rathausplatz", 6000 ,520, new int[]{2600, 7800, 18000, 22000}, 25500, 4000, 4000, Color.LIME, 6, 31));
        s.put(32, new Street("Hauptstrasse", 6000 ,520, new int[]{2600, 7800, 18000, 22000}, 25500, 4000, 4000, Color.LIME, 6, 32));
        s.put(33, new GetCard(33));
        s.put(34, new Street("Bahnhofstrasse", 6400 ,560, new int[]{3000, 9000, 20000, 24000}, 28000, 4000, 4000, Color.LIME, 6, 34));
        s.put(35, new Station("Hauptbahnhof", 35));
        s.put(36, new GetCard(GetCard.ChanceColors.RED, 36));
        s.put(37, new Street("Parkstrasse", 7000 ,700, new int[]{3500, 10000, 22000, 26000}, 30000, 4000, 4000, Color.DARKBLUE, 7, 37));
        s.put(38, new PayExtra(200, ExtraFields.NAME_FOUR, 38));
        s.put(39, new Street("Schlossallee", 8000 ,1000, new int[]{4000, 12000, 28000, 34000}, 40000, 4000, 4000, Color.DARKBLUE, 7, 39));

        return s;
    }


}

/* @// TODO: 21.03.2024
 * Hier werden die Sachen aufglisstet die das Spiel am Technischen noch können muss, nichts Grafisches.
 * 1. Nach 3 mal Pash ins Gefängnis
 * 2. Das Gameboard an sich ein biischen kleiner machen als die höhe des Fensters (Das heißt alle werte in dem einfach die Höhe anstat ein wert für die Gameboardgröße genommen wird ÄNDERN!!!)
 * ... (Hier stehen nur die Sachen die ich denke ich vergesse)
 */