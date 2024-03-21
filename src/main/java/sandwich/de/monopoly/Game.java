package sandwich.de.monopoly;

import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;
import sandwich.de.monopoly.Exceptions.ToManyPlayersExceptions;
import sandwich.de.monopoly.GUI.Game.GameDisplayControllerOne;
import sandwich.de.monopoly.GUI.Game.GameDisplayControllerTwo;

import java.util.ArrayList;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

public class Game {

    private Player playerOne = null;
    private Player playerTwo = null;
    private Player playerThree = null;
    private Player playerFour = null;
    private Player playerFive = null;
    private Player turnPlayer;
    private final ArrayList<Player> playerList = new ArrayList<>();

    private int playerInGame = 0;

    public Game(Player[] players) {

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, "Game Controller, Konstruktor:");
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

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
        GameDisplayControllerOne.displayPlayers(playerList);
        GameDisplayControllerTwo.waitForTheDiceRoll();

        Main.getGameField().setPlayerToGameboard(playerList);

        Main.changeScene(Main.scenes.GAME);
    }

    public void playerRolledDice(int diceOne, int diceTwo) {
        try {
            Main.getGameField().movePlayerOnGameBoard(turnPlayer, diceOne + diceTwo);
        } catch (PlayerNotFoundExceptions ignored) {}
        turnPlayer.moveFieldPostion(diceOne + diceTwo);
        System.out.println("Player new Postion: " + turnPlayer.getFieldPostion());
    }

}
