package sandwich.de.monopoly;

import javafx.application.Application;
import javafx.beans.value.ChangeListener;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;
import javafx.stage.Stage;
import sandwich.de.monopoly.Enums.Figuren;
import sandwich.de.monopoly.GUI.Spielfeld;
import sandwich.de.monopoly.GUI.StartMenu;

import java.util.Objects;

public class Main extends Application {

    public static final String textFont = "Verdana";

    public static final Image[] playerFigures = new Image[5];
    @Override
    public void start(Stage stage) {

        Scene game = new Scene(Spielfeld.buildGameBoard(0, 950, Color.BEIGE), 1800, 950, Color.BLACK);

        Scene start = new Scene(StartMenu.buildMenu(1800, 950), 1800, 950, Color.BLACK);

        stage.setTitle("-M---o-----n----o---p----o---l----y");
        stage.setResizable(false);
        stage.setScene(start);
        stage.show();
    }

    public static void main(String[] args) {

        playerFigures[0] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/affe.png")));
        playerFigures[1] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/burger.png")));
        playerFigures[2] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/doener.png")));
        playerFigures[3] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/pinguin.png")));
        playerFigures[4] = new Image(Objects.requireNonNull(Main.class.getResourceAsStream("/sandwich/de/monopoly/figuren/sandwich.png")));

        launch();


        Spieler sp = new Spieler(200, 2, "Paul", Figuren.BOOT);


    }
}