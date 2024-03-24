package sandwich.de.monopoly.Fields;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

public abstract class Field {

    private final double POS;

    public Field(double postion) {
        POS = postion;
    }

    public Pane buildField(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        return new Pane();
    }

}
