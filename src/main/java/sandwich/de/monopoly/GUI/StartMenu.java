package sandwich.de.monopoly.GUI;

import javafx.geometry.Insets;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Polygon;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.TextAlignment;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Utilities;

import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

public class StartMenu {

    public static Pane buildMenu(double width, double height) {
        Pane root = new Pane();
        root.setId("MenuPane");
        root.setStyle("-fx-background-color: black;");
        root.setMaxSize(width, height);

        //Menu Background und Überschrift werden erstellt
        ImageView background = Utilities.createImageView("menu_Background", "/sandwich/de/monopoly/menu/background.png", width, height, 0, 0);
        ImageView header = Utilities.createImageView("menu_Header", "/sandwich/de/monopoly/menu/header.png", width / 2.196, (width / 2.196) * 0.18, width / 2 - (width / 2.196) / 2, 0);

        //Der Los-Button wird erstellt, und die funktion werden bestimmt
        Image startButtonNormal = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/menu/start_button.png")));
        Image startButtonHover = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/menu/start_button_hover.png")));
        ImageView startButton = Utilities.createImageView("menu_StartButton", startButtonNormal, width / 3.516, (width / 3.516) * 0.230, width / 2 - (width / 3.516) / 2, height * 0.84);
        startButton.setOnMouseEntered(event -> startButton.setImage(startButtonHover));
        startButton.setOnMouseExited(event -> startButton.setImage(startButtonNormal));
        startButton.setOnMouseClicked(event -> Main.changeScene(Main.scenes.GAME));

        //Koordinaten des Spielers in der Mitte werden berechnet und extra für die weiteren Rechnungen gespeichert
        double middlePlayerWidth = width * 0.149;
        double middlePlayerHeight = height * 0.6;
        double middlePlayerX = width / 2 - (width * 0.149) / 2;
        double middlePlayerY = height * 0.184;

        //Spieler Boxen werden erstellt
        Pane middlePlayer = buildPlayerSelector(middlePlayerWidth, middlePlayerHeight, Color.rgb(126, 217, 87), middlePlayerX, middlePlayerY, 2);
        middlePlayer.setLayoutX(middlePlayerX);
        middlePlayer.setLayoutY(middlePlayerY);

        Pane leftPlayerOne = buildPlayerSelector(middlePlayerWidth * 0.881, middlePlayerHeight * 0.881, Color.rgb(92, 225, 230), middlePlayerX * 0.533, middlePlayerY, 1);
        leftPlayerOne.setLayoutX(middlePlayerX * 0.533);
        leftPlayerOne.setLayoutY(middlePlayerY);

        Pane leftPlayerTwo = buildPlayerSelector(middlePlayerWidth * 0.756, middlePlayerHeight * 0.722, Color.rgb(217, 217, 217), middlePlayerX * 0.110, middlePlayerY, 3);
        leftPlayerTwo.setLayoutX(middlePlayerX * 0.110);
        leftPlayerTwo.setLayoutY(middlePlayerY);

        Pane rightPlayerOne = buildPlayerSelector(middlePlayerWidth * 0.881, middlePlayerHeight * 0.881, Color.rgb(92, 225, 230), middlePlayerX * 1.500, middlePlayerY, 1);
        rightPlayerOne.setLayoutX(middlePlayerX * 1.500);
        rightPlayerOne.setLayoutY(middlePlayerY);

        Pane rightPlayerTwo = buildPlayerSelector(middlePlayerWidth * 0.756, middlePlayerHeight * 0.722, Color.rgb(217, 217, 217), middlePlayerX * 1.968, middlePlayerY, 3);
        rightPlayerTwo.setLayoutX(middlePlayerX * 1.968);
        rightPlayerTwo.setLayoutY(middlePlayerY);

        //Alle objekte werden der Root hinzugefügt
        root.getChildren().addAll(background, header, startButton, middlePlayer, leftPlayerOne, leftPlayerTwo, rightPlayerOne, rightPlayerTwo);

        return root;
    }

    private static Pane buildPlayerSelector(double width, double height, Color color, double x, double y, int arrowNumber) {
        Pane root = new Pane();
        root.setMaxSize(width, height + height * 0.046);
        root.setId("menu_PlayerSelector");

        //Background
        Rectangle background = Utilities.buildRectangle("menu_playerSelector_Background", width, height, color, true, null, 0);
        Polygon backgroundBottom = Utilities.buildTriangle("menu_playerSelector_background_Bottom", new Point2D(x, y + height), new Point2D(x + width / 2, y + height + height * 0.071), new Point2D(x + width, y + height),color, null, -x, -y - 0.5);

        //Name label
        Label name = Utilities.buildLabel("menu_playerSelector_Name", "", new Font(Main.textFont, 100), TextAlignment.LEFT, Color.WHITE, 0, 0);
        Utilities.centeringChildInPane(name, root);

        //Cancel Button
        StackPane cancelButton = new StackPane();
        Rectangle xLineOne = Utilities.buildRectangle("menu_playerSelector_canceleButton_LineOne", width / 4, width / 18, Color.RED, true, null, 0);
        Rectangle xLineTwo = Utilities.buildRectangle("menu_playerSelector_canceleButton_LineOne", width / 4, width / 18, Color.RED, true, null, 0);
        xLineOne.setRotate(45);
        xLineTwo.setRotate(-45);

        cancelButton.setLayoutX(width / 2 - width / 8);
        cancelButton.setLayoutY(height / 1.02);

        cancelButton.getChildren().addAll(xLineOne, xLineTwo);
        cancelButton.setVisible(false);

        //Name Input screen
        VBox nameInputBox = new VBox(height / 20);
        nameInputBox.setId("menu_playerSelector_NameInput");
        Border nameInputborder = new Border(new BorderStroke(Color.BLACK, BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(3)));
        Background nameInputbackground = new Background(new BackgroundFill(color, null, null));

        //NameInput input Box
        TextField nameInput = Utilities.buildTextField("addPlayer_NameInput", "Name", width, height / 10, Font.font(Main.textFont, width / 10));
        nameInput.setBackground(nameInputbackground);
        nameInput.setBorder(nameInputborder);
        nameInput.setAlignment(Pos.CENTER);

        //Finish Button
        StackPane finishButtonNameInput = new StackPane();

        Rectangle finishButtonBackground = Utilities.buildRectangle("addPlayer_finishNameInput_Background", width / 5, width / 5, Color.LIME, true, Color.BLACK, width / 120);
        Rectangle finishButtonCheckmarkOne = Utilities.buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width / 42.5, width / 14.5, Color.WHITE, true, null, 0);
        Rectangle finishButtonCheckmarkTwo = Utilities.buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width / 42.5, width / 22.5, Color.WHITE, true, null, 0);

        finishButtonCheckmarkOne.setRotate(45);
        finishButtonCheckmarkTwo.setRotate(-45);

        StackPane.setMargin(finishButtonCheckmarkTwo, new Insets(0, 0, -(width / 60), -(width * 0.046)));

        finishButtonNameInput.getChildren().addAll(finishButtonBackground, finishButtonCheckmarkTwo, finishButtonCheckmarkOne);

        //Cancel Button
        StackPane cancelButtonNameInput = new StackPane();

        Rectangle cancelButtonBackground = Utilities.buildRectangle("addPlayer_finishNameInput_Background", width / 5, width / 5, Color.RED, true, Color.BLACK, width / 120);
        Rectangle cancelButtonXOne = Utilities.buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width / 42.5, width / 7.5, Color.WHITE, true, null, 0);
        Rectangle cancelButtonXTwo = Utilities.buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width / 42.5, width / 7.5, Color.WHITE, true, null, 0);

        cancelButtonXOne.setRotate(45);
        cancelButtonXTwo.setRotate(-45);

        cancelButtonNameInput.getChildren().addAll(cancelButtonBackground, cancelButtonXTwo, cancelButtonXOne);

        HBox nameInputButtons = new HBox(width / 4, cancelButtonNameInput, finishButtonNameInput);
        nameInputButtons.setAlignment(Pos.CENTER);

        nameInputBox.getChildren().addAll(nameInput, nameInputButtons);
        nameInputBox.setLayoutY(height / 18);
        nameInputBox.setVisible(false);

        //Add player Button
        StackPane addButton = new StackPane();
        double buttonRadius = width * 0.359;
        double buttonStrokeWidth = width / 18;

        Circle addButtonBackground = Utilities.buildCircle("menu_playerSelector_addButton_Background", buttonRadius, color.brighter(), true, Color.WHITE, 0);
        Rectangle addButtonPlus1 = Utilities.buildRectangle("menu_playerSelector_addButton_PlusSymboleOne", buttonRadius / 2.5, buttonRadius / 0.7, color, true, null, 0,  (width / 2) - buttonRadius / 2, 0);
        Rectangle addButtonPlus2 = Utilities.buildRectangle("menu_playerSelector_addButton_PlusSymboleOne", buttonRadius / 2.5, buttonRadius / 0.7, color, true, null, 0,  (width / 2) - buttonRadius / 2, 0);
        addButtonPlus2.setRotate(90);

        addButton.setLayoutX(width / 2 - buttonRadius);
        addButton.setLayoutY(height / 3 - buttonRadius);

        //AddPlayer Button Aktionen
        addButton.setOnMouseEntered(event -> {
            addButtonBackground.setStrokeWidth(buttonStrokeWidth);
            addButton.setLayoutX(addButton.getLayoutX() - buttonStrokeWidth / 2);
            addButton.setLayoutY(addButton.getLayoutY() - buttonStrokeWidth / 2);
        });

        addButton.setOnMouseExited(event -> {
            addButtonBackground.setStrokeWidth(0);
            addButton.setLayoutX(addButton.getLayoutX() + buttonStrokeWidth / 2);
            addButton.setLayoutY(addButton.getLayoutY() + buttonStrokeWidth / 2);
        });

        addButton.setOnMouseClicked(event -> {
            nameInputBox.setVisible(true);
            addButton.setVisible(false);
        });

        addButton.getChildren().addAll(addButtonBackground, addButtonPlus1, addButtonPlus2);

        //Player is in Game Symbol
        StackPane playerSymbol = new StackPane();

        Circle playerSymbolbackground = Utilities.buildCircle("menu_playerSelector_playerSymbol_Background", buttonRadius, color.darker(), true, Color.BLACK, buttonRadius / 20);
        Circle playerHead = Utilities.buildCircle("menu_playerSelector_playerSymbol_Head", buttonRadius / 2.5, color.brighter(), true, Color.BLACK, buttonRadius / 20);

        StackPane.setMargin(playerHead, new Insets(0, 0, height / 10, 0));

        Rectangle playerBody = Utilities.buildRectangle("menu_playerSelector_playerSymbol_Body", buttonRadius, buttonRadius / 1.2, color.brighter(), true, Color.BLACK, buttonRadius / 20);
        playerBody.setArcWidth(40);
        playerBody.setArcHeight(20);

        StackPane.setMargin(playerBody, new Insets(0, 0, -(height / 4), 0));

        playerSymbol.setLayoutX(width / 2 - buttonRadius);
        playerSymbol.setLayoutY(height / 3 - buttonRadius);

        playerSymbol.getChildren().addAll(playerSymbolbackground, playerHead, playerBody);
        playerSymbol.setVisible(false);

        //Select Player Figure
        Pane selectPlayerFigur = new Pane();

        Rectangle figurBackground = Utilities.buildRectangle("menu_playerSelector_selectPlayerFigur_Background", buttonRadius * 2, buttonRadius / 1.3, color.brighter(), true, Color.BLACK, width / 43);
        figurBackground.setArcHeight(buttonRadius / 1.3);
        figurBackground.setArcWidth(buttonRadius / 1.3);

        ImageView figure = Utilities.createImageView("menu_playerSelector_selectPlayerFigur_Figur", new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/b.png"))), 0, 0);
        figure.setY(buttonRadius / 20);
        figure.setX(buttonRadius * 0.4 );
        figure.setFitWidth(width / 2.125);
        figure.setFitHeight(width / 4.25);
        figure.setVisible(false);

        selectPlayerFigur.getChildren().addAll(figurBackground, figure);

        ImageView arrowOne = buildArrow(arrowNumber);
        assert arrowOne != null;
        arrowOne.setRotate(180);
        arrowOne.setFitWidth(width / 4);
        arrowOne.setFitHeight(width / 4);
        arrowOne.setY(height / 1.3);
        arrowOne.setX(width / 6);

        ImageView arrowTwo = buildArrow(arrowNumber);
        assert arrowTwo != null;
        arrowTwo.setFitWidth(width / 4);
        arrowTwo.setFitHeight(width / 4);
        arrowTwo.setY(height / 1.3);
        arrowTwo.setX(width / 1.7);

        AtomicInteger imagePosition = new AtomicInteger();

        arrowOne.setOnMouseClicked(event -> {
            if (!name.getText().isEmpty()) {
                figure.setImage(Main.playerFigures[imagePosition.get()]);
                imagePosition.set(setFigurePosition(true, imagePosition.get()));
            }
        });

        arrowTwo.setOnMouseClicked(event -> {
            if (!name.getText().isEmpty()) {
                figure.setImage(Main.playerFigures[imagePosition.get()]);
                imagePosition.set(setFigurePosition(false, imagePosition.get()));
            }
        });

        //Name input Listener
        cancelButtonNameInput.setOnMouseClicked(event -> {
            nameInput.setText("");
            nameInputBox.setVisible(false);
            addButton.setVisible(true);
        });

        finishButtonNameInput.setOnMouseClicked(event -> {
            if (!nameInput.getText().isEmpty()) {
                name.setText(nameInput.getText());
                name.setFont(new Font(calculateOptimalFontSize(name.getText(), background.getWidth())));
                name.layoutXProperty().bind(background.layoutXProperty().add((background.getWidth() - name.prefWidth(-1)) / 2)); // Zentriert horizontal
                name.layoutYProperty().bind(background.layoutYProperty().add((background.getHeight() - name.prefHeight(-1)) / 120)); // Oben im Rechteck

                figure.setVisible(true);
                playerSymbol.setVisible(true);
                cancelButton.setVisible(true);

                nameInputBox.setVisible(false);
                name.setVisible(true);
            }
        });

        cancelButton.setOnMouseClicked(event -> {
            nameInput.setText("");
            name.setText("");
            figure.setVisible(false);
            playerSymbol.setVisible(false);
            cancelButton.setVisible(false);

            addButton.setVisible(true);
        });

        selectPlayerFigur.setLayoutX(width / 2 - buttonRadius);
        selectPlayerFigur.setLayoutY(height / 1.7);


        root.getChildren().addAll(background, backgroundBottom, name, addButton, selectPlayerFigur, nameInputBox, arrowOne, arrowTwo, playerSymbol, cancelButton);

        return root;
    }

    private static double calculateOptimalFontSize(String text, double availableWidth) {
        Label label = new Label(text);
        double fontSize = 40; // Standard-Schriftgröße
        label.setFont(new Font(fontSize));

        // Solange die Breite des Labels größer als die verfügbare Breite ist, die Schriftgröße reduzieren
        while (label.prefWidth(-1) > availableWidth) {
            fontSize -= 1;
            label.setFont(new Font(fontSize));
        }
        return fontSize;
    }

    private static ImageView buildArrow(int arrowNumber) {
        switch (arrowNumber) {
            case 1 -> {
                return Utilities.createImageView("selectFigureArrow", "/sandwich/de/monopoly/menu/blue_arrow.png", 0, 0);
            }
            case 2 -> {
                return Utilities.createImageView("selectFigureArrow", "/sandwich/de/monopoly/menu/grey_arrow.png", 0, 0);
            }
            case 3 -> {
                return Utilities.createImageView("selectFigureArrow", "/sandwich/de/monopoly/menu/white_arrow.png", 0, 0);
            }
            default -> {
                return null;
            }
        }
    }

    private static int setFigurePosition(boolean plus, int position) {
        if (plus) {
            if (position + 1 < Main.playerFigures.length)
                return position + 1;
            else return 0;
        } else {
            if (position - 1 >= 0)
                return position - 1;
            else return Main.playerFigures.length - 1;
        }
    }

}
