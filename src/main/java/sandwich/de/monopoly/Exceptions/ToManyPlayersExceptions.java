package sandwich.de.monopoly.Exceptions;

public class ToManyPlayersExceptions extends IndexOutOfBoundsException {

    public ToManyPlayersExceptions() {
        super("More than 5 players have been created!");
    }

}
