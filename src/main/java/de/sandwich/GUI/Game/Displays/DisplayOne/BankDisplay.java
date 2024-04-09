package de.sandwich.GUI.Game.Displays.DisplayOne;

import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne;
import javafx.animation.ScaleTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;

import java.util.ArrayList;
import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.WrongIdExceptions;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.Street;


public class BankDisplay extends Pane {

    private final double WIDTH;
    private final double HEIGHT;

    private final Label mortgageDisplay;
    private final StackPane button;

    private Player activPalyer;

    private int mortgage = 0;
    private ArrayList<Integer> mortgageStreets = new ArrayList<>();


    /*
     * Muss noch hinzugefügt werden:
     * Wenn Häuser oder ähnliches auf der Straße sind, kann keine Hyphothek genommen werden!
     */

    public BankDisplay (double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_displayOne_BankDisplay");
        setMaxSize(width, height);

        mortgageDisplay = buildLabel("gameScene_displayOne_bankDisplay_MortgageDisplay", mortgage + "€", Font.font(Main.TEXT_FONT, width * 0.05), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor(), 0, 0);
        centeringChildInPane(mortgageDisplay, rootDisplay);
        mortgageDisplay.setLayoutY(height * 0.80);

        button = createButton(width * 0.35, height * 0.08);
        button.setLayoutX(width / 2 - width * 0.175);
        button.setLayoutY(height - height * 0.10);

        button.setOnMouseClicked(mouseEvent -> {
            if (activPalyer != null) {

                Main.getGameOperator();
                HashMap<Integer, Field> fields = Game.getFields();

                System.out.println("Street nummbers: " + mortgageStreets);
                for (int i = 0; i < fields.size(); i++) {
                    if (fields.get(i) instanceof Street s) {
                        if (s.getOwner() == activPalyer) {
                            if (s.isInBank()) {
                                if (!mortgageStreets.contains(i)) {
                                    s.purchaseFromBank();
                                }
                            } else if (mortgageStreets.contains(i)) {
                                s.sellToTheBank();
                            }
                        }
                    }
                }

                rootDisplay.displayPlayers(Main.getGameOperator().getPlayers());
            }

        });

        getChildren().addAll(mortgageDisplay, button);
        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void displayPlayer(Player p) {
        if (getChildren().size() > 2) {
            getChildren().remove(2);
        }

        addMortgage(-(mortgage));
        mortgageStreets.clear();
        activPalyer = p;

        Pane playerDisplay = GameDisplayControllerOne.buildPlayer(WIDTH * 0.38, HEIGHT * 0.60, ProgramColor.BANK_PLAYER_BACKGROUND.getColor(), p);
        playerDisplay.setLayoutY(HEIGHT / 2 - playerDisplay.getMaxHeight() / 2);
        playerDisplay.setLayoutX(WIDTH / 2 - playerDisplay.getMaxWidth() / 2);

        Rectangle[] streets = GameDisplayControllerOne.buildStreetInventar(WIDTH * 0.38, HEIGHT * 0.60, p);

        Pane streetDisplay = new Pane();
        streetDisplay.setLayoutY(HEIGHT / 2 - playerDisplay.getMaxHeight() / 2);
        streetDisplay.setLayoutX(WIDTH / 2 - playerDisplay.getMaxWidth() / 2);

        System.out.println("Street nummbers: " + mortgageStreets);

        for (Rectangle sObject : streets) {
            streetDisplay.getChildren().add(sObject);
            int fieldNumber = Integer.parseInt(sObject.getId().substring(12, 14));

            Main.getGameOperator();
            if (Game.getFields().get(fieldNumber) instanceof Street street) {
                if (street.getOwner() == activPalyer) {
                    if (street.isInBank()) {
                        sObject.setId(sObject.getId() + "M");
                            sObject.setStroke(ProgramColor.SELECT_COLOR.getColor());
            
                            mortgageStreets.add(fieldNumber);

                            System.out.println("Street nummbers: " + mortgageStreets);


                            ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), sObject);
                            scaleTransitionBig.setByX(sObject.getWidth() * 0.02);
                            scaleTransitionBig.setByY(sObject.getWidth() * 0.02);
                            scaleTransitionBig.play();
            
                            ScaleTransition scaleTransitionSmall = new ScaleTransition(Duration.seconds(0.05), sObject);
                            scaleTransitionSmall.setByX(-(sObject.getWidth() * 0.01));
                            scaleTransitionSmall.setByY(-(sObject.getWidth() * 0.01));
            
                            scaleTransitionBig.setOnFinished(actionEvent -> scaleTransitionSmall.play());
                    }
                }
            }


            sObject.setOnMouseClicked(mouseEvent -> {

                if (Game.getFields().get(fieldNumber) instanceof Street street) {
                    if (street.getOwner() == activPalyer) {

                        if (sObject.getId().endsWith("true")) {
                            sObject.setId(sObject.getId() + "M");
                            sObject.setStroke(ProgramColor.SELECT_COLOR.getColor());

                            mortgageStreets.add(fieldNumber);

                            System.out.println("Street nummbers: " + mortgageStreets);

                            ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), sObject);
                            scaleTransitionBig.setByX(sObject.getWidth() * 0.02);
                            scaleTransitionBig.setByY(sObject.getWidth() * 0.02);
                            scaleTransitionBig.play();

                            ScaleTransition scaleTransitionSmall = new ScaleTransition(Duration.seconds(0.05), sObject);
                            scaleTransitionSmall.setByX(-(sObject.getWidth() * 0.01));
                            scaleTransitionSmall.setByY(-(sObject.getWidth() * 0.01));

                            scaleTransitionBig.setOnFinished(actionEvent -> scaleTransitionSmall.play());

                            if (Game.getFields().get(fieldNumber) instanceof Street s)
                                addMortgage(s.getSalePrice() / 2);
                            else throw new WrongIdExceptions();
                        } else if (sObject.getId().endsWith("trueM")) {
                            sObject.setId(sObject.getId().substring(0, sObject.getId().length() - 1));

                            if (!(mortgageStreets.size() <= 1))
                                mortgageStreets.remove(fieldNumber);
                            else 
                                mortgageStreets.clear();

                            System.out.println("Street nummbers: " + mortgageStreets);

                            sObject.setStroke(ProgramColor.BORDER_COLOR_LIGHT.getColor());

                            ScaleTransition scaleTransitionBig = new ScaleTransition(Duration.seconds(0.15), sObject);
                            scaleTransitionBig.setByX((-sObject.getWidth() * 0.01));
                            scaleTransitionBig.setByY(-(sObject.getWidth() * 0.01));
                            scaleTransitionBig.play();

                            if (Game.getFields().get(fieldNumber) instanceof Street s)
                                addMortgage(-(s.getSalePrice() / 2));
                            else throw new WrongIdExceptions();
                        }
            
                    }
                }
            });

        }

        getChildren().addAll(playerDisplay, streetDisplay);
    }

    private void addMortgage(int x) {
        mortgage = mortgage + x;
        mortgageDisplay.setText(mortgage + "€");

        RuntimeException wrongNode = new IllegalArgumentException("Bank button has wrong nodes orders!");

        if (button.getChildren().get(0) instanceof Rectangle b) {
            if (mortgage < 0) {
                b.setFill(ProgramColor.BANK_MORTGAGE_BUTTON_PLUS.getColor());
            } else if (mortgage > 0) {
                b.setFill(ProgramColor.BANK_MORTGAGE_BUTTON_MINUS.getColor());
            } else {
                b.setFill(ProgramColor.BANK_MORTGAGE_BUTTON_ZERO.getColor());
            }
        } else throw wrongNode;

        if (button.getChildren().get(1) instanceof Label t) {
            if (mortgage < 0) {
                t.setText("Wieder auf kaufen");
            } else if (mortgage > 0) {
               t.setText("Hypothek aufnehmen");
            } else {
                t.setText("");
            }
        }
    }

    private StackPane createButton(double width, double height) {
        StackPane pane = new StackPane();
        pane.setId("gameScene_displayOne_bankDisplay_MortgageButton");

        Rectangle background = buildRectangle("gameScene_displayOne_bankDisplay_mortgageButton_Background", width, height, ProgramColor.BANK_MORTGAGE_BUTTON_ZERO.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.01);
        background.setArcWidth(width * 0.25);
        background.setArcHeight(width * 0.25);

        Label text = buildLabel("gameScene_displayOne_bankDisplay_mortgageButton_Text", "", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.10), null, ProgramColor.TEXT_COLOR.getColor());

        pane.getChildren().addAll(background, text);
        return pane;
    }

}
