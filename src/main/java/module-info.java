module de.sandwich {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;
    requires java.desktop;

    opens de.sandwich to javafx.fxml;
    exports de.sandwich;
}
