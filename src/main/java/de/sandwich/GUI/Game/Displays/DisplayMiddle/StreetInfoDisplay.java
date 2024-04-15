package de.sandwich.GUI.Game.Displays.DisplayMiddle;

import de.sandwich.Fields.Street;
import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLine;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildTriangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.buildPlus;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;

public class StreetInfoDisplay extends Pane {

    private final MiddleGameDisplayController rootDisplay;

    private final double WIDTH;
    private final double HEIGHT;

    public StreetInfoDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("StreetInfoDisplay");
        setMaxSize(width, height);

        this.WIDTH = width;
        this.HEIGHT = height;
        this.rootDisplay = rootDisplay;
    }

    public void buildStreetDisplay(Street street) {
        if (!getChildren().isEmpty())
            getChildren().clear();

        //Remove info
        StackPane removeButton = buildPlus("streetInfo_removeButton_", WIDTH * 0.01, WIDTH * 0.10, 45, WIDTH * 0.01, ProgramColor.CHANCEL_BUTTONS.getColor(), ProgramColor.SYMBOLE_COLOR.getColor(), 0, 0);
        removeButton.setLayoutX(WIDTH - WIDTH * 0.08);

        removeButton.setOnMouseClicked(mouseEvent -> rootDisplay.removeDisplay());

        //Street name
        Label name = buildLabel("streetInfo_Name", street.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.125), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor());
        centeringChildInPane(name, rootDisplay);
        name.setLayoutY(HEIGHT * 0.03);

        //Fonts
        Font infoTextFont = Font.font(Main.TEXT_FONT, WIDTH * 0.08);
        Font infoFont = Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.08);

        //Property price info
        Label infoPropertyPrice = buildLabel("streetInfo_info_PropertyPrice", "Grundstückswert:", infoTextFont, TextAlignment.RIGHT, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, HEIGHT * 0.20);
        Label propertyPrice = buildLabel("streetInfo_number_PropertyPrice", street.getSalePrice() + "€ ", infoFont, TextAlignment.LEFT, ProgramColor.TEXT_COLOR.getColor(), 0 ,HEIGHT * 0.20);
        propertyPrice.layoutXProperty().bind(widthProperty().subtract(propertyPrice.widthProperty()));

        //Rent alone info
        Label infoRentAlone = buildLabel("streetInfo_info_RentAlone", "Miete alleine:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, HEIGHT * 0.28);
        Label rentAlone = buildLabel("streetInfo_number_RentAlone", street.getRent() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, HEIGHT * 0.28);
        rentAlone.layoutXProperty().bind(widthProperty().subtract(rentAlone.widthProperty()));

        //Rent with all streets from the same color
        Label infoRentAll = buildLabel("streetInfo_info_RentAll", "Miete Gruppe:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, HEIGHT * 0.36);
        Label rentAll = buildLabel("streetInfo_number_RentAll",  (street.getRent() * 2) + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, HEIGHT * 0.36);
        rentAll.layoutXProperty().bind(widthProperty().subtract(rentAll.widthProperty()));

        //Rent houses
        double houseWidth = WIDTH * 0.09;
        double houseHeight = WIDTH * 0.07;

        //Houses
        VBox houses = new VBox(houseWidth * 0.10,
                createHouseRow(houseWidth, houseHeight, 1),
                createHouseRow(houseWidth, houseHeight, 2),
                createHouseRow(houseWidth, houseHeight, 3),
                createHouseRow(houseWidth, houseHeight, 4),
                buildHotelSymbole(houseWidth, houseHeight)
        );
        houses.setAlignment(Pos.CENTER);
        houses.setLayoutX(WIDTH * 0.01);
        houses.setLayoutY(HEIGHT * 0.48);

        //Doppel points
        VBox doppelPoints = new VBox(WIDTH * 0.0225,
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_hotelRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor())
        );
        doppelPoints.setLayoutY(HEIGHT * 0.45);
        doppelPoints.setLayoutX(WIDTH * 0.60);

        //Doppel numbers
        VBox housesNumbers = new VBox(WIDTH * 0.0225,
                buildLabel("streetInfo_number_RentOneHouse",  street.getRentHouse()[0] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentTowHouse", street.getRentHouse()[1] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentThreeHouse", street.getRentHouse()[2] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentFourHouse", street.getRentHouse()[3] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentHotel", street.getRentHotel() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );
        housesNumbers.setLayoutY(HEIGHT * 0.45);
        housesNumbers.layoutXProperty().bind(widthProperty().subtract(housesNumbers.widthProperty()));

        //Info house price
        Label infoHousePrice = buildLabel("streetInfo_info_HousePrice", "Haus kostet:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, HEIGHT * 0.85);
        Label housePrice = buildLabel("streetInfo_number_HousePrice",  street.getHousePrice() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, HEIGHT * 0.85);
        housePrice.layoutXProperty().bind(widthProperty().subtract(housePrice.widthProperty()));

        //Info hotel price
        Label infoHotelPrice = buildLabel("streetInfo_info_HotelPrice", "Hotel kostet:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, HEIGHT * 0.92);

        Label hotelPriceNumberHouses = buildLabel("", "4", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        hotelPriceNumberHouses.setLayoutY(HEIGHT * 0.92);
        hotelPriceNumberHouses.setLayoutX(WIDTH * 0.46);

        Pane hotelHouseSymbole = buildHouseSymbole(WIDTH * 0.08, WIDTH * 0.06);
        hotelHouseSymbole.setLayoutY(HEIGHT * 0.94);
        hotelHouseSymbole.setLayoutX(WIDTH * 0.52);

        Label hotelPrice = buildLabel("streetInfo_number_HotelPrice", "u. " + street.getHotelPrice() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        hotelPrice.setLayoutY(HEIGHT * 0.92);
        hotelPrice.setLayoutX(WIDTH * 0.60);
        hotelPrice.layoutXProperty().bind(widthProperty().subtract(hotelPrice.widthProperty()));

        //Adding all nodes
        getChildren().addAll(
                buildRectangle("streetInfo_ColorIndicator", WIDTH, HEIGHT * 0.20, street.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), WIDTH * 0.02),
                removeButton,
                name,
                infoPropertyPrice,
                propertyPrice,
                buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, HEIGHT * 0.28), new Point2D(WIDTH * 0.99, HEIGHT * 0.28), WIDTH * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoRentAlone,
                rentAlone,
                buildLine("streetInfo_separatingLine_RentOneStreet|RentFullStreet", new Point2D(0.01, HEIGHT * 0.36), new Point2D(WIDTH * 0.99, HEIGHT * 0.36), WIDTH * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoRentAll,
                rentAll,
                buildLine("streetInfo_separatingLine_RentFullStreet|RentHouses", new Point2D(0.01, HEIGHT * 0.44), new Point2D(WIDTH * 0.99, HEIGHT * 0.44), WIDTH * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                houses,
                housesNumbers,
                buildLine("streetInfo_separatingLine_RentHouses|HousePrice", new Point2D(0.01, HEIGHT * 0.85), new Point2D(WIDTH * 0.99, HEIGHT * 0.85), WIDTH * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoHousePrice,
                housePrice,
                doppelPoints,
                buildLine("streetInfo_separatingLine_HousePrice|HotelPrice", new Point2D(0.01, HEIGHT * 0.93), new Point2D(WIDTH * 0.99, HEIGHT * 0.92), WIDTH * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoHotelPrice,
                hotelPriceNumberHouses,
                hotelHouseSymbole,
                hotelPrice
        );
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    private HBox createHouseRow(double bottomBoxWidth, double bottomBoxHeight, int houseNumber) {
        if (!(houseNumber <= 0)) {
            HBox houseRow = new HBox();
            houseRow.setAlignment(Pos.CENTER);

            for (int i = 0; i < houseNumber; i++)
                houseRow.getChildren().add(buildHouseSymbole(bottomBoxWidth, bottomBoxHeight));

            return houseRow;
        } else throw new IllegalArgumentException("You cannot create a row of houses with " + houseNumber + " houses");
    }
    private Pane buildHouseSymbole(double bottomBoxWidth, double bottomBoxHeight) {
        Pane house = new Pane();
        house.setId("streetInfo_HouseSymbole");

        Polygon houseTop = buildTriangle("streetInfo_houseSymbole_Top", new Point2D(-(bottomBoxWidth * 0.025), 0), new Point2D(bottomBoxWidth * 0.525, -(bottomBoxHeight * 0.30)), new Point2D(bottomBoxWidth * 1.025, 0), ProgramColor.STREETS_HOUSE.getColor(), ProgramColor.BORDER_COLOR_DARK.getColor());
        Rectangle houseBotton = buildRectangle("streetInfo_houseSymbole_Botton", bottomBoxWidth, bottomBoxHeight, ProgramColor.STREETS_HOUSE.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), 0);

        house.getChildren().addAll(houseTop, houseBotton);
        return house;
    }

    private HBox buildHotelSymbole(double bottomBoxWidth, double bottomBoxHeight) {
        HBox hotel = new HBox();
        hotel.setId("streetInfo_HouseSymbole");
        hotel.setAlignment(Pos.CENTER);

        hotel.getChildren().add(new Pane(
                buildTriangle("streetInfo_hotelSymbole_Top", new Point2D(-(bottomBoxWidth * 0.025), 0), new Point2D(bottomBoxWidth * 0.525, -(bottomBoxHeight * 0.30)), new Point2D(bottomBoxWidth * 1.025, 0), ProgramColor.STREETS_HOTEL.getColor(), ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildRectangle("streetInfo_hotelSymbole_Botton", bottomBoxWidth, bottomBoxHeight, ProgramColor.STREETS_HOTEL.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), 0)
        ));
        return hotel;
    }
}
