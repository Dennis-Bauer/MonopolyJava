package sandwich.de.monopoly.Exceptions;

public class PlayerNotFoundExceptions extends Exception {

    public PlayerNotFoundExceptions() {
        super("An attempt was made to use a player object that could not be found. This can happen if an attempt is made to use a player object that, for example, has not already been saved in the playing field.");
    }

}
