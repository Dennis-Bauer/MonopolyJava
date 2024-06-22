package de.sandwich.Fields;

import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Enums.Values;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildTriangle;

import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Street extends Field{

    //Street values
    private final String NAME;
    private final Color COLOR;
    private final int PRICE;
    private final int GROUP_ID;
    private final int RENT;
    private final int[] RENT_HOUSES;
    private final int RENT_HOTEL;
    private final int HOUSE_PRICE;
    private final int HOTEL_PRICE;

    //Functional values
    private int houseNumber = Values.STREET_HOUSES_START.getValue();
    private boolean isInBank = false;
    private boolean ownerHasFullColor = false;
    private boolean isOwned;
    private Player owner;

    public Street(String name, int salePrice, int rent, int[] rentHouses, int rentHotel, int housePrice, int hotelPrice, Color color, int group, double position) {
        super(position);
        this.NAME = name;
        this.PRICE = salePrice;
        this.GROUP_ID = group;
        this.RENT = rent;

        if (rentHouses.length != 4)
            throw new IllegalArgumentException("Rents must be stated for 4 houses, Street" + name + "has not sensed this");
        else
            this.RENT_HOUSES = rentHouses;

        this.RENT_HOTEL = rentHotel;

        this.HOUSE_PRICE = housePrice;
        this.HOTEL_PRICE = hotelPrice;

        this.COLOR = color;
    }

    public void setOwner(Player owner) {
        isOwned = owner != null;

        this.owner = owner;

        ownerHasFullColor = isFullColorGroup(owner);
    }
    
    private Rectangle background;
    private Rectangle colorIndicator;
    private Pane[] houses = new Pane[4];
    private Pane hotel;

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("street_field");
        field.setMaxSize(width, height);

        background = buildRectangle("street_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        colorIndicator = buildRectangle("street_ColorIndicator" ,width, height/4, COLOR, true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label nameIndicator =  buildLabel("street_NameIndicator", NAME, Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 3);
        Label priceIndicator = buildLabel("street_PriceIndicator", (PRICE + "€"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));

        centeringChildInPane(nameIndicator, field);
        centeringChildInPane(priceIndicator, field);

        final double HOUSE_W = width * 0.18;
        final double HOUSE_H = height * 0.16;
        final double HOUSE_Y = HOUSE_H / 2;
        final double HOUSE_SPACING = (width - (HOUSE_W * 4)) / 5;
        houses[0] = buildHouseSymbole(HOUSE_W, HOUSE_H, HOUSE_SPACING, HOUSE_Y);
        houses[1] = buildHouseSymbole(HOUSE_W, HOUSE_H, HOUSE_W + (HOUSE_SPACING * 2), HOUSE_Y);
        houses[2] = buildHouseSymbole(HOUSE_W, HOUSE_H, (HOUSE_W * 2) + (HOUSE_SPACING * 3), HOUSE_Y);
        houses[3] = buildHouseSymbole(HOUSE_W, HOUSE_H, (HOUSE_W * 3) + (HOUSE_SPACING * 4), HOUSE_Y);
        hotel = buildHotelSymbole(HOUSE_W, HOUSE_H, (width / 2) - (HOUSE_W / 2), HOUSE_Y);

        field.getChildren().addAll(background, colorIndicator, nameIndicator, priceIndicator, hotel);

        for (Pane h : houses) {
            h.setVisible(false);
            field.getChildren().add(h);
        }
        hotel.setVisible(false);

        super.field = field;
        return field;
    }

    public void sellToTheBank() {
        if (!isInBank) {
            isInBank = true;
            owner.transferMoneyToBankAccount(PRICE / 2);
        }
    }

    public void purchaseFromBank() {
        if (isInBank) {
            isInBank = false;
            owner.transferMoneyToBankAccount(-(PRICE / 2));
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

            for (int i = 0; i < houses.length; i++) {
                if (i <= houseNumber - 1) {
                    houses[i].setVisible(true);
                } else {
                    houses[i].setVisible(false);
                }
            }

            if (houseNumber > 4) {
                houseNumber = -1;

                for (Pane house : houses) 
                    house.setVisible(false);
                
                hotel.setVisible(true);
            }
        }
    }

    public void removeHouse() {
        if (houseNumber == -1) {
            houseNumber = 4;
            hotel.setVisible(false);
        } else {
            houseNumber--;

            for (int i = 0; i < houses.length; i++) {
                if (i <= houseNumber - 1) {
                    houses[i].setVisible(true);
                } else {
                    houses[i].setVisible(false);
                }
            }

        }

    }

    private void setOwnerHasFullColor(boolean ownerHasFullColor) {
        this.ownerHasFullColor = ownerHasFullColor;
    }

    private boolean isFullColorGroup(Player owner) {
        Main.getGameOperator();
        HashMap<Integer, Field> fields = Game.getFields();

        if (isOwned) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i) instanceof Street s) {
                    if (s != this) {
                        if (s.getGroupId() == GROUP_ID) {
                            if (s.isOwned) {
                                if (s.getOwner() != owner) {
                                    return false;
                                }
                            } else return false; 
                        }
                    }
                }
            }
        } else return false;
        

        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i) instanceof Street s) {
                if (s.getGroupId() == GROUP_ID) {
                    s.setOwnerHasFullColor(true);
                }
            }
        }

        return true;
    }

    public int getPlayerRent() {
        if (ownerHasFullColor) {
            switch (houseNumber) {
                case 0 -> {return RENT * 2;}
                case 1 -> {return RENT_HOUSES[0];}
                case 2 -> {return RENT_HOUSES[1];}
                case 3 -> {return RENT_HOUSES[2];}
                case 4 -> {return RENT_HOUSES[3];}
                case -1 -> {return RENT_HOTEL;}
                default -> throw new IllegalArgumentException("An unexpected number of houses were discovered");
            }
        } else {
            return RENT;
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
        return COLOR;
    }

    public int getPrice() {
        return PRICE;
    }

    public String getName() {
        return NAME;
    }

    public int getRent() {
        return RENT;
    }

    public int[] getRentHouse() {
        return RENT_HOUSES;
    }

    public int getRentHotel() {
        return RENT_HOTEL;
    }

    public int getHousePrice() {
        return HOUSE_PRICE;
    }

    public int getHotelPrice() {
        return HOTEL_PRICE;
    }

    public int getGroupId() {
        return GROUP_ID;
    }

    public boolean isInBank() {
        return isInBank;
    }

    public static Pane buildHouseSymbole(double houseWidth, double houseHeight, double x, double y) {
        Pane house = new Pane();
        house.setId("streetInfo_HouseSymbole");

        Polygon houseTop = buildTriangle("streetInfo_houseSymbole_Top", new Point2D(-(houseWidth * 0.025), 0), new Point2D(houseWidth * 0.525, -(houseHeight * 0.30)), new Point2D(houseWidth * 1.025, 0), ProgramColor.STREETS_HOUSE.getColor(), ProgramColor.BORDER_COLOR_DARK.getColor());
        Rectangle houseBotton = buildRectangle("streetInfo_houseSymbole_Botton", houseWidth, houseHeight * 0.70, ProgramColor.STREETS_HOUSE.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), 0);

        house.setLayoutX(x);
        house.setLayoutY(y);

        house.getChildren().addAll(houseTop, houseBotton);
        return house;
    }

    public static Pane buildHotelSymbole(double hotelWidth, double hotelHeight, double x, double y) {
        Pane hotel = new Pane();
        hotel.setId("streetInfo_HotelSymbole");

        Polygon hotelTop = buildTriangle("streetInfo_hotelSymbole_Top", new Point2D(-(hotelWidth * 0.025), 0), new Point2D(hotelWidth * 0.525, -(hotelHeight * 0.30)), new Point2D(hotelWidth * 1.025, 0), ProgramColor.STREETS_HOTEL.getColor(), ProgramColor.BORDER_COLOR_DARK.getColor());
        Rectangle hotelBotton = buildRectangle("streetInfo_hotelSymbole_Botton", hotelWidth, hotelHeight * 0.70, ProgramColor.STREETS_HOTEL.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), 0);

        hotel.setLayoutX(x);
        hotel.setLayoutY(y);

        hotel.getChildren().addAll(hotelTop, hotelBotton);
        return hotel;
    }
    
}
