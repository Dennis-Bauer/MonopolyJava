module sandwich.de.monopoly {
    requires javafx.controls;
    requires javafx.fxml;


    opens sandwich.de.monopoly to javafx.fxml;
    exports sandwich.de.monopoly;
}