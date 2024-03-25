package sandwich.de.monopoly.Fields;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Enums.ExtraFields;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.Main.TEXT_FONT;

public class PayExtra extends Field{

    private final int price;
    private final ExtraFields typ;

    public PayExtra(int price, ExtraFields typ, double postion) {
        super(postion);
        this.price = price;
        this.typ = typ;

    }

    public ExtraFields getTyp() {
        return typ;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        Pane field = new Pane();
        field.setId("extraPay_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("extraPay_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = buildLabel("extraPay_Header", "ERROR", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 10);
        Label priceIndicator = buildLabel("station_PriceIndicator", (price + "€"), Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));


        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        ImageView picture = null;
        switch (typ) {
            case SPOTIFY_PREMIUM -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText(buildLongText("Spotify", "Premium Abo"));
            }
            case HESSLER_SCHULDEN -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/hessler.png", width / 3.4, height / 2.3,(width - width / 3.3) / 2, height / 2.7);
                header.setText(buildLongText("Freu Hessler", "Schulden ab", "bezahlen"));
            }
            case NAME_THREE -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText("NAME3");
            }
            case NAME_FOUR -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText("NAME4");
            }
        }

        field.getChildren().addAll(background, header, picture, priceIndicator);

        super.field = field;
        return field;
    }

}
