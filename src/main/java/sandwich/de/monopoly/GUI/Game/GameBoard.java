package sandwich.de.monopoly.GUI.Game;

import javafx.animation.TranslateTransition;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;
import sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities;
import sandwich.de.monopoly.Enums.ProgramColor;
import sandwich.de.monopoly.Exceptions.PlayerIsOutOfBoundsExceptions;
import sandwich.de.monopoly.Exceptions.PlayerNotFoundExceptions;
import sandwich.de.monopoly.Exceptions.ToManyPlayersExceptions;
import sandwich.de.monopoly.Fields.Corner;
import sandwich.de.monopoly.Fields.Field;
import sandwich.de.monopoly.Fields.Street;
import sandwich.de.monopoly.Main;
import sandwich.de.monopoly.Player;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;
import static sandwich.de.monopoly.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;


public class GameBoard extends Pane{
    private final ImageView[] playerFigures = new ImageView[5];
    private final double MIDDLE_RECTANGLE_RATION = 1.4;
    private final double FONT_SIZE, BORDER_WIDTH;
    private final double FIELD_HEIGHT;
    private final double FIELD_WIDTH;
    private final double PLAYER_FIGURE_SIZE;
    public GameBoard(double size, HashMap<Integer, Field> fields) {


        FONT_SIZE = ((size / MIDDLE_RECTANGLE_RATION) / 9) / 8;
        BORDER_WIDTH = ((size / MIDDLE_RECTANGLE_RATION) / 9) / 25;
        FIELD_HEIGHT = (size - size / MIDDLE_RECTANGLE_RATION) / 2;
        FIELD_WIDTH = (size / MIDDLE_RECTANGLE_RATION) / 9;
        PLAYER_FIGURE_SIZE = size * 0.035;

        getChildren().add(
            buildRectangle("gameBoard_Background", size, size, ProgramColor.GAMEBOARD_COLOR.getColor(),true, null, 0)
        );

        buildGameBoard(size, fields);

        for (int i = 0; i != 5; i++) {
            playerFigures[i] = new ImageView();
            playerFigures[i].setVisible(false);

            //Player Size
            playerFigures[i].setFitHeight(PLAYER_FIGURE_SIZE);
            playerFigures[i].setFitWidth(PLAYER_FIGURE_SIZE);

            getChildren().add(playerFigures[i]);
        }
    }

    public void addPlayers(ArrayList<Player> playerList) {

        if (Main.CONSOLE_OUT_PUT) {
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, "Spieler Figuren werden auf dem Spielbrett Positioniert:");
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);
        }

        if (!(playerList.size() > 5)) {
            for (int i = 0; i < playerList.size(); i++) {
                if (playerList.get(i) != null) {
                    //Testes if the player is out of bounce the area.
                    if (playerList.get(i).getFieldPostion() > 39 || playerList.get(i).getFieldPostion() < 0) throw new PlayerIsOutOfBoundsExceptions(playerList.get(i).getFieldPostion());

                    //Saves the player figure in an extra variable
                    playerFigures[i].setImage(playerList.get(i).getFigur().getFigureImage());

                    playerFigures[i].setVisible(true);
                    playerFigures[i].toFront();

                    playerFigures[i].setX(calculateXYPostion(i, playerList.get(i).getFieldPostion()).getX());
                    playerFigures[i].setY(calculateXYPostion(i, playerList.get(i).getFieldPostion()).getY());
                    if (Main.CONSOLE_OUT_PUT) {
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + i + ". Spieler wurde auf diese Position gesetzt: ");
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "[" + (calculateXYPostion(i, playerList.get(i).getFieldPostion()).getX()) + "/" + (calculateXYPostion(i, playerList.get(i).getFieldPostion()).getY()) + "]");
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

    public void setPlayerInJail(Player p) {
        int playerArrayPostion = 0;
        for (ImageView i: playerFigures) {
            if (i.getImage() == p.getFigur().getFigureImage())
                break;
            else playerArrayPostion++;
        }

        if (!(playerArrayPostion > 4)) {
            playerFigures[playerArrayPostion].setX(FIELD_HEIGHT * 0.6 - (FIELD_HEIGHT + FIELD_WIDTH * 9));
            playerFigures[playerArrayPostion].setY(FIELD_HEIGHT * 0.6);
        }
    }

    public void removePlayerFromJail(Player p) {
        int playerArrayPostion = 0;
        for (ImageView i: playerFigures) {
            if (i.getImage() == p.getFigur().getFigureImage())
                break;
            else playerArrayPostion++;
        }

        if (!(playerArrayPostion > 4)) {
            playerFigures[playerArrayPostion].setX(calculateXYPostion(p.getOrderNumber(), 10).getX());
            playerFigures[playerArrayPostion].setY(calculateXYPostion(p.getOrderNumber(), 10).getY());
        }
    }

    public void movePlayer(Player player, int steps) throws PlayerNotFoundExceptions {

        int playerArrayPostion = 0;
        for (ImageView i: playerFigures) {
            if (i.getImage() == player.getFigur().getFigureImage())
                break;
            else playerArrayPostion++;
        }

        final int pArrayPos = playerArrayPostion;
        final int startPostion = player.getFieldPostion();

        if (!(playerArrayPostion > 4)) {

            TranslateTransition moveAnimation = new TranslateTransition(Duration.seconds(0.8), playerFigures[pArrayPos]);
            moveAnimation.setToX((calculateXYPostion(pArrayPos, startPostion + 1).getX()) - (calculateXYPostion(pArrayPos, startPostion).getX()));
            moveAnimation.setToY((calculateXYPostion(pArrayPos, startPostion + 1).getY()) - (calculateXYPostion(pArrayPos, startPostion).getY()));
            moveAnimation.play();


            AtomicInteger i = new AtomicInteger();
            AtomicInteger step = new AtomicInteger();

            moveAnimation.setOnFinished(event -> {
                if (!(i.get() >= steps)) {
                    if ((startPostion + step.get()) >= 39)
                        step.set(-(startPostion + 1));
                    step.getAndIncrement();

                    moveAnimation.setToX((calculateXYPostion(pArrayPos, startPostion + step.get()).getX()) - (calculateXYPostion(pArrayPos, startPostion).getX()));
                    moveAnimation.setToY((calculateXYPostion(pArrayPos, startPostion + step.get()).getY()) - (calculateXYPostion(pArrayPos, startPostion).getY()));
                    moveAnimation.play();


                    if (Main.CONSOLE_OUT_PUT) {
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + pArrayPos + ". Spieler wurde auf diese Position gesetzt: ");
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "[" + (calculateXYPostion(pArrayPos, player.getFieldPostion()).getX()) + "/" + (calculateXYPostion(pArrayPos, player.getFieldPostion()).getY()) + "]");
                        System.out.println();
                    }
                } else {
                    moveAnimation.stop();
                    Main.getGameOperator().playerHasMoved();
                }
                i.getAndIncrement();
            });
        } else throw new PlayerNotFoundExceptions();

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

    private void buildGameBoard(double size, HashMap<Integer, Field> fieldObjects) {
        setId("gameBoard_Root");
        StackPane board = new StackPane();
        board.setId("gameBoard");
        board.setAlignment(Pos.CENTER);

        Pane[] fields = new Pane[40];

        //Creating Fields
        for (int i = 0; i < fields.length; i++) {
            if (i != 0 && i != 10 && i != 20 && i != 30) {
                fields[i] = fieldObjects.get(i).buildField(FIELD_WIDTH, FIELD_HEIGHT, BORDER_WIDTH, FONT_SIZE);
            } else {
                fields[i] = ( (Corner) fieldObjects.get(i)).buildCorner(BORDER_WIDTH, FONT_SIZE, FIELD_HEIGHT);
            }
        }

        //Position fields
        for (int i = 0; i < 40; i++) {
            board.getChildren().add(fields[i]);
            StackPane.setAlignment(fields[i], Pos.TOP_LEFT);
            double rightCorner = (size / MIDDLE_RECTANGLE_RATION) + ((size * (MIDDLE_RECTANGLE_RATION - 1) / (2 * MIDDLE_RECTANGLE_RATION)));
            double cornerCoordinate = ((size - size / MIDDLE_RECTANGLE_RATION) / 2) + size / MIDDLE_RECTANGLE_RATION;

            if(i == 0) {
                fields[i].setTranslateY(cornerCoordinate);
            } else if(i == 10) {
                fields[i].setRotate(90);
            } else if(i == 20) {
                fields[i].setRotate(180);
                fields[i].setTranslateX(cornerCoordinate);
            } else if (i == 30){
                fields[i].setRotate(270);
                fields[i].setTranslateY(cornerCoordinate);
                fields[i].setTranslateX(cornerCoordinate);
            } else if (i <= 9) {
                assert fields[i] != null;
                fields[i].rotateProperty().set(90);
                fields[i].setTranslateY(((FIELD_HEIGHT + FIELD_WIDTH) / 2) + (8 * FIELD_WIDTH - FIELD_WIDTH * (i - 1)));
                fields[i].setTranslateX((FIELD_HEIGHT - FIELD_WIDTH) / 2);
            } else if (i <= 19) {
                fields[i].rotateProperty().set(180);
                fields[i].setTranslateY(0);
                fields[i].setTranslateX(rightCorner - FIELD_WIDTH * 17 + FIELD_WIDTH * (i - 2) - FIELD_WIDTH);
            } else if (i <= 29) {
                fields[i].rotateProperty().set(270);
                fields[i].setTranslateY((FIELD_HEIGHT + FIELD_WIDTH) / 2 - 18 * FIELD_WIDTH + FIELD_WIDTH * (i - 3));
                fields[i].setTranslateX(rightCorner + (FIELD_HEIGHT - FIELD_WIDTH) / 2);
            } else {
                fields[i].setTranslateY(size - FIELD_HEIGHT);
                fields[i].setTranslateX(FIELD_HEIGHT + 8 * FIELD_WIDTH - (i - 4) * FIELD_WIDTH + 27 * FIELD_WIDTH);
            }
        }


        for (int i = 0; i < fields.length; i++) {
            if (fieldObjects.get(i) instanceof Street street) {

                fields[i].setOnMouseEntered(mouseEvent -> street.highlightField());

                fields[i].setOnMouseClicked(mouseEvent -> Main.getGameOperator().getMiddleDisplayController().displayStreetInfoDisplay(street));

                fields[i].setOnMouseExited(mouseEvent -> street.removeHighlight());
            }
        }



        getChildren().addAll(board);
    }

}

/*
Die höhe einer Straße wird immer Größe des Feldes / 9 (9 Felder sind an jeder Seite (ohne die Ecken)) 
*/
