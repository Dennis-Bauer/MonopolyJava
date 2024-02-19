package sandwich.de.monopoly.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sandwich.de.monopoly.Utilities;

public class AktionAnzeige extends Pane {

    public AktionAnzeige(double width, double height, Color backgroundColor) {
        setId("gameScene_TradingDisplay");
        setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("gameScene_action_Background", width, height, backgroundColor, true, Color.WHITE, width * 0.006);

        getChildren().addAll(background);
    }


    /*
    public static Pane buildActionDisplay(double width, double height, Color backgroundColor) {
        Pane root = new Pane();
        root.setId("gameScene_TradingDisplay");
        root.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("gameScene_action_Background", width, height, backgroundColor, true, Color.WHITE, width * 0.006);

        root.getChildren().addAll(background);

        return root;
    }

     */

}
