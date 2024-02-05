package sandwich.de.monopoly;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.GUI.Spielfeld;
import sandwich.de.monopoly.GUI.StartMenu;

public class Main extends Application {
    @Override
    public void start(Stage stage) {

        Scene game = new Scene(Spielfeld.buildGameBoard(0, 900, Color.BEIGE), 1400, 950);

        Scene start = new Scene(StartMenu.buildMenu(1400, 950), 1400, 950);

        stage.setTitle("-M---o-----n----o---p----o---l----y");
        stage.setScene(game);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
        Spieler sp = new Spieler(200, 2, "Paul", Figuren.BOOT);

    }
}