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
import de.sandwich.Enums.Values;
import de.sandwich.Exceptions.PlayerNotFoundExceptions;
import de.sandwich.Exceptions.ToManyPlayersExceptions;
import de.sandwich.Exceptions.WrongNodeException;
import de.sandwich.Fields.Corner;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.GetCard;
import de.sandwich.Fields.PayExtra;
import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.Fields.Utilitie;
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
    private final ArrayList<Player> PLAYER_LIST = new ArrayList<>();

    //Displays
    private final GameDisplayControllerOne DISPLAY_CONTROLLER_ONE;
    private final GameDisplayControllerTwo DISPLAY_CONTROLLER_TWO;
    private final MiddleGameDisplayController MIDDLE_DISPLAY_CONTROLLER;

    //Gameboard
    private static final HashMap<Integer, Field> FIELDS = createFields();
    private final GameBoard GAME_BOARD = new GameBoard(Main.WINDOW_HEIGHT * 0.98, FIELDS);
    private final StackPane TURN_FINISH_BUTTON = new StackPane();
    private final Label ERROR_MESSAGE = buildLabel("gameBoard_ErrorMessage", "NULL", Font.font( Main.TEXT_FONT, FontWeight.BOLD, Main.WINDOW_HEIGHT * 0.015), TextAlignment.CENTER, ProgramColor.ERROR_MESSAGES.getColor(), 0, 0);
    private final Scene GAME_SECENE;
    private final Pane ROOT;

    //Game variables
    private int freeParkingMoney = Values.START_FREEPARKING_MONEY.getValue();
    private int pashRow = 0;

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
        DISPLAY_CONTROLLER_ONE = new GameDisplayControllerOne((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.60);
        DISPLAY_CONTROLLER_TWO = new GameDisplayControllerTwo((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1, Main.WINDOW_HEIGHT * 0.38);
        MIDDLE_DISPLAY_CONTROLLER = new MiddleGameDisplayController(Main.WINDOW_HEIGHT * 0.40, Main.WINDOW_HEIGHT * 0.25, Main.WINDOW_HEIGHT * 0.18);   

        VBox sideDisplays = new VBox(Main.WINDOW_HEIGHT * 0.02);

        sideDisplays.getChildren().addAll(
            DISPLAY_CONTROLLER_ONE,
            DISPLAY_CONTROLLER_TWO
        );
        sideDisplays.setLayoutX(Main.WINDOW_HEIGHT + (((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 2) - ((Main.WINDOW_WIDTH - Main.WINDOW_HEIGHT) / 1.1) / 2));
        sideDisplays.setLayoutY(0);

        ERROR_MESSAGE.setLayoutX(Main.WINDOW_WIDTH * 0.18);
        ERROR_MESSAGE.setLayoutY(Main.WINDOW_HEIGHT * 0.81);
        ERROR_MESSAGE.setVisible(false);

        TURN_FINISH_BUTTON.getChildren().addAll(
            buildRectangle("gameScene_turnFinishButton_Background", Main.WINDOW_WIDTH * 0.18, Main.WINDOW_HEIGHT * 0.08, ProgramColor.SELECT_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), Main.WINDOW_WIDTH * 0.005),
            buildLabel("gameScene_turnFinishButton_Text", "Beende Zug", Font.font(Main.TEXT_FONT, FontWeight.BOLD ,Main.WINDOW_WIDTH * 0.02), null, ProgramColor.TEXT_COLOR.getColor())
        );
        TURN_FINISH_BUTTON.setLayoutX(Main.WINDOW_WIDTH * 0.18);
        TURN_FINISH_BUTTON.setLayoutY(Main.WINDOW_HEIGHT - Main.WINDOW_HEIGHT * 0.285);

        TURN_FINISH_BUTTON.setOnMouseClicked(mouseEvent -> {
            turnFinish();
        });

        ROOT = new Pane(
            buildRectangle("gameScene_Background", Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, ProgramColor.BACKGROUND.getColor(), true, null, 0, 0, 0),
            GAME_BOARD,
            sideDisplays,
            MIDDLE_DISPLAY_CONTROLLER,
            ERROR_MESSAGE,
            TURN_FINISH_BUTTON
        );

        GAME_SECENE = new Scene(ROOT);

        for (int i = 0, j = 1; i != 5; i++) {
            if (players[i] != null) {
                assert false;

                switch (j) {
                    case 1 -> {
                        playerOne = players[i];
                        PLAYER_LIST.add(playerOne);
                    }
                    case 2 -> {
                        playerTwo = players[i];
                        PLAYER_LIST.add(playerTwo);
                    }
                    case 3 -> {
                        playerThree = players[i]; 
                        PLAYER_LIST.add(playerThree);
                    }
                    case 4 -> {
                        playerFour = players[i]; 
                        PLAYER_LIST.add(playerFour);
                    }
                    case 5 ->  {
                        playerFive = players[i]; 
                        PLAYER_LIST.add(playerFive);
                    }
                }
                j++;

            }
        }
        turnPlayer = playerOne;

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "Game Operator wurde erstellt!");
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.size() + " Player Objects: ");

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
        GAME_BOARD.addPlayers(PLAYER_LIST);
        DISPLAY_CONTROLLER_ONE.displayPlayers(PLAYER_LIST);
        DISPLAY_CONTROLLER_TWO.displayDice();
        TURN_FINISH_BUTTON.setVisible(false);

        //Zeigt das Game Board an
        Main.getPrimaryStage().setScene(GAME_SECENE);
    }

    //Starts the Animation and controls the postion from the player.
    public void playerRolledDice(int diceOne, int diceTwo) {
        DiceDisplay.lockeDices();
        setNextPlayer();

        if (turnPlayer.isInJail()) {
            if (diceOne == diceTwo) {
                turnPlayer.removePlayerFromJail();

                try { 
                    GAME_BOARD.movePlayer(turnPlayer, diceOne + diceTwo); 
                    turnPlayer.moveFieldPostion(diceOne + diceTwo);
                }catch (NullPointerException notUse) {

                    GAME_BOARD.getPlayerMoveTransition().setOnFinished(actionEvent -> {
                        try {GAME_BOARD.movePlayer(turnPlayer, diceOne + diceTwo);} catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }
                        turnPlayer.moveFieldPostion(diceOne + diceTwo);
                    });

                } catch (PlayerNotFoundExceptions e) { e.printStackTrace(); }
            } else {
                turnPlayer.removeOnInJailRemain();
                MIDDLE_DISPLAY_CONTROLLER.displayInJailDisplay(turnPlayer, false);
            }

        } else {
            try { GAME_BOARD.movePlayer(turnPlayer, diceOne + diceTwo); } catch (PlayerNotFoundExceptions ignored) {}
            turnPlayer.moveFieldPostion(diceOne + diceTwo);

            if (diceOne == diceTwo) {
                nextPlayer = turnPlayer;
                pashRow++;
                if (pashRow >= 3) {
                    try {GAME_BOARD.setPlayerInJail(turnPlayer);} catch(Exception e) {System.out.println(e);}
                    setVisibilityTurnFinButton(true);
                    setNextPlayer();
                    turnFinish();
                    pashRow = 0;
                } else pashRow = 0;
            }

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
        DISPLAY_CONTROLLER_TWO.displayPlayerAction();

        if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Street street) { 
            if (!street.isOwned()) {
                if (street.getPrice() <= turnPlayer.getBankAccount()) {
                    MIDDLE_DISPLAY_CONTROLLER.displayBuyStreetDisplay(street);
                } else setVisibilityTurnFinButton(true);
                
            } else if (street.getOwner() != turnPlayer) {
                MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(buildLongText("Du schuldest den Spieler", street.getOwner().getName() + " " + (street.getPlayerRent() + "€")), -(street.getPlayerRent()));
            } else setVisibilityTurnFinButton(true);
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Corner corner) {
            switch (corner.getTyp()) {
                case FREE_PARKING -> {                    
                    if (freeParkingMoney <= 0)
                        setVisibilityTurnFinButton(true);
                    else 
                        MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(buildLongText("Du hast auf dem Parkplatz", "ein bisschen Geld gefunden."), freeParkingMoney);
                    freeParkingMoney = 0;
                }
                case GO_TO_JAIL -> {
                    try {GAME_BOARD.setPlayerInJail(turnPlayer);} catch(Exception e) {System.out.println(e);}
                    setVisibilityTurnFinButton(true);
                }
                default -> setVisibilityTurnFinButton(true);
            }
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof PayExtra payField) { 
            MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(payField.getTyp().getMessage(), payField.getPrice());
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Station station) {
            if (!station.isOwned()) {
                if (station.getPrice() <= turnPlayer.getBankAccount()) {
                    MIDDLE_DISPLAY_CONTROLLER.displayBuyStationDisplay(station);
                } else setVisibilityTurnFinButton(true);
                
            } else if (station.getOwner() != turnPlayer) {
                MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(buildLongText("Du schuldest den Spieler", station.getOwner().getName() + " " + (station.getRent() + "€")), -(station.getRent()));
            } else setVisibilityTurnFinButton(true);
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Utilitie utilitie) {

            if (!utilitie.isOwned()) {
                if (utilitie.getPrice() <= turnPlayer.getBankAccount()) {
                    MIDDLE_DISPLAY_CONTROLLER.displayBuyUtilitieDisplay(utilitie);
                } else {
                    setVisibilityTurnFinButton(true);
                }
            } else if (utilitie.getOwner() != turnPlayer) {
                MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(buildLongText("Du schuldest den Spieler", utilitie.getOwner().getName() + " " + ((utilitie.getMultiplicator() * (DiceDisplay.getDiceNumbers()[0] + DiceDisplay.getDiceNumbers()[1])) + "€")), -(utilitie.getMultiplicator() * (DiceDisplay.getDiceNumbers()[0] + DiceDisplay.getDiceNumbers()[1])));
            } else setVisibilityTurnFinButton(true);
        } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof GetCard getCardField) {
            if (getCardField.isFieldChance()) {
                //Field ist eine Chance Card
                ChanceCards card = GetCard.getChanceCard();

                //Spieler bewegt sich zu einem Feld das nicht der Start ist
                if (card.getMoneyTransfer() <= -101 && card.getMoneyTransfer() >= -104) {
                    final int TO_FIELD = Integer.parseInt(card.getMessage());
                    final String MOVE_MESSAGE = "Rück vor bis ";

                    if (FIELDS.get(TO_FIELD) instanceof Street s) {
                        if (turnPlayer.getFieldPostion() != TO_FIELD) {
                            if (turnPlayer.getFieldPostion() < TO_FIELD) {
                                try { GAME_BOARD.movePlayer(turnPlayer, TO_FIELD - turnPlayer.getFieldPostion()); } catch (PlayerNotFoundExceptions ignored) {}
                                turnPlayer.moveFieldPostion(TO_FIELD - turnPlayer.getFieldPostion());
    
                                if (Main.CONSOLE_OUT_PUT) {
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                }
                            } else {
                                try { GAME_BOARD.movePlayer(turnPlayer, 40 - (turnPlayer.getFieldPostion() - TO_FIELD)); } catch (PlayerNotFoundExceptions ignored) {}
                                turnPlayer.moveFieldPostion(40 - (turnPlayer.getFieldPostion() - TO_FIELD));

                                if (Main.CONSOLE_OUT_PUT) {
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                }
                            }
                        }
    
                        MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(MOVE_MESSAGE + s.getName());
                    } else throw new WrongNodeException("ChanceCards given Field");
                    

                } else if (card.getMoneyTransfer() <= -1 && card.getMoneyTransfer() >= -8) {
                    switch (card.getMoneyTransfer()) {
                        case -1 -> {
                            //Give player a free jail card
                            turnPlayer.addFreeJailCard();
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());

                            DISPLAY_CONTROLLER_ONE.displayPlayers(PLAYER_LIST);
                        }
                        case -2 -> {
                            //Set player to jail
                            try {GAME_BOARD.setPlayerInJail(turnPlayer);} catch(Exception e) {System.out.println(e);}
                                                        
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                        }
                        case -3 -> {
                            //Move 3 steps back
                            try { GAME_BOARD.movePlayer(turnPlayer, -3); } catch (PlayerNotFoundExceptions ignored) {}
                            turnPlayer.moveFieldPostion(-3);

                            if (Main.CONSOLE_OUT_PUT) {
                                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                            }
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                        }
                        case -4 -> {
                            //Got to start
                            if (turnPlayer.getFieldPostion() != 0) {
                                try { GAME_BOARD.movePlayer(turnPlayer, 40 - turnPlayer.getFieldPostion()); } catch (PlayerNotFoundExceptions ignored) {}
                                turnPlayer.moveFieldPostion(40 - turnPlayer.getFieldPostion());

                                if (Main.CONSOLE_OUT_PUT) {
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                }
                            }

                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                        }
                        case -5 -> {
                            //Go to next Station
                            final int PLAYER_POS = turnPlayer.getFieldPostion();
                            int moveTo = 0;

                            if (PLAYER_POS <= 5 || PLAYER_POS > 35) 
                                moveTo = 5;
                            else if (PLAYER_POS <= 15) 
                                moveTo = 15;
                            else if (PLAYER_POS <= 25) 
                                moveTo = 25;
                            else if (PLAYER_POS <= 35) 
                                moveTo = 35;
                            
                            if (moveTo != 0) {
                                try { GAME_BOARD.movePlayer(turnPlayer, moveTo - PLAYER_POS); } catch (PlayerNotFoundExceptions ignored) {}
                                turnPlayer.moveFieldPostion(moveTo - PLAYER_POS);

                                if (Main.CONSOLE_OUT_PUT) {
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                }
                            }
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
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
                                MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(card.getMessage(), -payment);
                            } else {
                                MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());;
                            }
                        }
                        case -7 -> {
                            //Got to south station
                            if (turnPlayer.getFieldPostion() != 5) {
                                if (turnPlayer.getFieldPostion() < 5) {
                                    try { GAME_BOARD.movePlayer(turnPlayer, 5 - turnPlayer.getFieldPostion()); } catch (PlayerNotFoundExceptions ignored) {}
                                    turnPlayer.moveFieldPostion(5 - turnPlayer.getFieldPostion());

                                    if (Main.CONSOLE_OUT_PUT) {
                                        consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                        consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                        consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    }
                                } else {

                                    try { GAME_BOARD.movePlayer(turnPlayer, 40 - (turnPlayer.getFieldPostion() - 5)); } catch (PlayerNotFoundExceptions ignored) {}
                                    turnPlayer.moveFieldPostion(40 - (turnPlayer.getFieldPostion() - 5));

                                    if (Main.CONSOLE_OUT_PUT) {
                                        consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                        consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                        consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    }
                                }
                            }
                
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                        
                        }
                        case -8 -> {
                            //Had to give every player 1000
                            int playerHasToPay = 0;
                            for (Player p : PLAYER_LIST) {
                                if (!p.hasGivenUp()) {
                                    p.transferMoneyToBankAccount(1000);
                                    playerHasToPay++;
                                }
                            }
                            turnPlayer.transferMoneyToBankAccount(-(playerHasToPay * 1000));
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                        }
                    }
                } else {
                    MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(card.getMessage(), card.getMoneyTransfer());
                }

            } else {
                //Field ist eine Comunitty
                CommunityCards card = GetCard.getCommunityCard();

                if (card.getMoneyTransfer() <= -1 && card.getMoneyTransfer() >= -5) {
                    switch (card.getMoneyTransfer()) {
                        case -1 -> {
                            //Give player a free jail card
                            turnPlayer.addFreeJailCard();
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                            DISPLAY_CONTROLLER_ONE.displayPlayers(PLAYER_LIST);
                        }
                        case -2 -> {
                            //Got to start
                            if (turnPlayer.getFieldPostion() != 0) {
                                try { GAME_BOARD.movePlayer(turnPlayer, 40 - turnPlayer.getFieldPostion()); } catch (PlayerNotFoundExceptions ignored) {}
                                turnPlayer.moveFieldPostion(40 - turnPlayer.getFieldPostion());

                                if (Main.CONSOLE_OUT_PUT) {
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
                                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
                                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                                }
                            }

                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                        }
                        case -3 -> {
                            //Set player to jail
                            try {GAME_BOARD.setPlayerInJail(turnPlayer);} catch(Exception e) {System.out.println(e);}
                            
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
                        }
                        case -4 -> {
                            //Get from every player 1000
                            int playerHasToPay = 0;
                            for (Player p : PLAYER_LIST) {
                                if (!p.hasGivenUp()) {
                                    p.transferMoneyToBankAccount(-1000);
                                    playerHasToPay++;
                                }
                            }
                            turnPlayer.transferMoneyToBankAccount(playerHasToPay * 1000);
                            MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());
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

                            if (payment != 0) 
                                MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(card.getMessage(), -payment);
                            else 
                                MIDDLE_DISPLAY_CONTROLLER.displayInfoDisplay(card.getMessage());;
                        }
                    }
                } else MIDDLE_DISPLAY_CONTROLLER.displayPayDisplay(card.getMessage(), card.getMoneyTransfer());
            }
        }
    
    }

    //Starts the new Round for a new Player
    public void turnFinish() {
        turnPlayer = nextPlayer;
        nextPlayer = null;

        DiceDisplay.unlockDices();

        MIDDLE_DISPLAY_CONTROLLER.removeDisplay();
        setVisibilityTurnFinButton(false);
        DISPLAY_CONTROLLER_ONE.displayPlayers(PLAYER_LIST);
        
        DISPLAY_CONTROLLER_TWO.displayDice();

        if (!(turnPlayer.getBankAccount() < 0)) {
            if (turnPlayer.isInJail())
                MIDDLE_DISPLAY_CONTROLLER.displayInJailDisplay(turnPlayer, true);
        } else {
            DiceDisplay.lockeDices();

            MIDDLE_DISPLAY_CONTROLLER.displayPlayerIsInMinusDisplay();
        }

    }

    public void removePlayer() {
        int remainPlayers = 0;
        for (Player player : PLAYER_LIST) {
            if (!player.hasGivenUp())
                remainPlayers++;
        }

        if (remainPlayers -1 != 1) {
            turnPlayer.giveUp();

            if (Main.CONSOLE_OUT_PUT) {
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                for (int i = 0; i != 5; i++) {
                    switch (i) {
                        case 0 -> {
                            if (playerOne != null) {
                                if (playerOne.hasGivenUp()) {
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerOne (Aufgeben): ");
                                    consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(0).toString());
                                } else {
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerOne: ");
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(0).toString());
                                }
   
                            } else
                                consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                        }
                        case 1 -> {
                            if (playerTwo != null) {
                                if (playerTwo.hasGivenUp()) {   
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerTwo (Aufgegeben): ");
                                    consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(1).toString());
                                } else {
                                                
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerTwo: ");
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(1).toString());
                                }
                            } else
                                consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                        }
                        case 2 -> {
                            if (playerThree != null) {
                                if (playerThree.hasGivenUp()) {   
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerThree (Aufgegeben): ");
                                    consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(2).toString());
                                } else {
                                                
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "Playerhree: ");
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(2).toString());
                                }
                            } else
                                consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                        }
                        case 3 -> {
                            if (playerFour != null) {
                                if (playerFour.hasGivenUp()) {   
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFour (Aufgegeben): ");
                                    consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(3).toString());
                                } else {
                                                
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFour: ");
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(3).toString());
                                }
                            } else
                                consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                        }
                        case 4 -> {
                            if (playerFive != null) {
                                if (playerFive.hasGivenUp()) {   
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFive (Aufgegeben): ");
                                    consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(4).toString());
                                } else {
                                                
                                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "PlayerFive: ");
                                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, PLAYER_LIST.get(4).toString());
                                }
                            } else
                                consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                        }
                        default -> throw new ToManyPlayersExceptions();
                    }
                    System.out.println();
                }
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
            }

            setNextPlayer();
            
            MIDDLE_DISPLAY_CONTROLLER.removeDisplay();
            try { GAME_BOARD.removePlayerFromBoard(turnPlayer);} catch (PlayerNotFoundExceptions ignored) { }
            turnFinish();
        } else Main.showWinner(nextPlayer.getName());
    }

    public void transferMoney(int money) {
        if (!(money <= 0)) {
            if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Street f) {
                if (f.isOwned())
                    f.getOwner().transferMoneyToBankAccount(money);
            } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Station f) {
                if (f.isOwned())
                    f.getOwner().transferMoneyToBankAccount(money);
            } else if (FIELDS.get(turnPlayer.getFieldPostion()) instanceof Utilitie f) {
                if (f.isOwned())
                    f.getOwner().transferMoneyToBankAccount(money);
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
        if (ERROR_MESSAGE.getText().equals("NULL")) {
            ERROR_MESSAGE.setText(message);
            ERROR_MESSAGE.setVisible(true);
            ERROR_MESSAGE.toFront();

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), ERROR_MESSAGE);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);
            fadeTransition.play();

            ScaleTransition waitTransition = new ScaleTransition(Duration.seconds(1), ERROR_MESSAGE);
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
                    ERROR_MESSAGE.setVisible(false);
                    ERROR_MESSAGE.setText("NULL");
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
        return DISPLAY_CONTROLLER_ONE;
    }

    @SuppressWarnings("exports")
    public GameDisplayControllerTwo getDisplayControllerTwo() {
        return DISPLAY_CONTROLLER_TWO;
    }

    @SuppressWarnings("exports")
    public MiddleGameDisplayController getMiddleDisplayController() {
        return MIDDLE_DISPLAY_CONTROLLER;
    }

    @SuppressWarnings("exports")
    public GameBoard getBoard() {
        return GAME_BOARD;
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
        final double ANIMATION_LENGTH = 1;
        if (visible) {
            if (!TURN_FINISH_BUTTON.isVisible()) {
                TURN_FINISH_BUTTON.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_LENGTH), TURN_FINISH_BUTTON);
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);

                fadeTransition.play();
            }
        } else {
            if (TURN_FINISH_BUTTON.isVisible()) {
                TURN_FINISH_BUTTON.setVisible(true);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(ANIMATION_LENGTH), TURN_FINISH_BUTTON);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);

                fadeTransition.play();

                fadeTransition.setOnFinished(event -> TURN_FINISH_BUTTON.setVisible(false));
            }

        }
        TURN_FINISH_BUTTON.setVisible(visible);
    }

    private void setNextPlayer() {
        nextPlayer = turnPlayer;
        do {

            int i = nextPlayer.getOrderNumber() + 1;
            switch (nextPlayer.getOrderNumber() + 1) {
                case 1 -> nextPlayer = playerTwo;
                case 2 -> nextPlayer = playerThree;
                case 3 -> nextPlayer = playerFour;
                case 4 -> nextPlayer = playerFive;
                case 5 -> nextPlayer = playerOne;
                default -> nextPlayer = playerOne;
            }
            
            while (nextPlayer == null) {
                switch (i) {
                    case 1 -> nextPlayer = playerTwo;
                    case 2 -> nextPlayer = playerThree;
                    case 3 -> nextPlayer = playerFour;
                    case 4 -> nextPlayer = playerFive;
                    case 5 -> nextPlayer = playerOne;
                    default -> nextPlayer = playerOne;
                }
                i++;
                if (i > 5)
                    i = 0;
            } 
        } while (nextPlayer.hasGivenUp());
    } 

    //Private
    private static HashMap<Integer, Field> createFields() {
        HashMap<Integer, Field> s = new HashMap<>();

        s.put(0, new Corner(CornerTyp.START, 0));
        s.put(1, new Street("MC-Donalds", 1200 ,80, new int[]{400, 1200, 3600, 6400}, 9000, 1000, 1000, Color.rgb(112, 40, 0), 0, 1));
        s.put(2, new GetCard(2));
        s.put(3, new Street("BurgerKing", 1200 ,40, new int[]{200, 600, 1800, 3200}, 5000, 1000, 1000,  Color.rgb(112, 40, 0), 0, 3));
        s.put(4, new PayExtra(-500, ExtraFields.SPOTIFY_PREMIUM, 4));
        s.put(5, new Station("Südbahnhof", 5));
        s.put(6, new Street("Hoppelweg", 2000 ,120, new int[]{600, 1800, 5400, 8000}, 11000, 1000, 1000, Color.rgb(56, 215, 255), 1, 6));
        s.put(7, new GetCard(GetCard.ChanceColors.GREEN, 7));
        s.put(8, new Street("ZickZack Weg", 2000 ,120, new int[]{600, 1800, 5400, 8000}, 11000, 1000, 1000, Color.rgb(56, 215, 255), 1, 8));
        s.put(9, new Street("Amongus Weg", 2400 ,160, new int[]{800, 2000, 6000, 9000}, 12000, 1000, 1000, Color.rgb(56, 215, 255), 1, 9));
        s.put(10, new Corner(CornerTyp.JAIL, 10));
        s.put(11, new Street("Nether-Strasse", 2800 ,200, new int[]{1000, 3000, 9000, 12500}, 15000, 2000, 2000, Color.rgb(201, 4, 185), 2, 11));
        s.put(12, new Utilitie(buildLongText("Elektrizitäts-", "Werk"), 12));
        s.put(13, new Street(buildLongText("Overworld", "Strasse"), 2800 ,200, new int[]{1000, 3000, 9000, 12500}, 15000, 2000, 2000, Color.rgb(201, 4, 185), 2, 13));
        s.put(14, new Street("End-Strasse", 3200 ,240, new int[]{1200, 3600, 10000, 14000}, 18000, 2000, 2000, Color.rgb(201, 4, 185), 2, 14));
        s.put(15, new Station("Westbahnhof", 15));
        s.put(16, new Street(buildLongText("Frankfuhrter", "Strasse"), 3600 ,280, new int[]{1400, 4000, 11000, 15000}, 19000, 2000, 2000, Color.rgb(255, 147, 31), 3, 16));
        s.put(17, new Street(buildLongText("Remscheider", "Strasse"), 3600 ,280, new int[]{1400, 4000, 11000, 15000}, 19000, 2000, 2000, Color.rgb(255, 147, 31), 3, 17));
        s.put(18, new GetCard(18));
        s.put(19, new Street(buildLongText("Duisburg", "Strasse"), 4000 ,320, new int[]{1600, 4400, 12000, 16000}, 20000, 2000, 2000, Color.rgb(255, 147, 31), 3, 19));
        s.put(20, new Corner(CornerTyp.FREE_PARKING, 20));
        s.put(21, new Street(buildLongText("Theo-Otto", "Theater Strasse"), 4400 ,360, new int[]{1800, 5000, 14000, 17500}, 21000, 3000, 3000, Color.rgb(255, 54, 54), 4, 21));
        s.put(22, new Street(buildLongText("Cinestar", "Strasse"), 4400 ,360, new int[]{1800, 5000, 14000, 17500}, 21000, 3000, 3000, Color.rgb(255, 54, 54), 4, 22));
        s.put(23, new GetCard(GetCard.ChanceColors.BLUE, 23));
        s.put(24, new Street(buildLongText("RemscheidHBF", "Strasse"), 4800 ,400, new int[]{2000, 6000, 15000, 18500}, 22000, 3000, 3000, Color.rgb(255, 54, 54), 4, 24));
        s.put(25, new Station("Nordbahnohof", 25));
        s.put(26, new Street(buildLongText("Samsung", "Strasse"), 5200 ,480, new int[]{2200, 6600, 16000, 19500}, 23000, 3000, 3000, Color.rgb(255, 230, 64), 5, 26));
        s.put(27, new Street("Google Strasse", 5200 ,480, new int[]{2200, 6600, 16000, 19500}, 23000, 3000, 3000, Color.rgb(255, 230, 64), 5, 27));
        s.put(28, new Utilitie("Wasser- Werk", 28));
        s.put(29, new Street("Appel Strasse", 5600 ,580, new int[]{2400, 7200, 17000, 20500}, 24000, 3000, 3000, Color.rgb(255, 230, 64), 5, 29));
        s.put(30, new Corner(CornerTyp.GO_TO_JAIL, 30));
        s.put(31, new Street(buildLongText("Coolins-Hütte", "Strasse"), 6000 ,520, new int[]{2600, 7800, 18000, 22000}, 25500, 4000, 4000, Color.rgb(56, 204, 51), 6, 31));
        s.put(32, new Street(buildLongText("Rafaels-Haus", "Strasse"), 6000 ,520, new int[]{2600, 7800, 18000, 22000}, 25500, 4000, 4000, Color.rgb(56, 204, 51), 6, 32));
        s.put(33, new GetCard(33));
        s.put(34, new Street(buildLongText("Dennis-Haus", "Strasse"), 6400 ,560, new int[]{3000, 9000, 20000, 24000}, 28000, 4000, 4000, Color.rgb(56, 204, 51), 6, 34));
        s.put(35, new Station("Hauptbahnhof", 35));
        s.put(36, new GetCard(GetCard.ChanceColors.RED, 36));
        s.put(37, new Street("Döner 2.0", 7000 ,700, new int[]{3500, 10000, 22000, 26000}, 30000, 4000, 4000, Color.rgb(89, 109, 255), 7, 37));
        s.put(38, new PayExtra(-600, ExtraFields.HESSLER_SCHULDEN, 38));
        s.put(39, new Street("Aldi", 8000 ,1000, new int[]{4000, 12000, 28000, 34000}, 40000, 4000, 4000, Color.rgb(89, 109, 255), 7, 39));

        return s;
    }
}