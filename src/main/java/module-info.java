module de.sandwich {
    requires javafx.controls;
    requires javafx.fxml;
    requires javafx.graphics;

    opens de.sandwich to javafx.fxml;
    exports de.sandwich;
}
