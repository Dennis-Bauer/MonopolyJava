package sandwich.de.monopoly.Fields;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import java.util.Objects;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.creatImage;
import static sandwich.de.monopoly.Main.TEXT_FONT;

public class GetCard extends Field{


    private final boolean fieldIsChance;

    private final ChanceColors color;

    public GetCard(ChanceColors c, double position) {
        super(position);
        this.color = c;

        fieldIsChance = true;
    }

    public GetCard(double position) {
        super(position);
        this.color = null;

        fieldIsChance = false;
    }

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        if (fieldIsChance)
            return buildGetChanceCard(width, height, borderWidth, fontSize, backgroundColor);
        else
            return buildGetCommunityCard(width, height, borderWidth, fontSize, backgroundColor);
    }

    private Pane buildGetChanceCard(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        Pane field = new Pane();
        field.setId("getChanceCard_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("chance_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = buildLabel("chance_Header", "Chance", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 50);

        Image i = null;
        switch (Objects.requireNonNull(color)) {
            case RED ->
                    i = creatImage("/sandwich/de/monopoly/gameBoard/chance_red.png");
            case BLUE ->
                    i = creatImage("/sandwich/de/monopoly/gameBoard/chance_blue.png");
            case GREEN ->
                    i = creatImage("/sandwich/de/monopoly/gameBoard/chance_green.png");
        }

        ImageView image = createImageView("chance_Image", i, width / 1.1, height / 1.6,(width - width / 1.15) / 2, height / 3.5);

        centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);
        return field;
    }

    private Pane buildGetCommunityCard(double width, double height, double borderWidth, double fontSize, Color backgroundColor) {
        Pane field = new Pane();
        field.setId("getCommunityCard_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("community_Background" ,width, height, backgroundColor, true, Color.BLACK, borderWidth);
        Label header = buildLabel("community_Header", buildLongText("Gesellschafts", "Feld"), Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, Color.BLACK, 0, height / 50);
        ImageView image = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/communityChest.png", width / 1.1, height / 1.9,(width - width / 1.15) / 2, height / 3.5);

        centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);
        return field;
    }

    public enum ChanceColors {
        RED,
        GREEN,
        BLUE
    }

}
