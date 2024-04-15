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

    //private final GameDisplayControllerTwo rootDisplay;

    public ActionDisplay(double width, double height, GameDisplayControllerTwo rootDisplay) {
        setId("gameScene_TradingDisplay");
        setMaxSize(width, height);

        //this.rootDisplay = rootDisplay;

        double middleX = (width / 2) - (width * 0.25) / 2;
        double y = height * 0.30;
        double space = width * 0.30;

        Pane bankButton = buildButton("Bank", width * 0.25, height * 0.45, "/de/sandwich/monopoly/aktionDisplay/bank.png", ProgramColor.ACTION_BANK_BUTTON.getColor(), "bank", middleX - space, y);
        bankButton.setOnMouseClicked(mouseEvent -> {
            Main.getGameOperator().getDisplayControllerOne().displayBankDisplay(Main.getGameOperator().getTurnPlayer());
            rootDisplay.displayReturnButton();
        });

        Pane buildButton = buildButton("Bauen", width * 0.25, height * 0.45, "/de/sandwich/monopoly/aktionDisplay/build.png", ProgramColor.ACTION_BUILD_BUTTON.getColor(), "build", middleX, y);
        buildButton.setOnMouseClicked(mouseEvent -> {
            Main.getGameOperator().getDisplayControllerOne().displayBuildDisplay((Main.getGameOperator().getTurnPlayer()));
            rootDisplay.displayReturnButton();
        });

        Pane leaveButton = buildButton("Aufgeben", width * 0.25, height * 0.45, "/de/sandwich/monopoly/aktionDisplay/leave.png", ProgramColor.ACTION_LEAVE_BUTTON.getColor(), "leave", middleX + space, y);
        leaveButton.setOnMouseClicked(mouseEvent -> {

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
