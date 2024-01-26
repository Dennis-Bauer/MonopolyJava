package sandwich.de.monopoly.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Utilities;


public class Spielfeld {

    private static final Pane[] fields = new Pane[36];
    private static final double middleRectangleRatio = 1.4;

    public static Pane buildGameBoard(double size, Color boardColor) {
        Pane fieldD = new Pane();
        StackPane field = new StackPane();
        field.setAlignment(Pos.CENTER);

        //Black Field
        Rectangle f = Utilities.buildRectangle("Test" ,size, size, Color.BLACK, true, Color.BLACK, 0);
        Rectangle v = Utilities.buildRectangle("Test" ,size / middleRectangleRatio, size/ middleRectangleRatio, boardColor, true, Color.BLACK, 0);

        field.getChildren().addAll(f, v);

        //Creating Fields
        double width = (size / middleRectangleRatio) / 9;
        double height = (size - size / middleRectangleRatio) / 2;
        fields[0] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 00"), 200, boardColor, width, height);
        fields[1] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 01"), 200, boardColor, width, height);
        fields[2] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 02"), 200, boardColor, width, height);
        fields[3] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 03"), 200, boardColor, width, height);
        fields[4] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 04"), 200, boardColor, width, height);
        fields[5] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 05"), 200, boardColor, width, height);
        fields[6] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 06"), 200, boardColor, width, height);
        fields[7] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 07"), 200, boardColor, width, height);
        fields[8] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 08"), 200, boardColor, width, height);
        fields[9] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 09"), 200, boardColor, width, height);
        fields[10] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 10"), 200, boardColor, width, height);
        fields[11] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 11"), 200, boardColor, width, height);
        fields[12] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 12"), 200, boardColor, width, height);
        fields[13] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 13"), 200, boardColor, width, height);
        fields[14] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 14"), 200, boardColor, width, height);
        fields[15] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 15"), 200, boardColor, width, height);
        fields[16] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 16"), 200, boardColor, width, height);
        fields[17] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 17"), 200, boardColor, width, height);
        fields[18] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 18"), 200, boardColor, width, height);
        fields[19] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 19"), 200, boardColor, width, height);
        fields[20] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 20"), 200, boardColor, width, height);
        fields[21] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 21"), 200, boardColor, width, height);
        fields[22] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 22"), 200, boardColor, width, height);
        fields[23] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 23"), 200, boardColor, width, height);
        fields[24] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 24"), 200, boardColor, width, height);
        fields[25] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 25"), 200, boardColor, width, height);
        fields[26] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 26"), 200, boardColor, width, height);
        fields[27] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 27"), 200, boardColor, width, height);
        fields[28] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 28"), 200, boardColor, width, height);
        fields[29] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 29"), 200, boardColor, width, height);
        fields[30] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 30"), 200, boardColor, width, height);
        fields[31] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 31"), 200, boardColor, width, height);
        fields[32] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 32"), 200, boardColor, width, height);
        fields[33] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 33"), 200, boardColor, width, height);
        fields[34] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 34"), 200, boardColor, width, height);
        fields[35] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 35"), 200, boardColor, width, height);


        //Position fields
        for (int i = 0; i < 36; i++) {
            field.getChildren().add(fields[i]);
            StackPane.setAlignment(fields[i], Pos.TOP_LEFT);
            double rightCorner = ((size / middleRectangleRatio) + (size - size / middleRectangleRatio) / 2);
            if (i < 9) {
                fields[i].setTranslateY(size - height);
                fields[i].setTranslateX(height + (i * width));
            } else if (i < 18) {
                fields[i].rotateProperty().set(270);
                fields[i].setTranslateY( ((((10 * width) + (size / middleRectangleRatio)) - (i + 1) * width)) - (width / 2 - height / 2) );
                fields[i].setTranslateX(rightCorner + (height / 2 - width / 2));
            } else if (i < 27) {
                fields[i].rotateProperty().set(180);
                fields[i].setTranslateY(0);
                fields[i].setTranslateX((rightCorner - width) - width * (i - 18));
            } else {
                fields[i].rotateProperty().set(90);
                fields[i].setTranslateY(((height / 2) + width / 2) + width * (i - 27));
                fields[i].setTranslateX((height / 2) - width / 2);
            }
        }



        fieldD.getChildren().addAll(field);

        return fieldD;
    }

    private static Pane buildStreet(Paint streetColor, String name, double price, Paint backgroundColor, double width, double height) {
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
