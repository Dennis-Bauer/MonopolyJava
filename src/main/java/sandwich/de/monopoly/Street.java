package sandwich.de.monopoly;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.Main.TEXT_FONT;

public class Street {

    //Field (Visual) values
    private final Color color;

    //Functional values
    private final String name;
    private final int salePrice;
    private final int position;
    private boolean isOwned;
    private Player owner;

    public Street(String name, int salePrice, int position, Color color){
        this.name = name;
        this.salePrice = salePrice;
        this.position = position;
        this.color = color;

    }

    public Pane buildStreetField(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        Pane field = new Pane();
        field.setId("street_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("street_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Rectangle colorIndicator = buildRectangle("street_ColorIndicator" ,width, height/4, color, true, Color.BLACK, borderWidth);
        Label nameIndicator =  buildLabel("street_NameIndicator", name, Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 3);
        Label priceIndicator = buildLabel("street_PriceIndicator", (salePrice + "€"), Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));

        centeringChildInPane(nameIndicator, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, colorIndicator, nameIndicator, priceIndicator);

        return field;
    }

}
