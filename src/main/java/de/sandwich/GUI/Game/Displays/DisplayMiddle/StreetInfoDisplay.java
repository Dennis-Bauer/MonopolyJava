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

    private Pane infoBox = new Pane();

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

        infoBox = getInfoBox(street, WIDTH, HEIGHT);
        infoBox.getChildren().add(removeButton);
        
        getChildren().add(infoBox);
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    public static Pane getInfoBox(Street street, double w, double h) {
        Pane box = new Pane();
        box.setMaxSize(w, h);

        //Street name
        Label name = buildLabel("streetInfo_Name", street.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.125), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor());
        centeringChildInPane(name, box);
        name.setLayoutY(h * 0.03);

        //Fonts
        Font infoTextFont = Font.font(Main.TEXT_FONT, w * 0.08);
        Font infoFont = Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.08);

        //Property price info
        Label infoPropertyPrice = buildLabel("streetInfo_info_PropertyPrice", "Grundstückswert:", infoTextFont, TextAlignment.RIGHT, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.18);
        Label propertyPrice = buildLabel("streetInfo_number_PropertyPrice", street.getSalePrice() + "€ ", infoFont, TextAlignment.LEFT, ProgramColor.TEXT_COLOR.getColor(), 0 ,h * 0.18);
        propertyPrice.layoutXProperty().bind(box.widthProperty().subtract(propertyPrice.widthProperty()));

        //Rent alone info
        Label infoRentAlone = buildLabel("streetInfo_info_RentAlone", "Miete alleine:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.26);
        Label rentAlone = buildLabel("streetInfo_number_RentAlone", street.getRent() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.26);
        rentAlone.layoutXProperty().bind(box.widthProperty().subtract(rentAlone.widthProperty()));

        //Rent with all streets from the same color
        Label infoRentAll = buildLabel("streetInfo_info_RentAll", "Miete Gruppe:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.343);
        Label rentAll = buildLabel("streetInfo_number_RentAll",  (street.getRent() * 2) + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.343);
        rentAll.layoutXProperty().bind(box.widthProperty().subtract(rentAll.widthProperty()));

        //Rent houses
        double houseWidth = w * 0.09;
        double houseHeight = h * 0.05;

        //Houses
        VBox houses = new VBox(h * 0.03,
                createHouseRow(houseWidth, houseHeight, 1),
                createHouseRow(houseWidth, houseHeight, 2),
                createHouseRow(houseWidth, houseHeight, 3),
                createHouseRow(houseWidth, houseHeight, 4),
                buildHotelSymbole(houseWidth, houseHeight)
        );
        houses.setAlignment(Pos.CENTER);
        houses.setLayoutX(w * 0.01);
        houses.setLayoutY(h * 0.45);

        //Doppel points
        VBox doppelPoints = new VBox(h * 0.025,
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor())
        );
        doppelPoints.setLayoutX(w * 0.60);
        doppelPoints.setLayoutY(h * 0.425);

        //Doppel numbers
        VBox housesNumbers = new VBox(h * 0.025,
                buildLabel("streetInfo_number_RentOneHouse",  street.getRentHouse()[0] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentTowHouse", street.getRentHouse()[1] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentThreeHouse", street.getRentHouse()[2] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentFourHouse", street.getRentHouse()[3] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentHotel", street.getRentHotel() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );
        housesNumbers.setLayoutY(h * 0.425);
        housesNumbers.layoutXProperty().bind(box.widthProperty().subtract(housesNumbers.widthProperty()));

        //Info house price
        Label infoHousePrice = buildLabel("streetInfo_info_HousePrice", "Haus kostet:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.841);
        Label housePrice = buildLabel("streetInfo_number_HousePrice",  street.getHousePrice() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.841);
        housePrice.layoutXProperty().bind(box.widthProperty().subtract(housePrice.widthProperty()));

        //Info hotel price
        Label infoHotelPrice = buildLabel("streetInfo_info_HotelPrice", "Hotel kostet:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.92);

        Label hotelPriceNumberHouses = buildLabel("", "4", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        hotelPriceNumberHouses.setLayoutY(h * 0.92);
        hotelPriceNumberHouses.setLayoutX(w * 0.46);

        Pane hotelHouseSymbole = buildHouseSymbole(w * 0.08, w * 0.06);
        hotelHouseSymbole.setLayoutY(h * 0.94);
        hotelHouseSymbole.setLayoutX(w * 0.52);

        Label hotelPrice = buildLabel("streetInfo_number_HotelPrice", "u. " + street.getHotelPrice() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        hotelPrice.setLayoutY(h * 0.92);
        hotelPrice.setLayoutX(w * 0.60);
        hotelPrice.layoutXProperty().bind(box.widthProperty().subtract(hotelPrice.widthProperty()));

        //Houses nummber indicator
        Pane indicatorHouse;
        if (street.getHouseNumber() > 0) {
            double indicatorY =  0.416 + (0.083 * (street.getHouseNumber() - 1));
            indicatorHouse = new Pane(
                buildLine("streetInfo_indicatorLine_Top", new Point2D(0.01, h * indicatorY), new Point2D(w * 0.99, h * indicatorY), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor()),
                buildLine("streetInfo_indicatorLine_Buttom", new Point2D(0.01, h * (indicatorY + 0.083)), new Point2D(w * 0.99, h * (indicatorY + 0.083)), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor())
            );

        } else if (street.getHouseNumber() == -1) {
            double indicatorY =  0.416 + (0.083 * 4);
            indicatorHouse = new Pane(
                buildLine("streetInfo_indicatorLine_Top", new Point2D(0.01, h * indicatorY), new Point2D(w * 0.99, h * indicatorY), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor()),
                buildLine("streetInfo_indicatorLine_Buttom", new Point2D(0.01, h * (indicatorY + 0.083)), new Point2D(w * 0.99, h * (indicatorY + 0.083)), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor())
            );

        } else if (street.ownerHasFullColor()) {
            double indicatorY =  0.416 + (0.083 * -1);
            indicatorHouse = new Pane(
                buildLine("streetInfo_indicatorLine_Top", new Point2D(0.01, h * indicatorY), new Point2D(w * 0.99, h * indicatorY), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor()),
                buildLine("streetInfo_indicatorLine_Buttom", new Point2D(0.01, h * (indicatorY + 0.083)), new Point2D(w * 0.99, h * (indicatorY + 0.083)), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor())
            );
        } else if (street.isOwned()) {
            double indicatorY =  0.416 + (0.083 * -2);
            indicatorHouse = new Pane(
                buildLine("streetInfo_indicatorLine_Top", new Point2D(0.01, h * indicatorY), new Point2D(w * 0.99, h * indicatorY), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor()),
                buildLine("streetInfo_indicatorLine_Buttom", new Point2D(0.01, h * (indicatorY + 0.083)), new Point2D(w * 0.99, h * (indicatorY + 0.083)), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor())
            );
        } else {
            indicatorHouse = new Pane();
        }


        //Adding all nodes
        box.getChildren().addAll(
            buildRectangle("streetInfo_Background", w, h, ProgramColor.BUILD_STREET_INFO.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), w * 0.02),
            buildRectangle("streetInfo_ColorIndicator", w, h * 0.17, street.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), w * 0.02),
            name,
            infoPropertyPrice,
            propertyPrice,
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * 0.25), new Point2D(w * 0.99, h * 0.25), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoRentAlone,
            rentAlone,
            buildLine("streetInfo_separatingLine_RentOneStreet|RentFullStreet", new Point2D(0.01, h * 0.333), new Point2D(w * 0.99, h * 0.333), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoRentAll,
            rentAll,
            buildLine("streetInfo_separatingLine_RentFullStreet|RentHosues", new Point2D(0.01, h * 0.416), new Point2D(w * 0.99, h * 0.416), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            houses,
            housesNumbers,
            buildLine("streetInfo_separatingLine_RentHouses|HousePrice", new Point2D(0.01, h * 0.831), new Point2D(w * 0.99, h * 0.831), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoHousePrice,
            housePrice,
            doppelPoints,
            buildLine("streetInfo_separatingLine_HousePrice|HotelPrice", new Point2D(0.01, h * 0.914), new Point2D(w * 0.99, h * 0.914), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoHotelPrice,
            hotelPriceNumberHouses,
            hotelHouseSymbole,
            hotelPrice,
            indicatorHouse
        );
        
        /*
        //Lienen für die verschieden Abteile. Bei fertigstellung löschen!
        double plus = 0.083;
        box.getChildren().addAll(
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * 0.25), new Point2D(w * 0.99, h * 0.25), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + plus)), new Point2D(w * 0.99, h * (0.25 + plus)), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 2))), new Point2D(w * 0.99, h * (0.25 + (plus * 2))), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 3))), new Point2D(w * 0.99, h * (0.25 + (plus * 3))), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 4))), new Point2D(w * 0.99, h * (0.25 + (plus * 4))), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 5))), new Point2D(w * 0.99, h * (0.25 + (plus * 5))), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 6))), new Point2D(w * 0.99, h * (0.25 + (plus * 6))), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 7))), new Point2D(w * 0.99, h * (0.25 + (plus * 7))), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 8))), new Point2D(w * 0.99, h * (0.25 + (plus * 8))), w * 0.02, ProgramColor.NULL_COLOR.getColor()),
            buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * (0.25 + (plus * 9))), new Point2D(w * 0.99, h * (0.25 + (plus * 9))), w * 0.02, ProgramColor.NULL_COLOR.getColor())        
        );
         */
             
        return box;

    }

    private static HBox createHouseRow(double houseWidth, double houseHeight, int houseNumber) {
        if (!(houseNumber <= 0)) {
            HBox houseRow = new HBox();
            houseRow.setAlignment(Pos.CENTER);

            for (int i = 0; i < houseNumber; i++)
                houseRow.getChildren().add(buildHouseSymbole(houseWidth, houseHeight));

            return houseRow;
        } else throw new IllegalArgumentException("You cannot create a row of houses with " + houseNumber + " houses");
    }
    private static Pane buildHouseSymbole(double houseWidth, double houseHeight) {
        Pane house = new Pane();
        house.setId("streetInfo_HouseSymbole");

        Polygon houseTop = buildTriangle("streetInfo_houseSymbole_Top", new Point2D(-(houseWidth * 0.025), 0), new Point2D(houseWidth * 0.525, -(houseHeight * 0.30)), new Point2D(houseWidth * 1.025, 0), ProgramColor.STREETS_HOUSE.getColor(), ProgramColor.BORDER_COLOR_DARK.getColor());
        Rectangle houseBotton = buildRectangle("streetInfo_houseSymbole_Botton", houseWidth, houseHeight * 0.70, ProgramColor.STREETS_HOUSE.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), 0);

        house.getChildren().addAll(houseTop, houseBotton);
        return house;
    }

    private static HBox buildHotelSymbole(double houseWidth, double houseHeight) {
        HBox hotel = new HBox();
        hotel.setId("streetInfo_HouseSymbole");
        hotel.setAlignment(Pos.CENTER);

        hotel.getChildren().add(new Pane(
                buildTriangle("streetInfo_hotelSymbole_Top", new Point2D(-(houseWidth * 0.025), 0), new Point2D(houseWidth * 0.525, -(houseHeight * 0.30)), new Point2D(houseWidth * 1.025, 0), ProgramColor.STREETS_HOTEL.getColor(), ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildRectangle("streetInfo_hotelSymbole_Botton", houseWidth, houseHeight * 0.70, ProgramColor.STREETS_HOTEL.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), 0)
        ));
        return hotel;
    }
}
