package sandwich.de.monopoly.GUI.Game.DisplayController;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayMiddle.BuyStreetDisplay;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayMiddle.PayDisplay;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Fields.Street;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

public class MiddleGameDisplayController {

    private static final NullPointerException DISPLAY_NOT_CREATED = new NullPointerException("Middle display is not yet created!");
    private static Pane display;
    private static BuyStreetDisplay buyStreetDisplay;
    private static PayDisplay payDisplay;

    private static Rectangle background;

    private static double maxY = 0;
    private static ScaleTransition waitTransition;

    public static void createDisplay(double width, double height, double maxY, Color backgroundColor) {
        if (display == null) {
            MiddleGameDisplayController.maxY = maxY;

            display = new Pane();
            display.setId("MiddleDisplay");
            display.setMaxSize(width, height);
            background = buildRectangle("middleDisplay_Background", width, height, backgroundColor, true, Color.BLACK, width * 0.01);
            display.getChildren().add(background);

            waitTransition = new ScaleTransition(Duration.seconds(1), display);
            waitTransition.setCycleCount(Animation.INDEFINITE);
            waitTransition.setByX(width * 0.00006);
            waitTransition.setByY(width * 0.00006);
            waitTransition.setAutoReverse(true);

            buyStreetDisplay = new BuyStreetDisplay(width, height);
            buyStreetDisplay.setVisible(false);

            payDisplay = new PayDisplay(width, height);
            payDisplay.setVisible(false);

            display.getChildren().addAll(buyStreetDisplay, payDisplay);
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

            payDisplay.setVisible(false);

            buyStreetDisplay.showStreet(street);
            buyStreetDisplay.setVisible(true);
            enterAnimation();
        } else throw DISPLAY_NOT_CREATED;
    }

    public static void displayPayDisplay(String message, int price) {
        if (display != null) {

            display.setVisible(true);

            buyStreetDisplay.setVisible(false);

            payDisplay.showPayScreen(message, price);
            payDisplay.setVisible(true);
            enterAnimation();
        } else throw DISPLAY_NOT_CREATED;
    }

    public static void removeDisplay() {
        waitTransition.stop();

        TranslateTransition upTransition = new TranslateTransition(Duration.seconds(0.30), display);
        upTransition.setToY(-(Main.WINDOW_HEIGHT * 0.95));

        TranslateTransition downTransition = new TranslateTransition(Duration.seconds(0.9), display);
        downTransition.setToY(Main.WINDOW_HEIGHT);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), display);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        upTransition.play();

        upTransition.setOnFinished(actionEvent -> {
            downTransition.play();
            fadeTransition.play();
        });

        fadeTransition.setOnFinished(actionEvent -> {
            display.setVisible(false);

            buyStreetDisplay.setVisible(false);
            payDisplay.setVisible(false);
        });

    }

    public static void errorAnimation() {

        RotateTransition transitionPositiv = new RotateTransition(Duration.seconds(0.125), display);
        transitionPositiv.setByAngle(4);
        transitionPositiv.setCycleCount(2);
        transitionPositiv.setInterpolator(Interpolator.LINEAR);
        transitionPositiv.setAutoReverse(true);

        RotateTransition transitionNegativ = new RotateTransition(Duration.seconds(0.125), display);
        transitionNegativ.setByAngle(-4);
        transitionNegativ.setCycleCount(2);
        transitionNegativ.setInterpolator(Interpolator.LINEAR);
        transitionNegativ.setAutoReverse(true);

        transitionPositiv.play();

        transitionPositiv.setOnFinished(actionEvent -> transitionNegativ.play());

        background.setStroke(Color.RED);
        background.setOnMouseExited(mouseEvent -> background.setStroke(Color.BLACK));
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
