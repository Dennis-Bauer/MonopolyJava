package de.sandwich.Fields;

import javafx.scene.layout.Pane;

public abstract class Field {

    private final double POS;

    protected Pane field;

    public Field(double postion) {
        POS = postion;
    }

    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        return new Pane();
    }

    public double getPosition() {
        return POS;
    }


}
