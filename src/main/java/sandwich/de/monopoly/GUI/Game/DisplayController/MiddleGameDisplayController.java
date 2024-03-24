package sandwich.de.monopoly.GUI.Game.DisplayController;

import javafx.animation.Animation;
import javafx.animation.FadeTransition;
import javafx.animation.ScaleTransition;
import javafx.animation.TranslateTransition;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.util.Duration;
import sandwich.de.monopoly.GUI.Game.Displays.BuyStreetDisplay;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Fields.Street;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

public class MiddleGameDisplayController {

    private static final NullPointerException DISPLAY_NOT_CREATED = new NullPointerException("Middle display is not yet created!");
    private static Pane display;
    private static BuyStreetDisplay buyStreetDisplay;
    private static double maxY = 0;
    private static ScaleTransition waitTransition;

    public static void createDisplay(double width, double height, double maxY, Color backgroundColor) {
        if (display == null) {
            MiddleGameDisplayController.maxY = maxY;

            display = new Pane();
            display.setId("MiddleDisplay");
            display.setMaxSize(width, height);
            display.getChildren().add(buildRectangle("middleDisplay_Background", width, height, backgroundColor, true, Color.BLACK, width * 0.01));

            waitTransition = new ScaleTransition(Duration.seconds(1), display);
            waitTransition.setCycleCount(Animation.INDEFINITE);
            waitTransition.setByX(width * 0.00006);
            waitTransition.setByY(width * 0.00006);
            waitTransition.setAutoReverse(true);

            buyStreetDisplay = new BuyStreetDisplay(width, height);
            buyStreetDisplay.setVisible(false);

            display.getChildren().add(buyStreetDisplay);
            display.setVisible(false);
        } else throw new RuntimeException("Middle display is already created!");
    }

    public static Pane getDisplay() {
        if (display != null)
            return display;
        else throw DISPLAY_NOT_CREATED;
    }

    public static void displayBuyStreet(Street street) {
        if (display != null) {

            display.setVisible(true);

            buyStreetDisplay.showStreet(street);
            buyStreetDisplay.setVisible(true);
            enterAnimation();
        } else throw DISPLAY_NOT_CREATED;




    }

    public static void removeDisplay() {
        waitTransition.stop();
        display.setVisible(false);

        buyStreetDisplay.setVisible(false);
    }

    private static void enterAnimation() {
        if (display != null) {

            display.setLayoutY(Main.WINDOW_HEIGHT + 10);

            display.toFront();

            TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(2), display);
            moveTransition.setToY(-(Main.WINDOW_HEIGHT - maxY));

            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), display);
            fadeTransition.setFromValue(0);
            fadeTransition.setToValue(1);

            fadeTransition.play();
            moveTransition.play();

            fadeTransition.setOnFinished(actionEvent -> startWaitAnimation());

        } else throw DISPLAY_NOT_CREATED;
    }

    private static void startWaitAnimation() {
        waitTransition.play();
    }
}
