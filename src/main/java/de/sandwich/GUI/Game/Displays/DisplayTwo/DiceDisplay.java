package de.sandwich.GUI.Game.Displays.DisplayTwo;

import java.util.Random;

import de.sandwich.Main;
import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerTwo;
import de.sandwich.Threads.NumberAnimationThread;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildTriangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;

public class DiceDisplay extends Pane {

    //private final GameDisplayControllerTwo rootDisplay;
    private final Random rn = new Random();
    private NumberAnimationThread rowOneAnimation;
    private NumberAnimationThread rowTwoAnimation;

    public DiceDisplay(double width, double height, GameDisplayControllerTwo rootDisplay) {
        setId("gameScene_DiceDisplay");
        setMaxSize(width, height);

        //this.rootDisplay = rootDisplay;

        Pane numberField = new Pane();
        numberField.setId("gameScene_diceDisplay_Pane");
        numberField.setMaxSize(width * 0.80, height * 0.35);
        numberField.setLayoutX(width / 2 - (width * 0.80) / 2);
        numberField.setLayoutY(height * 0.30);

        Rectangle numberBackground = buildRectangle("gameScene_diceDisplay_NumberBackground", numberField.getMaxWidth(), numberField.getMaxHeight(), ProgramColor.DICE_BACKGROUND.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.0075);
        Polygon arrowOne = buildTriangle("gameScene_diceDisplay_NumberArrowOne", new Point2D(0, 0), new Point2D(width * 0.05, 0), new Point2D(width * 0.025, height * 0.05), ProgramColor.BORDER_COLOR_DARK.getColor(), null, numberBackground.getWidth() / 2 - width * 0.025, 0);
        Polygon arrowTwo = buildTriangle("gameScene_diceDisplay_NumberArrowTwo", new Point2D(0, height * 0.05), new Point2D(width * 0.05, height * 0.05), new Point2D(width * 0.025, 0), ProgramColor.BORDER_COLOR_DARK.getColor(), null, numberBackground.getWidth() / 2 - width * 0.025, numberBackground.getHeight() - height * 0.05);

        Label numberRowOne = buildLabel("gameScene_diceDisplay_NumberRowOne", "1-2-3-4-5-6-1-2-3-4-5-6-1-2-3-4-5", new Font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 0);
        centeringChildInPane(numberRowOne, numberField);

        Label numberRowTwo = buildLabel("gameScene_diceDisplay_NumberRowTwo", "1-2-3-4-5-6-1-2-3-4-5-6-1-2-3-4-5", new Font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, numberBackground.getHeight() / 2);
        centeringChildInPane(numberRowTwo, numberField);

        Pane roleDiceButton = new Pane();
        roleDiceButton.setId("gameScene_roleDiceButton_Pane");
        roleDiceButton.setMaxSize(width * 0.80, height * 0.15);
        roleDiceButton.setLayoutX(width / 2 - (width * 0.80) / 2);
        roleDiceButton.setLayoutY(numberField.getLayoutY() + numberField.getMaxHeight() + height * 0.05);

        Rectangle buttonBackground = buildRectangle("gameScene_roleDiceButton_Background", roleDiceButton.getMaxWidth(), roleDiceButton.getMaxHeight(), ProgramColor.DICE_ROLE_BUTTON.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.0075);
        Label buttonText = buildLabel("gameScene_roleDiceButton_Text", "Würfel!", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(buttonText, roleDiceButton);

        numberField.getChildren().addAll(numberBackground, arrowOne, arrowTwo, numberRowOne, numberRowTwo);
        roleDiceButton.getChildren().addAll(buttonBackground, buttonText);

        //Starts the player's turn
        roleDiceButton.setOnMouseClicked(event -> {

            rowOneAnimation = new NumberAnimationThread(numberRowOne);
            rowTwoAnimation = new NumberAnimationThread(numberRowTwo);

            if (!rowOneAnimation.isAlive() && !rowTwoAnimation.isAlive() && !Main.getGameOperator().isTurnPlayerIsMoving()) {
                rowOneAnimation.setLength(rn.nextInt(60 - 20 + 1) + 20);
                rowTwoAnimation.setLength(rn.nextInt(60 - 20 + 1) + 20);

                rowOneAnimation.start();
                rowTwoAnimation.start();

                //Main.getGameOperator().playerRolledDice(rowOneAnimation.getLastNumber(), rowTwoAnimation.getLastNumber());
                Main.getGameOperator().playerRolledDice(4, 0);
                if (Main.CONSOLE_OUT_PUT) {
                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Der erste Würfel hat eine");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + rowOneAnimation.getLastNumber());
                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, " gewürfelt!");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Der zweite Würfel hat eine");
                    consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, " " + rowTwoAnimation.getLastNumber());
                    consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, " gewürfelt!");
                    consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                }
            }
        });

        getChildren().addAll(numberField, roleDiceButton);
    }


}
