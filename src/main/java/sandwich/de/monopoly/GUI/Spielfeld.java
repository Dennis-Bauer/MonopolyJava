package sandwich.de.monopoly.GUI;

import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Enums.ExtraFields;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Utilities;

import java.util.Objects;

import static sandwich.de.monopoly.Main.textFont;


public class Spielfeld {

    private static final Pane[] fields = new Pane[36];
    private static final Pane[] corners = new Pane[4];
    private static final double middleRectangleRatio = 1.4;
    private static double fontSize;
    private static double borderWidth;

    public static Pane buildGameBoard(double rotate, double size, Color boardColor) {
        Pane root = new Pane();
        root.setId("root");
        root.setStyle("-fx-background-color: black;");
        StackPane board = new StackPane();
        board.setId("gameBoard");
        board.setAlignment(Pos.CENTER);
        fontSize = ((size / middleRectangleRatio) / 9) / 8;
        borderWidth = ((size / middleRectangleRatio) / 9) / 25;

        //Black Field
        Rectangle f = Utilities.buildRectangle("Test" ,size, size, Color.BLACK, true, Color.BLACK, 0);
        Rectangle v = Utilities.buildRectangle("Test" ,size / middleRectangleRatio, size/ middleRectangleRatio, boardColor, true, Color.BLACK, 0);

        board.getChildren().addAll(f, v);

        //Creating Fields
        double width = (size / middleRectangleRatio) / 9;
        double height = (size - size / middleRectangleRatio) / 2;
        fields[0] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 00"), 200, boardColor, width, height);
        fields[1] = buildExtraPayField(ExtraFields.SPOTIFY_PREMIUM, 200, boardColor, width, height);
        fields[2] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 02"), 200, boardColor, width, height);
        fields[3] = buildGetChanceCard(ChanceColors.RED ,boardColor, width, height);
        fields[4] = buildStation(Utilities.buildLongText("Nord-", "Bahnhof"), 200, boardColor, width, height);
        fields[5] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 05"), 200, boardColor, width, height);
        fields[6] = buildGetCommunityCard(boardColor, width, height);
        fields[7] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 07"), 200, boardColor, width, height);
        fields[8] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 08"), 200, boardColor, width, height);
        fields[9] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 09"), 200, boardColor, width, height);
        fields[10] = buildExtraPayField(ExtraFields.HESSLER_SCHULDEN, 200, boardColor, width, height);
        fields[11] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 11"), 200, boardColor, width, height);
        fields[12] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 12"), 200, boardColor, width, height);
        fields[13] = buildStation(Utilities.buildLongText("West-", "Bahnhof"), 200, boardColor, width, height);
        fields[14] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 13"), 200, boardColor, width, height);
        fields[15] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 15"), 200, boardColor, width, height);
        fields[16] = buildGetChanceCard(ChanceColors.BLUE, boardColor, width, height);
        fields[17] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 17"), 200, boardColor, width, height);
        fields[18] = buildStreet(Color.YELLOW, Utilities.buildLongText("Bank", "Straße 18"), 200, boardColor, width, height);
        fields[19] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 19"), 200, boardColor, width, height);
        fields[20] = buildGetCommunityCard(boardColor, width, height);
        fields[21] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 21"), 200, boardColor, width, height);
        fields[22] = buildStation(Utilities.buildLongText("Süd-", "Bahnhof"), 200, boardColor, width, height);
        fields[23] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 23"), 200, boardColor, width, height);
        fields[24] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 24"), 200, boardColor, width, height);
        fields[25] = buildExtraPayField(ExtraFields.NAME_THREE, 200, boardColor, width, height);
        fields[26] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 26"), 200, boardColor, width, height);
        fields[27] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 27"), 200, boardColor, width, height);
        fields[28] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 28"), 200, boardColor, width, height);
        fields[29] = buildGetChanceCard(ChanceColors.GREEN, boardColor, width, height);
        fields[30] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 30"), 200, boardColor, width, height);
        fields[31] = buildStation(Utilities.buildLongText("Haupt-", "Bahnhof"), 200, boardColor, width, height);
        fields[32] = buildExtraPayField(ExtraFields.NAME_FOUR, 200, boardColor, width, height);
        fields[33] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 33"), 200, boardColor, width, height);
        fields[34] = buildGetCommunityCard(boardColor, width, height);
        fields[35] = buildStreet(Color.ORANGE, Utilities.buildLongText("Bank", "Straße 35"), 200, boardColor, width, height);

        //Creating Corners
        corners[0] = buildStart(boardColor, height);
        corners[1] = buildJail(boardColor, height);
        corners[2] = buildFreeParking(boardColor, height);
        corners[3] = buildGoToJail(boardColor, height);


        //Position fields
        for (int i = 0; i < 36; i++) {
            board.getChildren().add(fields[i]);
            StackPane.setAlignment(fields[i], Pos.TOP_LEFT);
            double rightCorner = ((size / middleRectangleRatio) + (size - size / middleRectangleRatio) / 2);
            if (i < 9) {
                fields[i].setTranslateY(size - height);
                fields[i].setTranslateX(height + (i * width));
            } else if (i < 18) {
                fields[i].rotateProperty().set(270);
                fields[i].setTranslateY( ((((10 * width) + (size / middleRectangleRatio)) - (i + 1) * width)) - (width / 2 - height / 2) );
                fields[i].setTranslateX(rightCorner + (height / 2 - width / 2));
            } else if (i < 27) {
                fields[i].rotateProperty().set(180);
                fields[i].setTranslateY(0);
                fields[i].setTranslateX((rightCorner - width) - width * (i - 18));
            } else {
                fields[i].rotateProperty().set(90);
                fields[i].setTranslateY(((height / 2) + width / 2) + width * (i - 27));
                fields[i].setTranslateX((height / 2) - width / 2);
            }
        }

        //Position Corners
        for (int i = 0; i < 4; i++) {
            board.getChildren().add(corners[i]);
            StackPane.setAlignment(corners[i], Pos.TOP_LEFT);
            double cornerCoordinate = ((size - size / middleRectangleRatio) / 2) + size / middleRectangleRatio;
            if(i == 0) {
                corners[i].setTranslateY(cornerCoordinate);
            } else if(i == 1) {
                corners[i].setRotate(90);
            } else if(i == 2) {
                corners[i].setRotate(180);
                corners[i].setTranslateX(cornerCoordinate);
            } else if(i == 3) {
                corners[i].setRotate(270);
                corners[i].setTranslateY(cornerCoordinate);
                corners[i].setTranslateX(cornerCoordinate);
            }
        }

        root.getChildren().addAll(board);

        board.setRotate(rotate);

        return root;
    }

    private static Pane buildStreet(Paint streetColor, String name, double price, Paint backgroundColor, double width, double height) {
        Pane field = new Pane();
        field.setId("street_field");
        field.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("street_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Rectangle colorIndicator = Utilities.buildRectangle("street_ColorIndicator" ,width, height/4, streetColor, true, Color.BLACK, borderWidth);
        Label nameIndicator = Utilities.buildLabel("street_NameIndicator", name, Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 3);
        Label priceIndicator = Utilities.buildLabel("street_PriceIndicator", (price + "€"), Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));

        Utilities.centeringChildInPane(nameIndicator, field);
        Utilities.centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, colorIndicator, nameIndicator, priceIndicator);

        return field;
    }

    private static Pane buildStart(Paint backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("start_field");
        field.setMaxSize(size, size);

        Rectangle background = Utilities.buildRectangle("corner_start_Background", size, size, backgroundColor, true, Color.BLACK, borderWidth);
        ImageView arrow = Utilities.createImageView("corner_start_Arrow" ,"/sandwich/de/monopoly/gameBoard/startArrow.png", size / 6, size / 1.25, (size -(size / 1.25)) / 2, (size -(size / 1.25)) / 2);
        Label text = Utilities.buildLabel("corner_start_Text", Utilities.buildLongText("LOS", "Bekomme 200", "beim drüber gehen"), Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK);

        text.widthProperty().addListener((obs, oldVal, newVal) -> {text.setTranslateX((size - newVal.doubleValue()) / 0.8); });
        text.heightProperty().addListener((obs, oldVal, newVal) -> {text.setTranslateY((newVal.doubleValue()) / 1.5); });
        text.setRotate(45);

        field.getChildren().addAll(background, arrow, text);
        return field;
    }

    private static Pane buildJail(Paint backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("jail_field");
        field.setMaxSize(size, size);

        Rectangle background = Utilities.buildRectangle("corner_jail_Background", size, size, backgroundColor, true, Color.BLACK, borderWidth);
        Rectangle backgroundJail = Utilities.buildRectangle("corner_jail_JailBackground", size / 2, size / 2, Color.ORANGE, true, Color.BLACK, borderWidth, size - size / 2, 0);
        ImageView jailMan = Utilities.createImageView("corner_jail_Man" ,"/sandwich/de/monopoly/gameBoard/jailMan.png", size / 3, size / 3, size - size / 2 + (((size / 2) - (size / 3) / 2) - size / 4), (((size / 2) - (size / 3) / 2) - size / 4));
        Label header = Utilities.buildLabel("corner_jail_Header", "Im", Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, size / 2 + size / 3, size / 18);
        Label footer = Utilities.buildLabel("corner_jail_FooterText", "Bau", Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, size / 1.9, size / 2.9);
        Label textOne = Utilities.buildLabel("corner_jail_FirstText", "Nur", Font.font(textFont, FontWeight.BOLD, fontSize * 1.5), TextAlignment.CENTER, Color.BLACK, 0, size / 5.5);
        Label textTwo = Utilities.buildLabel("corner_jail_SecondText", "zu besuch", Font.font(textFont, FontWeight.BOLD, fontSize * 1.5), TextAlignment.CENTER, Color.BLACK, 0, size / 1.3);

        Utilities.centeringChildInPane(textTwo, field);

        jailMan.setRotate(45);
        header.setRotate(45);
        footer.setRotate(45);
        textOne.setRotate(90);

        field.getChildren().addAll(background, backgroundJail, jailMan, header, footer, textOne, textTwo);
        return field;
    }

    private static Pane buildFreeParking(Paint backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("freeParking_field");
        field.setMaxSize(size, size);

        Rectangle background = Utilities.buildRectangle("corner_freeParking_Background", size, size, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = Utilities.buildLabel("corner_freeParking_Header", "Freies", Font.font(textFont, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, Color.BLACK, size / 5 + size / 3.25, size / 5.75);
        ImageView freeParking = Utilities.createImageView("corner_freeParking_Picture", "/sandwich/de/monopoly/gameBoard/freeParking.png", size / 1.5, size / 1.75, size / 4.75,size / 5);
        Label footer = Utilities.buildLabel("corner_freeParking_Footer", "Parken", Font.font(textFont, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, Color.BLACK, size / 8.25, size / 1.7);

        header.setRotate(45);
        freeParking.setRotate(45);
        footer.setRotate(45);

        field.getChildren().addAll(background, header, freeParking, footer);
        return field;
    }

    private static Pane buildGoToJail(Paint backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("goToJail_field");
        field.setMaxSize(size, size);

        Rectangle background = Utilities.buildRectangle("corner_goToJail_Background", size, size, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = Utilities.buildLabel("corner_goToJail_Header", "Geh ins", Font.font(textFont, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, Color.BLACK, size / 6 + size / 3.25, size / 5.75);
        ImageView freeParking = Utilities.createImageView("corner_goToJail_Picture", "/sandwich/de/monopoly/gameBoard/goToJail.png", size / 1.5, size / 1.75, size / 4.75,size / 5);
        Label footer = Utilities.buildLabel("corner_goToJail_Footer", "Gefängnis", Font.font(textFont, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, Color.BLACK, -(size / 15), size / 1.8);

        header.setRotate(45);
        freeParking.setRotate(45);
        footer.setRotate(45);

        field.getChildren().addAll(background, header, freeParking, footer);
        return field;
    }

    private static Pane buildStation(String stationName, double price, Paint backgroundColor ,double width, double height) {
        Pane field = new Pane();
        field.setId("station_field");
        field.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("station_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = Utilities.buildLabel("station_Header", stationName, Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 50);
        ImageView train = Utilities.createImageView("station_Image", "/sandwich/de/monopoly/gameBoard/train.png", width / 1.15, height / 3.7,(width - width / 1.15) / 2, height / 3);
        Label priceIndicator = Utilities.buildLabel("station_PriceIndicator", (price + "€"), Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));


        Utilities.centeringChildInPane(header, field);
        Utilities.centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, header, train, priceIndicator);
        return field;
    }

    private static Pane buildGetChanceCard(ChanceColors c, Paint backgroundColor ,double width, double height) {
        Pane field = new Pane();
        field.setId("getChanceCard_field");
        field.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("chance_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = Utilities.buildLabel("chance_Header", "Chance", Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 50);

        Image i = null;
        switch (c) {
            case RED ->
                i = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/gameBoard/chance_red.png")));
            case BLUE ->
                i = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/gameBoard/chance_blue.png")));
            case GREEN ->
                i = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/gameBoard/chance_green.png")));
        }

        ImageView image = Utilities.createImageView("chance_Image", i, width / 1.1, height / 1.6,(width - width / 1.15) / 2, height / 3.5);

        Utilities.centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);
        return field;
    }

    private static Pane buildGetCommunityCard(Paint backgroundColor ,double width, double height) {
        Pane field = new Pane();
        field.setId("getCommunityCard_field");
        field.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("community_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = Utilities.buildLabel("community_Header", Utilities.buildLongText("Gesellschafts", "Feld"), Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 50);
        ImageView image = Utilities.createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/communityChest.png", width / 1.1, height / 1.9,(width - width / 1.15) / 2, height / 3.5);

        Utilities.centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);
        return field;
    }

	private static Pane buildExtraPayField(ExtraFields f, int price, Paint backgroundColor, double width, double height) {
        Pane field = new Pane();
        field.setId("extraPay_field");
        field.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("extraPay_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = Utilities.buildLabel("extraPay_Header", "ERROR", Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 10);
        Label priceIndicator = Utilities.buildLabel("station_PriceIndicator", (price + "€"), Font.font(textFont, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));


        Utilities.centeringChildInPane(header, field);
        Utilities.centeringChildInPane(priceIndicator, field);

        ImageView picture = null;
        switch (f) {
            case SPOTIFY_PREMIUM -> {
                picture = Utilities.createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText(Utilities.buildLongText("Spotify", "Premium Abo"));
            }
            case HESSLER_SCHULDEN -> {
                picture = Utilities.createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/hessler.png", width / 3.4, height / 2.3,(width - width / 3.3) / 2, height / 2.7);
                header.setText(Utilities.buildLongText("Freu Hessler", "Schulden ab", "bezahlen"));
            }
            case NAME_THREE -> {
                picture = Utilities.createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText("NAME3");
            }
            case NAME_FOUR -> {
                picture = Utilities.createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText("NAME4");
            }
        }

        field.getChildren().addAll(background, header, picture, priceIndicator);
        return field;
	}

    private enum ChanceColors {
        RED,
        GREEN,
        BLUE;
    }
}

/*
Todo:
1. Für die extra Fleder die Methode benutzten die oben erstellt wurde
2. Enum ersteller für die jewaldigen arten der "Kraftwerke" (Hier auch die Enum klassen für die Karten erstellen)
3. Issue ab arbeiten!

Die höhe einer Straße wird immer Größe des Feldes / 9 (9 Felder sind an jeder Seite (ohne die Ecken)) 
*/
