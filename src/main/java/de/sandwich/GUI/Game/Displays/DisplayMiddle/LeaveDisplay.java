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

public class LeaveDisplay extends Pane {

    private final Label infoText;

    public LeaveDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("LeaveDisplay");
        setMaxSize(width, height);

        infoText = buildLabel("leaveDisplay_InfoText", buildLongText("Möchtest du wirlich", "aufgeben?"), Font.font(Main.TEXT_FONT, width * 0.10), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(infoText, rootDisplay);

        double spaceEdge = width * 0.05;

        StackPane yesButton = new StackPane();
        yesButton.setId("leaveDisplay_BuyButton");
        yesButton.setLayoutY(height * 0.70);
        yesButton.setLayoutX(spaceEdge);

        yesButton.getChildren().addAll(
                buildRectangle("leaveDisplay_buyButton_Background", width * 0.30, height * 0.15, ProgramColor.FINISH_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01),
                buildLabel("leaveDisplay_buyButton_Text", "Ja", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );

        StackPane noButton = new StackPane();
        noButton.setId("leaveDisplay_BuyButton");
        noButton.setLayoutY(height * 0.70);
        noButton.setLayoutX(width - width * 0.30 - spaceEdge);

        noButton.getChildren().addAll(
                buildRectangle("leaveDisplay_refuseButton_Background", width * 0.30, height * 0.15, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01),
                buildLabel("leaveDisplay_refuseButton_Text", "Nein", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );

        getChildren().addAll(infoText, yesButton, noButton);

        yesButton.setOnMouseClicked(mouseEvent -> Main.getGameOperator().removePlayer());

        noButton.setOnMouseClicked(mouseEvent -> rootDisplay.removeDisplay());

    }
}