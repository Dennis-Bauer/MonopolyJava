package sandwich.de.monopoly.GUI.Game;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.GUI.Game.Displays.ActionDisplay;
import sandwich.de.monopoly.GUI.Game.Displays.DiceDisplay;
import sandwich.de.monopoly.Main;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class GameDisplayControllerTwo {

    private static Pane display;
    private static Pane actionDisplay;
    private static Pane diceDisplay;

    public static void createDisplayTwo(double width, double height, Color backgroundColor) {
        if (display == null) {
            display = new Pane();
            display.setId("gameScene_DisplayTwo");
            display.setMaxSize(width, height);

            Rectangle background = buildRectangle("gameScene_action_Background", width, height, backgroundColor, true, Color.WHITE, width * 0.006);
            Label header = buildLabel("gameScene_action_Header", "Aktionen", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), TextAlignment.CENTER, Color.WHITE);

            centeringChildInPane(header, display);

            actionDisplay = new ActionDisplay(width, height);
            diceDisplay = new DiceDisplay(width, height);
            actionDisplay.setVisible(false);
            diceDisplay.setVisible(true);

            display.getChildren().addAll(background, header, actionDisplay, diceDisplay);
        } else throw new RuntimeException("Display Two is already created!");
    }

    public static Pane getDisplay() {
        if (display != null)
            return display;
        else throw new RuntimeException("Display One is not yet created!");
    }

    public static void displayDice() {
        diceDisplay.setVisible(true);
        actionDisplay.setVisible(false);
    }

    public static void showPlayerAction() {
        diceDisplay.setVisible(false);
        actionDisplay.setVisible(true);
    }


}
