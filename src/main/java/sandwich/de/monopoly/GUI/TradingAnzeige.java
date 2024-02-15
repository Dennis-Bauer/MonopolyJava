package sandwich.de.monopoly.GUI;

import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import sandwich.de.monopoly.Utilities;

public class TradingAnzeige {

    public static Pane buildTradingDisplay(double width, double height, Color backgroundColor) {
        Pane root = new Pane();
        root.setId("gameScene_TradingDisplay");
        root.setMaxSize(width, height);

        Rectangle background = Utilities.buildRectangle("gameScene_trading_Background", width, height, backgroundColor, true, null, 0);

        root.getChildren().addAll(background);

        return root;
    }

}
