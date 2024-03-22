package sandwich.de.monopoly.GUI.Game;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.TextAlignment;
import javafx.util.Duration;
import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.Enums.ExtraFields;
import sandwich.de.monopoly.Exceptions.PlayerIsOutOfBoundsExceptions;
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;
import sandwich.de.monopoly.Exceptions.ToManyPlayersExceptions;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Player;
import sandwich.de.monopoly.Street;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.JavaUtilities.buildLongText;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildLabel;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXUtilities.*;
import static sandwich.de.monopoly.Main.TEXT_FONT;


public class Spielfeld extends Pane{

    private final Pane[] fields = new Pane[36];
    private final Pane[] corners = new Pane[4];
    private final Player[] players = new Player[5];
    private final ImageView[] playerFigures = new ImageView[5];
    private final Pane BOARD;
    private final double MIDDLE_RECTANGLE_RATION = 1.4;
    private final double FONT_SIZE, BORDER_WIDTH;
    private final double FIELD_HEIGHT;
    private final double FIELD_WIDTH;
    private final double PLAYER_FIGURE_SIZE;
    private final Color COLOR;

    public Spielfeld(double gameBoardRotate, double width, double height, Color backgroundColor) {

        this.COLOR = backgroundColor;

        FONT_SIZE = ((height / MIDDLE_RECTANGLE_RATION) / 9) / 8;
        BORDER_WIDTH = ((height / MIDDLE_RECTANGLE_RATION) / 9) / 25;
        FIELD_HEIGHT = (height - height / MIDDLE_RECTANGLE_RATION) / 2;
        FIELD_WIDTH = (height / MIDDLE_RECTANGLE_RATION) / 9;
        PLAYER_FIGURE_SIZE = height * 0.035;

        Rectangle background = buildRectangle("gameScene_Background", width, height, backgroundColor, true, null, 0, height, 0);

        VBox displays = new VBox(height * 0.02);

        GameDisplayControllerOne.createDisplayOne((width - height) / 1.1, height * 0.60);
        GameDisplayControllerTwo.createDisplayTwo((width - height) / 1.1, height * 0.38, Color.rgb(56, 182, 255));

        displays.getChildren().addAll(GameDisplayControllerOne.getDisplay(), GameDisplayControllerTwo.getDisplay());
        displays.setLayoutX(height + (((width - height) / 2) - ((width - height) / 1.1) / 2));
        displays.setLayoutY(0);

        BOARD = buildGameBoard(height, gameBoardRotate);
        getChildren().add(BOARD);

        for (int i = 0; i != 5; i++) {
            playerFigures[i] = new ImageView();
            playerFigures[i].setVisible(false);

            //Player Size
            playerFigures[i].setFitHeight(PLAYER_FIGURE_SIZE);
            playerFigures[i].setFitWidth(PLAYER_FIGURE_SIZE);

            getChildren().add(playerFigures[i]);
        }

        getChildren().addAll(background, displays);
        background.toBack();
        BOARD.toFront();
        setId("gameScreen_Root");
    }


    public void setPlayerToGameboard(ArrayList<Player> playerList) {

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, "Spieler Figuren werden auf dem Spielbrett Positioniert:");
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        if (!(playerList.size() > 5)) {
            for (int i = 0; i != 5; i++) {
                if (i < playerList.size())
                    players[i] = playerList.get(i);
                if (players[i] != null) {
                    //Testes if the player is out of bounce the area.
                    if (players[i].getFieldPostion() > 39 || players[i].getFieldPostion() < 0) throw new PlayerIsOutOfBoundsExceptions(players[i].getFieldPostion());

                    //Saves the player figure in an extra variable
                    playerFigures[i].setImage(players[i].getFigur().getFigureImage());

                    playerFigures[i].setVisible(true);
                    playerFigures[i].toFront();

                    playerFigures[i].setX(calculateXYPostion(i, players[i].getFieldPostion()).getX());
                    playerFigures[i].setY(calculateXYPostion(i, players[i].getFieldPostion()).getY());
                    if (Main.CONSOLE_OUT_PUT) {
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + i + ". Spieler wurde auf diese Position gesetzt: ");
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "[" + (calculateXYPostion(i, players[i].getFieldPostion()).getX()) + "/" + (calculateXYPostion(i, players[i].getFieldPostion()).getY()) + "]");
                        System.out.println();
                    }
                } else {
                    playerFigures[i].setVisible(false);
                }
            }
        } else throw new ToManyPlayersExceptions();

        if (Main.CONSOLE_OUT_PUT)
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);

    }

    public void movePlayerOnGameBoard(Player player, int steps) throws PlayerNotFoundExceptions {

        int playerArrayPostion = 0;
        for (Player p: players) {
            if (p == player)
                break;
            else playerArrayPostion++;
        }

        final int pArrayPos = playerArrayPostion;
        final int startPostion = players[pArrayPos].getFieldPostion();

        if (!(playerArrayPostion > 4)) {

            TranslateTransition moveAnimation = new TranslateTransition(Duration.seconds(0.8), playerFigures[pArrayPos]);
            moveAnimation.setToX((calculateXYPostion(pArrayPos, startPostion + 1).getX()) - (calculateXYPostion(pArrayPos, startPostion).getX()));
            moveAnimation.setToY((calculateXYPostion(pArrayPos, startPostion + 1).getY()) - (calculateXYPostion(pArrayPos, startPostion).getY()));
            moveAnimation.play();


            AtomicInteger i = new AtomicInteger();
            AtomicInteger step = new AtomicInteger();

            moveAnimation.setOnFinished(event -> {
                if (!(i.get() >= steps)) {
                    if (step.get() + 1 > 39)
                        step.set(-1);
                    step.getAndIncrement();

                    moveAnimation.setToX((calculateXYPostion(pArrayPos, startPostion + step.get()).getX()) - (calculateXYPostion(pArrayPos, startPostion).getX()));
                    moveAnimation.setToY((calculateXYPostion(pArrayPos, startPostion + step.get()).getY()) - (calculateXYPostion(pArrayPos, startPostion).getY()));
                    moveAnimation.play();


                    if (Main.CONSOLE_OUT_PUT) {
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + pArrayPos + ". Spieler wurde auf diese Position gesetzt: ");
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "[" + (calculateXYPostion(pArrayPos, players[pArrayPos].getFieldPostion()).getX()) + "/" + (calculateXYPostion(pArrayPos, players[pArrayPos].getFieldPostion()).getY()) + "]");
                        System.out.println();
                    }
                } else {
                    moveAnimation.stop();
                    GameDisplayControllerTwo.showPlayerAction();
                }
                i.getAndIncrement();
            });
        } else throw new PlayerNotFoundExceptions();

    }
    public void rotateGameBoard(double rotate) {
        BOARD.setRotate(rotate);
    }

    public Point2D calculateXYPostion(int playerOrderPostion, int setToPostion) {
        //Creates the array that stores the base variable of the player coordinate from which it is then calculated
        //where the player will be positioned
        double[] calculationBaseX = new double[5];
        double[] calculationBaseY = new double[5];

        //Calculate the left corner X Postion from each Player
        //and thus also creates the base coordinate
        //from which every calculation begins.
        calculationBaseX[0] = FIELD_HEIGHT / 2 - (PLAYER_FIGURE_SIZE * 3 + (FIELD_HEIGHT * 0.01) * 2) / 2;
        calculationBaseX[1] = calculationBaseX[0] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.01;
        calculationBaseX[2] = calculationBaseX[0] + (PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.01) * 2;
        calculationBaseX[3] = FIELD_HEIGHT / 2 - (PLAYER_FIGURE_SIZE * 2 + (FIELD_HEIGHT * 0.01) * 2) / 2;
        calculationBaseX[4] = calculationBaseX[3] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.01;

        //Calculate the left corner Y Postion from each Player
        //and thus also creates the base coordinate
        //from which every calculation begins
        calculationBaseY[0] = FIELD_WIDTH * 9 + FIELD_HEIGHT + FIELD_HEIGHT * 0.35;
        calculationBaseY[1] = FIELD_WIDTH * 9 + FIELD_HEIGHT + FIELD_HEIGHT * 0.35;
        calculationBaseY[2] = FIELD_WIDTH * 9 + FIELD_HEIGHT + FIELD_HEIGHT * 0.35;
        calculationBaseY[3] = calculationBaseY[0] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.001;
        calculationBaseY[4] = calculationBaseY[0] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.001;

        if (!(setToPostion > 39 || setToPostion < 0)) {
            if (setToPostion == 0 || setToPostion == 10 || setToPostion == 20 || setToPostion == 30) {
                switch (setToPostion) {
                    case 0 -> { //Bottom left corner
                        //Positions remain the same
                        //because the start calculation begins at this position
                    }
                    case 10 ->  { //Upper left corner
                        calculationBaseY[playerOrderPostion] = calculationBaseY[playerOrderPostion] - FIELD_HEIGHT - FIELD_WIDTH * 9 - FIELD_HEIGHT * 0.25;
                    }
                    case 20 -> { //Upper right corner
                        calculationBaseY[playerOrderPostion] = calculationBaseY[playerOrderPostion] - FIELD_HEIGHT - FIELD_WIDTH * 9;
                        calculationBaseX[playerOrderPostion] = calculationBaseX[playerOrderPostion] + FIELD_HEIGHT + FIELD_WIDTH * 9;
                    }
                    case 30 -> { //Bottom right corner
                        calculationBaseX[playerOrderPostion] = calculationBaseX[playerOrderPostion] + FIELD_HEIGHT + FIELD_WIDTH * 9;
                    }
                }
            } else {
                if (setToPostion <= 9 ||(setToPostion >= 21 && setToPostion <= 29)) {
                    //Creates the basic coordinates when the player is on the left or right on fields
                    calculationBaseX[0] = FIELD_HEIGHT * 0.20;
                    calculationBaseX[1] = calculationBaseX[0];
                    calculationBaseX[2] = calculationBaseX[0];
                    calculationBaseX[3] = calculationBaseX[0];
                    calculationBaseX[4] = calculationBaseX[0];

                    calculationBaseY[0] = FIELD_WIDTH * 8 + FIELD_HEIGHT + (FIELD_WIDTH / 2 - (PLAYER_FIGURE_SIZE * 2 + FIELD_HEIGHT * 0.005) / 2);
                    calculationBaseY[1] = calculationBaseY[0] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.005;
                    calculationBaseY[2] = calculationBaseY[0];
                    calculationBaseY[3] = calculationBaseY[1];
                } else {
                    //Creates the basic coordinates when the player is on top or bottom of fields
                    calculationBaseX[0] = FIELD_HEIGHT + (FIELD_WIDTH / 2 - (PLAYER_FIGURE_SIZE * 2 + FIELD_HEIGHT * 0.005) / 2);
                    calculationBaseX[1] = calculationBaseX[0] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.005;
                    calculationBaseX[2] = calculationBaseX[0];
                    calculationBaseX[3] = calculationBaseX[1];
                    calculationBaseX[4] = calculationBaseX[0];

                    calculationBaseY[0] = FIELD_HEIGHT * 0.20;
                    calculationBaseY[1] = calculationBaseY[0];
                    calculationBaseY[2] = calculationBaseY[0];
                    calculationBaseY[3] = calculationBaseY[0];
                }
                calculationBaseY[4] = calculationBaseY[0];

                //Calculates the exact position of the player
                if (setToPostion <= 9) {
                    //Left up (field 1-9)
                    calculationBaseY[playerOrderPostion] = calculationBaseY[playerOrderPostion] - ((setToPostion - 1) * FIELD_WIDTH);
                } else if (setToPostion <= 19) {
                    //above (field 11-19)
                    calculationBaseX[playerOrderPostion] = calculationBaseX[playerOrderPostion] + ((setToPostion - 11) * FIELD_WIDTH);
                } else if (setToPostion <= 29) {
                    //Right down (field 21-29)
                    calculationBaseY[playerOrderPostion] = calculationBaseY[playerOrderPostion] - ((29 - setToPostion) * FIELD_WIDTH);
                    calculationBaseX[playerOrderPostion] = calculationBaseX[playerOrderPostion] + FIELD_HEIGHT + FIELD_WIDTH * 9 + FIELD_HEIGHT * 0.60 - PLAYER_FIGURE_SIZE;
                } else {
                    //Bottom (field 31-39)
                    calculationBaseY[playerOrderPostion] = calculationBaseY[playerOrderPostion] + FIELD_HEIGHT + FIELD_WIDTH * 9 + FIELD_HEIGHT * 0.60 - PLAYER_FIGURE_SIZE;
                    calculationBaseX[playerOrderPostion] = calculationBaseX[playerOrderPostion] - ((setToPostion - 39) * FIELD_WIDTH);
                }

            }

        } else throw new PlayerIsOutOfBoundsExceptions(setToPostion);
        return new Point2D(calculationBaseX[playerOrderPostion], calculationBaseY[playerOrderPostion]);
    }

    private Pane buildGameBoard(double size, double rotate, HashMap<Integer, Street> streets) {
        Pane root = new Pane();
        root.setId("gameBoard_Root");
        StackPane board = new StackPane();
        board.setId("gameBoard");
        board.setAlignment(Pos.CENTER);

        //Black Field
        Rectangle f = buildRectangle("Test" ,size, size, Color.BLACK, true, Color.BLACK, 0);
        Rectangle v = buildRectangle("Test" ,size / MIDDLE_RECTANGLE_RATION, size/ MIDDLE_RECTANGLE_RATION, BACKGROUND_COLOR, true, Color.BLACK, 0);

        board.getChildren().addAll(f, v);

        //Creating Fields
        fields[0] = streets.get(1).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[1] = buildExtraPayField(ExtraFields.SPOTIFY_PREMIUM, 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[2] = streets.get(3).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[3] = buildGetChanceCard(ChanceColors.RED , BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[4] = buildStation(buildLongText("Nord-", "Bahnhof"), 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[5] = streets.get(6).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[6] = buildGetCommunityCard(BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[7] = streets.get(8).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[8] = streets.get(9).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[9] = streets.get(11).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[10] = buildExtraPayField(ExtraFields.HESSLER_SCHULDEN, 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[11] = streets.get(13).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[12] = streets.get(14).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[13] = buildStation(buildLongText("West-", "Bahnhof"), 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[14] = streets.get(16).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[15] = streets.get(17).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[16] = buildGetChanceCard(ChanceColors.BLUE, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[17] = streets.get(19).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[18] = streets.get(21).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[19] = streets.get(22).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[20] = buildGetCommunityCard(BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[21] = streets.get(24).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[22] = buildStation(buildLongText("Süd-", "Bahnhof"), 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[23] = streets.get(26).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[24] = streets.get(27).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[25] = buildExtraPayField(ExtraFields.NAME_THREE, 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[26] = streets.get(29).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[27] = streets.get(31).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[28] = streets.get(32).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[29] = buildGetChanceCard(ChanceColors.GREEN, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[30] = streets.get(34).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[31] = buildStation(buildLongText("Haupt-", "Bahnhof"), 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[32] = buildExtraPayField(ExtraFields.NAME_FOUR, 200, BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[33] = streets.get(37).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);
        fields[34] = buildGetCommunityCard(BACKGROUND_COLOR, FIELD_WIDTH, FIELD_HEIGHT);
        fields[35] = streets.get(39).buildStreetField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE, BACKGROUND_COLOR);

        //Creating Corners
        corners[0] = buildStart(BACKGROUND_COLOR, FIELD_HEIGHT);
        corners[1] = buildJail(BACKGROUND_COLOR, FIELD_HEIGHT);
        corners[2] = buildFreeParking(BACKGROUND_COLOR, FIELD_HEIGHT);
        corners[3] = buildGoToJail(BACKGROUND_COLOR, FIELD_HEIGHT);


        //Position fields
        for (int i = 0; i < 36; i++) {
            board.getChildren().add(fields[i]);
            StackPane.setAlignment(fields[i], Pos.TOP_LEFT);
            double rightCorner = (size / MIDDLE_RECTANGLE_RATION) + ((size * (MIDDLE_RECTANGLE_RATION - 1) / (2 * MIDDLE_RECTANGLE_RATION)));

            if (i < 9) {
                fields[i].rotateProperty().set(90);
                fields[i].setTranslateY(((FIELD_HEIGHT + FIELD_WIDTH) / 2) + (8 * FIELD_WIDTH - FIELD_WIDTH * i));
                fields[i].setTranslateX((FIELD_HEIGHT - FIELD_WIDTH) / 2);
            } else if (i < 18) {
                fields[i].rotateProperty().set(180);
                fields[i].setTranslateY(0);
                fields[i].setTranslateX(rightCorner - FIELD_WIDTH * 17 + FIELD_WIDTH * i - FIELD_WIDTH);
            } else if (i < 27) {
                fields[i].rotateProperty().set(270);
                fields[i].setTranslateY((FIELD_HEIGHT + FIELD_WIDTH) / 2 - 18 * FIELD_WIDTH + FIELD_WIDTH * i);
                fields[i].setTranslateX(rightCorner + (FIELD_HEIGHT - FIELD_WIDTH) / 2);
            } else {
                fields[i].setTranslateY(size - FIELD_HEIGHT);
                fields[i].setTranslateX(FIELD_HEIGHT + 8 * FIELD_WIDTH - i * FIELD_WIDTH + 27 * FIELD_WIDTH);
            }
        }

        //Position Corners
        for (int i = 0; i < 4; i++) {
            board.getChildren().add(corners[i]);
            StackPane.setAlignment(corners[i], Pos.TOP_LEFT);
            double cornerCoordinate = ((size - size / MIDDLE_RECTANGLE_RATION) / 2) + size / MIDDLE_RECTANGLE_RATION;
            if(i == 0) {
                corners[i].setTranslateY(cornerCoordinate);
            } else if(i == 1) {
                corners[i].setRotate(90);
            } else if(i == 2) {
                corners[i].setRotate(180);
                corners[i].setTranslateX(cornerCoordinate);
            } else if(i == 3) {
                corners[i].setRotate(270);
                corners[i].setTranslateY(cornerCoordinate);
                corners[i].setTranslateX(cornerCoordinate);
            }
        }

        root.getChildren().addAll(board);

        board.setRotate(rotate);

        return root;
    }

    private Pane buildStart(Color backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("start_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_start_Background", size, size, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        ImageView arrow = createImageView("corner_start_Arrow" ,"/sandwich/de/monopoly/gameBoard/startArrow.png", size / 6, size / 1.25, (size -(size / 1.25)) / 2, (size -(size / 1.25)) / 2);
        Label text = buildLabel("corner_start_Text", buildLongText("LOS", "Bekomme 200", "beim drüber gehen"), Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK);

        text.widthProperty().addListener((obs, oldVal, newVal) -> text.setTranslateX((size - newVal.doubleValue()) / 0.8));
        text.heightProperty().addListener((obs, oldVal, newVal) -> text.setTranslateY((newVal.doubleValue()) / 1.5));
        text.setRotate(45);

        field.getChildren().addAll(background, arrow, text);
        return field;
    }

    private Pane buildJail(Color backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("jail_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_jail_Background", size, size, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        Rectangle backgroundJail = buildRectangle("corner_jail_JailBackground", size / 2, size / 2, Color.ORANGE, true, Color.BLACK, BORDER_WIDTH, size - size / 2, 0);
        ImageView jailMan = createImageView("corner_jail_Man" ,"/sandwich/de/monopoly/gameBoard/jailMan.png", size / 3, size / 3, size - size / 2 + (((size / 2) - (size / 3) / 2) - size / 4), (((size / 2) - (size / 3) / 2) - size / 4));
        Label header = buildLabel("corner_jail_Header", "Im", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, size / 2 + size / 3, size / 18);
        Label footer = buildLabel("corner_jail_FooterText", "Bau", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, size / 1.9, size / 2.9);
        Label textOne = buildLabel("corner_jail_FirstText", "Nur", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE * 1.5), TextAlignment.CENTER, Color.BLACK, 0, size / 5.5);
        Label textTwo = buildLabel("corner_jail_SecondText", "zu besuch", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE * 1.5), TextAlignment.CENTER, Color.BLACK, 0, size / 1.3);

        centeringChildInPane(textTwo, field);

        jailMan.setRotate(45);
        header.setRotate(45);
        footer.setRotate(45);
        textOne.setRotate(90);

        field.getChildren().addAll(background, backgroundJail, jailMan, header, footer, textOne, textTwo);
        return field;
    }

    private Pane buildFreeParking(Color backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("freeParking_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_freeParking_Background", size, size, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        Label header = buildLabel("corner_freeParking_Header", "Freies", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE * 2), TextAlignment.CENTER, Color.BLACK, size / 5 + size / 3.25, size / 5.75);
        ImageView freeParking = createImageView("corner_freeParking_Picture", "/sandwich/de/monopoly/gameBoard/freeParking.png", size / 1.5, size / 1.75, size / 4.75,size / 5);
        Label footer = buildLabel("corner_freeParking_Footer", "Parken", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE * 2), TextAlignment.CENTER, Color.BLACK, size / 8.25, size / 1.7);

        header.setRotate(45);
        freeParking.setRotate(45);
        footer.setRotate(45);

        field.getChildren().addAll(background, header, freeParking, footer);
        return field;
    }

    private Pane buildGoToJail(Color backgroundColor, double size) {
        Pane field = new Pane();
        field.setId("goToJail_field");
        field.setMaxSize(size, size);

        Rectangle background = buildRectangle("corner_goToJail_Background", size, size, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        Label header = buildLabel("corner_goToJail_Header", "Geh ins", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE * 2), TextAlignment.CENTER, Color.BLACK, size / 6 + size / 3.25, size / 5.75);
        ImageView freeParking = createImageView("corner_goToJail_Picture", "/sandwich/de/monopoly/gameBoard/goToJail.png", size / 1.5, size / 1.75, size / 4.75,size / 5);
        Label footer = buildLabel("corner_goToJail_Footer", "Gefängnis", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE * 2), TextAlignment.CENTER, Color.BLACK, -(size / 15), size / 1.8);

        header.setRotate(45);
        freeParking.setRotate(45);
        footer.setRotate(45);

        field.getChildren().addAll(background, header, freeParking, footer);
        return field;
    }

    private Pane buildStation(String stationName, double price, Color backgroundColor ,double width, double height) {
        Pane field = new Pane();
        field.setId("station_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("station_Background" ,width, height, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        Label header = buildLabel("station_Header", stationName, Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, 0, height / 50);
        ImageView train = createImageView("station_Image", "/sandwich/de/monopoly/gameBoard/train.png", width / 1.15, height / 3.7,(width - width / 1.15) / 2, height / 3);
        Label priceIndicator = buildLabel("station_PriceIndicator", (price + "€"), Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));


        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        field.getChildren().addAll(background, header, train, priceIndicator);
        return field;
    }

    private Pane buildGetChanceCard(ChanceColors c, Color backgroundColor ,double width, double height) {
        Pane field = new Pane();
        field.setId("getChanceCard_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("chance_Background" ,width, height, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        Label header = buildLabel("chance_Header", "Chance", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, 0, height / 50);

        Image i = null;
        switch (c) {
            case RED ->
                i = creatImage("/sandwich/de/monopoly/gameBoard/chance_red.png");
            case BLUE ->
                i = creatImage("/sandwich/de/monopoly/gameBoard/chance_blue.png");
            case GREEN ->
                i = creatImage("/sandwich/de/monopoly/gameBoard/chance_green.png");
        }

        ImageView image = createImageView("chance_Image", i, width / 1.1, height / 1.6,(width - width / 1.15) / 2, height / 3.5);

        centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);
        return field;
    }

    private Pane buildGetCommunityCard(Color backgroundColor ,double width, double height) {
        Pane field = new Pane();
        field.setId("getCommunityCard_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("community_Background" ,width, height, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        Label header = buildLabel("community_Header", buildLongText("Gesellschafts", "Feld"), Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, 0, height / 50);
        ImageView image = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/communityChest.png", width / 1.1, height / 1.9,(width - width / 1.15) / 2, height / 3.5);

        centeringChildInPane(header, field);


        field.getChildren().addAll(background, header, image);
        return field;
    }

	private Pane buildExtraPayField(ExtraFields f, int price, Color backgroundColor, double width, double height) {
        Pane field = new Pane();
        field.setId("extraPay_field");
        field.setMaxSize(width, height);

        Rectangle background = buildRectangle("extraPay_Background" ,width, height, backgroundColor, true, Color.BLACK, BORDER_WIDTH);
        Label header = buildLabel("extraPay_Header", "ERROR", Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, 0, height / 10);
        Label priceIndicator = buildLabel("station_PriceIndicator", (price + "€"), Font.font(TEXT_FONT, FontWeight.BOLD, FONT_SIZE), TextAlignment.CENTER, Color.BLACK, 0, 5 * (height / 6));


        centeringChildInPane(header, field);
        centeringChildInPane(priceIndicator, field);

        ImageView picture = null;
        switch (f) {
            case SPOTIFY_PREMIUM -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText(buildLongText("Spotify", "Premium Abo"));
            }
            case HESSLER_SCHULDEN -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/hessler.png", width / 3.4, height / 2.3,(width - width / 3.3) / 2, height / 2.7);
                header.setText(buildLongText("Freu Hessler", "Schulden ab", "bezahlen"));
            }
            case NAME_THREE -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText("NAME3");
            }
            case NAME_FOUR -> {
                picture = createImageView("community_Image", "/sandwich/de/monopoly/gameBoard/spotify.png", width / 1.2, width / 1.2,(width - width / 1.15) / 2, height / 3);
                header.setText("NAME4");
            }
        }

        field.getChildren().addAll(background, header, picture, priceIndicator);
        return field;
	}

    private enum ChanceColors {
        RED,
        GREEN,
        BLUE
    }
}

/*
Todo:
1. Für die extra Fleder die Methode benutzten die oben erstellt wurde
2. Enum ersteller für die jewaldigen arten der "Kraftwerke" (Hier auch die Enum klassen für die Karten erstellen)
3. Issue ab arbeiten!

Die höhe einer Straße wird immer Größe des Feldes / 9 (9 Felder sind an jeder Seite (ohne die Ecken)) 
*/
