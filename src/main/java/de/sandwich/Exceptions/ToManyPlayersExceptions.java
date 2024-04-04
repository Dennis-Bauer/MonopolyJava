package de.sandwich.Exceptions;

public class ToManyPlayersExceptions extends IndexOutOfBoundsException {

    public ToManyPlayersExceptions() {
        super("More than 5 players were created or an attempt was made to access more to than 5 players!");
    }

}
