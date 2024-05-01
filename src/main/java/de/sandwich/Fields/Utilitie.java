package de.sandwich.Fields;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

public class Utilitie extends Field {

    private final String NAME;
    private final int PRICE = 3000;
    private final int MULTIPLICATOR_ONE = 80;
    private final int MULTIPLICATOR_TWO = 200;

    private Player owner;
    private boolean isOwned = false;
    private boolean ownerHasBoth = false;

    public Utilitie(String name, int postion) {
        super(postion);

        this.NAME = name;
    }

    private void setOwnerBoth() {
        Main.getGameOperator();
        HashMap<Integer, Field> fields = Game.getFields();

        ownerHasBoth = false;
        if (owner != null) {
            for (int i = 0; i < fields.size(); i++) {
                if (fields.get(i) instanceof Utilitie u) {
                    if (u != this) {
                        if (u.getOwner() == owner) {
                            ownerHasBoth = true;
                        }
                    }
                }
            }
            
        }
    }

    public void setOwner(Player p) {
        isOwned = p != null;

        this.owner = p;

        setOwnerBoth();
    }

    private Rectangle background;

    @Override
    public Pane buildField(double width, double height, double borderWidth, double fontSize) {
        Pane field = new Pane();
        field.setId("station_field");
        field.setMaxSize(width, height);

        background = buildRectangle("station_Background" ,width, height, ProgramColor.GAMEBOARD_COLOR.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), borderWidth);
        Label header = buildLabel("station_Header", NAME, Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, height / 50);
        Label priceIndicator = buildLabel("station_PriceIndicator", (PRICE + "€"), Font.font(Main.TEXT_FONT, FontWeight.BOLD, fontSize), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 5 * (height / 6));

        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, header, priceIndicator);

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

    public Player getOwner() {
        return owner;
    }

    public int getPrice() {
        return PRICE;
    }

    public String getName() {
        return NAME;
    }

    public int getMultiplicator() {
        setOwnerBoth();
        if (ownerHasBoth)
            return MULTIPLICATOR_TWO;
        else return MULTIPLICATOR_ONE;
    }

    public boolean hasOwnerBoth() {
        setOwnerBoth();
        return ownerHasBoth;
    }

    public boolean isOwned() {
        return isOwned;
    }
}
