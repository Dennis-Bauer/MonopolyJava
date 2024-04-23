package de.sandwich.Exceptions;

public class NumberIsToBigLowExceptions extends IllegalArgumentException {

    public NumberIsToBigLowExceptions(boolean toBig, String classInformation) {
        if (toBig)
            System.out.println("A smaller number was expected. In " + classInformation);
        else 
            System.out.println("A bigger number was expected. In " + classInformation);
    }

}
