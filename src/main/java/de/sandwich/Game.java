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

    //Game Scene

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

        //Displays

        displayControllerOne = new GameDisplayControllerOne((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.60);
        displayControllerTwo = new GameDisplayControllerTwo((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.38);
        middleDisplayController = new MiddleGameDisplayController(Main.WINDOW_HEIGHT * 0.40, Main.WINDOW_HEIGHT * 0.20, Main.WINDOW_HEIGHT * 0.18);

        VBox sideDisplays = new VBox(Main.WINDOW_HEIGHT * 0.02);

        sideDisplays.getChildren().addAll(
            displayControllerOne,
            displayControllerTwo
        );
        sideDisplays.setLayoutX(Main.WINDOW_HEIGHT + (((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 2) - ((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1) / 2));
        sideDisplays.setLayoutY(0);

        errorMessage.layoutXProperty().bind(Main.getPrimaryStage().heightProperty().divide(2).subtract(errorMessage.widthProperty().divide(2)));
        errorMessage.setLayoutY(Main.WINDOW_HEIGHT * 0.81);
        errorMessage.setVisible(false);

        turnFinishButton.getChildren().addAll(
            buildRectangle("gameScene_turnFinishButton_Background", Main.WINDOW_WIDTH * 0.18, Main.WINDOW_HEIGHT * 0.08, ProgramColor.SELECT_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), Main.WINDOW_WIDTH * 0.005),
            buildLabel("gameScene_turnFinishButton_Text", "Beende Zug", Font.font(Main.TEXT_FONT, FontWeight.BOLD ,Main.WINDOW_WIDTH * 0.02), null, ProgramColor.TEXT_COLOR.getColor())
        );
        turnFinishButton.setLayoutX(Main.WINDOW_HEIGHT * 0.49 - Main.WINDOW_WIDTH * 0.09);
        turnFinishButton.setLayoutY(Main.WINDOW_HEIGHT - Main.WINDOW_HEIGHT * 0.26);

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
                
                try { gameBoard.movePlayer(turnPlayer, diceOne + diceTwo); } catch (PlayerNotFoundExceptions ignored) {}
                turnPlayer.moveFieldPostion(diceOne + diceTwo);
                
                turnPlayer.setInJail(false);
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
                    middleDisplayController.displayBuyStreet(street);
                } else {
                    setVisibilityTurnFinButton(true);
                }
            } else {
                middleDisplayController.displayPayDisplay(buildLongText("Du schuldest den Spieler", street.getOwner().getName() + " " + (street.getPlayerRent() + "€")), street.getPlayerRent());
            }
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Corner corner) { //Is the player on a Corner
            switch (corner.getTyp()) {
                case FREE_PARKING -> {
                    turnPlayer.addBankAccount(freeParkingMoney);
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
        } else {
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

        if (turnPlayer.isInJail()) {
            middleDisplayController.displayInJailDisplay(turnPlayer, true);
        }

    }

    //Controls the Street buy System
    public void buyStreet(@SuppressWarnings("exports") Street street) {
        street.setOwner(turnPlayer);
        turnPlayer.addBankAccount(-street.getSalePrice());

        setVisibilityTurnFinButton(true);
    }

    public void transferMoney(int money) {
        if (!(money <= 0)) {

            if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Street street) {
                if (street.isOwned())
                    street.getOwner().addBankAccount(money);
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
        errorMessage.setText(message);
        errorMessage.setVisible(true);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), errorMessage);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        ScaleTransition waitTransition = new ScaleTransition(Duration.seconds(1), errorMessage);
        waitTransition.setCycleCount(3);
        waitTransition.setByX(Main.WINDOW_WIDTH * 0.00003);
        waitTransition.setByY(Main.WINDOW_WIDTH * 0.00003);
        waitTransition.setAutoReverse(true);
        waitTransition.play();

        waitTransition.setOnFinished(actionEvent -> fadeTransition.play());
        fadeTransition.setOnFinished(actionEvent -> {
            if (errorMessage.isVisible()) {
                errorMessage.setVisible(false);
                fadeTransition.setToValue(1);
                fadeTransition.play();
            }
        });
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
        s.put(1, new Street("Oma Straße", 111 ,222, new int[]{333, 444, 555, 666}, 777, 23, 54, Color.rgb(112, 40, 0), 0, 1));
        s.put(2, new GetCard(2));
        s.put(3, new Street("Braune Straße", 112 ,223, new int[]{334, 445, 556, 667}, 778, 12, 23,  Color.rgb(112, 40, 0), 0, 3));
        s.put(4, new PayExtra(200, ExtraFields.SPOTIFY_PREMIUM, 4));
        s.put(5, new Station("Bahnhof", 200, 5));
        s.put(6, new Street("Aqua Straße", 113 ,224, new int[]{335, 446, 557, 668}, 779, 324, 2365, Color.AQUA, 1, 6));
        s.put(7, new GetCard(GetCard.ChanceColors.GREEN, 7));
        s.put(8, new Street("Aqua Straße", 114 ,225, new int[]{336, 447, 558, 669}, 780, 64, 6543, Color.AQUA, 1, 8));
        s.put(9, new Street("Aqua Straße", 115 ,226, new int[]{337, 448, 559, 670}, 781, 1234, 8976, Color.AQUA, 1, 9));
        s.put(10, new Corner(CornerTyp.JAIL, 10));
        s.put(11, new Street("Purple Straße", 116 ,227, new int[]{338, 449, 560, 671}, 782, 34, 4, Color.PURPLE, 2, 11));
        s.put(12, new PayExtra(200, ExtraFields.HESSLER_SCHULDEN, 12));
        s.put(13, new Street("Purple Straße", 117 ,228, new int[]{339, 450, 561, 672}, 783, 634, 756, Color.PURPLE, 2, 13));
        s.put(14, new Street("Purple Straße", 118 ,229, new int[]{340, 451, 562, 673}, 784, 65437, 345, Color.PURPLE, 2, 14));
        s.put(15, new Station("Bahnhof", 200, 15));
        s.put(16, new Street("Orang Straße", 119 ,230, new int[]{341, 452, 563, 674}, 785, 2344, 22, Color.ORANGE, 3, 16));
        s.put(17, new Street("Orang Straße", 120 ,231, new int[]{342, 453, 564, 675}, 786, 222, 22, Color.ORANGE, 3, 17));
        s.put(18, new GetCard(18));
        s.put(19, new Street("Orang Straße", 121 ,232, new int[]{343, 454, 565, 676}, 787, 99, 999, Color.ORANGE, 3, 19));
        s.put(20, new Corner(CornerTyp.FREE_PARKING, 20));
        s.put(21, new Street("Red Straße", 122 ,233, new int[]{344, 455, 566, 677}, 788, 23423, 66, Color.RED, 4, 21));
        s.put(22, new Street("Red Straße", 123 ,234, new int[]{345, 456, 567, 678}, 789, 345, 76546, Color.RED, 4, 22));
        s.put(23, new GetCard(GetCard.ChanceColors.BLUE, 23));
        s.put(24, new Street("Red Straße", 124 ,235, new int[]{346, 457, 568, 679}, 790, 1234, 654, Color.RED, 4, 24));
        s.put(25, new Station("Bahnhoff", 200, 25));
        s.put(26, new Street("Yellow Straße", 125 ,236, new int[]{347, 458, 569, 680}, 791, 874, 896, Color.YELLOW, 5, 26));
        s.put(27, new Street("Yellow Straße", 126 ,237, new int[]{348, 459, 570, 681}, 792, 2444, 111, Color.YELLOW, 5, 27));
        s.put(28, new PayExtra(200, ExtraFields.NAME_THREE, 28));
        s.put(29, new Street("Yellow Straße", 127 ,238, new int[]{349, 460, 571, 682}, 793, 2333, 44, Color.YELLOW, 5, 29));
        s.put(30, new Corner(CornerTyp.GO_TO_JAIL, 30));
        s.put(31, new Street("LIME Straße", 128 ,239, new int[]{350, 461, 572, 683}, 794, 55, 898, Color.LIME, 6, 31));
        s.put(32, new Street("LIME Straße", 129 ,240, new int[]{351, 462, 573, 684}, 795, 123, 44, Color.LIME, 6, 32));
        s.put(33, new GetCard(33));
        s.put(34, new Street("LIME Straße", 130 ,241, new int[]{352, 463, 574, 685}, 796, 44, 6674, Color.LIME, 6, 34));
        s.put(35, new Station("Bhanhoff", 200, 35));
        s.put(36, new GetCard(GetCard.ChanceColors.RED, 36));
        s.put(37, new Street("Dark Blue Straße", 131 ,242, new int[]{353, 464, 575, 686}, 797, 33, 67, Color.DARKBLUE, 7, 37));
        s.put(38, new PayExtra(200, ExtraFields.NAME_FOUR, 38));
        s.put(39, new Street("Dark Blue Straße", 132 ,243, new int[]{354, 465, 576, 687}, 798, 43, 64, Color.DARKBLUE, 7, 39));

        return s;
    }


}

/* @// TODO: 21.03.2024
 * Hier werden die Sachen aufglisstet die das Spiel am Technischen noch können muss, nichts Grafisches.
 * 1. Nach 3 mal Pash ins Gefängnis
 * 2. Das Gameboard an sich ein biischen kleiner machen als die höhe des Fensters (Das heißt alle werte in dem einfach die Höhe anstat ein wert für die Gameboardgröße genommen wird ÄNDERN!!!)
 * ... (Hier stehen nur die Sachen die ich denke ich vergesse)
 */