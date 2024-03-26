package sandwich.de.monopoly.GUI.Game.DisplayController;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayMiddle.BuyStreetDisplay;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayMiddle.PayDisplay;
import sandwich.de.monopoly.GUI.Game.Displays.DisplayMiddle.StreetInfoDisplay;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Fields.Street;

import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

public class MiddleGameDisplayController extends Pane{

    //Displays
    private final BuyStreetDisplay buyStreetDisplay;
    private final PayDisplay payDisplay;
    private final StreetInfoDisplay streetInfoDisplay;

    //Variables
    private Rectangle background;

    private final double MAX_Y;
    private ScaleTransition waitTransition;

    public MiddleGameDisplayController(double width, double height, double maxY, Color backgroundColor) {
        MAX_Y = maxY;

        setId("MiddleDisplay");
        setMaxSize(width, height);
        background = buildRectangle("middleDisplay_Background", width, height, backgroundColor, true, Color.BLACK, width * 0.01);
        getChildren().add(background);

        waitTransition = new ScaleTransition(Duration.seconds(1), this);
        waitTransition.setCycleCount(Animation.INDEFINITE);
        waitTransition.setByX(width * 0.00006);
        waitTransition.setByY(width * 0.00006);
        waitTransition.setAutoReverse(true);

        buyStreetDisplay = new BuyStreetDisplay(width, height, this);
        buyStreetDisplay.setVisible(false);

        payDisplay = new PayDisplay(width, height, this);
        payDisplay.setVisible(false);

        streetInfoDisplay = new StreetInfoDisplay(width, height, this);
        streetInfoDisplay.setVisible(true);

        setLayoutX((Main.WINDOW_HEIGHT / 2) - (Main.WINDOW_HEIGHT * 0.40) / 2);
        getChildren().addAll(buyStreetDisplay, payDisplay);
        setVisible(true);
    }

    public void displayBuyStreet(Street street) {
        setVisible(true);

        payDisplay.setVisible(false);

        buyStreetDisplay.showStreet(street);
        buyStreetDisplay.setVisible(true);
        enterAnimation();
    }

    public void displayPayDisplay(String message, int price) {
        setVisible(true);

        buyStreetDisplay.setVisible(false);

        payDisplay.showPayScreen(message, price);
        payDisplay.setVisible(true);
        enterAnimation();
    }

    public void removeDisplay() {
        waitTransition.stop();

        TranslateTransition upTransition = new TranslateTransition(Duration.seconds(0.30), this);
        upTransition.setToY(-(Main.WINDOW_HEIGHT * 0.95));

        TranslateTransition downTransition = new TranslateTransition(Duration.seconds(0.9), this);
        downTransition.setToY(Main.WINDOW_HEIGHT);

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), this);
        fadeTransition.setFromValue(1);
        fadeTransition.setToValue(0);

        upTransition.play();

        upTransition.setOnFinished(actionEvent -> {
            downTransition.play();
            fadeTransition.play();
        });

        fadeTransition.setOnFinished(actionEvent -> {
            setVisible(false);

            buyStreetDisplay.setVisible(false);
            payDisplay.setVisible(false);
        });

    }

    public void errorAnimation() {

        RotateTransition transitionPositiv = new RotateTransition(Duration.seconds(0.125), this);
        transitionPositiv.setByAngle(4);
        transitionPositiv.setCycleCount(2);
        transitionPositiv.setInterpolator(Interpolator.LINEAR);
        transitionPositiv.setAutoReverse(true);

        RotateTransition transitionNegativ = new RotateTransition(Duration.seconds(0.125), this);
        transitionNegativ.setByAngle(-4);
        transitionNegativ.setCycleCount(2);
        transitionNegativ.setInterpolator(Interpolator.LINEAR);
        transitionNegativ.setAutoReverse(true);

        transitionPositiv.play();

        transitionPositiv.setOnFinished(actionEvent -> transitionNegativ.play());

        background.setStroke(Color.RED);
        background.setOnMouseExited(mouseEvent -> background.setStroke(Color.BLACK));
    }

    private void enterAnimation() {

        setLayoutY(Main.WINDOW_HEIGHT + 10);

        toFront();

        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(2),this);
        moveTransition.setToY(-(Main.WINDOW_HEIGHT - MAX_Y));

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
        moveTransition.play();

        fadeTransition.setOnFinished(actionEvent -> startWaitAnimation());

    }

    private void startWaitAnimation() {
        waitTransition.play();
    }
}
