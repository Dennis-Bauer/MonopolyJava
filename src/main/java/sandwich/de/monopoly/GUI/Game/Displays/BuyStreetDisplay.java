package sandwich.de.monopoly.GUI.Game.Displays;

import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.GUI.Game.DisplayController.MiddleGameDisplayController;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Fields.Street;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class BuyStreetDisplay extends Pane {

    private final Label infoText;
    private Street displayedStreet;

    public BuyStreetDisplay(double width, double height) {
        setId("BuyStreetDisplay");
        setMaxSize(width, height);

        infoText = buildLabel("buyStreetDisplay_InfoText", buildLongText("NULL_STREET", "NULL_MONEY"), Font.font(Main.TEXT_FONT, width * 0.10), TextAlignment.CENTER, Color.WHITE);
        centeringChildInPane(infoText, MiddleGameDisplayController.getDisplay());

        double spaceEdge = width * 0.05;

        StackPane buyButton = new StackPane();
        buyButton.setId("buyStreetDisplay_BuyButton");
        buyButton.setLayoutY(height * 0.70);
        buyButton.setLayoutX(spaceEdge);

        buyButton.getChildren().addAll(
                buildRectangle("buyStreetDisplay_buyButton_Background", width * 0.30, height * 0.15, Color.rgb(68, 214, 36), true, Color.BLACK, width * 0.01),
                buildLabel("buyStreetDisplay_buyButton_Text", "Kaufen", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, Color.WHITE)
        );

        StackPane refuseButton = new StackPane();
        refuseButton.setId("buyStreetDisplay_BuyButton");
        refuseButton.setLayoutY(height * 0.70);
        refuseButton.setLayoutX(width - width * 0.30 - spaceEdge);

        refuseButton.getChildren().addAll(
                buildRectangle("buyStreetDisplay_refuseButton_Background", width * 0.30, height * 0.15, Color.rgb(207, 17, 17), true, Color.BLACK, width * 0.01),
                buildLabel("buyStreetDisplay_refuseButton_Text", "Nicht Kaufen", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, Color.WHITE)
        );

        getChildren().addAll(infoText, buyButton, refuseButton);

        buyButton.setOnMouseClicked(mouseEvent -> {
            MiddleGameDisplayController.removeDisplay();
            if (displayedStreet != null)
                Main.getGameOperator().buyStreet(displayedStreet);
            else throw new NullPointerException("The street the player want to buy is null!");
        });

        refuseButton.setOnMouseClicked(mouseEvent -> MiddleGameDisplayController.removeDisplay());

    }

    public void showStreet(Street s) {
        infoText.setText(buildLongText(s.getName(), s.getSalePrice() + "€"));
        centeringChildInPane(infoText, MiddleGameDisplayController.getDisplay());

        displayedStreet = s;
    }
}
