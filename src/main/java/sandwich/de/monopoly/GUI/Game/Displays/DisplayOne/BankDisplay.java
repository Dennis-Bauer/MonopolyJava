package sandwich.de.monopoly.GUI.Game.Displays.DisplayOne;

import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sandwich.de.monopoly.Enums.ProgramColor;
import sandwich.de.monopoly.Exceptions.WrongIdExceptions;
import sandwich.de.monopoly.Fields.Street;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne;
import sandwich.de.monopoly.Game;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Player;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

public class BankDisplay extends Pane {

    private final double WIDTH;
    private final double HEIGHT;

    private final Label mortgageDisplay;

    private int mortgage = 0;

    public BankDisplay (double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_displayOne_BankDisplay");
        setMaxSize(width, height);

        mortgageDisplay = buildLabel("gameScene_displayOne_bankDisplay_MortgageDisplay", mortgage + "€", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, 0);
        centeringChildInPane(mortgageDisplay, rootDisplay);
        mortgageDisplay.setLayoutY(height * 0.80);

        getChildren().add(mortgageDisplay);
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void displayPlayer(Player p) {
        if (getChildren().size() > 1) {
            getChildren().remove(1);
            getChildren().remove(2);
        }

        Pane playerDisplay = GameDisplayControllerOne.buildPlayer(WIDTH * 0.38, HEIGHT * 0.60, ProgramColor.BANK_PLAYER_BACKGROUND.getColor(), p);
        playerDisplay.setLayoutY(HEIGHT / 2 - playerDisplay.getMaxHeight() / 2);
        playerDisplay.setLayoutX(WIDTH / 2 - playerDisplay.getMaxWidth() / 2);

        Rectangle[] streets = GameDisplayControllerOne.buildStreetInventar(WIDTH * 0.38, HEIGHT * 0.60, p);

        Pane streetDisplay = new Pane();
        streetDisplay.setLayoutY(HEIGHT / 2 - playerDisplay.getMaxHeight() / 2);
        streetDisplay.setLayoutX(WIDTH / 2 - playerDisplay.getMaxWidth() / 2);

        for (Rectangle street : streets) {
            streetDisplay.getChildren().add(street);

            street.setOnMouseClicked(mouseEvent -> {

                if (street.getId().endsWith("true")) {
                    street.setId(street.getId() + "C");
                    street.setStroke(ProgramColor.SELECT_COLOR.getColor());

                    ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), street);
                    scaleTransitionBig.setByX(street.getWidth() * 0.02);
                    scaleTransitionBig.setByY(street.getWidth() * 0.02);
                    scaleTransitionBig.play();

                    ScaleTransition scaleTransitionSmall = new ScaleTransition(Duration.seconds(0.05), street);
                    scaleTransitionSmall.setByX(-(street.getWidth() * 0.01));
                    scaleTransitionSmall.setByY(-(street.getWidth() * 0.01));

                    scaleTransitionBig.setOnFinished(actionEvent -> scaleTransitionSmall.play());

                    if (Game.getFields().get(Integer.parseInt(street.getId().substring(12, 14))) instanceof Street s)
                        addMortgage(s.getSalePrice() / 2);
                    else throw new WrongIdExceptions();
                } else if (street.getId().endsWith("trueC")) {
                    street.setId(street.getId().substring(0, street.getId().length() - 1));
                    System.out.println("New id: " + street.getId());

                    street.setStroke(ProgramColor.BORDER_COLOR_LIGHT.getColor());

                    ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), street);
                    scaleTransitionBig.setByX((-street.getWidth() * 0.01));
                    scaleTransitionBig.setByY(-(street.getWidth() * 0.01));
                    scaleTransitionBig.play();

                    if (Game.getFields().get(Integer.parseInt(street.getId().substring(12, 14))) instanceof Street s)
                        addMortgage(-(s.getSalePrice() / 2));
                    else throw new WrongIdExceptions();
                }
            });

        }

        getChildren().addAll(playerDisplay, streetDisplay);
    }

    private void addMortgage(int x) {
        mortgage = mortgage + x;
        mortgageDisplay.setText(mortgage + "€");
    }

}
