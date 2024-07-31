module org.example.week12_javafxapp_assignment02 {
    requires javafx.controls;
    requires javafx.fxml;


    opens org.example.week12_javafxapp_assignment02 to javafx.fxml;
    exports org.example.week12_javafxapp_assignment02;
}