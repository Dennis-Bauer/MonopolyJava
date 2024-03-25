package sandwich.de.monopoly.Fields;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Player;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.Main.TEXT_FONT;

public class Street extends Field{

    //Field (Visual) values
    private final Color color;

    //Functional values
    private final String name;
    private final int salePrice;
    private boolean isOwned;
    private Player owner;
    private int rent;

    public Street(String name, int salePrice, Color color, double position) {
        super(position);
        this.name = name;
        this.salePrice = salePrice;
        this.color = color;


        rent = 200;
    }

    public void setOwner(Player owner) {
        isOwned = owner != null;

        this.owner = owner;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public int getRent() {
        return rent;
    }

    public String getName() {
        return name;
    }

    public Color getColor() {
        return color;
    }

    private Rectangle background;
    private Rectangle colorIndicator;

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        Pane field = new Pane();
        field.setId("street_field");
        field.setMaxSize(width, height);

        background = buildRectangle("street_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        colorIndicator = buildRectangle("street_ColorIndicator" ,width, height/4, color, true, Color.BLACK, borderWidth);
        Label nameIndicator =  buildLabel("street_NameIndicator", name, Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 3);
        Label priceIndicator = buildLabel("street_PriceIndicator", (salePrice + "€"), Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));

        centeringChildInPane(nameIndicator, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, colorIndicator, nameIndicator, priceIndicator);

        super.field = field;
        return field;
    }

    public void highlightField() {
        background.setStrokeWidth(background.getStrokeWidth() * 2);
        background.setStroke(Color.WHITE);

        colorIndicator.setStrokeWidth(colorIndicator.getStrokeWidth() * 2);
        colorIndicator.setStroke(Color.WHITE);

        field.toFront();
    }

    public void removeHighlight() {
        background.setStrokeWidth(background.getStrokeWidth() / 2);
        background.setStroke(BORDER_COLOR);

        colorIndicator.setStrokeWidth(colorIndicator.getStrokeWidth() / 2);
        colorIndicator.setStroke(BORDER_COLOR);
    }

}
