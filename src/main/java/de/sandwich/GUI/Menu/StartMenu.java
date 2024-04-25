package de.sandwich.GUI.Menu;

import javafx.animation.*;
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
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildCircle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildTextField;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildTriangle;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.createImageView;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.creatImage;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.centeringChildInPane;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.moveAnimation;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.scaleAnimation;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.buildPlus;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.hasArrayDuplicates;
import static de.sandwich.DennisUtilitiesPackage.Java.JavaUtilities.hasArrayNotNullDuplicates;

import java.util.Arrays;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicInteger;

import de.sandwich.Game;
import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;
import de.sandwich.Enums.Figuren;
import de.sandwich.Enums.ProgramColor;

public class StartMenu extends Pane{

    /*
     *  Auf jeden fall noch änderungen vor nehmen!
     *  Vieleicht auch dierekt eine Scene raus machen?
     *
     */

    private final double width, height;

    private final Figuren[] playerFigures = new Figuren[Figuren.values().length];

    //Player Variables:

    private final Boolean[] isPlayerBoxAktiv = new Boolean[5];
    private final String[] playerNames = new String[5];
    private final Figuren[] playerBoxFigures = new Figuren[5];

    public StartMenu(double width, double height) {
        this.width = width;
        this.height = height;

        System.arraycopy(Figuren.values(), 0, playerFigures, 0, Figuren.values().length);

        for (int i = 0; i < 5; i++)
            playerNames[i] = "" + i;

        Arrays.fill(isPlayerBoxAktiv, false);

        buildBackground();
        createPlayerBoxes();

        setMaxSize(width, height);
        setId("MenuPane");
        setStyle("-fx-background-color: black;");
    }

    private void buildBackground() {
        ImageView background = createImageView("menu_Background", "/de/sandwich/monopoly/menu/background.png", width, height, 0, 0);
        ImageView header = createImageView("menu_Header", "/de/sandwich/monopoly/menu/header.png", width / 2.196, (width / 2.196) * 0.18, width / 2 - (width / 2.196) / 2, 0);

        ImageView clouds1 = createImageView("menu_CloudAnimation", "/de/sandwich/monopoly/menu/clouds.png", 0, 0);
        ImageView clouds2 = createImageView("menu_CloudAnimation", "/de/sandwich/monopoly/menu/clouds.png", 0, 0);

        assert clouds1 != null;
        clouds1.setFitWidth(width * 4.167);
        clouds1.setFitHeight(height * 0.259);

        assert clouds2 != null;
        clouds2.setFitWidth(width * 4.167);
        clouds2.setFitHeight(height * 0.259);

        clouds1.setX( -1 * (width * 4.167));
        clouds2.setX(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(100), clouds1);
        translateTransition.setToX((width * 4.167));
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(100), clouds2);
        translateTransition2.setToX((width * 4.167));
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        //Der Los-Button wird erstellt, und die funktion werden bestimmt
        Image startButtonNormal = creatImage("/de/sandwich/monopoly/menu/start_button.png");
        Image startButtonHover = creatImage("/de/sandwich/monopoly/menu/startButtonGif.gif");
        Image startButtonPressed = creatImage("/de/sandwich/monopoly/menu/startButtonPressed.gif");
        ImageView startButton = createImageView("menu_StartButton", startButtonNormal, width / 3.516, (width / 3.516) * 0.230, width / 2 - (width / 3.516) / 2, height * 0.84);
        startButton.setOnMouseEntered(event -> {
            startButton.setImage(startButtonHover);
        });
        startButton.setOnMouseExited(event -> startButton.setImage(startButtonNormal));
        startButton.setOnMousePressed(event -> startButton.setImage(startButtonPressed));
        startButton.setOnMouseReleased(event -> startButton.setImage(startButtonHover));


        Label errorMessage = buildLabel("menu_ErrorLabel", "ERROR", Font.font(Main.TEXT_FONT, FontWeight.BOLD, width * 0.01), TextAlignment.CENTER, ProgramColor.ERROR_MESSAGES.getColor(), 0, height * 0.81);
        errorMessage.toFront();
        errorMessage.setVisible(false);

        centeringChildInPane(errorMessage, this);

        startButton.setOnMouseClicked(event -> {
            errorMessage.toFront();

            if (Main.CONSOLE_OUT_PUT) {
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, "Start Button action:");
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
            }

            if (Main.CONSOLE_OUT_PUT) {
                for (int i = 0; i != 5; i++) {
                    consoleOutPut(ConsoleUtilities.colors.BLUE, ConsoleUtilities.textStyle.REGULAR, "Player" + i + ": ");
                    if (isPlayerBoxAktiv[i]) {
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Name: " + playerNames[i] + ", Figure: " + playerBoxFigures[i].toString());
                    } else {
                        consoleOutPut(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "Null");
                    }
                    System.out.println();
                }
            }

            int aktivPlayer = 0;
            for (int i = 0; i != 5; i++) {
                if (isPlayerBoxAktiv[i])
                    aktivPlayer++;
            }

            if(aktivPlayer >= 2) {
                if (!hasArrayDuplicates(playerNames)) {
                    if (!hasArrayNotNullDuplicates(playerBoxFigures)) {
                        if (Main.CONSOLE_OUT_PUT)
                            consoleOutPutLine(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Spieler werden erstellt und gespeichert!");

                        Player[] players = new Player[5];

                        for (int i = 0, j = 0; i != 5; i++) {
                            if (isPlayerBoxAktiv[i]) {
                                players[i] = new Player(playerNames[i], playerBoxFigures[i], j);
                                j++;
                            }
                        }

                        if (Main.CONSOLE_OUT_PUT)
                            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
                        Main.setGameOperator(new Game(players));
                    } else {
                        errorMessage.setText("Fehler: Alle müssen verschieden Figuren haben!");
                        errorMessage.setVisible(true);
                        if (Main.CONSOLE_OUT_PUT)
                            consoleOutPutLine(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "FEHLER: 001");
                    }
                } else {
                    errorMessage.setText("Fehler: Alle müssen verschieden Namen haben!");
                    errorMessage.setVisible(true);
                    if (Main.CONSOLE_OUT_PUT)
                        consoleOutPutLine(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "FEHLER: 002");
                }
            } else if (aktivPlayer == 1) {
                errorMessage.setText("Fehler: Es muss mehr als ein Spieler Spielen!");
                errorMessage.setVisible(true);
                if (Main.CONSOLE_OUT_PUT)
                    consoleOutPutLine(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "FEHLER: 003");
            } else {
                errorMessage.setText("Fehler: Es müssen Spieler erstellt werden damit das Spiel beginnen kann!");
                errorMessage.setVisible(true);
            }

            if (Main.CONSOLE_OUT_PUT)
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);

        });

        getChildren().addAll(background, header, clouds1, clouds2, startButton, errorMessage);
    }

    private void createPlayerBoxes() {
        //Koordinaten des Spielers in der Mitte werden berechnet und extra für die weiteren Rechnungen gespeichert
        final double middlePlayerWidth = width * 0.149;
        final double middlePlayerHeight = height * 0.6;
        final double middlePlayerX = width / 2 - (width * 0.149) / 2;
        final double middlePlayerY = height * 0.184;

        //Transition Variables
        final Duration startAnimationLength = Duration.seconds(2.5);
        final Duration loopAnimationSpeed = Duration.seconds(2);
        final double loopTransitionSize = 0.04;

        //Middle Player
        Pane middlePlayer = buildPlayerBox(middlePlayerWidth, middlePlayerHeight, ProgramColor.MENU_THIRD_PLAYER.getColor(), middlePlayerX, middlePlayerY, 2, 2);
        middlePlayer.setLayoutX(middlePlayerX);
        middlePlayer.setLayoutY(-middlePlayerHeight);

        //Start Transition
        TranslateTransition middleStartTransition = moveAnimation(middlePlayer, startAnimationLength, middlePlayerHeight + middlePlayerY, 0, 1);

        //Loop Transition
        ScaleTransition middleTransition = scaleAnimation(middlePlayer, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        middleTransition.setAutoReverse(true);

        //Left Player One
        Pane leftPlayerOne = buildPlayerBox(middlePlayerWidth * 0.881, middlePlayerHeight * 0.881, ProgramColor.MENU_FIRST_PLAYER.getColor(), middlePlayerX * 0.533, middlePlayerY, 1, 1);
        leftPlayerOne.setLayoutX(middlePlayerX * 0.533 - 2 * (middlePlayerWidth * 0.881));
        leftPlayerOne.setLayoutY(-(middlePlayerHeight * 0.881));

        TranslateTransition leftOnStartTransition = moveAnimation(leftPlayerOne, startAnimationLength, middlePlayerHeight * 0.881 + middlePlayerY, 2 * (middlePlayerWidth * 0.881), 1);

        ScaleTransition leftOneTransition = scaleAnimation(leftPlayerOne, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        leftOneTransition.setAutoReverse(true);

        //Left Player Two
        Pane leftPlayerTwo = buildPlayerBox(middlePlayerWidth * 0.756, middlePlayerHeight * 0.722, ProgramColor.MENU_SECOND_PLAYER.getColor(), middlePlayerX * 0.110, middlePlayerY, 3, 0);
        leftPlayerTwo.setLayoutX(middlePlayerX * 0.110 - 2 * (middlePlayerWidth * 0.756));
        leftPlayerTwo.setLayoutY(-(middlePlayerHeight * 0.722));

        TranslateTransition leftTwoStartTransition = moveAnimation(leftPlayerTwo, startAnimationLength, middlePlayerHeight * 0.722 + middlePlayerY, 2 * (middlePlayerWidth * 0.756), 1);

        ScaleTransition leftTwoTransition = scaleAnimation(leftPlayerTwo, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        leftTwoTransition.setAutoReverse(true);

        //Right Player One
        Pane rightPlayerOne = buildPlayerBox(middlePlayerWidth * 0.881, middlePlayerHeight * 0.881, ProgramColor.MENU_FIRST_PLAYER.getColor(), middlePlayerX * 1.500, middlePlayerY, 1, 3);
        rightPlayerOne.setLayoutX(middlePlayerX * 1.500 + 2 * (middlePlayerWidth * 0.881));
        rightPlayerOne.setLayoutY(-(middlePlayerHeight * 0.881));

        TranslateTransition rightOneStartTransition = moveAnimation(rightPlayerOne, startAnimationLength, middlePlayerHeight * 0.881 + middlePlayerY, 2 * -(middlePlayerWidth * 0.881), 1);

        ScaleTransition rightOneTransition = scaleAnimation(rightPlayerOne, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        rightOneTransition.setAutoReverse(true);

        //Right Player Two
        Pane rightPlayerTwo = buildPlayerBox(middlePlayerWidth * 0.756, middlePlayerHeight * 0.722, ProgramColor.MENU_SECOND_PLAYER.getColor(), middlePlayerX * 1.968, middlePlayerY, 3, 4);
        rightPlayerTwo.setLayoutX(middlePlayerX * 1.968 + 2 * (middlePlayerWidth * 0.756));
        rightPlayerTwo.setLayoutY(-(middlePlayerHeight * 0.722));

        TranslateTransition rightTwoStartTransition = moveAnimation(rightPlayerTwo, startAnimationLength, middlePlayerHeight * 0.722 + middlePlayerY, 2 * -(middlePlayerWidth * 0.756), 1);

        ScaleTransition rightTwoTransition = scaleAnimation(rightPlayerTwo, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        rightTwoTransition.setAutoReverse(true);

        //Start Transitions
        middleStartTransition.play();
        leftOnStartTransition.play();
        leftTwoStartTransition.play();
        rightOneStartTransition.play();
        rightTwoStartTransition.play();

        //Loop Transitions
        System.out.println("ACHTUNG FEHLER MIT ANIMATION IM MENU");
        middleStartTransition.setOnFinished(event -> middleTransition.play());
        leftOnStartTransition.setOnFinished(event -> leftOneTransition.play());
        leftTwoStartTransition.setOnFinished(event -> leftTwoTransition.play());
        rightOneStartTransition.setOnFinished(event -> rightOneTransition.play());
        rightTwoStartTransition.setOnFinished(event -> rightTwoTransition.play());


        middlePlayer.toBack();
        leftPlayerOne.toBack();
        leftPlayerTwo.toBack();
        rightPlayerOne.toBack();
        rightPlayerTwo.toBack();
        getChildren().addAll(middlePlayer, leftPlayerOne, leftPlayerTwo, rightPlayerOne, rightPlayerTwo);
    }

    private Pane buildPlayerBox(double width, double height, Color color, double x, double y, int arrowNumber, int playerNummer) {
        Pane playerBox = new Pane();
        playerBox.setMaxSize(width, height + height * 0.046);
        playerBox.setId("menu_PlayerSelector");

        //Background
        Rectangle background = buildRectangle("menu_playerSelector_Background", width, height, color, true, null, 0);
        Polygon backgroundBottom = buildTriangle("menu_playerSelector_background_Bottom", new Point2D(x, y + height), new Point2D(x + width / 2, y + height + height * 0.071), new Point2D(x + width, y + height),color, null, -x, -y - 0.5);
        //Name label
        Label nameLabel = buildLabel("menu_playerSelector_Name", "", new Font(Main.TEXT_FONT, 100), TextAlignment.LEFT, ProgramColor.TEXT_COLOR.getColor(), 0, 0);
        centeringChildInPane(nameLabel, playerBox);

        //Cancel Button
        StackPane removePlayerButton = buildPlus("menu_playerSelector_canceleButton", width / 4, width / 18, 45, 0, null, ProgramColor.CHANCEL_BUTTONS.getColor(), 0, 0);
        removePlayerButton.setLayoutX(width / 2 - width / 8);
        removePlayerButton.setLayoutY(height / 1.02);

        removePlayerButton.setVisible(false);

        //Name Input screen
        VBox nameInputScreen = new VBox(height / 20);
        nameInputScreen.setId("menu_playerSelector_NameInput");
        Border nameInputborder = new Border(new BorderStroke(ProgramColor.BORDER_COLOR_DARK.getColor(), BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(3)));
        Background nameInputBackground = new Background(new BackgroundFill(color, null, null));

        //NameInput input Box
        TextField nameInput = buildTextField("addPlayer_NameInput", "Name", width, height / 10, Font.font(Main.TEXT_FONT, width / 10));
        nameInput.setBackground(nameInputBackground);
        nameInput.setBorder(nameInputborder);
        nameInput.setAlignment(Pos.CENTER);

        //Finish Button
        StackPane nameInputFinishButton = new StackPane();

        Rectangle finishButtonBackground = buildRectangle("addPlayer_finishNameInput_Background", width / 5, width / 5, ProgramColor.FINISH_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width / 120);
        Rectangle finishButtonCheckmarkOne = buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width / 42.5, width / 14.5, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0);
        Rectangle finishButtonCheckmarkTwo = buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width / 42.5, width / 22.5, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0);

        finishButtonCheckmarkOne.setRotate(45);
        finishButtonCheckmarkTwo.setRotate(-45);

        StackPane.setMargin(finishButtonCheckmarkTwo, new Insets(0, 0, -(width / 60), -(width * 0.046)));

        nameInputFinishButton.getChildren().addAll(finishButtonBackground, finishButtonCheckmarkTwo, finishButtonCheckmarkOne);

        //Cancel Button
        StackPane nameInputChancelButton = buildPlus("addPlayer_finishNameInput_chancelButton", width / 42.5, width / 7.5, 45, 0, null, ProgramColor.SYMBOLE_COLOR.getColor(), 0, 0);

        Rectangle cancelButtonBackground = buildRectangle("addPlayer_finishNameInput_chancelButton_Background", width / 5, width / 5, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width / 120);

        nameInputChancelButton.getChildren().addAll(cancelButtonBackground);
        cancelButtonBackground.toBack();

        HBox nameInputButtons = new HBox(width / 4, nameInputChancelButton, nameInputFinishButton);
        nameInputButtons.setAlignment(Pos.CENTER);

        nameInputScreen.getChildren().addAll(nameInput, nameInputButtons);
        nameInputScreen.setLayoutY(height / 18);
        nameInputScreen.setVisible(false);

        //Add player Button
        StackPane createPlayerButton;
        double buttonRadius = width * 0.359;
        double buttonStrokeWidth = width / 18;

        createPlayerButton = buildPlus("menu_playerSelector_addButton_plus",  buttonRadius / 2.5, buttonRadius / 0.7, 0,0, null, color, (width / 2) - buttonRadius / 2, 0);

        Circle addButtonBackground = buildCircle("menu_playerSelector_addButton_Background", buttonRadius, color.brighter(), true,  ProgramColor.BORDER_COLOR_LIGHT.getColor(), 0);

        createPlayerButton.setLayoutX(width / 2 - buttonRadius);
        createPlayerButton.setLayoutY(height / 3 - buttonRadius);

        //AddPlayer Button Aktionen
        createPlayerButton.setOnMouseEntered(event -> {
            addButtonBackground.setStrokeWidth(buttonStrokeWidth);
            createPlayerButton.setLayoutX(createPlayerButton.getLayoutX() - buttonStrokeWidth / 2);
            createPlayerButton.setLayoutY(createPlayerButton.getLayoutY() - buttonStrokeWidth / 2);
        });

        createPlayerButton.setOnMouseExited(event -> {
            addButtonBackground.setStrokeWidth(0);
            createPlayerButton.setLayoutX(createPlayerButton.getLayoutX() + buttonStrokeWidth / 2);
            createPlayerButton.setLayoutY(createPlayerButton.getLayoutY() + buttonStrokeWidth / 2);
        });

        createPlayerButton.setOnMouseClicked(event -> {
            nameInputScreen.setVisible(true);
            createPlayerButton.setVisible(false);
        });

        createPlayerButton.getChildren().add(addButtonBackground);
        addButtonBackground.toBack();

        //Player is in Game Symbol
        StackPane playerSymbol = new StackPane();

        Circle playerSymbolbackground = buildCircle("menu_playerSelector_playerSymbol_Background", buttonRadius, color.darker(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), buttonRadius / 20);
        Circle playerHead = buildCircle("menu_playerSelector_playerSymbol_Head", buttonRadius / 2.5, color.brighter(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), buttonRadius / 20);

        StackPane.setMargin(playerHead, new Insets(0, 0, height / 10, 0));

        Rectangle playerBody = buildRectangle("menu_playerSelector_playerSymbol_Body", buttonRadius, buttonRadius / 1.2, color.brighter(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), buttonRadius / 20);
        playerBody.setArcWidth(40);
        playerBody.setArcHeight(20);

        StackPane.setMargin(playerBody, new Insets(0, 0, -(height / 4), 0));

        playerSymbol.setLayoutX(width / 2 - buttonRadius);
        playerSymbol.setLayoutY(height / 3 - buttonRadius);

        playerSymbol.getChildren().addAll(playerSymbolbackground, playerHead, playerBody);
        playerSymbol.setVisible(false);

        //Select Player Figure
        Pane selectPlayerFigur = new Pane();

        Rectangle figurBackground = buildRectangle("menu_playerSelector_selectPlayerFigur_Background", buttonRadius * 2, buttonRadius / 1.3, color.brighter(), true, Color.BLACK, width / 43);
        figurBackground.setArcHeight(buttonRadius / 1.3);
        figurBackground.setArcWidth(buttonRadius / 1.3);

        ImageView figure = createImageView("menu_playerSelector_selectPlayerFigur_Figur", new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/de/sandwich/monopoly/figuren/affe.png"))), 0, 0);
        figure.setY(buttonRadius / 20);
        figure.setX(buttonRadius * 0.4 + (width / 4.25) / 2);
        figure.setFitWidth(width / 4.25);
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
            if (!nameLabel.getText().isEmpty()) {
                imagePosition.set(setFigurePosition(true, imagePosition.get()));
                figure.setImage(playerFigures[imagePosition.get()].getFigureImage());

                if (isPlayerBoxAktiv[playerNummer])
                    playerBoxFigures[playerNummer] = playerFigures[imagePosition.get()];
            }
        });

        arrowTwo.setOnMouseClicked(event -> {
            if (!nameLabel.getText().isEmpty()) {
                imagePosition.set(setFigurePosition(false, imagePosition.get()));
                figure.setImage(playerFigures[imagePosition.get()].getFigureImage());

                if (isPlayerBoxAktiv[playerNummer])
                    playerBoxFigures[playerNummer] = playerFigures[imagePosition.get()];
            }
        });

        //Name input Listener
        nameInputChancelButton.setOnMouseClicked(event -> {
            nameInput.setText("");
            nameInputScreen.setVisible(false);
            createPlayerButton.setVisible(true);
            isPlayerBoxAktiv[playerNummer] = false;
        });

        nameInputFinishButton.setOnMouseClicked(event -> {
            if (!nameInput.getText().isEmpty()) {
                nameLabel.setText(nameInput.getText());
                nameLabel.setFont(new Font(calculateOptimalFontSize(nameLabel.getText(), background.getWidth())));
                nameLabel.layoutXProperty().bind(background.layoutXProperty().add((background.getWidth() - nameLabel.prefWidth(-1)) / 2)); // Zentriert horizontal
                nameLabel.layoutYProperty().bind(background.layoutYProperty().add((background.getHeight() - nameLabel.prefHeight(-1)) / 120)); // Oben im Rechteck

                figure.setVisible(true);
                figure.setImage(playerFigures[1].getFigureImage());
                playerBoxFigures[playerNummer] = playerFigures[1];

                playerSymbol.setVisible(true);
                removePlayerButton.setVisible(true);

                nameInputScreen.setVisible(false);
                nameLabel.setVisible(true);
                isPlayerBoxAktiv[playerNummer] = true;
                playerNames[playerNummer] = nameLabel.getText();
            }
        });

        removePlayerButton.setOnMouseClicked(event -> {
            nameInput.setText("");
            nameLabel.setText("");
            figure.setVisible(false);
            playerSymbol.setVisible(false);
            removePlayerButton.setVisible(false);

            playerNames[playerNummer] = "" + playerNummer;

            createPlayerButton.setVisible(true);
            isPlayerBoxAktiv[playerNummer] = false;
        });

        selectPlayerFigur.setLayoutX(width / 2 - buttonRadius);
        selectPlayerFigur.setLayoutY(height / 1.7);


        playerBox.getChildren().addAll(background, backgroundBottom, nameLabel, createPlayerButton, selectPlayerFigur, nameInputScreen, arrowOne, arrowTwo, playerSymbol, removePlayerButton);

        return playerBox;
    }

    private double calculateOptimalFontSize(String text, double availableWidth) {
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

    private ImageView buildArrow(int arrowNumber) {
        switch (arrowNumber) {
            case 1 -> {
                return createImageView("selectFigureArrow", "/de/sandwich/monopoly/menu/blue_arrow.png", 0, 0);
            }
            case 2 -> {
                return createImageView("selectFigureArrow", "/de/sandwich/monopoly/menu/grey_arrow.png", 0, 0);
            }
            case 3 -> {
                return createImageView("selectFigureArrow", "/de/sandwich/monopoly/menu/white_arrow.png", 0, 0);
            }
            default -> {
                return null;
            }
        }
    }

    private int setFigurePosition(boolean plus, int position) {
        if (plus) {
            if (position + 1 < playerFigures.length)
                return position + 1;
            else return 0;
        } else {
            if (position - 1 >= 0)
                return position - 1;
            else return playerFigures.length - 1;
        }
    }

}
