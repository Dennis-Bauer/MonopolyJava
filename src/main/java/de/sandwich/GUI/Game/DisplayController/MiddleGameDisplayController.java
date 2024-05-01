package de.sandwich.GUI.Game.DisplayController;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;

import java.util.ArrayList;

import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.Fields.Utilitie;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.BuyStationDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.BuyStreetDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.BuyUtilitieDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.InJailDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.InfoDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.LeaveDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.PayDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.PlayerIsInMinusDisplay;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.FieldInfoDisplay;
import javafx.animation.*;
import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import javafx.util.Duration;


public class MiddleGameDisplayController extends Pane{

    //Displays
    private final BuyStreetDisplay buyStreetDisplay;
    private final PayDisplay payDisplay;
    private final FieldInfoDisplay streetInfoDisplay;
    private final InJailDisplay inJailDisplay;
    private final InfoDisplay infoDisplay;
    private final PlayerIsInMinusDisplay playerIsInMinusDisplay;
    private final BuyStationDisplay buyStationDisplay;
    private final BuyUtilitieDisplay buyUtilitieDisplay;
    private final LeaveDisplay leaveDisplay;

    //Variables
    private final Rectangle background;
    private final ScaleTransition waitTransition;
    private final double MAX_Y;
    private final double NORMAL_WIDTH;
    private final double NORMAL_HEIGHT;
    
    private ArrayList<Transition> liveTransitions = new ArrayList<>();
    private boolean displayIsInRemoveAnimation = false;
    private boolean displayIsInErrorAnimation = false;

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

        streetInfoDisplay = new FieldInfoDisplay(width * 0.50, height * 1.40, this);
        streetInfoDisplay.setVisible(false);

        inJailDisplay = new InJailDisplay(width, height * 1.60, this);
        inJailDisplay.setVisible(false);

        infoDisplay = new InfoDisplay(width, height, this);
        inJailDisplay.setVisible(false);

        playerIsInMinusDisplay = new PlayerIsInMinusDisplay(width, height, this);
        playerIsInMinusDisplay.setVisible(false);

        buyStationDisplay = new BuyStationDisplay(width, height, this);
        buyStationDisplay.setVisible(false);

        buyUtilitieDisplay = new BuyUtilitieDisplay(width, height, this);
        buyStationDisplay.setVisible(false);

        leaveDisplay = new LeaveDisplay(width, height, this);
        leaveDisplay.setVisible(false);

        setLayoutX(((Main.WINDOW_HEIGHT * 0.98) / 2) - width / 2);
        getChildren().addAll(buyStreetDisplay, payDisplay, streetInfoDisplay, inJailDisplay, infoDisplay, playerIsInMinusDisplay, buyStationDisplay, buyUtilitieDisplay, leaveDisplay);
        setVisible(false);
    }

    public void displayBuyStreetDisplay(Street street) {
        resetDisplay();

        Main.getGameOperator().setVisibilityTurnFinButton(false);

        buyStreetDisplay.showStreet(street);
        buyStreetDisplay.setVisible(true);
        enterAnimation(true);
    }

    public void displayPayDisplay(String message, int price) {
        resetDisplay();

        Main.getGameOperator().setVisibilityTurnFinButton(false);

        buyStreetDisplay.setVisible(false);

        payDisplay.showPayScreen(message, price);
        payDisplay.setVisible(true);
        enterAnimation(true);
    } 

    public void displayUtilitieInfoDisplay(Utilitie utilitie) {
        if (!buyStreetDisplay.isVisible() && !payDisplay.isVisible() && !inJailDisplay.isVisible() && !buyStationDisplay.isVisible()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), this);
            if (streetInfoDisplay.isVisible()) {
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.play();
                liveTransitions.add(fadeTransition);
            } else {
                resetDisplay();

                setMaxSize(streetInfoDisplay.getWIDTH(), streetInfoDisplay.getHEIGHT());
                setLayoutX(((Main.WINDOW_HEIGHT * 0.98) / 2) - streetInfoDisplay.getWIDTH() / 2);
                background.setFill(ProgramColor.STREET_CARD_BACKGROUND.getColor());
                background.setWidth(streetInfoDisplay.getWIDTH());
                background.setHeight(streetInfoDisplay.getHEIGHT());

                streetInfoDisplay.buildUtilitieDisplay(utilitie);;
                streetInfoDisplay.setVisible(true);

                enterAnimation(false);
            }

            fadeTransition.setOnFinished(event -> {
                if (liveTransitions.contains(fadeTransition)) {
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(1);

                    streetInfoDisplay.buildUtilitieDisplay(utilitie);

                    fadeTransition.play();
                    liveTransitions.remove(fadeTransition);
                }
            });
        } else
            Main.getGameOperator().displayErrorMessage("Du kannst im moment keine Werk infos einsehen!");
    }

    public void displayStationInfoDisplay(Station station) {
        if (!buyStreetDisplay.isVisible() && !payDisplay.isVisible() && !inJailDisplay.isVisible() && !buyStationDisplay.isVisible()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), this);
            if (streetInfoDisplay.isVisible()) {
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.play();
                liveTransitions.add(fadeTransition);
            } else {
                resetDisplay();

                setMaxSize(streetInfoDisplay.getWIDTH(), streetInfoDisplay.getHEIGHT());
                setLayoutX(((Main.WINDOW_HEIGHT * 0.98) / 2) - streetInfoDisplay.getWIDTH() / 2);
                background.setFill(ProgramColor.STREET_CARD_BACKGROUND.getColor());
                background.setWidth(streetInfoDisplay.getWIDTH());
                background.setHeight(streetInfoDisplay.getHEIGHT());

                streetInfoDisplay.buildStationDisplay(station);;
                streetInfoDisplay.setVisible(true);

                enterAnimation(false);
            }

            fadeTransition.setOnFinished(event -> {
                if (liveTransitions.contains(fadeTransition)) {
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(1);

                    streetInfoDisplay.buildStationDisplay(station);;

                    fadeTransition.play();

                    liveTransitions.remove(fadeTransition);
                }
            });
        } else
            Main.getGameOperator().displayErrorMessage("Du kannst im moment keine Straßen infos einsehen!");
    }

    public void displayStreetInfoDisplay(Street street) {
        if (!buyStreetDisplay.isVisible() && !payDisplay.isVisible() && !inJailDisplay.isVisible() && !buyStationDisplay.isVisible()) {
            FadeTransition fadeTransition = new FadeTransition(Duration.seconds(0.5), this);
            if (streetInfoDisplay.isVisible()) {
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);
                fadeTransition.play();
                liveTransitions.add(fadeTransition);
            } else {
                resetDisplay();

                setMaxSize(streetInfoDisplay.getWIDTH(), streetInfoDisplay.getHEIGHT());
                setLayoutX(((Main.WINDOW_HEIGHT * 0.98) / 2) - streetInfoDisplay.getWIDTH() / 2);
                background.setFill(ProgramColor.STREET_CARD_BACKGROUND.getColor());
                background.setWidth(streetInfoDisplay.getWIDTH());
                background.setHeight(streetInfoDisplay.getHEIGHT());

                streetInfoDisplay.buildStreetDisplay(street);
                streetInfoDisplay.setVisible(true);

                enterAnimation(false);
            }

            fadeTransition.setOnFinished(event -> {
                if (liveTransitions.contains(fadeTransition)) {
                    fadeTransition.setFromValue(0);
                    fadeTransition.setToValue(1);

                    streetInfoDisplay.buildStreetDisplay(street);

                    fadeTransition.play();

                    liveTransitions.remove(fadeTransition);
                }
            });
        } else
            Main.getGameOperator().displayErrorMessage("Du kannst im moment keine Straßen infos einsehen!");
    }

    public void displayInJailDisplay(Player p, boolean beforeDice) {
        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(1.5), this);
        if (inJailDisplay.isVisible() && !beforeDice) {
            fadeTransition.setFromValue(1);
            fadeTransition.setToValue(0);
            fadeTransition.play();
            liveTransitions.add(fadeTransition);
        } else {
            resetDisplay();

            setMaxHeight(inJailDisplay.getHEIGHT());
            background.setHeight(inJailDisplay.getHEIGHT());

            inJailDisplay.displayBeforDice(p);
            inJailDisplay.setVisible(true);

            enterAnimation(false);
        }

        fadeTransition.setOnFinished(event -> {
            if (liveTransitions.contains(fadeTransition)) {
                fadeTransition.setFromValue(0);
                fadeTransition.setToValue(1);

                inJailDisplay.displayAfterDice(p);

                fadeTransition.play();

                liveTransitions.remove(fadeTransition);
            } else Main.getGameOperator().getDisplayControllerTwo().displayPlayerAction();
        });
    }

    public void displayInfoDisplay(String message) {
        resetDisplay();

        infoDisplay.display(message);
        infoDisplay.setVisible(true);

        enterAnimation(false);
    }

    public void displayPlayerIsInMinusDisplay() {
        resetDisplay();

        playerIsInMinusDisplay.setVisible(true);
        playerIsInMinusDisplay.display();

        enterAnimation(true);
    }

    public void displayBuyStationDisplay(Station station) {
        resetDisplay();

        Main.getGameOperator().setVisibilityTurnFinButton(false);

        buyStationDisplay.showStation(station);
        buyStationDisplay.setVisible(true);

        enterAnimation(true);
    }

    public void displayBuyUtilitieDisplay(Utilitie utilitie) {
        resetDisplay();

        Main.getGameOperator().setVisibilityTurnFinButton(false);

        buyUtilitieDisplay.showUtilitie(utilitie);
        buyUtilitieDisplay.setVisible(true);

        enterAnimation(true);
    }

    public void displayLeaveDisplay() {
        if (!buyStreetDisplay.isVisible() && !payDisplay.isVisible() && !inJailDisplay.isVisible() && !buyStationDisplay.isVisible()) {
            resetDisplay();
            leaveDisplay.setVisible(true);
            
            enterAnimation(true);
        } else Main.getGameOperator().removePlayer();
    }

    public void removeDisplay() {
        if (!(payDisplay.isVisible() && buyStreetDisplay.isVisible() && buyStationDisplay.isVisible())) {
            if (!displayIsInRemoveAnimation) {
                displayIsInRemoveAnimation = true;

                waitTransition.stop();
                liveTransitions.remove(waitTransition);

                TranslateTransition upTransition = new TranslateTransition(Duration.seconds(0.30), this);
                upTransition.setByY(-(Main.WINDOW_HEIGHT * 0.10));

                TranslateTransition downTransition = new TranslateTransition(Duration.seconds(0.9), this);
                downTransition.setByY(Main.WINDOW_HEIGHT);

                FadeTransition fadeTransition = new FadeTransition(Duration.seconds(2), this);
                fadeTransition.setFromValue(1);
                fadeTransition.setToValue(0);

                upTransition.play();
                liveTransitions.add(upTransition);

                upTransition.setOnFinished(actionEvent -> {
                    liveTransitions.remove(upTransition);

                    downTransition.play();
                    liveTransitions.add(downTransition);

                    fadeTransition.play();
                    liveTransitions.add(fadeTransition);
                });

                fadeTransition.setOnFinished(actionEvent -> {
                    liveTransitions.remove(downTransition);
                    liveTransitions.remove(fadeTransition);

                    resetDisplay();
                    displayIsInRemoveAnimation = false;
                });
            }
        }
    }

    public void errorAnimation() {
        if (!displayIsInErrorAnimation) {
            displayIsInErrorAnimation = true;

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
            liveTransitions.add(transitionPositiv);
    
            transitionPositiv.setOnFinished(actionEvent -> {
                liveTransitions.remove(transitionPositiv);
                
                transitionNegativ.play();
                liveTransitions.add(transitionNegativ);
            });
    
            background.setStroke(ProgramColor.CHANCEL_BUTTONS.getColor());
            background.setOnMouseExited(mouseEvent -> background.setStroke(ProgramColor.BORDER_COLOR_DARK.getColor()));
        
            transitionNegativ.setOnFinished(actionEvent -> {
                liveTransitions.remove(transitionNegativ);
                displayIsInErrorAnimation = false;
            });
        } 
    }

    private void enterAnimation(boolean playWaitTransition) {
        toFront();

        setVisible(true);

        TranslateTransition moveTransition = new TranslateTransition(Duration.seconds(2),this);
        moveTransition.setFromY(Main.WINDOW_HEIGHT);
        moveTransition.setByY(-(Main.WINDOW_HEIGHT - MAX_Y));

        FadeTransition fadeTransition = new FadeTransition(Duration.seconds(3), this);
        fadeTransition.setFromValue(0);
        fadeTransition.setToValue(1);

        fadeTransition.play();
        liveTransitions.add(fadeTransition);

        moveTransition.play();
        liveTransitions.add(moveTransition);

        if (playWaitTransition) {
            fadeTransition.setOnFinished(actionEvent -> {
                waitTransition.play();
                liveTransitions.add(waitTransition);
            });
        }

        fadeTransition.setOnFinished(actionEvent -> {
            liveTransitions.remove(fadeTransition);
            liveTransitions.remove(moveTransition);
        });
    }

    private void resetDisplay() {
        setVisible(false);

        for (Transition transition : liveTransitions)
            transition.stop();
        

        displayIsInRemoveAnimation = false;
        displayIsInErrorAnimation = false;

        buyStreetDisplay.setVisible(false);
        payDisplay.setVisible(false);
        streetInfoDisplay.setVisible(false);
        inJailDisplay.setVisible(false);
        infoDisplay.setVisible(false);
        playerIsInMinusDisplay.setVisible(false);
        buyStationDisplay.setVisible(false);
        buyUtilitieDisplay.setVisible(false);
        leaveDisplay.setVisible(false);

        setMaxSize(NORMAL_WIDTH, NORMAL_HEIGHT);
        setLayoutX((Main.WINDOW_HEIGHT / 2) - (Main.WINDOW_HEIGHT * 0.40) / 2);

        background.setFill(ProgramColor.DISPLAY_MID_BACKGROUND.getColor());
        background.setWidth(NORMAL_WIDTH);
        background.setHeight(NORMAL_HEIGHT);
    }
}
