package de.sandwich.GUI.Game.Displays.DisplayMiddle;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import de.sandwich.GUI.Game.Displays.DisplayTwo.DiceDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import de.sandwich.Game;

public class PlayerIsInMinusDisplay extends Pane {

    private final MiddleGameDisplayController rootDisplay;
    private final StackPane returnButton;

    public PlayerIsInMinusDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("PlayerMinusDisplay");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        Label text = buildLabel("playerMinusDisplay_Text", buildLongText("Du bist im Minus", "komme erst wieder ins", "Plus um weiter zu Spielen!"), Font.font(Main.TEXT_FONT, width * 0.070), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(text, rootDisplay);

        returnButton = new StackPane(
            buildRectangle("playerMinusDisplay_button_Background", width * 0.80, height * 0.30, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01),
            buildLabel("playerMinusDisplay_button_Text", "Schlißen", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );
        returnButton.setLayoutY(height * 0.60);
        returnButton.setLayoutX(width / 2 - (width * 0.80) / 2);

        getChildren().addAll(text, returnButton);
    }

    public void display() {
        Game gameOperator = Main.getGameOperator();

        returnButton.setOnMouseClicked(mouseEvent -> {
            if (gameOperator.getTurnPlayer().getBankAccount() >= 0) {
                rootDisplay.removeDisplay();

                DiceDisplay.unlockDices();

                gameOperator.getDisplayControllerTwo().displayDice();

            if (gameOperator.getTurnPlayer().isInJail()) {
                rootDisplay.displayInJailDisplay(gameOperator.getTurnPlayer(), true);
            }

            } else {
                rootDisplay.errorAnimation();
                gameOperator.displayErrorMessage("Komme erst wieder ins Plus!");
            }
        });

    }

}
