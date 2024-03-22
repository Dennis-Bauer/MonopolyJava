package sandwich.de.monopoly;

import javafx.scene.Scene;
import javafx.scene.paint.Color;
import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;
import sandwich.de.monopoly.Exceptions.ToManyPlayersExceptions;
import sandwich.de.monopoly.GUI.Game.GameDisplayControllerOne;
import sandwich.de.monopoly.GUI.Game.GameDisplayControllerTwo;
import sandwich.de.monopoly.GUI.Game.GameField;

import java.util.ArrayList;
import java.util.HashMap;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

public class Game {

    private Player playerOne;
    private Player playerTwo;
    private Player playerThree;
    private Player playerFour;
    private Player playerFive;
    private Player turnPlayer;
    private Player nextPlayer;
    private final ArrayList<Player> playerList = new ArrayList<>();
    private final HashMap<Integer, Street> streets = createStreets();
    private GameField gameField;
    private int playerInGame = 0;

    public Game(Player[] players) {

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, "Game Controller, Konstruktor:");
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        gameField = new GameField(0, Main.WINDOW_WIDTH, Main.WINDOW_HEIGHT, Color.rgb(112, 224, 88), streets);

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
        gameField.setPlayerToGameboard(playerList);
        GameDisplayControllerOne.displayPlayers(playerList);
        GameDisplayControllerTwo.displayDice();
        //Zeigt das Game Board an
        Main.getPrimaryStage().setScene(new Scene(gameField));
    }

    public void playerRolledDice(int diceOne, int diceTwo) {

        try { gameField.movePlayerOnGameBoard(turnPlayer, diceOne + diceTwo); } catch (PlayerNotFoundExceptions ignored) {}
        turnPlayer.moveFieldPostion(diceOne + diceTwo);

        if (diceOne == diceTwo)
            nextPlayer = turnPlayer;

        if (streets.get(turnPlayer.getFieldPostion()) == null) {

        }

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler nummer " + turnPlayer.getOrderNumber() + ", wurde bewegt auf die Feld Postion Nummer:");
            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + turnPlayer.getFieldPostion());
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }
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

    public HashMap<Integer, Street> getStreets() {
        return streets;
    }

    private HashMap<Integer, Street> createStreets() {
        HashMap<Integer, Street> s = new HashMap<>();

        s.put(1, new Street("Braune Straße", 200, 1, Color.rgb(112, 40, 0)));
        s.put(3, new Street("Braune Straße", 200, 2, Color.rgb(112, 40, 0)));
        s.put(6, new Street("Aqua Straße", 200, 3, Color.AQUA));
        s.put(8, new Street("Aqua Straße", 200, 4, Color.AQUA));
        s.put(9, new Street("Aqua Straße", 200, 5, Color.AQUA));
        s.put(11, new Street("Purple Straße", 200, 6, Color.PURPLE));
        s.put(13, new Street("Purple Straße", 200, 7, Color.PURPLE));
        s.put(14, new Street("Purple Straße", 200, 8, Color.PURPLE));
        s.put(16, new Street("Orang Straße", 200, 9, Color.ORANGE));
        s.put(17, new Street("Orang Straße", 200, 10, Color.ORANGE));
        s.put(19, new Street("Orang Straße", 200, 11, Color.ORANGE));
        s.put(21, new Street("Red Straße", 200, 12, Color.RED));
        s.put(22, new Street("Red Straße", 200, 13, Color.RED));
        s.put(24, new Street("Red Straße", 200, 14, Color.RED));
        s.put(26, new Street("Yellow Straße", 200, 15, Color.YELLOW));
        s.put(27, new Street("Yellow Straße", 200, 16, Color.YELLOW));
        s.put(29, new Street("Yellow Straße", 200, 17, Color.YELLOW));
        s.put(31, new Street("LIME Straße", 200, 18, Color.LIME));
        s.put(32, new Street("LIME Straße", 200, 19, Color.LIME));
        s.put(34, new Street("LIME Straße", 200, 20, Color.LIME));
        s.put(37, new Street("Dark Blue Straße", 200, 21, Color.DARKBLUE));
        s.put(39, new Street("Dark Blue Straße", 200, 12, Color.DARKBLUE));

        return s;
    }
}

/* @// TODO: 21.03.2024
 * Hier werden die Sachen aufglisstet die das Spiel am Technischen noch können muss, nichts Grafisches.</h1>
 * 1. Nach 3 mal Pash ins Gefängnis
 * ... (Hier stehen nur die Sachen die ich denke ich vergesse)
 */