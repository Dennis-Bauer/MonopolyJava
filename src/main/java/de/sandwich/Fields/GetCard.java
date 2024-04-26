package de.sandwich.Fields;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.creatImage;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import java.util.Objects;
import java.util.Random;

import de.sandwich.Main;
import de.sandwich.Enums.ChanceCards;
import de.sandwich.Enums.CommunityCards;
import de.sandwich.Enums.ProgramColor;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class GetCard extends Field{


    private final boolean fieldIsChance;

    private final ChanceColors color;

    //Cards
    private final CommunityCards[] communityCards = CommunityCards.values();
    private int communityCardPositon = 0;
    private final ChanceCards[] chanceCard = ChanceCards.values();
    private int chanceCardPositon = 0;

    public GetCard(ChanceColors c, double position) {
        super(position);
        this.color = c;

        //Cards
        Random random = new Random();
        for (int i = communityCards.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            CommunityCards temp = communityCards[index];
            communityCards[index] = communityCards[i];
            communityCards[i] = temp;
        }

        for (int i = chanceCard.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            ChanceCards temp = chanceCard[index];
            chanceCard[index] = chanceCard[i];
            chanceCard[i] = temp;
        }

        fieldIsChance = true;
    }

    public GetCard(double position) {
        super(position);
        this.color = null;

        fieldIsChance = false;
    }

    public boolean isFieldChance() {
        return fieldIsChance;
    }

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        if (fieldIsChance)
            return buildGetChanceCard(width, height, borderWidth, fontSize);
        else
            return buildGetCommunityCard(width, height, borderWidth, fontSize);
    }

    private Pane buildGetChanceCard(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("getChanceCard_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("chance_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("chance_Header", "Chance", Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 50);

        Image i = null;
        switch (Objects.requireNonNull(color)) {
            case RED ->
                    i = creatImage("/de/sandwich/monopoly/gameBoard/chance_red.png");
            case BLUE ->
                    i = creatImage("/de/sandwich/monopoly/gameBoard/chance_blue.png");
            case GREEN ->
                    i = creatImage("/de/sandwich/monopoly/gameBoard/chance_green.png");
        }

        ImageView image = createImageView("chance_Image", i, width / 1.1, height / 1.6,(width - width / 1.15) / 2, height / 3.5);

        centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);

        super.field = field;
        return field;
    }

    private Pane buildGetCommunityCard(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("getCommunityCard_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("community_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("community_Header", buildLongText("Gesellschafts", "Feld"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 50);
        ImageView image = createImageView("community_Image", "/de/sandwich/monopoly/gameBoard/communityChest.png", width / 1.1, height / 1.9,(width - width / 1.15) / 2, height / 3.5);

        centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);
        return field;
    }

    public CommunityCards getCommunityCard() {
        
        CommunityCards c = communityCards[communityCardPositon];

        communityCardPositon++;
        if (communityCardPositon >= communityCards.length - 1)
            communityCardPositon = 0;

        return c;
    }

    public enum ChanceColors {
        RED,
        GREEN,
        BLUE
    }

}
