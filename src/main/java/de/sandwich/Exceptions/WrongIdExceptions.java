package de.sandwich.Exceptions;

public class WrongIdExceptions extends IllegalArgumentException {

    public WrongIdExceptions() {
        super("An incorrect ID was read!");
    }

}

