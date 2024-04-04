package sandwich.de.monopoly.GUI.Game.Displays.DisplayTwo;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sandwich.de.monopoly.Enums.ProgramColor;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerTwo;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

public class GoBackDisplay extends Pane {

    public GoBackDisplay(double width, double height, GameDisplayControllerTwo rootDisplay) {
        setId("gameScene_GoBackActionButton");
        setMaxSize(width, height);

        Rectangle background = buildRectangle("gameScene_goBackActionButton_Background", width * 0.75, height * 0.35, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01, width / 2 - (width * 0.75) / 2, height / 2 - (height * 0.35) / 2);

        getChildren().add(background);
    }

}