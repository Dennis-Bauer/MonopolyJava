package de.sandwich.GUI.Game.Displays.DisplayMiddle;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;

import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Fields.Street;

public class BuyStreetDisplay extends Pane {

    private final Label infoText;
    private final MiddleGameDisplayController rootDisplay;
    private Street displayedStreet;

    public BuyStreetDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("BuyStreetDisplay");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        infoText = buildLabel("buyStreetDisplay_InfoText", buildLongText("NULL_STREET", "NULL_MONEY"), Font.font(Main.TEXT_FONT, width * 0.10), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(infoText, rootDisplay);

        double spaceEdge = width * 0.05;

        StackPane buyButton = new StackPane();
        buyButton.setId("buyStreetDisplay_BuyButton");
        buyButton.setLayoutY(height * 0.70);
        buyButton.setLayoutX(spaceEdge);

        buyButton.getChildren().addAll(
                buildRectangle("buyStreetDisplay_buyButton_Background", width * 0.30, height * 0.15, ProgramColor.FINISH_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01),
                buildLabel("buyStreetDisplay_buyButton_Text", "Kaufen", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );

        StackPane refuseButton = new StackPane();
        refuseButton.setId("buyStreetDisplay_BuyButton");
        refuseButton.setLayoutY(height * 0.70);
        refuseButton.setLayoutX(width - width * 0.30 - spaceEdge);

        refuseButton.getChildren().addAll(
                buildRectangle("buyStreetDisplay_refuseButton_Background", width * 0.30, height * 0.15, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01),
                buildLabel("buyStreetDisplay_refuseButton_Text", "Nicht Kaufen", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );

        getChildren().addAll(infoText, buyButton, refuseButton);

        buyButton.setOnMouseClicked(mouseEvent -> {
            rootDisplay.removeDisplay();
            if (displayedStreet != null)
                Main.getGameOperator().buyStreet(displayedStreet);
            else throw new NullPointerException("The street the player want to buy is null!");
        });

        refuseButton.setOnMouseClicked(mouseEvent -> {
            rootDisplay.removeDisplay();
            Main.getGameOperator().setVisibilityTurnFinButton(true);
        });

    }

    public void showStreet(Street s) {
        infoText.setText(buildLongText(s.getName(), s.getSalePrice() + "€"));
        centeringChildInPane(infoText, rootDisplay);

        displayedStreet = s;
    }
}
