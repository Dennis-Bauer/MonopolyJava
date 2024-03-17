package sandwich.de.monopoly.GUI.Game.Displays;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Main;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.createImageView;

public class ActionDisplay extends Pane {

    private Pane aktionButtons;
    private Pane diceButton;

    public ActionDisplay(double width, double height, Color backgroundColor) {
        setId("gameScene_TradingDisplay");
        setMaxSize(width, height);

        Rectangle background = buildRectangle("gameScene_action_Background", width, height, backgroundColor, true, Color.WHITE, width * 0.006);
        Label header = buildLabel("gameScene_action_Header", "Aktionen", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), TextAlignment.CENTER, Color.WHITE);

        centeringChildInPane(header, this);

        double middleX = (width / 2) - (width * 0.25) / 2;
        double y = height * 0.30;
        double space = width * 0.30;

        Pane bankButton = buildButton("Bank", width * 0.25, height * 0.45, "/sandwich/de/monopoly/aktionDisplay/bank.png", Color.DARKGRAY, "bank", middleX - space, y);
        Pane buildButton = buildButton("Bauen", width * 0.25, height * 0.45, "/sandwich/de/monopoly/aktionDisplay/build.png", Color.LIME, "build", middleX, y);
        Pane leaveButton = buildButton("Verlassen", width * 0.25, height * 0.45, "/sandwich/de/monopoly/aktionDisplay/leave.png", Color.RED, "leave", middleX + space, y);

        aktionButtons = new Pane(bankButton, buildButton, leaveButton);

        aktionButtons.setVisible(false);

        diceButton = buildButton("Würfel", width * 0.80, height * 0.45, "/sandwich/de/monopoly/aktionDisplay/wuerfel.png", Color.DARKGRAY, "dice", (width / 2) - (width * 0.80) / 2, y);

        getChildren().addAll(background, header, aktionButtons, diceButton);
    }

    public void changeButton() {
        if (aktionButtons.isVisible()) {
            aktionButtons.setVisible(false);
            diceButton.setVisible(true);
        } else  {
            aktionButtons.setVisible(true);
            diceButton.setVisible(false);
        }
    }

    private Pane buildButton(String headerText, double width, double height, String pathButtonSymbol, Color backgroundColor, String id, double x, double y) {
        Pane button = new Pane();
        button.setId("gameScene_action_button_" + id);
        button.setMaxWidth(width);

        Label header = buildLabel("gameScene_action_button_" + id + "_Header", headerText, Font.font(Main.TEXT_FONT, FontWeight.BOLD, width / 4), TextAlignment.CENTER, Color.WHITE);
        Rectangle background = buildRectangle("gameScene_action_button_" + id + "_Background", width, height, backgroundColor, true, Color.WHITE, width / 25, 0, height * 0.35);
        ImageView symbol = createImageView("gameScene_action_button_" + id + "_Symbol", pathButtonSymbol, height * 0.90, height * 0.90, (width / 2) - (height * 0.90) / 2, (height * 0.35) + (height / 2) - (height * 0.90) / 2);

        centeringChildInPane(header, button);

        button.getChildren().addAll(header, background, symbol);
        button.setLayoutX(x);
        button.setLayoutY(y);
        return button;
    }

}
