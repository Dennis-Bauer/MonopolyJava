package de.sandwich.GUI.Game.Displays.DisplayMiddle;

import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.Fields.Utilitie;
import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLine;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.buildPlus;
import static de.sandwich.Fields.Street.buildHotelSymbole;
import static de.sandwich.Fields.Street.buildHouseSymbole;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.WrongNodeException;

public class FieldInfoDisplay extends Pane {

    private final double WIDTH;
    private final double HEIGHT;

    private Pane infoBox = new Pane();
    private StackPane removeButton;

    public FieldInfoDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("StreetInfoDisplay");
        setMaxSize(width, height);
        
        this.WIDTH = width;
        this.HEIGHT = height;

        removeButton = buildPlus("streetInfo_removeButton_", WIDTH * 0.01, WIDTH * 0.10, 45, WIDTH * 0.01, ProgramColor.CHANCEL_BUTTONS.getColor(), ProgramColor.SYMBOLE_COLOR.getColor(), 0, 0);
        removeButton.setLayoutX(WIDTH - WIDTH * 0.08);

        removeButton.setOnMouseClicked(mouseEvent -> rootDisplay.removeDisplay());
    }

    public void buildUtilitieDisplay(Utilitie utilitie) {
        if (!getChildren().isEmpty())
        getChildren().clear();

        infoBox = getUtilitieInfoBox(utilitie, WIDTH, HEIGHT);
        infoBox.getChildren().add(removeButton);
        
        getChildren().add(infoBox);
    }

    public void buildStationDisplay(Station station) {
        if (!getChildren().isEmpty())
            getChildren().clear();

        infoBox = getStationInfoBox(station, WIDTH, HEIGHT);
        infoBox.getChildren().add(removeButton);
        
        getChildren().add(infoBox);
    }

    public void buildStreetDisplay(Street street) {
        if (!getChildren().isEmpty())
            getChildren().clear();

        infoBox = getStreetInfoBox(street, WIDTH, HEIGHT);
        infoBox.getChildren().add(removeButton);
        
        getChildren().add(infoBox);
    }

    public static Pane getStreetInfoBox(Street street, double w, double h) {
        final double heightMultiplicator = 0.083;

        Pane box = new Pane();
        box.setMaxSize(w, h);

        //Street name
        Label name = buildLabel("streetInfo_Name", street.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.1), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor());
        centeringChildInPane(name, box);
        name.setLayoutY(h * 0.02);

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
        Pane houses = new Pane(
                createHouseRow(houseWidth, houseHeight, 1, houseWidth * 2.4, 0),
                createHouseRow(houseWidth, houseHeight, 2, houseWidth * 1.7, h * heightMultiplicator),
                createHouseRow(houseWidth, houseHeight, 3, houseWidth * 0.87, h * (heightMultiplicator * 2)),
                createHouseRow(houseWidth, houseHeight, 4, 0, h * (heightMultiplicator * 3)),
                buildHotelSymbole(houseWidth, houseHeight, houseWidth * 2.4, h * (heightMultiplicator * 4))
        );
        houses.setLayoutX(w * 0.04);
        houses.setLayoutY(h * (0.28 + (2 * heightMultiplicator)));

        //Doppel points
        Pane doppelPoints = new Pane(
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 0),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * heightMultiplicator),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * (2 * heightMultiplicator)),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * (3 * heightMultiplicator)),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * (4 * heightMultiplicator))
        );
        doppelPoints.setLayoutX(w * 0.57);
        doppelPoints.setLayoutY(h * (0.26 + (2 * heightMultiplicator)));

        //Doppel numbers
        Pane housesNumbers = new Pane(
                buildLabel("streetInfo_number_RentOneHouse",  street.getRentHouse()[0] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, 0),
                buildLabel("streetInfo_number_RentTowHouse", street.getRentHouse()[1] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * heightMultiplicator),
                buildLabel("streetInfo_number_RentThreeHouse", street.getRentHouse()[2] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * (2 * heightMultiplicator)),
                buildLabel("streetInfo_number_RentFourHouse", street.getRentHouse()[3] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * (3 * heightMultiplicator)),
                buildLabel("streetInfo_number_RentHotel", street.getRentHotel() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * (4 * heightMultiplicator))
        );
        housesNumbers.setLayoutY(h * (0.26 + (2 * heightMultiplicator)));
        housesNumbers.layoutXProperty().bind(box.widthProperty().subtract(housesNumbers.widthProperty()));

        for (int i = 0; i < housesNumbers.getChildren().size(); i++) {
            if (housesNumbers.getChildren().get(i) instanceof Label l) {
                l.layoutXProperty().bind(box.widthProperty().subtract(l.widthProperty()));
            } else throw new WrongNodeException("houseNumbers");
        }

        //Info house price
        Label infoHousePrice = buildLabel("streetInfo_info_HousePrice", "Haus kostet:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.841);
        Label housePrice = buildLabel("streetInfo_number_HousePrice",  street.getHousePrice() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.841);
        housePrice.layoutXProperty().bind(box.widthProperty().subtract(housePrice.widthProperty()));

        //Info hotel price
        Label infoHotelPrice = buildLabel("streetInfo_info_HotelPrice", "Hotel kostet:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.92);

        Label hotelPriceNumberHouses = buildLabel("", "4", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        hotelPriceNumberHouses.setLayoutY(h * 0.92);
        hotelPriceNumberHouses.setLayoutX(w * 0.46);

        Pane hotelHouseSymbole = buildHouseSymbole(w * 0.08, w * 0.06, 0, 0);
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
            buildRectangle("streetInfo_Background", w, h, ProgramColor.FIELD_INFO_BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), w * 0.02),
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
             
        return box;

    }

    public static Pane getStationInfoBox(Station station, double w, double h) {
        final double heightMultiplicator = 0.13833;

        Pane box = new Pane();
        box.setMaxSize(w, h);

        //Station name
        Label name = buildLabel("stationInfo_Name", station.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.125), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor());
        centeringChildInPane(name, box);
        name.setLayoutY(h * 0.03);

        //Fonts
        Font infoTextFont = Font.font(Main.TEXT_FONT, w * 0.095);
        Font infoFont = Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.095);

        //Property price info
        Label infoPropertyPrice = buildLabel("stationInfo_info_PropertyPrice", "Grundstückswert:", infoTextFont, TextAlignment.RIGHT, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.17);
        centeringChildInPane(infoPropertyPrice, box);
        Label propertyPrice = buildLabel("stationInfo_number_PropertyPrice", station.getPrice() + "€ ", infoFont, TextAlignment.LEFT, ProgramColor.TEXT_COLOR.getColor(), 0 ,h * 0.22);
        centeringChildInPane(propertyPrice, box);

        //Rent alone info
        Label infoRentAlone = buildLabel("stationInfo_info_RentAlone", "Miete alleine:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.31);
        centeringChildInPane(infoRentAlone, box);
        Label rentAlone = buildLabel("stationInfo_number_RentAlone", station.RENT_ALONE + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.36);
        centeringChildInPane(rentAlone, box);

        //Rent two stations info
        Label infoRentTwo = buildLabel("stationInfo_info_RentTwo", "Miete 2 Bahnhöfe:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.45);
        centeringChildInPane(infoRentTwo, box);
        Label rentTwo = buildLabel("stationInfo_number_RentTwo", station.RENT_TWO + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.50);
        centeringChildInPane(rentTwo, box);

        //Rent three stations info
        Label infoRentThree = buildLabel("stationInfo_info_RentThree", "Miete 3 Bahnhöfe:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.59);
        centeringChildInPane(infoRentThree, box);
        Label rentThree = buildLabel("stationInfo_number_RentThree", station.RENT_THREE + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.64);
        centeringChildInPane(rentThree, box);

        //Rent four stations info
        Label infoRentFour = buildLabel("stationInfo_info_RentFour", "Miete 4 Bahnhöfe:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.73);
        centeringChildInPane(infoRentFour, box);
        Label rentFour = buildLabel("stationInfo_number_RentFour", station.RENT_FOUR + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.78);
        centeringChildInPane(rentFour, box);

        //Rent this station info
        Label infoRentThis = buildLabel("stationInfo_info_RentThis", "Dieser Bahnhof:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.86);
        centeringChildInPane(infoRentThis, box);
        Label rentThis = buildLabel("stationInfo_number_RentFour", "---", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.91);
        centeringChildInPane(rentThis, box);

        if (station.isOwned())
            rentThis.setText(station.getRent() + "€ ");

        double startLineY = 0.17;
    
        //Adding all nodes
        box.getChildren().addAll(
            buildRectangle("stationInfo_Background", w, h, ProgramColor.FIELD_INFO_BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), w * 0.02),
            buildLine("stationInfo_separatingLine_Header|Rent", new Point2D(0.01, h * startLineY), new Point2D(w * 0.99, h * startLineY), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            name,
            infoPropertyPrice,
            propertyPrice,
            buildLine("stationInfo_separatingLine_PropertyPrice|RentOneStation", new Point2D(0.01, h * (startLineY + heightMultiplicator)), new Point2D(w * 0.99, h * (startLineY + heightMultiplicator)), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoRentAlone,
            rentAlone,
            buildLine("stationInfo_separatingLine_RentOneStation|RentTwoStations", new Point2D(0.01, h * (startLineY + (heightMultiplicator * 2))), new Point2D(w * 0.99, h * (startLineY + (heightMultiplicator * 2))), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoRentTwo,
            rentTwo,
            buildLine("stationInfo_separatingLine_RentTwoStation|RentThreeStations", new Point2D(0.01, h * (startLineY + (heightMultiplicator * 3))), new Point2D(w * 0.99, h * (startLineY + (heightMultiplicator * 3))), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoRentThree,
            rentThree,
            buildLine("stationInfo_separatingLine__RentThreeStation|RentFourStations", new Point2D(0.01, h * (startLineY + (heightMultiplicator * 4))), new Point2D(w * 0.99, h * (startLineY + (heightMultiplicator * 4))), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoRentFour,
            rentFour,
            buildLine("stationInfo_separatingLine_RentFourStations|RentThis", new Point2D(0.01, h * (startLineY + (heightMultiplicator * 5))), new Point2D(w * 0.99, h * (startLineY + (heightMultiplicator * 5))), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoRentThis,
            rentThis
        );
             
        return box;

    }

    public static Pane getUtilitieInfoBox(Utilitie utilitie, double w, double h) {
        final double heightMultiplicator = 0.35;

        Pane box = new Pane();
        box.setMaxSize(w, h);

        //Station name
        Label name = buildLabel("utilitieInfo_Name", utilitie.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.1), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor());
        centeringChildInPane(name, box);
        name.setLayoutY(h * 0.02);

        //Fonts
        Font infoTextFont = Font.font(Main.TEXT_FONT, w * 0.075);

        Label textOne = buildLabel("utilitieInfo_info_MultiplicatorOne", buildLongText("Wenn der Besitzter nur einer", "der Werke besitzt, ist die Mite", "bei seinem Werk 80mal die", "Augenzahl der Würfel!"), infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.17);
        centeringChildInPane(textOne, box);

        Label textTwo = buildLabel("utilitieInfo_info_MultiplicatorTwo", buildLongText("Wenn der Besitzter beide", "der Werke besitzt, ist die Mite", "bei beiden Werken 200mal die", "Augenzahl der Würfel!"), infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.52);
        centeringChildInPane(textTwo, box);

        if (utilitie.isOwned()) {
            if (!utilitie.hasOwnerBoth()) {
                textOne.setTextFill(ProgramColor.TEXT_COLOR.getColor());
                textTwo.setTextFill(ProgramColor.BORDER_COLOR_DARK.getColor());
            } else {
                textTwo.setTextFill(ProgramColor.TEXT_COLOR.getColor());
                textOne.setTextFill(ProgramColor.BORDER_COLOR_DARK.getColor());
            }
        }
        
        //Property price info
        Label infoPropertyPrice = buildLabel("utilitieInfo_info_PropertyPrice", "Grundstückswert:", Font.font(Main.TEXT_FONT, w * 0.095), TextAlignment.RIGHT, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.87);
        centeringChildInPane(infoPropertyPrice, box);
        Label propertyPrice = buildLabel("utilitieInfo_number_PropertyPrice", utilitie.getPrice() + "€ ", Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.095), TextAlignment.LEFT, ProgramColor.TEXT_COLOR.getColor(), 0 ,h * 0.92);
        centeringChildInPane(propertyPrice, box);

        double startLineY = 0.17;
    
        //Adding all nodes
        box.getChildren().addAll(
            buildRectangle("stationInfo_Background", w, h, ProgramColor.FIELD_INFO_BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), w * 0.02),
            name,
            buildLine("stationInfo_separatingLine_Header|Rent", new Point2D(0.01, h * startLineY), new Point2D(w * 0.99, h * startLineY), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            textOne,
            buildLine("stationInfo_separatingLine_PropertyPrice|RentOneStation", new Point2D(0.01, h * (startLineY + heightMultiplicator)), new Point2D(w * 0.99, h * (startLineY + heightMultiplicator)), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            textTwo,
            buildLine("stationInfo_separatingLine_RentOneStation|RentTwoStations", new Point2D(0.01, h * (startLineY + (heightMultiplicator * 2))), new Point2D(w * 0.99, h * (startLineY + (heightMultiplicator * 2))), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
            infoPropertyPrice,
            propertyPrice
        );  
         
             
        return box;

    }

    private static HBox createHouseRow(double houseWidth, double houseHeight, int houseNumber, double x, double y) {
        if (!(houseNumber <= 0)) {
            HBox houseRow = new HBox();
            houseRow.setAlignment(Pos.CENTER);

            for (int i = 0; i < houseNumber; i++)
                houseRow.getChildren().add(buildHouseSymbole(houseWidth, houseHeight, 0, 0));

            houseRow.setLayoutY(y);
            houseRow.setLayoutX(x);

            return houseRow;
        } else throw new IllegalArgumentException("You cannot create a row of houses with " + houseNumber + " houses");
    }

    public double getWIDTH() {
        return WIDTH;
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

}
