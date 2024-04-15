package de.sandwich.GUI.Game.Displays.DisplayOne;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLine;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildTriangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;


import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Fields.Street;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne;
import javafx.animation.FadeTransition;
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
import javafx.util.Duration;

public class BuildDisplay extends Pane {

    private final double WIDTH;
    private final double HEIGHT;

    private final GameDisplayControllerOne rootDisplay;

    private Pane playerPane = new Pane();
    private Pane streetInfo = new Pane();

    private Rectangle buttonBackground;
    private Label buttonText;

    private Player activePlayer;

    public BuildDisplay(double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_displayOne_BuildDisplay");

        this.WIDTH = width;
        this.HEIGHT = height;
        this.rootDisplay = rootDisplay;

        streetInfo.setLayoutX(WIDTH / 2);
        streetInfo.setLayoutY(-(HEIGHT * 0.05));

        buttonBackground = buildRectangle("gameScene_displayOne_buildDisplay_buyButton_Background", width * 0.45, height * 0.13, ProgramColor.STREETS_HOUSE.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), WIDTH * 0.008);
        buttonBackground.setArcHeight(WIDTH * 0.05);
        buttonBackground.setArcWidth(WIDTH * 0.05);

        buttonText = buildLabel("gameScene_displayOne_buildDisplay_buyButton_Text", "NULL", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.04), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());

        StackPane button = new StackPane(buttonBackground, buttonText);
        button.setId("gameScene_displayOne_buildDisplay_BuyButton");
        
        button.setLayoutX(WIDTH / 2 - width * 0.225);
        button.setLayoutY(height * 0.825);

        getChildren().addAll(
            buildRectangle("gameScene_displayOne_buildDisplay_Background", width, height, ProgramColor.BACKGROUND.getColor(), false, null, 0), playerPane,
            streetInfo,
            button
        );

    }

    public void display(Player p) {
        if (!playerPane.getChildren().isEmpty()) {
            playerPane.getChildren().clear();
        }

        activePlayer = p;

        Pane playerDisplay = GameDisplayControllerOne.buildPlayer(WIDTH * 0.38, HEIGHT * 0.60, ProgramColor.BANK_PLAYER_BACKGROUND.getColor(), p);

        Rectangle[] streets = GameDisplayControllerOne.buildStreetInventar(WIDTH * 0.38, HEIGHT * 0.60, p);

        Pane streetDisplay = new Pane();

        streetInfo.setVisible(false);

        System.out.println("Das Streetdisplay wird Displayd");

        for (Rectangle sObject : streets) {
            streetDisplay.getChildren().add(sObject);
            int fieldNumber = Integer.parseInt(sObject.getId().substring(12, 14));

            sObject.setOnMouseClicked(mouseEvent -> {

                if (Game.getFields().get(fieldNumber) instanceof Street street) {
                    if (street.getOwner() == activePlayer) {
                        
                        if (streetInfo.isVisible()) {
                            FadeTransition transition = new FadeTransition(Duration.seconds(0.8), streetInfo);

                            transition.setFromValue(1);
                            transition.setToValue(0);

                            transition.play();

                            transition.setOnFinished(actionEvent -> setStreetDisplay(street, WIDTH * 0.35, HEIGHT * 0.65));

                        } else {
                            setStreetDisplay(street, WIDTH * 0.35, HEIGHT * 0.65);
                        }
                                   
                    }
                }
            });

        }

        playerPane.setLayoutY(HEIGHT * 0.18);
        playerPane.setLayoutX(WIDTH * 0.05);

        playerPane.getChildren().addAll(playerDisplay, streetDisplay, streetInfo);
    }

    private void setStreetDisplay(Street street, double w, double h) {
        streetInfo.getChildren().clear();
        streetInfo.setMaxSize(w, h);

        //Street name
        Label name = buildLabel("streetInfo_Name", street.getName(), Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.125), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor());
        centeringChildInPane(name, streetInfo);
        name.setLayoutY(h * 0.03);

        //Fonts
        Font infoTextFont = Font.font(Main.TEXT_FONT, w * 0.08);
        Font infoFont = Font.font(Main.TEXT_FONT, FontWeight.BOLD, w * 0.08);

        //Property price info
        Label infoPropertyPrice = buildLabel("streetInfo_info_PropertyPrice", "Grundstückswert:", infoTextFont, TextAlignment.RIGHT, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.20);
        Label propertyPrice = buildLabel("streetInfo_number_PropertyPrice", street.getSalePrice() + "€ ", infoFont, TextAlignment.LEFT, ProgramColor.TEXT_COLOR.getColor(), 0 ,h * 0.20);
        propertyPrice.layoutXProperty().bind(streetInfo.widthProperty().subtract(propertyPrice.widthProperty()));

        //Rent alone info
        Label infoRentAlone = buildLabel("streetInfo_info_RentAlone", "Miete alleine:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.28);
        Label rentAlone = buildLabel("streetInfo_number_RentAlone", street.getRent() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.28);
        rentAlone.layoutXProperty().bind(streetInfo.widthProperty().subtract(rentAlone.widthProperty()));

        //Rent with all streets from the same color
        Label infoRentAll = buildLabel("streetInfo_info_RentAll", "Miete Gruppe:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.36);
        Label rentAll = buildLabel("streetInfo_number_RentAll",  (street.getRent() * 2) + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.36);
        rentAll.layoutXProperty().bind(streetInfo.widthProperty().subtract(rentAll.widthProperty()));

        //Rent houses
        double houseWidth = w * 0.09;
        double houseHeight = w * 0.07;

        //Houses
        VBox houses = new VBox(houseWidth * 0.20,
                createHouseRow(houseWidth, houseHeight, 1),
                createHouseRow(houseWidth, houseHeight, 2),
                createHouseRow(houseWidth, houseHeight, 3),
                createHouseRow(houseWidth, houseHeight, 4),
                buildHotelSymbole(houseWidth, houseHeight)
        );
        houses.setAlignment(Pos.CENTER);
        houses.setLayoutX(w * 0.01);
        houses.setLayoutY(h * 0.48);

        //Doppel points
        VBox doppelPoints = new VBox(w * 0.02,
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor()),
                buildLabel("streetInfo_info_housesRent_Colon", ":", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor())
        );
        doppelPoints.setLayoutY(h * 0.45);
        doppelPoints.setLayoutX(w * 0.60);

        //Doppel numbers
        VBox housesNumbers = new VBox(w * 0.02,
                buildLabel("streetInfo_number_RentOneHouse",  street.getRentHouse()[0] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentTowHouse", street.getRentHouse()[1] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentThreeHouse", street.getRentHouse()[2] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentFourHouse", street.getRentHouse()[3] + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor()),
                buildLabel("streetInfo_number_RentHotel", street.getRentHotel() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );
        housesNumbers.setLayoutY(h * 0.45);
        housesNumbers.layoutXProperty().bind(streetInfo.widthProperty().subtract(housesNumbers.widthProperty()));

        //Info house price
        Label infoHousePrice = buildLabel("streetInfo_info_HousePrice", "Haus kostet:", infoTextFont, TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, h * 0.85);
        Label housePrice = buildLabel("streetInfo_number_HousePrice",  street.getHousePrice() + "€ ", infoFont, TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, h * 0.85);
        housePrice.layoutXProperty().bind(streetInfo.widthProperty().subtract(housePrice.widthProperty()));

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
        hotelPrice.layoutXProperty().bind(streetInfo.widthProperty().subtract(hotelPrice.widthProperty()));

        double lineYMultiplicator = ((street.getHouseNumber() * 0.08) + 0.44);
        Pane indicatorHouse = new Pane(
            buildLine("streetInfo_indicatorLine_Top", new Point2D(0.01, h * lineYMultiplicator), new Point2D(w * 0.99, h * lineYMultiplicator), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor()),
            buildLine("streetInfo_indicatorLine_Buttom", new Point2D(0.01, h * (lineYMultiplicator + 0.08)), new Point2D(w * 0.99, h * (lineYMultiplicator + 0.08)), w * 0.018, ProgramColor.BUILD_STREET_INFO_HOUSEINDICATOR.getColor())
        );


        //Adding all nodes
        streetInfo.getChildren().addAll(
                buildRectangle("streetInfo_Background", w, h, ProgramColor.BUILD_STREET_INFO.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), w * 0.02),
                buildRectangle("streetInfo_ColorIndicator", w, h * 0.20, street.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), w * 0.02),
                name,
                infoPropertyPrice,
                propertyPrice,
                buildLine("streetInfo_separatingLine_PropertyPrice|RentOneStreet", new Point2D(0.01, h * 0.28), new Point2D(w * 0.99, h * 0.28), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoRentAlone,
                rentAlone,
                buildLine("streetInfo_separatingLine_RentOneStreet|RentFullStreet", new Point2D(0.01, h * 0.36), new Point2D(w * 0.99, h * 0.36), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoRentAll,
                rentAll,
                buildLine("streetInfo_separatingLine_RentFullStreet|RentHosues", new Point2D(0.01, h * 0.44), new Point2D(w * 0.99, h * 0.44), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                houses,
                housesNumbers,
                buildLine("streetInfo_separatingLine_RentHouses|HousePrice", new Point2D(0.01, h * 0.85), new Point2D(w * 0.99, h * 0.85), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoHousePrice,
                housePrice,
                doppelPoints,
                buildLine("streetInfo_separatingLine_HousePrice|HotelPrice", new Point2D(0.01, h * 0.91), new Point2D(w * 0.99, h * 0.91), w * 0.02, ProgramColor.BORDER_COLOR_DARK.getColor()),
                infoHotelPrice,
                hotelPriceNumberHouses,
                hotelHouseSymbole,
                hotelPrice,
                indicatorHouse
        );

        streetInfo.setVisible(true);

        FadeTransition transition = new FadeTransition(Duration.seconds(0.8), streetInfo);

        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();

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
