package de.sandwich.GUI.Game.Displays.DisplayOne;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.Fields.Street.buildHouseSymbole;

import java.util.HashMap;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.Street;
import de.sandwich.GUI.Game.DisplayController.GameDisplayControllerOne;
import de.sandwich.GUI.Game.Displays.DisplayMiddle.StreetInfoDisplay;
import javafx.animation.FadeTransition;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

public class BuildDisplay extends Pane {

    private static int housesRemain = 32;

    private final double WIDTH;
    private final double HEIGHT;

    private final GameDisplayControllerOne rootDisplay;

    private final Label errorMessage;

    private final Label housesRemainLabel;

    private Pane playerPane = new Pane();
    private Pane streetInfo = new Pane();

    private Rectangle buttonBackground;
    private Label buttonText;

    private Street activStreet = null;

    private Player activePlayer;

    public BuildDisplay(double width, double height, GameDisplayControllerOne rootDisplay) {
        setId("gameScene_displayOne_BuildDisplay");

        this.WIDTH = width;
        this.HEIGHT = height;
        this.rootDisplay = rootDisplay;

        streetInfo.setLayoutX(WIDTH / 2);
        streetInfo.setLayoutY(HEIGHT * 0.05);

        Rectangle destroyDisplayButtonBackground = buildRectangle("gameScene_displayOne_buildDisplay_toDestroyDisplayButton_Background", WIDTH * 0.25, HEIGHT * 0.10, ProgramColor.BUTTON_DISABLED.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.008);

        destroyDisplayButtonBackground.setArcWidth(width * 0.01);
        destroyDisplayButtonBackground.setArcHeight(width * 0.01);

        StackPane toDestroyDisplayButton = new StackPane(
            destroyDisplayButtonBackground,
            buildLabel("gameScene_displayOne_buildDisplay_toDestroyDisplayButton_Text", "Zum abbreißen ->", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.03), null, ProgramColor.TEXT_COLOR.getColor())
        );

        toDestroyDisplayButton.setLayoutX(width * 0.74);
        toDestroyDisplayButton.setLayoutY(HEIGHT * 0.005);

        toDestroyDisplayButton.setOnMouseClicked(mouseEnvent -> rootDisplay.displayDestroyDisplay(activePlayer));

        errorMessage = buildLabel("gameScene_displayOne_buildDisplay_ErrorMessage", "NULL", Font.font(Main.TEXT_FONT, FontWeight.BOLD ,WIDTH * 0.03), null, ProgramColor.ERROR_MESSAGES.getColor());
        centeringChildInPane(errorMessage, rootDisplay);
        errorMessage.layoutYProperty().bind(rootDisplay.heightProperty().subtract(errorMessage.heightProperty()));
        errorMessage.setVisible(false);

        housesRemainLabel = buildLabel("gameScene_displayOne_buildDisplay_HousesRemainText", "" + housesRemain, Font.font(Main.TEXT_FONT, FontWeight.BOLD ,WIDTH * 0.08), null, ProgramColor.TEXT_COLOR.getColor(), width * 0.86, height * 0.82);
        Pane houseRemainSymbole = buildHouseSymbole(width * 0.12, height * 0.18, width * 0.85, height * 0.82);

        buttonBackground = buildRectangle("gameScene_displayOne_buildDisplay_buyButton_Background", width * 0.45, height * 0.13, ProgramColor.BUTTON_DISABLED.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), WIDTH * 0.008);
        buttonBackground.setArcHeight(WIDTH * 0.05);
        buttonBackground.setArcWidth(WIDTH * 0.05);

        buttonText = buildLabel("gameScene_displayOne_buildDisplay_buyButton_Text", "---", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.04), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());

        StackPane button = new StackPane(buttonBackground, buttonText);
        button.setId("gameScene_displayOne_buildDisplay_BuyButton");
        
        button.setLayoutX(WIDTH / 2 - width * 0.225);
        button.setLayoutY(height * 0.80);

        button.setOnMouseClicked(mouseEnvent -> {
            if (activStreet == null) {

                errorMessage.setText("Wähle eine Straße aus, um was zu bauen!");

                errorMessage.setVisible(true);
            } else if (!activStreet.ownerHasFullColor()) {
                
                errorMessage.setText("Du brauchst eine ganze Farb-gruppe um was bauen zu können!");

                errorMessage.setVisible(true);
            } else if (activStreet.getHouseNumber() == -1) {

                errorMessage.setText("Mehr als ein Hotel kannst du nicht bauen!");

                errorMessage.setVisible(true);
            } else {

                Main.getGameOperator();
                HashMap<Integer, Field> fields = Game.getFields();

                boolean playerCanBuild = true;
                for (int i = 0; i < fields.size(); i++) {
                    if (fields.get(i) instanceof Street s) {
                        if (s.getGroup() == activStreet.getGroup()) {
                            if (s.getHouseNumber() < activStreet.getHouseNumber()) {
                                playerCanBuild = false;
                            } if (s.getHouseNumber() == -1 || activStreet.getHouseNumber() == -1) {
                                playerCanBuild = true;
                            }
                        }
                    }
                }

                if (playerCanBuild) {

                    if (activStreet.getHouseNumber() < 4) {
                        if (activePlayer.getBankAccount() >= activStreet.getHotelPrice()) {
                            if (housesRemain != 0) {
                                activStreet.addHouse();

                                housesRemain--;
                                housesRemainLabel.setText("" + housesRemain);

                                activePlayer.transferMoneyToBankAccount(-(activStreet.getHousePrice()));
                            } else {
                                errorMessage.setText("Es gibt keine Häuser mehr die zum Bauen genutzt werden können!");

                                errorMessage.setVisible(true);
                            }
                        
                        } else {
                            errorMessage.setText("Du hast nicht genug Geld um ein Haus zu bauen!");

                            errorMessage.setVisible(true);
                        }
                    } else {
                        if (activePlayer.getBankAccount() >= activStreet.getHotelPrice()) {
                            activStreet.addHouse();

                            housesRemain = housesRemain + 4;
                            housesRemainLabel.setText("" + housesRemain);

                            activePlayer.transferMoneyToBankAccount(-(activStreet.getHotelPrice()));
                        } else {
                            errorMessage.setText("Du hast nicht genug Geld um ein Hotel zu bauen!");

                            errorMessage.setVisible(true);
                        }
                    }


                    setStreetDisplay(activStreet);

                    if (activStreet.getHouseNumber() == 4) {
                        buttonBackground.setFill(ProgramColor.STREETS_HOTEL.getColor());
                        buttonText.setText("Hotel Bauen");
                    } else if (activStreet.getHouseNumber() == -1) {
                        errorMessage.setVisible(false);
                        buttonBackground.setFill(ProgramColor.BUTTON_DISABLED.getColor());
                        buttonText.setText("---");
                    } else {
                        buttonBackground.setFill(ProgramColor.STREETS_HOUSE.getColor());
                        buttonText.setText("Haus Bauen");
                    }
                } else {

                    errorMessage.setText("Du kannst nur Bauen, wenn du auf der Farbgruppe überall gleich viel Gebaut hast!");

                    errorMessage.setVisible(true);
                }

            }
        });

        getChildren().addAll(
            buildRectangle("gameScene_displayOne_buildDisplay_Background", width, height, ProgramColor.BACKGROUND.getColor(), false, null, 0), playerPane,
            streetInfo,
            button,
            errorMessage,
            houseRemainSymbole,
            housesRemainLabel,
            toDestroyDisplayButton
        );

    }

    public void display(Player p) {
        if (!playerPane.getChildren().isEmpty()) {
            playerPane.getChildren().clear();
        }

        activePlayer = p;

        Pane playerDisplay = GameDisplayControllerOne.buildPlayer(WIDTH * 0.38, HEIGHT * 0.60, ProgramColor.BANK_PLAYER_BACKGROUND.getColor(), p);
        Rectangle[] streets = GameDisplayControllerOne.buildStreetInventar(WIDTH * 0.38, HEIGHT * 0.60, p);
        Pane streetDisplay = new Pane();

        buttonBackground.setFill(ProgramColor.BUTTON_DISABLED.getColor());
        buttonText.setText("---");

        streetInfo.setVisible(false);
        
        for (Rectangle sObject : streets) {
            streetDisplay.getChildren().add(sObject);
            int fieldNumber = Integer.parseInt(sObject.getId().substring(12, 14));

            sObject.setOnMouseClicked(mouseEvent -> {

                if (Game.getFields().get(fieldNumber) instanceof Street street) {
                    if (street.getOwner() == activePlayer) {
                        
                        activStreet = street;

                        if (street.ownerHasFullColor() && street.getHouseNumber() != -1) {
                            errorMessage.setVisible(false);

                            if (street.getHouseNumber() == 4) {
                                buttonBackground.setFill(ProgramColor.STREETS_HOTEL.getColor());
                                buttonText.setText("Hotel Bauen");
                            } else {
                                buttonBackground.setFill(ProgramColor.STREETS_HOUSE.getColor());
                                buttonText.setText("Haus Bauen");
                            }
                        } else {
                            errorMessage.setVisible(false);
                            buttonBackground.setFill(ProgramColor.BUTTON_DISABLED.getColor());
                            buttonText.setText("---");
                        }

                        if (streetInfo.isVisible()) {
                            FadeTransition transition = new FadeTransition(Duration.seconds(0.8), streetInfo);

                            transition.setFromValue(1);
                            transition.setToValue(0);

                            transition.play();

                            transition.setOnFinished(actionEvent -> setStreetDisplay(street));

                        } else {
                            setStreetDisplay(street);
                        }
                         
                                   
                    }
                }
            });

        }

        playerPane.setLayoutY(HEIGHT * 0.18);
        playerPane.setLayoutX(WIDTH * 0.05);

        playerPane.getChildren().addAll(playerDisplay, streetDisplay, streetInfo);
    }

    private void setStreetDisplay(Street street) {

        streetInfo.getChildren().clear();
        streetInfo.getChildren().add(StreetInfoDisplay.getStreetInfoBox(street, WIDTH * 0.25, HEIGHT * 0.50));

        streetInfo.setVisible(true);

        FadeTransition transition = new FadeTransition(Duration.seconds(0.8), streetInfo);

        transition.setFromValue(0);
        transition.setToValue(1);
        transition.play();

    }
    
    public static int getHousesRemain() {
        return housesRemain;
    }

    public static void setHousesRemain(int housesRemain) {
        BuildDisplay.housesRemain = housesRemain;
    }

}
