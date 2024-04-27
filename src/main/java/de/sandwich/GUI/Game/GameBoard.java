package de.sandwich.GUI.Game;

import javafx.animation.FadeTransition;
import javafx.animation.TranslateTransition;
import javafx.animation.Animation.Status;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.Point2D;
import javafx.geometry.Pos;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.StackPane;
import javafx.util.Duration;

import static de.sandwich.DennisUtilitiesPackage.JavaFX.JavaFXConstructorUtilities.buildRectangle;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPutLine;
import static de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities.consoleOutPut;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.concurrent.atomic.AtomicInteger;

import de.sandwich.Main;
import de.sandwich.Player;
import de.sandwich.DennisUtilitiesPackage.Java.ConsoleUtilities;
import de.sandwich.Enums.ProgramColor;
import de.sandwich.Exceptions.PlayerIsOutOfBoundsExceptions;
import de.sandwich.Exceptions.PlayerNotFoundExceptions;
import de.sandwich.Exceptions.ToManyPlayersExceptions;
import de.sandwich.Fields.Corner;
import de.sandwich.Fields.Field;
import de.sandwich.Fields.Station;
import de.sandwich.Fields.Street;
import de.sandwich.Fields.Utilitie;


public class GameBoard extends Pane {

    private final ImageView[] playerFigures = new ImageView[5];
    private final double MIDDLE_RECTANGLE_RATION = 1.4;
    private final double FONT_SIZE, BORDER_WIDTH;
    private final double FIELD_HEIGHT;
    private final double FIELD_WIDTH;
    private final double PLAYER_FIGURE_SIZE;

    private TranslateTransition playerMoveTransition = new TranslateTransition();

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

                    playerFigures[i].setX(calculateXYPath(i, playerList.get(i).getFieldPostion()).getX());
                    playerFigures[i].setY(calculateXYPath(i, playerList.get(i).getFieldPostion()).getY());
                    if (Main.CONSOLE_OUT_PUT) {
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + i + ". Spieler wurde auf diese Position gesetzt: ");
                        consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "[" + (calculateXYPath(i, playerList.get(i).getFieldPostion()).getX()) + "/" + (calculateXYPath(i, playerList.get(i).getFieldPostion()).getY()) + "]");
                        System.out.println();
                    }

                    int orderNumber = playerList.get(i).getOrderNumber();

                    //Maybe remove
                    playerFigures[i].layoutYProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (Main.CONSOLE_OUT_PUT) {
                                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + orderNumber + ". Spieler wurde auf die LayoutY Position gesetzt: ");
                                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, newValue + ".");
                                System.out.println();
                            }
                        }
                    });

                    //Maybe reomve
                    playerFigures[i].layoutXProperty().addListener(new ChangeListener<Number>() {
                        @Override
                        public void changed(ObservableValue<? extends Number> observable, Number oldValue, Number newValue) {
                            if (Main.CONSOLE_OUT_PUT) {
                                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + orderNumber + ". Spieler wurde auf die LayoutX Position gesetzt: ");
                                consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, newValue + ".");
                                System.out.println();
                            }
                        }
                    });

                } else {
                    playerFigures[i].setVisible(false);
                }
            }
        } else throw new ToManyPlayersExceptions();

        if (Main.CONSOLE_OUT_PUT)
            consoleOutPutLine(ConsoleUtilities.colors.WHITE, ConsoleUtilities.textStyle.REGULAR, Main.CONSOLE_OUT_PUT_LINEBREAK);

    }

    public void setPlayerInJail(Player p) throws PlayerNotFoundExceptions {
        int playerArrayPostion = 0;
        for (ImageView i: playerFigures) {
            if (i.getImage() == p.getFigur().getFigureImage())
                break;
            else playerArrayPostion++;
        }

        if (!(playerArrayPostion > 4)) {

            final int pArrayPos = playerArrayPostion;

            FadeTransition transition = new FadeTransition(Duration.seconds(0.5), playerFigures[playerArrayPostion]);
            transition.setFromValue(1);
            transition.setToValue(0);
            transition.play();

            transition.setOnFinished(actionEvent -> {
                if (p.getFieldPostion() != 10) {
                    playerFigures[pArrayPos].setLayoutX((calculateXYPath(0, 10).getX()) - (calculateXYPath(pArrayPos, p.getFieldPostion()).getX()) + FIELD_HEIGHT * 0.5);
                    playerFigures[pArrayPos].setLayoutY((calculateXYPath(0, 10).getY()) - (calculateXYPath(pArrayPos, p.getFieldPostion()).getY()) + FIELD_HEIGHT * 0.5);
                    p.setInJail(true);

                    transition.setFromValue(0);
                    transition.setToValue(1);
                    transition.play();
                }
            });

        } else throw new PlayerNotFoundExceptions();
    }

    public void removePlayerFromJail(Player p) throws PlayerNotFoundExceptions {
        int playerArrayPostion = 0;
        for (ImageView i: playerFigures) {
            if (i.getImage() == p.getFigur().getFigureImage())
                break;
            else playerArrayPostion++;
        }

        if (!(playerArrayPostion > 4)) {
            playerMoveTransition = new TranslateTransition(Duration.seconds(0.3), playerFigures[playerArrayPostion]);

            playerMoveTransition.setByX(-(FIELD_HEIGHT * 0.5) - (calculateXYPath(0, 10).getX() - calculateXYPath(playerArrayPostion, 10).getX()));
            playerMoveTransition.setByY(-(FIELD_HEIGHT * 0.5) - (calculateXYPath(0, 10).getY() - calculateXYPath(playerArrayPostion, 10).getY()));
            
            playerMoveTransition.play();
        } else throw new PlayerNotFoundExceptions();
    }

    public void movePlayer(Player player, int steps) throws PlayerNotFoundExceptions {
        if (playerMoveTransition.getStatus() == Status.PAUSED || playerMoveTransition.getStatus() == Status.STOPPED || playerMoveTransition == null) {
            if (steps == 0) {
                Main.getGameOperator().playerHasMoved();
            }

            int playerArrayPostion = 0;
            for (ImageView i: playerFigures) {
                if (i.getImage() == player.getFigur().getFigureImage())
                    break;
                else playerArrayPostion++;
            }

            final int pArrayPos = playerArrayPostion;
            final int startPostion = player.getFieldPostion();

            if (!(playerArrayPostion > 4)) {

                double seconds = 0;
                if (steps > 10) 
                    seconds = 0.25;
                else 
                    seconds = 0.5;

                playerMoveTransition = new TranslateTransition(Duration.seconds(seconds), playerFigures[pArrayPos]);
                playerMoveTransition.setByX((calculateXYPath(pArrayPos, startPostion + 1).getX()) - (calculateXYPath(pArrayPos, startPostion).getX()));
                playerMoveTransition.setByY((calculateXYPath(pArrayPos, startPostion + 1).getY()) - (calculateXYPath(pArrayPos, startPostion).getY()));
                playerMoveTransition.play();

                System.out.println("Der Spieler wird jetzt bewegt. Er startet an der Position: " + startPostion);

                AtomicInteger i = new AtomicInteger(1);
                AtomicInteger step = new AtomicInteger();

                playerMoveTransition.setOnFinished(event -> {
                    if (!(i.get() >= steps)) {
                        if ((startPostion + step.get()) >= 39)
                            step.set(-(startPostion + 1));
                        step.getAndIncrement();

                        if ((startPostion + step.get() + 1) == 40) {
                            playerMoveTransition.setByX(-((calculateXYPath(pArrayPos, startPostion + step.get()).getX()) - (calculateXYPath(pArrayPos, 0).getX())));
                            playerMoveTransition.setByY(-((calculateXYPath(pArrayPos, startPostion + step.get()).getY()) - (calculateXYPath(pArrayPos, 0).getY())));
                            
                        } else {
                            playerMoveTransition.setByX(-((calculateXYPath(pArrayPos, startPostion + step.get()).getX()) - (calculateXYPath(pArrayPos, startPostion + step.get() + 1).getX())));
                            playerMoveTransition.setByY(-((calculateXYPath(pArrayPos, startPostion + step.get()).getY()) - (calculateXYPath(pArrayPos, startPostion + step.get() + 1).getY())));
                        }
                        
                        playerMoveTransition.play();


                        if (Main.CONSOLE_OUT_PUT) {
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.REGULAR, "Die Figur vom " + pArrayPos + ". Spieler wurde auf diese Position gesetzt: ");
                            consoleOutPut(ConsoleUtilities.colors.GREEN, ConsoleUtilities.textStyle.BOLD, "[" + (calculateXYPath(pArrayPos, player.getFieldPostion()).getX()) + "/" + (calculateXYPath(pArrayPos, player.getFieldPostion()).getY()) + "], Field[" + player.getFieldPostion() + "]");
                            System.out.println();
                        }
                    } else {
                        playerMoveTransition.stop();
                        Main.getGameOperator().playerHasMoved();
                    }
                    i.getAndIncrement();
                });
            } else throw new PlayerNotFoundExceptions();
        } else throw new NullPointerException("The player can not move because he is moving!");
    }

    public Point2D calculateXYPath(int playerOrderPostion, int setToPostion) {
        //Creates the array that stores the base variable of the player coordinate from which it is then calculated
        //where the player will be positioned
        double[] calculationBaseX = new double[5];
        double[] calculationBaseY = new double[5];

        //Calculate the left corner X Postion from each Player
        //and this also creates the base coordinate
        //from which every calculation begins.
        calculationBaseX[0] = FIELD_HEIGHT / 2 - (PLAYER_FIGURE_SIZE * 3 + (FIELD_HEIGHT * 0.01) * 2) / 2;
        calculationBaseX[1] = calculationBaseX[0] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.01;
        calculationBaseX[2] = calculationBaseX[0] + (PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.01) * 2;
        calculationBaseX[3] = FIELD_HEIGHT / 2 - (PLAYER_FIGURE_SIZE * 2 + (FIELD_HEIGHT * 0.01) * 2) / 2;
        calculationBaseX[4] = calculationBaseX[3] + PLAYER_FIGURE_SIZE + FIELD_HEIGHT * 0.01;

        //Calculate the left corner Y Postion from each Player
        //and this also creates the base coordinate
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
            } else if (fieldObjects.get(i) instanceof Station station) {

                fields[i].setOnMouseEntered(mouseEvent -> station.highlightField());

                fields[i].setOnMouseClicked(mouseEvent -> Main.getGameOperator().getMiddleDisplayController().displayStationInfoDisplay(station));

                fields[i].setOnMouseExited(mouseEvent -> station.removeHighlight());
            } else if (fieldObjects.get(i) instanceof Utilitie utilitie) {

                fields[i].setOnMouseEntered(mouseEvent -> utilitie.highlightField());

                fields[i].setOnMouseClicked(mouseEvent -> Main.getGameOperator().getMiddleDisplayController().displayUtilitieInfoDisplay(utilitie));

                fields[i].setOnMouseExited(mouseEvent -> utilitie.removeHighlight());
            }
        }



        getChildren().addAll(board);
    }

    public TranslateTransition getPlayerMoveTransition() {
        return playerMoveTransition;
    }
}

/*
Die höhe einer Straße wird immer Größe des Feldes / 9 (9 Felder sind an jeder Seite (ohne die Ecken)) 
*/
