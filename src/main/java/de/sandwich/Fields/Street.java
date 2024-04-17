package de.sandwich.Fields;

import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Street extends Field{

    //Field (Visual) values
    private final Color color;

    private boolean isInBank = false;

    //Street values
    private final String name;
    private final int salePrice;
    private final int rent;
    private final int[] rentHouses;
    private final int rentHotel;
    private final int housePrice;
    private final int hotelPrice;


    //Functional values
    private int houseNumber = 0;
    private boolean ownerHasFullColor = false;
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

        ownerHasFullColor = isFullColorGroup(owner);

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
        Label nameIndicator =  buildLabel("street_NameIndicator", name, Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 3);
        Label priceIndicator = buildLabel("street_PriceIndicator", (salePrice + "€"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));

        centeringChildInPane(nameIndicator, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, colorIndicator, nameIndicator, priceIndicator);

        super.field = field;
        return field;
    }

    public void sellToTheBank() {
        if (!isInBank) {
            isInBank = true;
            owner.addBankAccount(salePrice / 2);
        }
    }

    public void purchaseFromBank() {
        if (isInBank) {
            isInBank = false;
            owner.addBankAccount(-(salePrice / 2));
        }
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

    public void addHouse() {
        if (houseNumber != -1) {
            houseNumber++;
            if (houseNumber >= 4)
                houseNumber = -1;
        }
    }

    public int getHouseNumber() {
        return houseNumber;
    }

    public Player getOwner() {
        return owner;
    }

    public boolean isOwned() {
        return isOwned;
    }

    public boolean ownerHasFullColor() {
        return ownerHasFullColor;
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

    public boolean isInBank() {
        return isInBank;
    }

    private boolean isFullColorGroup(Player owner) {
        Main.getGameOperator();
        HashMap<Integer, Field> fields = Game.getFields();

        if (isOwned) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i) instanceof Street s) {
                    if (s.getColor() == color) {
                        if (s.isOwned) {
                            if (s.getOwner() == owner) {
                                return true;
                            }
                        }
                    }
                }
            }
        }
        return false;
    }
}
