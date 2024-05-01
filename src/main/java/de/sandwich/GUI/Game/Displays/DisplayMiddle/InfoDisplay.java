package de.sandwich.GUI.Game.Displays.DisplayMiddle;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class InfoDisplay extends Pane {

    private final MiddleGameDisplayController rootDisplay;
    private final Label text;

    public InfoDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("InfoDisplay");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        text = buildLabel("InfoDisplay_Text", "NULL_TEXT", Font.font(Main.TEXT_FONT, width * 0.070), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(text, rootDisplay);
        text.layoutYProperty().bind(heightProperty().multiply(0.30).subtract(text.heightProperty().divide(2)));

        StackPane returnButton = new StackPane(
            buildRectangle("infoDisplay_button_Background", width * 0.80, height * 0.25, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01),
            buildLabel("infoDisplay_button_Text", "Schließen", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );
        returnButton.setLayoutY(height * 0.60);
        returnButton.setLayoutX(width / 2 - (width * 0.80) / 2);

        returnButton.setOnMouseClicked(mouseEvent -> {
            rootDisplay.removeDisplay();
            Main.getGameOperator().setVisibilityTurnFinButton(true);
        });


        getChildren().addAll(text, returnButton);
    }

    public void display(String message) {
        text.setText(message);
        centeringChildInPane(text, rootDisplay);
    }

}