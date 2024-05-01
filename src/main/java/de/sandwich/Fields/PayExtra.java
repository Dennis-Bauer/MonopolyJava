package de.sandwich.Fields;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import de.sandwich.Main;
import de.sandwich.Enums.ExtraFields;
import de.sandwich.Enums.ProgramColor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;


public class PayExtra extends Field{

    private final int PRICE;
    private final ExtraFields TYP;

    public PayExtra(int price, ExtraFields typ, double postion) {
        super(postion);
        
        this.PRICE = price;
        this.TYP = typ;
    }

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("extraPay_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("extraPay_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("extraPay_Header", "ERROR", Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 10);
        Label priceIndicator = buildLabel("station_PriceIndicator", (PRICE + "€"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));


        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        ImageView picture = null;
        switch (TYP) {
            case SPOTIFY_PREMIUM -> {
                picture = createImageView("community_Image", "/de/sandwich/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText(buildLongText("Spotify", "Premium-Abo"));
            }
            case HESSLER_SCHULDEN -> {
                picture = createImageView("community_Image", "/de/sandwich/monopoly/gameBoard/hessler.png", width / 3.4, height / 2.3,(width - width / 3.3) / 2, height / 2.7);
                header.setText(buildLongText("Freu Hessler", "Schulden ab", "bezahlen"));
            }
        }

        field.getChildren().addAll(background, header, picture, priceIndicator);

        super.field = field;
        return field;
    }

    public ExtraFields getTyp() {
        return TYP;
    }

    public int getPrice() {
        return PRICE;
    }
}
