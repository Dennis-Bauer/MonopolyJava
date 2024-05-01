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
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.*;
import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.*;
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

    private final double CLOUD_SPEED = 100; //Seconds

    private final double WIDTH, HEIGHT;

    private final Figuren[] playerFigures = new Figuren[Figuren.values().length];

    //Player Variables:
    private final Boolean[] isPlayerBoxAktiv = new Boolean[5];
    private final String[] playerNames = new String[5];
    private final Figuren[] playerBoxFigures = new Figuren[5];

    public StartMenu(double width, double height) {
        this.WIDTH = width;
        this.HEIGHT = height;

        System.arraycopy(Figuren.values(), 0, playerFigures, 0, Figuren.values().length);

        for (int i = 0; i < 5; i++)
            playerNames[i] = "" + i;

        Arrays.fill(isPlayerBoxAktiv, false);

        buildBackground();
        createPlayerBoxes();

        setMaxSize(width, height);
        setId("MenuPane");
    }

    private void buildBackground() {
        ImageView background = createImageView("menu_Background", "/de/sandwich/monopoly/menu/background.png", WIDTH, HEIGHT, 0, 0);
        ImageView header = createImageView("menu_Header", "/de/sandwich/monopoly/menu/header.png", WIDTH / 2.196, (WIDTH / 2.196) * 0.18, WIDTH / 2 - (WIDTH / 2.196) / 2, 0);

        ImageView clouds1 = createImageView("menu_CloudAnimation", "/de/sandwich/monopoly/menu/clouds.png", 0, 0);
        ImageView clouds2 = createImageView("menu_CloudAnimation", "/de/sandwich/monopoly/menu/clouds.png", 0, 0);

        assert clouds1 != null;
        clouds1.setFitWidth(WIDTH * 4.167);
        clouds1.setFitHeight(HEIGHT * 0.259);

        assert clouds2 != null;
        clouds2.setFitWidth(WIDTH * 4.167);
        clouds2.setFitHeight(HEIGHT * 0.259);

        clouds1.setX(-1 * (WIDTH * 4.167));
        clouds2.setX(0);

        TranslateTransition translateTransition = new TranslateTransition(Duration.seconds(CLOUD_SPEED), clouds1);
        translateTransition.setToX((WIDTH * 4.167));
        translateTransition.setInterpolator(Interpolator.LINEAR);

        TranslateTransition translateTransition2 = new TranslateTransition(Duration.seconds(CLOUD_SPEED), clouds2);
        translateTransition2.setToX((WIDTH * 4.167));
        translateTransition2.setInterpolator(Interpolator.LINEAR);

        ParallelTransition parallelTransition = new ParallelTransition(translateTransition, translateTransition2);
        parallelTransition.setCycleCount(Animation.INDEFINITE);
        parallelTransition.play();

        Image startButtonNormal = creatImage("/de/sandwich/monopoly/menu/startButtonRed.png");
        Image startButtonHover = creatImage("/de/sandwich/monopoly/menu/startButtonGreen.png");
        ImageView startButton = createImageView("menu_StartButton", startButtonNormal, WIDTH / 4.016, (WIDTH / 4.016) * 0.50, WIDTH / 2 - (WIDTH / 4.416) / 2, HEIGHT * 0.81);
        startButton.setOnMouseExited(event -> startButton.setImage(startButtonNormal));
        startButton.setOnMouseEntered(event -> {

            int aktivPlayer = 0;
            for (int i = 0; i != 5; i++) {
                if (isPlayerBoxAktiv[i])
                    aktivPlayer++;
            }

            if(aktivPlayer >= 2 && !hasArrayDuplicates(playerNames) && !hasArrayNotNullDuplicates(playerBoxFigures)) {
                startButton.setImage(startButtonHover);
            }
         });


        Label errorMessage = buildLabel("menu_ErrorLabel", "ERROR", Font.font(Main.TEXT_FONT, FontWeight.BOLD, WIDTH * 0.01), TextAlignment.CENTER, ProgramColor.ERROR_MESSAGES.getColor(), 0, HEIGHT * 0.81);
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
                errorMessage.setText("Fehler: Es müssen mindestens 2 Spieler spielen!");
                errorMessage.setVisible(true);
                if (Main.CONSOLE_OUT_PUT)
                    consoleOutPutLine(ConsoleUtilities.colors.RED, ConsoleUtilities.textStyle.REGULAR, "FEHLER: 003");
            } else {
                errorMessage.setText("Fehler: Es müssen Spieler erstellt werden, damit das Spiel beginnen kann!");
                errorMessage.setVisible(true);
            }

            if (Main.CONSOLE_OUT_PUT)
                consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);

        });

        getChildren().addAll(background, header, clouds1, clouds2, startButton, errorMessage);
        header.toFront();
    }

    private void createPlayerBoxes() {
        //Koordinaten des Spielers in der Mitte werden berechnet und extra für die weiteren Rechnungen gespeichert
        final double MIDD_PLAYER_WIDTH = WIDTH * 0.149;
        final double MIDD_PLAYER_HEIGHT = HEIGHT * 0.6;
        final double MIDD_PLAYER_X = WIDTH / 2 - (WIDTH * 0.149) / 2;
        final double middlePlayerY = HEIGHT * 0.184;

        //Transition Variables
        final Duration startAnimationLength = Duration.seconds(2.5);
        final Duration loopAnimationSpeed = Duration.seconds(2);
        final double loopTransitionSize = 0.04;

        //Middle Player
        Pane middlePlayer = buildPlayerBox(MIDD_PLAYER_WIDTH, MIDD_PLAYER_HEIGHT, ProgramColor.MENU_THIRD_PLAYER.getColor(), MIDD_PLAYER_X, middlePlayerY, 2, 2);
        middlePlayer.setLayoutX(MIDD_PLAYER_X);
        middlePlayer.setLayoutY(-MIDD_PLAYER_HEIGHT);

        //Start Transition
        TranslateTransition middleStartTransition = moveAnimation(middlePlayer, startAnimationLength, MIDD_PLAYER_HEIGHT + middlePlayerY, 0, 1);

        //Loop Transition
        ScaleTransition middleTransition = scaleAnimation(middlePlayer, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        middleTransition.setAutoReverse(true);

        //Left Player One
        Pane leftPlayerOne = buildPlayerBox(MIDD_PLAYER_WIDTH * 0.881, MIDD_PLAYER_HEIGHT * 0.881, ProgramColor.MENU_FIRST_PLAYER.getColor(), MIDD_PLAYER_X * 0.533, middlePlayerY, 1, 1);
        leftPlayerOne.setLayoutX(MIDD_PLAYER_X * 0.533 - 2 * (MIDD_PLAYER_WIDTH * 0.881));
        leftPlayerOne.setLayoutY(-(MIDD_PLAYER_HEIGHT * 0.881));

        TranslateTransition leftOnStartTransition = moveAnimation(leftPlayerOne, startAnimationLength, MIDD_PLAYER_HEIGHT * 0.881 + middlePlayerY, 2 * (MIDD_PLAYER_WIDTH * 0.881), 1);

        ScaleTransition leftOneTransition = scaleAnimation(leftPlayerOne, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        leftOneTransition.setAutoReverse(true);

        //Left Player Two
        Pane leftPlayerTwo = buildPlayerBox(MIDD_PLAYER_WIDTH * 0.756, MIDD_PLAYER_HEIGHT * 0.722, ProgramColor.MENU_SECOND_PLAYER.getColor(), MIDD_PLAYER_X * 0.110, middlePlayerY, 3, 0);
        leftPlayerTwo.setLayoutX(MIDD_PLAYER_X * 0.110 - 2 * (MIDD_PLAYER_WIDTH * 0.756));
        leftPlayerTwo.setLayoutY(-(MIDD_PLAYER_HEIGHT * 0.722));

        TranslateTransition leftTwoStartTransition = moveAnimation(leftPlayerTwo, startAnimationLength, MIDD_PLAYER_HEIGHT * 0.722 + middlePlayerY, 2 * (MIDD_PLAYER_WIDTH * 0.756), 1);

        ScaleTransition leftTwoTransition = scaleAnimation(leftPlayerTwo, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        leftTwoTransition.setAutoReverse(true);

        //Right Player One
        Pane rightPlayerOne = buildPlayerBox(MIDD_PLAYER_WIDTH * 0.881, MIDD_PLAYER_HEIGHT * 0.881, ProgramColor.MENU_FIRST_PLAYER.getColor(), MIDD_PLAYER_X * 1.500, middlePlayerY, 1, 3);
        rightPlayerOne.setLayoutX(MIDD_PLAYER_X * 1.500 + 2 * (MIDD_PLAYER_WIDTH * 0.881));
        rightPlayerOne.setLayoutY(-(MIDD_PLAYER_HEIGHT * 0.881));

        TranslateTransition rightOneStartTransition = moveAnimation(rightPlayerOne, startAnimationLength, MIDD_PLAYER_HEIGHT * 0.881 + middlePlayerY, 2 * -(MIDD_PLAYER_WIDTH * 0.881), 1);

        ScaleTransition rightOneTransition = scaleAnimation(rightPlayerOne, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        rightOneTransition.setAutoReverse(true);

        //Right Player Two
        Pane rightPlayerTwo = buildPlayerBox(MIDD_PLAYER_WIDTH * 0.756, MIDD_PLAYER_HEIGHT * 0.722, ProgramColor.MENU_SECOND_PLAYER.getColor(), MIDD_PLAYER_X * 1.968, middlePlayerY, 3, 4);
        rightPlayerTwo.setLayoutX(MIDD_PLAYER_X * 1.968 + 2 * (MIDD_PLAYER_WIDTH * 0.756));
        rightPlayerTwo.setLayoutY(-(MIDD_PLAYER_HEIGHT * 0.722));

        TranslateTransition rightTwoStartTransition = moveAnimation(rightPlayerTwo, startAnimationLength, MIDD_PLAYER_HEIGHT * 0.722 + middlePlayerY, 2 * -(MIDD_PLAYER_WIDTH * 0.756), 1);

        ScaleTransition rightTwoTransition = scaleAnimation(rightPlayerTwo, loopAnimationSpeed, loopTransitionSize, loopTransitionSize, Animation.INDEFINITE);
        rightTwoTransition.setAutoReverse(true);

        //Start Transitions
        ParallelTransition startTransitions = new ParallelTransition(middleStartTransition, leftOnStartTransition, leftTwoStartTransition, rightOneStartTransition, rightTwoStartTransition);
        startTransitions.play();

        ParallelTransition waitTransitions = new ParallelTransition(middleTransition, leftOneTransition, leftTwoTransition, rightOneTransition, rightTwoTransition);
        waitTransitions.setAutoReverse(true);

        //Loop Transitions
        startTransitions.setOnFinished(event -> waitTransitions.play());

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

        //Name label
        Label nameLabel = buildLabel("menu_playerSelector_Name", "", Font.font(Main.TEXT_FONT, FontWeight.BOLD, 100), TextAlignment.LEFT, ProgramColor.BORDER_COLOR_DARK.getColor(), 0, 0);
        centeringChildInPane(nameLabel, playerBox);

        //Cancel Button
        StackPane removePlayerButton = buildPlus("menu_playerSelector_canceleButton", width * 0.25, width * 0.0556, 45, 0, null, ProgramColor.CHANCEL_BUTTONS.getColor(), 0, 0);
        removePlayerButton.layoutXProperty().bind(playerBox.widthProperty().divide(2).subtract(removePlayerButton.widthProperty().divide(2)));
        removePlayerButton.setLayoutY(height);

        removePlayerButton.setVisible(false);

        //Name Input screen
        VBox nameInputScreen = new VBox(height * 0.05);
        nameInputScreen.setId("menu_playerSelector_NameInput");
        Border nameInputborder = new Border(new BorderStroke(ProgramColor.BORDER_COLOR_DARK.getColor(), BorderStrokeStyle.SOLID, new CornerRadii(15), new BorderWidths(3)));
        Background nameInputBackground = new Background(new BackgroundFill(color, null, null));

        //NameInput input Box
        TextField nameInput = buildTextField("addPlayer_NameInput", "Name", width, height * 0.10, Font.font(Main.TEXT_FONT, width * 0.10));
        nameInput.setBackground(nameInputBackground);
        nameInput.setBorder(nameInputborder);
        nameInput.setAlignment(Pos.CENTER);

        //Finish Button
        StackPane nameInputFinishButton = new StackPane();

        Rectangle finishButtonCheckmarkOne = buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width * 0.0235, width * 0.07, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0);
        Rectangle finishButtonCheckmarkTwo = buildRectangle("addPlayer_finishNameInput_CheckmarkOne", width * 0.0235, width * 0.044, ProgramColor.SYMBOLE_COLOR.getColor(), true, null, 0);

        finishButtonCheckmarkOne.setRotate(45);
        finishButtonCheckmarkTwo.setRotate(-45);

        StackPane.setMargin(finishButtonCheckmarkTwo, new Insets(0, 0, -(width * 0.0166), -(width * 0.046)));

        nameInputFinishButton.getChildren().addAll(
            buildRectangle("addPlayer_finishNameInput_Background", width * 0.20, width * 0.20, ProgramColor.FINISH_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.0083),
            finishButtonCheckmarkTwo,
            finishButtonCheckmarkOne
        );

        //Cancel Button
        StackPane nameInputChancelButton = buildPlus("addPlayer_finishNameInput_chancelButton", width * 0.0235, width * 0.1333, 45, 0, null, ProgramColor.SYMBOLE_COLOR.getColor(), 0, 0);

        Rectangle cancelButtonBackground = buildRectangle("addPlayer_finishNameInput_chancelButton_Background", width * 0.20, width * 0.20, ProgramColor.CHANCEL_BUTTONS.getColor(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), width * 0.00833);

        nameInputChancelButton.getChildren().addAll(cancelButtonBackground);
        cancelButtonBackground.toBack();

        HBox nameInputButtons = new HBox(width * 0.25, nameInputChancelButton, nameInputFinishButton);
        nameInputButtons.setAlignment(Pos.CENTER);

        nameInputScreen.getChildren().addAll(nameInput, nameInputButtons);
        nameInputScreen.setLayoutY(height * 0.055);
        nameInputScreen.setVisible(false);

        //Add player Button
        StackPane createPlayerButton;
        double buttonRadius = width * 0.359;
        double buttonStrokeWidth = width * 0.055;

        createPlayerButton = buildPlus("menu_playerSelector_addButton_plus",  buttonRadius * 0.40 , buttonRadius * 1.43, 0,0, null, color, (width * 0.5) - buttonRadius * 0.5, 0);

        Circle addButtonBackground = buildCircle("menu_playerSelector_addButton_Background", buttonRadius, color.brighter(), true,  ProgramColor.BORDER_COLOR_LIGHT.getColor(), 0);

        createPlayerButton.setLayoutX(width * 0.5 - buttonRadius);
        createPlayerButton.setLayoutY(height * 0.33 - buttonRadius);

        //AddPlayer Button Aktionen
        createPlayerButton.setOnMouseEntered(event -> {
            addButtonBackground.setStrokeWidth(buttonStrokeWidth);
            createPlayerButton.setLayoutX(createPlayerButton.getLayoutX() - buttonStrokeWidth * 0.5);
            createPlayerButton.setLayoutY(createPlayerButton.getLayoutY() - buttonStrokeWidth * 0.5);
        });

        createPlayerButton.setOnMouseExited(event -> {
            addButtonBackground.setStrokeWidth(0);
            createPlayerButton.setLayoutX(createPlayerButton.getLayoutX() + buttonStrokeWidth * 0.5);
            createPlayerButton.setLayoutY(createPlayerButton.getLayoutY() + buttonStrokeWidth * 0.5);
        });

        createPlayerButton.setOnMouseClicked(event -> {
            nameInputScreen.setVisible(true);
            createPlayerButton.setVisible(false);
        });

        createPlayerButton.getChildren().add(addButtonBackground);
        addButtonBackground.toBack();

        //Player is in Game Symbol
        StackPane playerSymbol = new StackPane();

        Circle playerHead = buildCircle("menu_playerSelector_playerSymbol_Head", buttonRadius * 0.40, color.brighter(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), buttonRadius * 0.05);

        StackPane.setMargin(playerHead, new Insets(0, 0, height / 10, 0));

        Rectangle playerBody = buildRectangle("menu_playerSelector_playerSymbol_Body", buttonRadius, buttonRadius * 0.83, color.brighter(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), buttonRadius * 0.05);
        playerBody.setArcWidth(40);
        playerBody.setArcHeight(20);

        StackPane.setMargin(playerBody, new Insets(0, 0, -(height * 0.25), 0));

        playerSymbol.setLayoutX(width / 2 - buttonRadius);
        playerSymbol.setLayoutY(height / 3 - buttonRadius);

        playerSymbol.getChildren().addAll(
            buildCircle("menu_playerSelector_playerSymbol_Background", buttonRadius, color.darker(), true, ProgramColor.BORDER_COLOR_DARK.getColor(), buttonRadius * 0.05),
            playerHead, 
            playerBody);
        playerSymbol.setVisible(false);
        playerHead.toFront();

        //Select Player Figure
        Pane selectPlayerFigur = new Pane();

        Rectangle figurBackground = buildRectangle("menu_playerSelector_selectPlayerFigur_Background", buttonRadius * 2, buttonRadius * 0.77, color.brighter(), true, Color.BLACK, width * 0.0232);
        figurBackground.setArcHeight(buttonRadius * 0.77);
        figurBackground.setArcWidth(buttonRadius * 0.77);

        ImageView figure = createImageView("menu_playerSelector_selectPlayerFigur_Figur", new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/de/sandwich/monopoly/figuren/paul.png"))), 0, 0);
        figure.setY(buttonRadius * 0.05);
        figure.setX(buttonRadius * 0.4 + (width * 0.2353) * 0.5);
        figure.setFitWidth(width * 0.2353);
        figure.setFitHeight(width * 0.2353);
        figure.setVisible(false);

        selectPlayerFigur.getChildren().addAll(figurBackground, figure);

        ImageView arrowOne = buildArrow(arrowNumber);
        assert arrowOne != null;
        arrowOne.setRotate(180);
        arrowOne.setFitWidth(width * 0.25);
        arrowOne.setFitHeight(width * 0.25);
        arrowOne.setY(height * 0.77);
        arrowOne.setX(width * 0.1666);

        ImageView arrowTwo = buildArrow(arrowNumber);
        assert arrowTwo != null;
        arrowTwo.setFitWidth(width * 0.25);
        arrowTwo.setFitHeight(width * 0.25);
        arrowTwo.setY(height * 0.77);
        arrowTwo.setX(width * 0.59);

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
                fitLabel(nameLabel, width, width * 0.25, 1);
               
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

        selectPlayerFigur.setLayoutX(width * 0.5 - buttonRadius);
        selectPlayerFigur.setLayoutY(height * 0.59);


        playerBox.getChildren().addAll(
            buildRectangle("menu_playerSelector_Background", width, height, color, true, null, 0),
            buildTriangle("menu_playerSelector_background_Bottom", new Point2D(x, y + height), new Point2D(x + width * 0.5 , y + height + height * 0.071), new Point2D(x + width, y + height),color, null, -x, -y - 0.5),
            nameLabel, 
            createPlayerButton, 
            selectPlayerFigur, 
            nameInputScreen, 
            arrowOne, 
            arrowTwo, 
            playerSymbol, 
            removePlayerButton
        );

        return playerBox;
    }


    private ImageView buildArrow(int arrowNumber) {
        switch (arrowNumber) {
            case 1 -> {
                return createImageView("selectFigureArrow", "/de/sandwich/monopoly/menu/blueArrow.png", 0, 0);
            }
            case 2 -> {
                return createImageView("selectFigureArrow", "/de/sandwich/monopoly/menu/greyArrow.png", 0, 0);
            }
            case 3 -> {
                return createImageView("selectFigureArrow", "/de/sandwich/monopoly/menu/whiteArrow.png", 0, 0);
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
