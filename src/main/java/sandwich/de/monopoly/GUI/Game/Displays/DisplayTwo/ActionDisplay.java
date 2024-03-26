package sandwich.de.monopoly.GUI.Game.Displays.DisplayTwo;

import javafx.scene.control.Label;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerTwo;
import sandwich.de.monopoly.Main;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class ActionDisplay extends Pane {

    private final GameDisplayControllerTwo rootDisplay;
    private Pane aktionButtons;
    private Pane diceButton;

    public ActionDisplay(double width, double height, GameDisplayControllerTwo rootDisplay) {
        setId("gameScene_TradingDisplay");
        setMaxSize(width, height);

        this.rootDisplay = rootDisplay;

        double middleX = (width / 2) - (width * 0.25) / 2;
        double y = height * 0.30;
        double space = width * 0.30;

        Pane bankButton = buildButton("Bank", width * 0.25, height * 0.45, "/sandwich/de/monopoly/aktionDisplay/bank.png", Color.DARKGRAY, "bank", middleX - space, y);
        bankButton.setOnMouseClicked(mouseEvent -> {
            Main.getGameOperator().getDisplayControllerOne().displayBankDisplay(Main.getGameOperator().getTurnPlayer());
        });

        Pane buildButton = buildButton("Bauen", width * 0.25, height * 0.45, "/sandwich/de/monopoly/aktionDisplay/build.png", Color.LIME, "build", middleX, y);
        buildButton.setOnMouseClicked(mouseEvent -> {

        });

        Pane leaveButton = buildButton("Aufgeben", width * 0.25, height * 0.45, "/sandwich/de/monopoly/aktionDisplay/leave.png", Color.RED, "leave", middleX + space, y);
        leaveButton.setOnMouseClicked(mouseEvent -> {

        });


        getChildren().addAll(bankButton, buildButton, leaveButton);
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
