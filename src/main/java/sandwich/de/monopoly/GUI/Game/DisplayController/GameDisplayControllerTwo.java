package sandwich.de.monopoly.GUI.Game.DisplayController;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayTwo.ActionDisplay;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayTwo.DiceDisplay;
import sandwich.de.monopoly.Main;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class GameDisplayControllerTwo extends Pane {
    private final Pane actionDisplay;
    private final Pane diceDisplay;

    public GameDisplayControllerTwo(double width, double height, Color backgroundColor) {
        setId("gameScene_DisplayTwo");
        setMaxSize(width, height);
        getChildren().add(buildRectangle("gameScene_action_Background", width, height, backgroundColor, true, Color.WHITE, width * 0.006));

        Label header = buildLabel("gameScene_action_Header", "Aktionen", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), TextAlignment.CENTER, Color.WHITE);
        centeringChildInPane(header, this);

        actionDisplay = new ActionDisplay(width, height, this);
        diceDisplay = new DiceDisplay(width, height, this);
        actionDisplay.setVisible(false);
        diceDisplay.setVisible(true);

        getChildren().addAll(header, actionDisplay, diceDisplay);
    }

    public void displayDice() {
        diceDisplay.setVisible(true);
        actionDisplay.setVisible(false);
    }

    public void showPlayerAction() {
        diceDisplay.setVisible(false);
        actionDisplay.setVisible(true);
    }


}
