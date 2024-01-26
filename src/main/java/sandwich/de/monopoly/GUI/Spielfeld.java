package sandwich.de.monopoly.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontPosture;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Utilities;


public class Spielfeld {

    public static Pane buildGameBoard(double size, Color boardColor) {
        Pane fieldD = new Pane();
        StackPane field = new StackPane();
        field.setAlignment(Pos.CENTER);

        //Black Field
        Rectangle f = Utilities.buildRectangle("Test" ,size, size, Color.BLACK, true, Color.BLACK, 0);
        Rectangle v = Utilities.buildRectangle("Test" ,size /1.5, size/1.5, boardColor, true, Color.BLACK, 0);

        //Test Street
        field.getChildren().addAll(f, v, buildStreet(Color.ORANGE, boardColor, Utilities.buildLongText("Bank", "Straße"), 200, size / 9, (size - size / 1.5) / 2));

        fieldD.getChildren().addAll(field);

        return fieldD;
    }

    private static Pane buildStreet(Paint streetColor, Paint backgroundColor, String name, double price, double width, double height) {
        Pane street = new Pane();
        double borderWidth = width / 25;
        street.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("street_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Rectangle colorIndicator = Utilities.buildRectangle("street_ColorIndicator" ,width, height/4, streetColor, true, Color.BLACK, borderWidth);
        Label nameIndicator = Utilities.buildLabel("street_NameIndicator", name, Font.font("Verdana", FontWeight.BOLD, width / 8), TextAlignment.CENTER, Color.BLACK, 0, height / 3);
        Label priceIndicator = Utilities.buildLabel("street_PriceIndicator", (price + "€"), Font.font("Verdana", FontWeight.BOLD, width / 8), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));

        Utilities.centeringChildInPane(nameIndicator, street);
        Utilities.centeringChildInPane(priceIndicator, street);

        street.getChildren().addAll(background, colorIndicator, nameIndicator, priceIndicator);

        return street;
    }

}

/*
Die höhe einer Straße wird immer Größe des Feldes / 9 (9 Felder sind an jeder Seite (ohne die Ecken))
 */
