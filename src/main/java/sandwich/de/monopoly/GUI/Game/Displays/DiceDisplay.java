package sandwich.de.monopoly.GUI.Game.Displays;

import javafx.application.Platform;
import javafx.geometry.Point2D;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Main;

import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class DiceDisplay extends Pane {

    private Thread numberAnimationOne = new Thread("NumberAnimationOne");
    private Thread numberAnimationTwo = new Thread("NumberAnimationTwo");
    private Random rn = new Random();

    public DiceDisplay(double width, double height) {
        setId("gameScene_DiceDisplay");
        setMaxSize(width, height);

        Pane numberField = new Pane();
        numberField.setId("gameScene_diceDisplay_Pane");
        numberField.setMaxSize(width * 0.80, height * 0.35);
        numberField.setLayoutX(width / 2 - (width * 0.80) / 2);
        numberField.setLayoutY(height * 0.30);

        Rectangle numberBackground = buildRectangle("gameScene_diceDisplay_NumberBackground", numberField.getMaxWidth(), numberField.getMaxHeight(), Color.rgb(93, 150, 212), true, Color.BLACK, width * 0.0075);
        Polygon arrowOne = buildTriangle("gameScene_diceDisplay_NumberArrowOne", new Point2D(0, 0), new Point2D(width * 0.05, 0), new Point2D(width * 0.025, height * 0.05), Color.BLACK, null, numberBackground.getWidth() / 2 - width * 0.025, 0);
        Polygon arrowTwo = buildTriangle("gameScene_diceDisplay_NumberArrowTwo", new Point2D(0, height * 0.05), new Point2D(width * 0.05, height * 0.05), new Point2D(width * 0.025, 0), Color.BLACK, null, numberBackground.getWidth() / 2 - width * 0.025, numberBackground.getHeight() - height * 0.05);

        Label numberRowOne = buildLabel("gameScene_diceDisplay_NumberRowOne", "1-2-3-4-5-6-1-2-3-4-5-6-1-2-3-4-5", new Font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, Color.BLACK, 0, 0);
        centeringChildInPane(numberRowOne, numberField);

        Label numberRowTwo = buildLabel("gameScene_diceDisplay_NumberRowTwo", "1-2-3-4-5-6-1-2-3-4-5-6-1-2-3-4-5", new Font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, Color.BLACK, 0, numberBackground.getHeight() / 2);
        centeringChildInPane(numberRowTwo, numberField);

        Pane roleDiceButton = new Pane();
        roleDiceButton.setId("gameScene_roleDiceButton_Pane");
        roleDiceButton.setMaxSize(width * 0.80, height * 0.15);
        roleDiceButton.setLayoutX(width / 2 - (width * 0.80) / 2);
        roleDiceButton.setLayoutY(numberField.getLayoutY() + numberField.getMaxHeight() + height * 0.05);

        Rectangle buttonBackground = buildRectangle("gameScene_roleDiceButton_Background", roleDiceButton.getMaxWidth(), roleDiceButton.getMaxHeight(), Color.rgb(93, 150, 212),true, Color.BLACK, width * 0.0075);
        Label buttonText = buildLabel("gameScene_roleDiceButton_Text", "Würfel!", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.05), TextAlignment.CENTER, Color.WHITE);
        centeringChildInPane(buttonText, roleDiceButton);

        numberField.getChildren().addAll(numberBackground, arrowOne, arrowTwo, numberRowOne, numberRowTwo);
        roleDiceButton.getChildren().addAll(buttonBackground, buttonText);

        roleDiceButton.setOnMouseClicked(event -> {

            if (!numberAnimationOne.isAlive() && !numberAnimationTwo.isAlive()) {
                AtomicInteger diceOneNumber = new AtomicInteger();
                AtomicInteger diceTwoNumber = new AtomicInteger();

                AtomicInteger randomOneLength = new AtomicInteger();
                randomOneLength.set(rn.nextInt(60 - 20 + 1) + 20);
                if ((randomOneLength.get() % 2) != 0)
                    randomOneLength.set(randomOneLength.get() + 1);

                int dOne = Integer.parseInt(String.valueOf(numberRowOne.getText().charAt(16)));
                for (int i = 0; i < randomOneLength.get() / 2; i++) {
                    if (dOne >= 6)
                        dOne = 1;
                    else dOne = dOne + 1;

                    System.out.println("DOne: " + dOne);
                }
                diceOneNumber.set(dOne);

                AtomicInteger randomTwoLength = new AtomicInteger();
                randomTwoLength.set(rn.nextInt(60 - 20 + 1) + 20);
                if ((randomTwoLength.get() % 2) != 0)
                    randomTwoLength.set(randomTwoLength.get() + 1);

                int dTwo = Integer.parseInt(String.valueOf(numberRowTwo.getText().charAt(16)));
                for (int i = 0; i < randomTwoLength.get() / 2; i++) {
                    if (dTwo >= 6)
                        dTwo = 1;
                    else dTwo = dTwo + 1;
                }
                diceTwoNumber.set(dTwo);


                numberAnimationOne = new Thread(() -> {
                    for (int i = 0; i < randomOneLength.get(); i++) {
                        try {Thread.sleep(20);} catch (InterruptedException ignored) {}

                        Platform.runLater(() -> {

                            String rowText = numberRowOne.getText();
                            StringBuilder rowNewText = new StringBuilder();

                            rowNewText.append(rowText.substring(1));
                            if (!rowText.endsWith("-")) {
                                rowNewText.append("-");
                            } else {
                                int rowLastNumber = Integer.parseInt(String.valueOf(rowText.charAt(rowText.length() - 2)));
                                if (rowLastNumber >= 6) {
                                    rowNewText.append("1");
                                } else {
                                    rowNewText.append(rowLastNumber + 1);
                                }
                            }
                            numberRowOne.setText(rowNewText.toString());


                        });
                    }
                });
                numberAnimationOne.start();

                numberAnimationTwo = new Thread(() -> {
                    for (int i = 0; i < randomTwoLength.get(); i++) {
                        try {Thread.sleep(20);} catch (InterruptedException ignored) {}

                        Platform.runLater(() -> {

                            String rowText = numberRowTwo.getText();
                            StringBuilder rowNewText = new StringBuilder();

                            rowNewText.append(rowText.substring(1));
                            if (!rowText.endsWith("-")) {
                                rowNewText.append("-");
                            } else {
                                int rowLastNumber = Integer.parseInt(String.valueOf(rowText.charAt(rowText.length() - 2)));
                                if (rowLastNumber >= 6) {
                                    rowNewText.append("1");
                                } else {
                                    rowNewText.append(rowLastNumber + 1);
                                }
                            }
                            numberRowTwo.setText(rowNewText.toString());


                        });
                    }
                });
                numberAnimationTwo.start();

                Main.getGameOperator().playerRolledDice(diceOneNumber.get(), diceTwoNumber.get());
            }

            //Main.getGameOperator().playerRolledDice(numberRowOne.getText().substring(15, 16));

        });

        getChildren().addAll(numberField, roleDiceButton);
    }


}
