package sandwich.de.monopoly.GUI.Game.DisplayController;

import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;
import sandwich.de.monopoly.Enums.ProgramColor;
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
    private final Rectangle background;
    private final ScaleTransition waitTransition;
    private final double MAX_Y;
    private final double NORMAL_WIDTH;
    private final double NORMAL_HEIGHT;

    public MiddleGameDisplayController(double width, double height, double maxY) {
        this.MAX_Y = maxY;
        this.NORMAL_WIDTH = width;
        this.NORMAL_HEIGHT = height;

        setId("MiddleDisplay");
        setMaxSize(width, height);
        background = buildRectangle("middleDisplay_Background", width, height, ProgramColor.DISPLAY_MID_BACKGROUND.getColor() , true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01);
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

        streetInfoDisplay = new StreetInfoDisplay(width * 0.50, height * 1.55, this);
        streetInfoDisplay.setVisible(false);

        setLayoutX(((Main.WINDOW_HEIGHT * 0.98) / 2) - width / 2);
        getChildren().addAll(buyStreetDisplay, payDisplay, streetInfoDisplay);
        setVisible(false);
    }

    public void displayBuyStreet(Street street) {
        resetDisplay();

        payDisplay.setVisible(false);

        buyStreetDisplay.showStreet(street);
        buyStreetDisplay.setVisible(true);
        enterAnimation(true);
    }

    public void displayPayDisplay(String message, int price) {
        resetDisplay();

        buyStreetDisplay.setVisible(false);

        payDisplay.showPayScreen(message, price);
        payDisplay.setVisible(true);
        enterAnimation(true);
    }
    public void displayStreetInfoDisplay(Street street) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), this);
        if (streetInfoDisplay.isVisible()) {
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
        } else {
            fadeTransition.setDuration(Duration.seconds(0.1));
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(1);
            fadeTransition.play();
        }

        fadeTransition.setOnFinished(event -> {
            resetDisplay();
            setMaxSize(streetInfoDisplay.getWIDTH(), streetInfoDisplay.getHEIGHT());
            setLayoutX(((Main.WINDOW_HEIGHT * 0.98) / 2) - streetInfoDisplay.getWIDTH() / 2);
            background.setFill(ProgramColor.STREET_CARD_BACKGROUND.getColor());
            background.setWidth(streetInfoDisplay.getWIDTH());
            background.setHeight(streetInfoDisplay.getHEIGHT());

            streetInfoDisplay.buildStreetDisplay(street);
            streetInfoDisplay.setVisible(true);

            enterAnimation(false);
        });
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

        fadeTransition.setOnFinished(actionEvent -> resetDisplay());

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

        background.setStroke(ProgramColor.CHANCEL_BUTTONS.getColor());
        background.setOnMouseExited(mouseEvent -> background.setStroke(ProgramColor.BORDER_COLOR_DARK.getColor()));
    }

    private void enterAnimation(boolean playWaitTransition) {

        setLayoutY(Main.WINDOW_HEIGHT + 10);

        toFront();

        setVisible(true);

        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(2),this);
        moveTransition.setToY(-(Main.WINDOW_HEIGHT - MAX_Y));

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
        moveTransition.play();

        if (playWaitTransition)
            fadeTransition.setOnFinished(actionEvent -> waitTransition.play());

    }

    private void resetDisplay() {
        setVisible(false);

        buyStreetDisplay.setVisible(false);
        payDisplay.setVisible(false);
        streetInfoDisplay.setVisible(false);

        setMaxSize(NORMAL_WIDTH, NORMAL_HEIGHT);
        setLayoutX((Main.WINDOW_HEIGHT / 2) - (Main.WINDOW_HEIGHT * 0.40) / 2);

        background.setFill(ProgramColor.DISPLAY_MID_BACKGROUND.getColor());
        background.setWidth(NORMAL_WIDTH);
        background.setHeight(NORMAL_HEIGHT);
    }
}
