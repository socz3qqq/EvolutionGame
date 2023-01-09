package evolutionGame.Controllers;

import evolutionGame.SimulationService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class WelcomeScene extends Application {
    List<SimulationService> simulationServiceList = new ArrayList<>();
    public void runSimulation() throws Exception {
        SimulationService newSimulation = new SimulationService();
        this.simulationServiceList.add(newSimulation);
        for(SimulationService simulationService: simulationServiceList){
            System.out.println(simulationService + "!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!!11\n\n\n\n\n\n!!!!!!!!!!!!!!!!!!!!!!!!!!");
        }
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Stage simulationStage = new Stage();
        Parent root = null;
        try {
            FXMLLoader loader =  new FXMLLoader(getClass().getResource("WelcomeScene.fxml"));
            root = loader.load();
        } catch (IOException e) {
            throw new RuntimeException("Nie odnaleziono ścieżki do pliku: " + e);
        }
        simulationStage.setTitle("Hello animals!");
        simulationStage.setScene( new Scene(root));
        simulationStage.show();
    }
}
