package sandwich.de.monopoly;

import sandwich.de.monopoly.Exceptions.ToManyPlayersExceptions;
import sandwich.de.monopoly.GUI.Game.GameDisplayControllerOne;

import java.util.ArrayList;

public class Game {

    private Spieler playerOne = null;
    private Spieler playerTwo = null;
    private Spieler playerThree = null;
    private Spieler playerFour = null;
    private Spieler playerFive = null;
    private final ArrayList<Spieler> playerList = new ArrayList<>();

    private int playerInGame = 0;

    public Game(Spieler[] players) {

        if (Main.CONSOLE_OUT_PUT) {
            Utilities.consoleOutPutLine(Utilities.colors.WHITE, Utilities.textStyle.REGULAR, "Game Controller, Konstruktor:");
            Utilities.consoleOutPutLine(Utilities.colors.WHITE, Utilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
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

        if (Main.CONSOLE_OUT_PUT) {
            Utilities.consoleOutPutLine(Utilities.colors.GREEN, Utilities.textStyle.BOLD, "Game Operator wurde erstellt!");
            Utilities.consoleOutPutLine(Utilities.colors.GREEN, Utilities.textStyle.REGULAR, playerInGame + " Player Objects: ");
            for (int i = 0; i != 5; i++) {
                switch (i) {
                    case 0 -> {
                        if (playerOne != null) {
                            Utilities.consoleOutPut(Utilities.colors.BLUE, Utilities.textStyle.REGULAR, "PlayerOne: ");
                            Utilities.consoleOutPut(Utilities.colors.GREEN, Utilities.textStyle.REGULAR, playerOne.toString());
                        } else
                            Utilities.consoleOutPut(Utilities.colors.RED, Utilities.textStyle.REGULAR, "Null");
                    }
                    case 1 -> {
                        if (playerTwo != null) {
                            Utilities.consoleOutPut(Utilities.colors.BLUE, Utilities.textStyle.REGULAR, "PlayerTwo: ");
                            Utilities.consoleOutPut(Utilities.colors.GREEN, Utilities.textStyle.REGULAR, playerTwo.toString());
                        } else
                            Utilities.consoleOutPut(Utilities.colors.RED, Utilities.textStyle.REGULAR, "Null");
                    }
                    case 2 -> {
                        if (playerThree != null) {
                            Utilities.consoleOutPut(Utilities.colors.BLUE, Utilities.textStyle.REGULAR, "PlayerThree: ");
                            Utilities.consoleOutPut(Utilities.colors.GREEN, Utilities.textStyle.REGULAR, playerThree.toString());
                        } else
                            Utilities.consoleOutPut(Utilities.colors.RED, Utilities.textStyle.REGULAR, "Null");
                    }
                    case 3 -> {
                        if (playerFour != null) {
                            Utilities.consoleOutPut(Utilities.colors.BLUE, Utilities.textStyle.REGULAR, "PlayerFour: ");
                            Utilities.consoleOutPut(Utilities.colors.GREEN, Utilities.textStyle.REGULAR, playerFour.toString());
                        } else
                            Utilities.consoleOutPut(Utilities.colors.RED, Utilities.textStyle.REGULAR, "Null");
                    }
                    case 4 -> {
                        if (playerFive != null) {
                            Utilities.consoleOutPut(Utilities.colors.BLUE, Utilities.textStyle.REGULAR, "PlayerFive: ");
                            Utilities.consoleOutPut(Utilities.colors.GREEN, Utilities.textStyle.REGULAR, playerFive.toString());
                        } else
                            Utilities.consoleOutPut(Utilities.colors.RED, Utilities.textStyle.REGULAR, "Null");
                    }
                    default -> throw new ToManyPlayersExceptions();
                }
                System.out.println();
            }
            Utilities.consoleOutPutLine(Utilities.colors.WHITE, Utilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        //Zeigt die Spieler an
        GameDisplayControllerOne.displayPlayers(playerList);

        Main.getGameField().setPlayerToGameboard(playerList);

        Main.changeScene(Main.scenes.GAME);
    }

}
