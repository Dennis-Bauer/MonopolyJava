package de.sandwich.Fields;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Station extends Field {

    private final String name;
    private final double price;

    public Station(String name ,int price, double postion) {
        super(postion);
        this.name = name;
        this.price = price;
    }

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("station_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("station_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("station_Header", name, Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 50);
        ImageView train = createImageView("station_Image", "/de/sandwich/monopoly/gameBoard/train.png", width / 1.15, height / 3.7,(width - width / 1.15) / 2, height / 3);
        Label priceIndicator = buildLabel("station_PriceIndicator", (price + "€"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));


        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, header, train, priceIndicator);

        super.field = field;
        return field;
    }

}
