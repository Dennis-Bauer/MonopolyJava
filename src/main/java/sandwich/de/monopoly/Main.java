package sandwich.de.monopoly;

import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.paint.Color;
import javafx.stage.Stage;
import sandwich.de.monopoly.GUI.Spielfeld;

public class Main extends Application {
    @Override
    public void start(Stage stage) {
        Scene scene = new Scene(Spielfeld.buildGameBoard(900, Color.BEIGE), 1400, 950);
        stage.setTitle("-M---o-----n----o---p----o---l----y");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}