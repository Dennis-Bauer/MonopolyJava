package sandwich.de.monopoly.GUI.Game.Displays.DisplayOne;

import javafx.scene.layout.Pane;
import javafx.scene.shape.Rectangle;
import sandwich.de.monopoly.Enums.ProgramColor;
import sandwich.de.monopoly.GUI.Game.DisplayController.GameDisplayControllerOne;
import sandwich.de.monopoly.Player;

public class BankDisplay extends Pane {

    private final double WIDTH;
    private final double HEIGHT;

    public BankDisplay (double width, double height) {
        setId("gameScene_playerDisplay_TradingMenu");
        setMaxSize(width, height);

        this.WIDTH = width;
        this.HEIGHT = height;
    }

    public void displayPlayer(Player p) {
        if (!getChildren().isEmpty()) {
            getChildren().remove(0);
            getChildren().remove(1);

        }

        Pane playerDisplay = GameDisplayControllerOne.buildPlayer(WIDTH * 0.38, HEIGHT * 0.60, ProgramColor.BANK_PLAYER_BACKGROUND.getColor(), p);
        playerDisplay.setLayoutY(HEIGHT / 2 - playerDisplay.getMaxHeight() / 2);
        playerDisplay.setLayoutX(WIDTH / 2 - playerDisplay.getMaxWidth() / 2);

        Rectangle[] streets = GameDisplayControllerOne.buildStreetInventar(WIDTH * 0.38, HEIGHT * 0.60, p);

        Pane streetDisplay = new Pane();
        streetDisplay.setLayoutY(HEIGHT / 2 - playerDisplay.getMaxHeight() / 2);
        streetDisplay.setLayoutX(WIDTH / 2 - playerDisplay.getMaxWidth() / 2);

        for (Rectangle street : streets) {streetDisplay.getChildren().add(street);}

        getChildren().addAll(playerDisplay, streetDisplay);


    }

}
