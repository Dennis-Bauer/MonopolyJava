package de.sandwich.Fields;

import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;

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

    //Field (Visual) values
    private final Color color;

    private boolean isInBank = false;

    //Street values
    private final String name;
    private final int salePrice;
    private final int group;
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


    public Street(String name, int salePrice, int rent, int[] rentHouses, int rentHotel, int housePrice, int hotelPrice, Color color, int group, double position) {
        super(position);
        this.name = name;
        this.salePrice = salePrice;
        this.group = group;
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
        colorIndicator = buildRectangle("street_ColorIndicator" ,width, height/4, color, true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label nameIndicator =  buildLabel("street_NameIndicator", name, Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 3);
        Label priceIndicator = buildLabel("street_PriceIndicator", (salePrice + "€"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));

        centeringChildInPane(nameIndicator, field);
        centeringChildInPane(priceIndicator, field);

        double hosueWidht = width * 0.18;
        double houseHeight = height * 0.16;
        double houseY = houseHeight / 2;
        double houseSpacing = (width - (hosueWidht * 4)) / 5;
        houses[0] = buildHouseSymbole(hosueWidht, houseHeight, houseSpacing, houseY);
        houses[1] = buildHouseSymbole(hosueWidht, houseHeight, hosueWidht + (houseSpacing * 2), houseY);
        houses[2] = buildHouseSymbole(hosueWidht, houseHeight, (hosueWidht * 2) + (houseSpacing * 3), houseY);
        houses[3] = buildHouseSymbole(hosueWidht, houseHeight, (hosueWidht * 3) + (houseSpacing * 4), houseY);
        hotel = buildHotelSymbole(hosueWidht, houseHeight, (width / 2) - (hosueWidht / 2), houseY);

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
            owner.transferMoneyToBankAccount(salePrice / 2);
        }
    }

    public void purchaseFromBank() {
        if (isInBank) {
            isInBank = false;
            owner.transferMoneyToBankAccount(-(salePrice / 2));
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

    public int getPlayerRent() {
        if (ownerHasFullColor) {
            switch (houseNumber) {
                case 0 -> {return rent * 2;}
                case 1 -> {return rentHouses[0];}
                case 2 -> {return rentHouses[1];}
                case 3 -> {return rentHouses[2];}
                case 4 -> {return rentHouses[3];}
                case -1 -> {return rentHotel;}
                default -> throw new IllegalArgumentException("An unexpected number of houses were discovered");
            }
        } else {
            return rent;
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

    public int getGroup() {
        return group;
    }

    public boolean isInBank() {
        return isInBank;
    }

    public void setOwnerHasFullColor(boolean ownerHasFullColor) {
        this.ownerHasFullColor = ownerHasFullColor;
    }

    private boolean isFullColorGroup(Player owner) {
        Main.getGameOperator();
        HashMap<Integer, Field> fields = Game.getFields();

        System.out.println("Die Straße hat die Gruppe: " + getGroup());

        if (isOwned) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i) instanceof Street s) {
                    if (s != this) {
                        System.out.println("Die Grade Bearbeitet Straße hat die Gruppe: " + s.getGroup() + " und den Namen: " + s.getName());
                        if (s.getGroup() == group) {
                            System.out.println("Es wurde die Gleiche Farbe gefunden!!!!!");
                            if (s.isOwned) {
                                if (s.getOwner() != owner) {
                                    return false;
                                }
                            } else {
                                return false;
                            }
                        }
                    }
                }
            }
        } else {
            return false;
        }

        for (int i = 0; i < fields.size(); i++) {
            if (fields.get(i) instanceof Street s) {
                if (s.getGroup() == group) {
                    s.setOwnerHasFullColor(true);
                }
            }
        }

        return true;
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
