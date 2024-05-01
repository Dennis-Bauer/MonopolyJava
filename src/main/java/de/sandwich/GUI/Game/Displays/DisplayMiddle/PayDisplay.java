package de.sandwich.GUI.Game.Displays.DisplayMiddle;

import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class PayDisplay extends Pane {

    private final MiddleGameDisplayController rootDisplay;
    private final Label text;
    private final Label buttonText;
    private int price;

    public PayDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("PayDisplay");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        text = buildLabel("payDisplay_Text", "NULL_TEXT", Font.font(Main.TEXT_FONT, width * 0.070), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(text, rootDisplay);
        text.layoutYProperty().bind(heightProperty().multiply(0.30).subtract(text.heightProperty().divide(2)));

        StackPane payButton = new StackPane();
        payButton.setLayoutY(height * 0.60);
        payButton.setLayoutX(width / 2 - (width * 0.80) / 2);

        buttonText = buildLabel("payDisplay_button_Text", "NULL_€ zahlen", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        Rectangle buttonBackground = buildRectangle("payDisplay_button_Background", width * 0.80, height * 0.25, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01);


        payButton.getChildren().addAll(buttonBackground, buttonText);

        payButton.setOnMouseClicked(mouseEvent -> {

            Player player = Main.getGameOperator().getTurnPlayer();

            if (price < 0) {
                if (player.getBankAccount() >= price * -1) {
                    player.transferMoneyToBankAccount(price);
                    Main.getGameOperator().transferMoney(price * -1);
                    rootDisplay.removeDisplay();

                    Main.getGameOperator().setVisibilityTurnFinButton(true);
                } else {
                    rootDisplay.errorAnimation();
                }
            } else {
                player.transferMoneyToBankAccount(price);
                rootDisplay.removeDisplay();

                Main.getGameOperator().setVisibilityTurnFinButton(true);
            }
        });


        getChildren().addAll(text, payButton);
    }

    public void showPayScreen(String message, int price) {
        text.setText(message);
        centeringChildInPane(text, rootDisplay);

        if (price < 0)
            buttonText.setText(price + "€ zahlen");
        else if (price > 0) 
            buttonText.setText("+" + price + "€");
        else throw new NullPointerException("You can not pay or get 0 oney!");
        this.price = price;
    }

}
