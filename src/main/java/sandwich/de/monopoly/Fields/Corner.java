package sandwich.de.monopoly.Fields;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Enums.CornerTyp;
import sandwich.de.monopoly.Enums.ProgramColor;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.Main.TEXT_FONT;

public class Corner extends Field {

    private final CornerTyp typ;

    public Corner(CornerTyp typ, double postion) {
        super(postion);
        this.typ = typ;
    }

    public CornerTyp getTyp() {
        return typ;
    }

    public Pane buildCorner(double borderWidth, double fontSize, double size) {
        switch (typ) {
            case START -> {
                return buildStart(borderWidth, fontSize, size);
            }
            case JAIL -> {
                return buildJail(borderWidth, fontSize, size);
            }
            case FREE_PARKING -> {
                return buildFreeParking(borderWidth, fontSize, size);
            }
            case GO_TO_JAIL -> {
                return buildGoToJail(borderWidth, fontSize, size);
            }
            default -> {
                return null;
            }
        }
    }

    private Pane buildStart(double borderWidth, double fontSize, double size) {
        Pane field = new Pane();
        field.setId("start_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_start_Background", size, size, ProgramColor.BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        ImageView arrow = createImageView("corner_start_Arrow" ,"/sandwich/de/monopoly/gameBoard/startArrow.png", size / 6, size / 1.25, (size -(size / 1.25)) / 2, (size -(size / 1.25)) / 2);
        Label text = buildLabel("corner_start_Text", buildLongText("LOS", "Bekomme 200", "beim drüber gehen"), Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor());

        text.widthProperty().addListener((obs, oldVal, newVal) -> text.setTranslateX((size - newVal.doubleValue()) / 0.8));
        text.heightProperty().addListener((obs, oldVal, newVal) -> text.setTranslateY((newVal.doubleValue()) / 1.5));
        text.setRotate(45);

        field.getChildren().addAll(background, arrow, text);

        super.field = field;
        return field;
    }

    private Pane buildJail(double borderWidth, double fontSize, double size) {
        Pane field = new Pane();
        field.setId("jail_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_jail_Background", size, size, ProgramColor.BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Rectangle backgroundJail = buildRectangle("corner_jail_JailBackground", size / 2, size / 2, ProgramColor.JAIL_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth, size - size / 2, 0);
        ImageView jailMan = createImageView("corner_jail_Man" ,"/sandwich/de/monopoly/gameBoard/jailMan.png", size / 3, size / 3, size - size / 2 + (((size / 2) - (size / 3) / 2) - size / 4), (((size / 2) - (size / 3) / 2) - size / 4));
        Label header = buildLabel("corner_jail_Header", "Im", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), size / 2 + size / 3, size / 18);
        Label footer = buildLabel("corner_jail_FooterText", "Bau", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), size / 1.9, size / 2.9);
        Label textOne = buildLabel("corner_jail_FirstText", "Nur", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize * 1.5), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, size / 5.5);
        Label textTwo = buildLabel("corner_jail_SecondText", "zu besuch", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize * 1.5), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, size / 1.3);

        centeringChildInPane(textTwo, field);

        jailMan.setRotate(45);
        header.setRotate(45);
        footer.setRotate(45);
        textOne.setRotate(90);

        field.getChildren().addAll(background, backgroundJail, jailMan, header, footer, textOne, textTwo);

        super.field = field;
        return field;
    }

    private Pane buildFreeParking(double borderWidth, double fontSize, double size) {
        Pane field = new Pane();
        field.setId("freeParking_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_freeParking_Background", size, size, ProgramColor.BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("corner_freeParking_Header", "Freies", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), size / 5 + size / 3.25, size / 5.75);
        ImageView freeParking = createImageView("corner_freeParking_Picture", "/sandwich/de/monopoly/gameBoard/freeParking.png", size / 1.5, size / 1.75, size / 4.75,size / 5);
        Label footer = buildLabel("corner_freeParking_Footer", "Parken", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), size / 8.25, size / 1.7);

        header.setRotate(45);
        freeParking.setRotate(45);
        footer.setRotate(45);

        field.getChildren().addAll(background, header, freeParking, footer);

        super.field = field;
        return field;
    }

    private Pane buildGoToJail(double borderWidth, double fontSize, double size) {
        Pane field = new Pane();
        field.setId("goToJail_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_goToJail_Background", size, size, ProgramColor.BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("corner_goToJail_Header", "Geh ins", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), size / 6 + size / 3.25, size / 5.75);
        ImageView freeParking = createImageView("corner_goToJail_Picture", "/sandwich/de/monopoly/gameBoard/goToJail.png", size / 1.5, size / 1.75, size / 4.75,size / 5);
        Label footer = buildLabel("corner_goToJail_Footer", "Gefängnis", Font.font(TEXT_FONT, FontWeight.BOLD, fontSize * 2), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), -(size / 15), size / 1.8);

        header.setRotate(45);
        freeParking.setRotate(45);
        footer.setRotate(45);

        field.getChildren().addAll(background, header, freeParking, footer);

        super.field = field;
        return field;
    }

}
