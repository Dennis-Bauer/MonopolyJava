package de.sandwich.GUI.Game.Displays.DisplayTwo;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerTwo;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;

public class ReturnDisplay extends Pane {

    public ReturnDisplay(double width, double height, GameDisplayControllerTwo rootDisplay) {
        setId("gameScene_GoBackActionButton");
        setMaxSize(width, height);

        StackPane button = new StackPane(
            buildRectangle("gameScene_goBackActionButton_Background", width * 0.75, height * 0.35, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01),
            buildLabel("gameScene_goBackActionButton_Text", "Zurück", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), null, ProgramColor.TEXT_COLOR.getColor())
        );
        button.setLayoutX(width / 2 - (width * 0.75) / 2);
        button.setLayoutY(height / 1.85 - (height * 0.35) / 2);

        button.setOnMouseClicked(mouseEvent -> {
            rootDisplay.displayPlayerAction();
            Main.getGameOperator().getDisplayControllerOne().displayPlayers(Main.getGameOperator().getPlayers());
        });

        getChildren().add(button);

    }

}