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


    private final boolean IS_CHANCE;
    private final ChanceColors COLOR;

    //Cards
    private static final CommunityCards[] COMMUNITY_CARDS = CommunityCards.values();
    private static int communityCardPositon = 0;

    private static final ChanceCards[] CHANCE_CARDS = ChanceCards.values();
    private static int chanceCardPositon = 0;

    public GetCard(ChanceColors c, double position) {
        super(position);
        this.COLOR = c;

        IS_CHANCE = true;
    }

    public GetCard(double position) {
        super(position);
        this.COLOR = null;

        IS_CHANCE = false;
    }

    public boolean isFieldChance() {
        return IS_CHANCE;
    }

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        if (IS_CHANCE)
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
        switch (Objects.requireNonNull(COLOR)) {
            case RED ->
                    i = creatImage("/de/sandwich/monopoly/gameBoard/chanceRed.png");
            case BLUE ->
                    i = creatImage("/de/sandwich/monopoly/gameBoard/chanceBlue.png");
            case GREEN ->
                    i = creatImage("/de/sandwich/monopoly/gameBoard/chanceGreen.png");
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

    public static void randomizeCards() {
        //Cards
        Random random = new Random();
        for (int i = COMMUNITY_CARDS.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            CommunityCards temp = COMMUNITY_CARDS[index];
            COMMUNITY_CARDS[index] = COMMUNITY_CARDS[i];
            COMMUNITY_CARDS[i] = temp;
        }
        
        for (int i = CHANCE_CARDS.length - 1; i > 0; i--) {
            int index = random.nextInt(i + 1);

            ChanceCards temp = CHANCE_CARDS[index];
            CHANCE_CARDS[index] = CHANCE_CARDS[i];
            CHANCE_CARDS[i] = temp;
        }
    }

    public static CommunityCards getCommunityCard() {
        CommunityCards c = COMMUNITY_CARDS[communityCardPositon];

        communityCardPositon++;
        if (communityCardPositon >= COMMUNITY_CARDS.length - 1)
            communityCardPositon = 0;

        return c;
    }

    public static ChanceCards getChanceCard() {
        ChanceCards c = CHANCE_CARDS[chanceCardPositon];

        chanceCardPositon++;
        if (chanceCardPositon >= CHANCE_CARDS.length - 1)
            chanceCardPositon = 0;

        return c;
    }

    public enum ChanceColors {
        RED,
        GREEN,
        BLUE
    }
}
