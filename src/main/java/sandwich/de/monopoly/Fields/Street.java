package sandwich.de.monopoly.Fields;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Enums.ProgramColor;
import sandwich.de.monopoly.Player;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.Main.TEXT_FONT;

public class Street extends Field{

    //Field (Visual) values
    private final Color color;

    //Street values
    private final String name;
    private final int salePrice;
    private final int rent;
    private final int[] rentHouses;
    private final int rentHotel;
    private final int housePrice;
    private final int hotelPrice;


    //Functional values

    private boolean isOwned;
    private Player owner;


    public Street(String name, int salePrice, int rent, int[] rentHouses, int rentHotel, int housePrice, int hotelPrice, Color color, double position) {
        super(position);
        this.name = name;
        this.salePrice = salePrice;
        this.rent = rent;

        if (rentHouses.length != 4)
            throw new IllegalArgumentException("Rents must be stated for 4 houses, Street" + name + "has not sensed this");
        else
            this.rentHouses = rentHouses;

        this.rentHotel = rentHotel;

        this.housePrice = housePrice;
        this.hotelPrice = hotelPrice;

        this.color = color;
    }

    public void setOwner(Player owner) {
        isOwned = owner != null;

        this.owner = owner;
    }
    private Rectangle background;
    private Rectangle colorIndicator;

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("street_field");
        field.setMaxSize(width, height);

        background = buildRectangle("street_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        colorIndicator = buildRectangle("street_ColorIndicator" ,width, height/4, color, true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label nameIndicator =  buildLabel("street_NameIndicator", name, Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 3);
        Label priceIndicator = buildLabel("street_PriceIndicator", (salePrice + "€"), Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));

        centeringChildInPane(nameIndicator, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, colorIndicator, nameIndicator, priceIndicator);

        super.field = field;
        return field;
    }

    public void highlightField() {
        background.setStrokeWidth(background.getStrokeWidth() * 2);
        background.setStroke(ProgramColor.BORDER_COLOR_LIGHT.getColor());

        colorIndicator.setStrokeWidth(colorIndicator.getStrokeWidth() * 2);
        colorIndicator.setStroke(ProgramColor.BORDER_COLOR_LIGHT.getColor());

        field.toFront();
    }

    public void removeHighlight() {
        background.setStrokeWidth(background.getStrokeWidth() / 2);
        background.setStroke(ProgramColor.BORDER_COLOR_DARK.getColor());

        colorIndicator.setStrokeWidth(colorIndicator.getStrokeWidth() / 2);
        colorIndicator.setStroke(ProgramColor.BORDER_COLOR_DARK.getColor());
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public Color getColor() {
        return color;
    }

    public int getSalePrice() {
        return salePrice;
    }

    public String getName() {
        return name;
    }

    public int getRent() {
        return rent;
    }

    public int[] getRentHouse() {
        return rentHouses;
    }

    public int getRentHotel() {
        return rentHotel;
    }

    public int getHousePrice() {
        return housePrice;
    }

    public int getHotelPrice() {
        return hotelPrice;
    }
}
