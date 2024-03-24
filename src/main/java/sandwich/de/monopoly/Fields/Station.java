package sandwich.de.monopoly.Fields;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.Main.TEXT_FONT;

public class Station extends Field {

    private final String name;
    private final double price;

    public Station(String name ,double price, double postion) {
        super(postion);
        this.name = name;
        this.price = price;
    }

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        Pane field = new Pane();
        field.setId("station_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("station_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = buildLabel("station_Header", name, Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 50);
        ImageView train = createImageView("station_Image", "/sandwich/de/monopoly/gameBoard/train.png", width / 1.15, height / 3.7,(width - width / 1.15) / 2, height / 3);
        Label priceIndicator = buildLabel("station_PriceIndicator", (price + "€"), Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));


        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, header, train, priceIndicator);
        return field;
    }

}
