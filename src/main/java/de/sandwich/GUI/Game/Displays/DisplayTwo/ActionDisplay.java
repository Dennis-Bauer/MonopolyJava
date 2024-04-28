package de.sandwich.GUI.Game.Displays.DisplayTwo;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;

import de.sandwich.Main;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerTwo;
import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class ActionDisplay extends Pane {


    public ActionDisplay(double width, double height, GameDisplayControllerTwo rootDisplay) {
        setId("gameScene_TradingDisplay");
        setMaxSize(width, height);

        final double MIDDLE_X = (width / 2) - (width * 0.25) / 2;
        final double BUTTON_WIDTH = width * 0.25;
        final double BUTTON_HEIGHT = height * 0.45;
        final double Y = height * 0.30;
        final double SPACE = width * 0.30;

        Pane bankButton = buildButton("Bank", BUTTON_WIDTH, BUTTON_HEIGHT, "/de/sandwich/monopoly/aktionDisplay/bank.png", ProgramColor.ACTION_BANK_BUTTON.getColor(), "bank", MIDDLE_X - SPACE, Y);
        bankButton.setOnMouseClicked(mouseEvent -> {
            Main.getGameOperator().getDisplayControllerOne().displayBankDisplay(Main.getGameOperator().getTurnPlayer());
            rootDisplay.displayReturnButton();
        });

        Pane buildButton = buildButton("Bauen", BUTTON_WIDTH, BUTTON_HEIGHT, "/de/sandwich/monopoly/aktionDisplay/build.png", ProgramColor.ACTION_BUILD_BUTTON.getColor(), "build", MIDDLE_X, Y);
        buildButton.setOnMouseClicked(mouseEvent -> {
            Main.getGameOperator().getDisplayControllerOne().displayBuildDisplay((Main.getGameOperator().getTurnPlayer()));
            rootDisplay.displayReturnButton();
        });

        Pane leaveButton = buildButton("Aufgeben", BUTTON_WIDTH, BUTTON_HEIGHT, "/de/sandwich/monopoly/aktionDisplay/leave.png", ProgramColor.ACTION_LEAVE_BUTTON.getColor(), "leave", MIDDLE_X + SPACE, Y);
        leaveButton.setOnMouseClicked(mouseEvent -> {
            Main.getGameOperator().getMiddleDisplayController().displayLeaveDisplay();
        });


        getChildren().addAll(bankButton, buildButton, leaveButton);
    }

    private Pane buildButton(String headerText, double width, double height, String pathButtonSymbol, Color backgroundColor, String id, double x, double y) {
        Pane button = new Pane();
        button.setId("gameScene_action_button_" + id);
        button.setMaxWidth(width);

        Label header = buildLabel("gameScene_action_button_" + id + "_Header", headerText, Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 4), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        Rectangle background = buildRectangle("gameScene_action_button_" + id + "_Background", width, height, backgroundColor, true, ProgramColor.BORDER_COLOR_LIGHT.getColor(), width / 25, 0, height * 0.35);
        ImageView symbol = createImageView("gameScene_action_button_" + id + "_Symbol", pathButtonSymbol, height * 0.90, height * 0.90, (width / 2) - (height * 0.90) / 2, (height * 0.35) + (height / 2) - (height * 0.90) / 2);

        centeringChildInPane(header, button);

        button.getChildren().addAll(header, background, symbol);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

}
