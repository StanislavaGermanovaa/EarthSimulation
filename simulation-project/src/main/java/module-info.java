module com.example.simulation.simulationproject {
    requires javafx.controls;
    requires javafx.fxml;


    opens com.example.simulation.simulationproject to javafx.fxml;
    exports com.example.simulation.simulationproject;
    exports com.example.simulation.simulationproject.controllers;
    opens com.example.simulation.simulationproject.controllers to javafx.fxml;
}