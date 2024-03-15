package sandwich.de.monopoly.Exceptions;

public class PlayerIsOutOfBoundsExceptions extends IllegalArgumentException {

    private static final int MAX_POSTION = 40;

    public PlayerIsOutOfBoundsExceptions(int playerPostion) {
        super("Player is on Postion: \033[1;31m" + playerPostion + "\033[0m but the Maximum Postion is \033[1;31m" + MAX_POSTION + "\033[0m");
    }

    public PlayerIsOutOfBoundsExceptions() {
        super("Player has a position that is too high or too low");
    }

}
