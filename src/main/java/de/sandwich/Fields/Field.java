package de.sandwich.Fields;

import javafx.scene.layout.Pane;

public abstract class Field {

    private final double POS;

    protected Pane field;

    public Field(double postion) {
        POS = postion;
    }

    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        throw new NoSuchMethodError("An attempt was made to execute a method that actually needs to be overwritten");
    }

    public double getPosition() {
        return POS;
    }


}
