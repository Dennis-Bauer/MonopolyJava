package de.sandwich.GUI.Game.DisplayController;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.Displays.DisplayTwo.ActionDisplay;
import de.sandwich.GUI.Game.Displays.DisplayTwo.DiceDisplay;
import de.sandwich.GUI.Game.Displays.DisplayTwo.ReturnDisplay;


public class GameDisplayControllerTwo extends Pane {

    private final Pane actionDisplay;
    private final Pane diceDisplay;
    private final Pane returnDisplay;

    public GameDisplayControllerTwo(double width, double height) {
        setId("gameScene_DisplayTwo");
        setMaxSize(width, height);
        getChildren().add(buildRectangle("gameScene_action_Background", width, height, ProgramColor.DISPLAY_TWO_BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), width * 0.006));

        Label header = buildLabel("gameScene_action_Header", "Aktionen", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(header, this);

        actionDisplay = new ActionDisplay(width, height, this);
        actionDisplay.setVisible(false);

        diceDisplay = new DiceDisplay(width, height, this);
        diceDisplay.setVisible(false);

        returnDisplay = new ReturnDisplay(width, height, this);
        returnDisplay.setVisible(false);

        getChildren().addAll(header, actionDisplay, diceDisplay, returnDisplay);
    }

    public void displayDice() {
        resetDisplay();

        diceDisplay.setVisible(true);
    }

    public void displayPlayerAction() {
        resetDisplay();

        actionDisplay.setVisible(true);
    }

    public void displayReturnButton() {
        resetDisplay();

        returnDisplay.setVisible(true);
    }   

    private void resetDisplay() {
        diceDisplay.setVisible(false);
        actionDisplay.setVisible(false);
        returnDisplay.setVisible(false);
    }
}
