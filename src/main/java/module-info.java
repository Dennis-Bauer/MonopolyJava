module sandwich.de.monopoly {
    requires javafx.controls;
    requires javafx.fxml;


    opens sandwich.de.monopoly to javafx.fxml;
    exports sandwich.de.monopoly;
    opens sandwich.de.monopoly.DennisUtilitiesPackage to javafx.fxml;
    exports sandwich.de.monopoly.DennisUtilitiesPackage.Java;
    opens sandwich.de.monopoly.DennisUtilitiesPackage.Java to javafx.fxml;
    exports sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX;
    opens sandwich.de.monopoly.DennisUtilitiesPackage.JavaFX to javafx.fxml;
}