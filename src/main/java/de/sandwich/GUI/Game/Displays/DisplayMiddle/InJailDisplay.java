package de.sandwich.GUI.Game.Displays.DisplayMiddle;

import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.WrongNodeException;
import de.sandwich.GUI.Game.DisplayController.MiddleGameDisplayController;
import de.sandwich.GUI.Game.Displays.DisplayTwo.DiceDisplay;
import javafx.scene.control.Label;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;

public class InJailDisplay extends Pane {

    private final MiddleGameDisplayController rootDisplay;

    private final double HEIGHT, WIDTH;

    private final Label infoText;

    private final Pane afterDicePane;
    private final Pane beforeDicePane;

    private Player activPlayer;

    public InJailDisplay(double width, double height, MiddleGameDisplayController rootDisplay) {
        setId("inJailDisplay");
        setMaxSize(width, height);

        this.HEIGHT = height;
        this.WIDTH = width;
        this.rootDisplay = rootDisplay;

        infoText = buildLabel("inJailDisplay_InfoText", "NULL", Font.font(Main.TEXT_FONT, width * 0.08),TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor());
        centeringChildInPane(infoText, rootDisplay);

        beforeDicePane = buildBeforeDicePane();
        beforeDicePane.setVisible(false);

        afterDicePane = buildAfterDicePane();
        afterDicePane.setVisible(false);

        getChildren().addAll(beforeDicePane, afterDicePane, infoText);
    }

    public void displayAfterDice(Player p) {
        activPlayer = p;

        beforeDicePane.setVisible(false);
        afterDicePane.setVisible(true);

        infoText.setLayoutY(0);

        if (afterDicePane.getChildren().get(1) instanceof StackPane button) {
            if (button.getChildren().get(0) instanceof Rectangle r) {

                if (activPlayer.getFreeJailCardNumber() <= 0)
                    r.setFill(ProgramColor.BUTTON_DISABLED.getColor());
                else 
                    r.setFill(ProgramColor.USE_FREE_JAIL_CARD.getColor());
                
            } else throw new WrongNodeException("useButton");
        } else throw new WrongNodeException("afterDicePane");

        // Umformertieren!!!
        infoText.setText(
                buildLongText("Leider kein Pash gewürfelt. Du",
                        ("kannst noch " + activPlayer.getInJailRemain() + " Tage warten,"),
                        "sonst musst du 1.000 zahlen.",
                        "Du kannst auch jetzt zahlen",
                        "oder eine Frei-karte nutzen.")
        );

    }

    public void displayBeforDice(Player p) {
        activPlayer = p;

        beforeDicePane.setVisible(true);
        afterDicePane.setVisible(false);

        infoText.setLayoutY(HEIGHT * 0.10);

        if (activPlayer.getInJailRemain() == 0) {

            DiceDisplay.lockeDices();

            // Umformertieren!!!
            infoText.setText(
                buildLongText("Du bist leider zu lange",
                        "im Gefängnis gewesen und musst",
                        "jetzt 1.000€ zahlen. (Tipp",
                        "falls du nicht genung Geld hast",
                        "geh zur Bank und verkaufe deine",
                        "Straßen.")
            );

            //Button hinzufügen!!!


        } else {
            // Umformertieren!!!
            infoText.setText(
                buildLongText("Du bist im Gefängins, du",
                        "kannst jetzt dein Glück",
                        "herausforderen und",
                        "versuchen ein Pash zu",
                        "würfeln. Falls du es",
                        "schafst, kommst du sofort",
                        "aus dem Gefängnis raus!")
            );
        }
    
    }

    public double getHEIGHT() {
        return HEIGHT;
    }

    private Pane buildBeforeDicePane() {
        return new Pane();
    }

    private Pane buildAfterDicePane() {
        final double SPACE_EDGE = WIDTH * 0.05;
        final double BUTTON_WIDTH = WIDTH * 0.25;
        final double BUTTON_HEIGHT = WIDTH * 0.18;
        final double BUTTON_Y = HEIGHT * 0.75;
        final double BUTTON_FONT_SIZE = WIDTH * 0.05;

        StackPane payButton = new StackPane();
        payButton.setId("inJailDisplay_PayButton");
        payButton.setLayoutY(BUTTON_Y);
        payButton.setLayoutX(SPACE_EDGE);

        payButton.getChildren().addAll(
            buildRectangle("inJailDisplay_payButton_Background", BUTTON_WIDTH, BUTTON_HEIGHT, ProgramColor.FINISH_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(),WIDTH * 0.01),
            buildLabel("inJailDisplay_payButton_Text", "Ja", Font.font(Main.TEXT_FONT, BUTTON_FONT_SIZE),TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );

        payButton.setOnMouseClicked(mouseEvent -> {

        });

        StackPane useButton = new StackPane();
        useButton.setId("inJailDisplay_UseButton");
        useButton.setLayoutY(BUTTON_Y);
        useButton.layoutXProperty().bind(rootDisplay.widthProperty().subtract(useButton.widthProperty()).divide(2));

        useButton.getChildren().addAll(
            buildRectangle("inJailDisplay_useButton_Background", BUTTON_WIDTH, BUTTON_HEIGHT, ProgramColor.USE_FREE_JAIL_CARD.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(),WIDTH * 0.01),
            buildLabel("inJailDisplay_useButton_Text", buildLongText("Karte", "nutzen"), Font.font(Main.TEXT_FONT, BUTTON_FONT_SIZE), TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );

        useButton.setOnMouseClicked(mouseEvent -> {
            if (activPlayer.getFreeJailCardNumber() > 0) {

            } else {
                Main.getGameOperator().displayErrorMessage("Du brauchs eine <Komm aus dem Gefängs frei> Karte!");
            }
        });

        StackPane refuseButton = new StackPane();
        refuseButton.setId("inJailDisplay_BuyButton");
        refuseButton.setLayoutY(BUTTON_Y);
        refuseButton.layoutXProperty().bind(refuseButton.layoutYProperty().subtract(refuseButton.layoutYProperty()).add(WIDTH) .subtract(SPACE_EDGE).subtract(refuseButton.widthProperty()));

        refuseButton.getChildren().addAll(
            buildRectangle("inJailDisplay_refuseButton_Background", BUTTON_WIDTH, BUTTON_HEIGHT, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(),WIDTH * 0.01),
            buildLabel("inJailDisplay_refuseButton_Text", "Nein", Font.font(Main.TEXT_FONT, BUTTON_FONT_SIZE),TextAlignment.CENTER, ProgramColor.TEXT_COLOR.getColor())
        );

        refuseButton.setOnMouseClicked(mouseEvent -> {
            rootDisplay.removeDisplay();
            Main.getGameOperator().setVisibilityTurnFinButton(true);
        });

        Pane p = new Pane();

        p.setMaxSize(WIDTH, HEIGHT);

        p.getChildren().addAll(payButton, useButton, refuseButton);

        return p;
    }

}


/*
 * 
 * Nicht vergessen Info:
 * 1. Der, ja ich will jetzt Kaufen buttonj Funktion
 * 2. Der, ok ich muss jetzt Kaufen button hinzufügen und funkton
 * 3. Aussehen, bzw Spieler position
 * 4. Karte nutzen 
 * 5. Karte anzeige(Mehr eine Funktion die durch die Karten kommt)
 * 
 */