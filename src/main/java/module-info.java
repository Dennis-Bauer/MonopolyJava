module de.sandwich {
    requires javafx.controls;
    requires javafx.fxml;

    opens de.sandwich to javafx.fxml;
    exports de.sandwich;
}
