package de.sandwich.Exceptions;

public class WrongNodeException extends IllegalArgumentException {

    public WrongNodeException() {
        super("An incorrect ID was read or an incorrect order was expected!");
    }

    public WrongNodeException(String paneName) {
        super("An incorrct node was giving form the order of the Pane: " + paneName);
    }

}

