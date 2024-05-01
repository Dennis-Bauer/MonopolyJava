package de.sandwich.Fields;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Station extends Field {

    private final String NAME;
    private boolean isOwned = false;
    private boolean isInBank = false;
    private int ownerStations = 0;
    
    private Player owner;

    //Standard Variables
    public final int PRICE = 400;
    public final int RENT_ALONE = 500;
    public final int RENT_TWO = 1000;
    public final int RENT_THREE = 2000;
    public final int RENT_FOUR = 4000;

    public Station(String name, double postion) {
        super(postion);
        this.NAME = name;
    }
    
    public void setOwner(Player owner) {
        isOwned = owner != null;

        this.owner = owner;

        countOwnersStations();
    }

    public void sellToTheBank() {
        if (!isInBank) {
            isInBank = true;
            owner.transferMoneyToBankAccount(PRICE / 2);
        }
    }

    public void purchaseFromBank() {
        if (isInBank) {
            isInBank = false;
            owner.transferMoneyToBankAccount(-(PRICE / 2));
        }
    }

    public void countOwnersStations() {
        Main.getGameOperator();
        HashMap<Integer, Field> fields = Game.getFields();

        ownerStations = 0;

        if (owner != null) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i) instanceof Station s) {
                    if (s.getOwner() == owner && !s.isInBank) {
                        ownerStations++;
                    } 
                }
            }
            
        }
    }

    private Rectangle background;

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("station_field");
        field.setMaxSize(width, height);

        background = buildRectangle("station_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("station_Header", NAME, Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 50);
        ImageView train = createImageView("station_Image", "/de/sandwich/monopoly/gameBoard/train.png", width / 1.15, height / 3.7,(width - width / 1.15) / 2, height / 3);
        Label priceIndicator = buildLabel("station_PriceIndicator", (PRICE + "€"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));


        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, header, train, priceIndicator);

        super.field = field;
        return field;
    }

    public void highlightField() {
        background.setStrokeWidth(background.getStrokeWidth() * 2);
        background.setStroke(ProgramColor.BORDER_COLOR_LIGHT.getColor());

        field.toFront();
    }

    public void removeHighlight() {
        background.setStrokeWidth(background.getStrokeWidth() / 2);
        background.setStroke(ProgramColor.BORDER_COLOR_DARK.getColor());
    }

    public int getRent() {
        countOwnersStations();
        switch (ownerStations) {
            case 1: return RENT_ALONE;
            case 2: return RENT_TWO;
            case 3: return RENT_THREE;
            case 4: return RENT_FOUR;
            default: throw new IllegalArgumentException("An unexpected number of train stations were detected!");
        }
    }

    public Player getOwner() {
        return owner;
    }

    public int getOwnerStations() {
        countOwnersStations();
        return ownerStations;
    }

    public String getName() {
        return NAME;
    }

    public int getPrice() {
        return PRICE;
    }

    public boolean isOwned() {
        return isOwned;
    }
}
