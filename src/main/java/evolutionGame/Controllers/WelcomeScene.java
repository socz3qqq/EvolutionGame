//package evolutionGame.Controllers;
//
//import evolutionGame.gui.App;
//import evolutionGame.simulation.SimulationEngine;
//import javafx.application.Application;
//import javafx.fxml.FXMLLoader;
//import javafx.scene.Parent;
//import javafx.scene.Scene;
//import javafx.stage.Stage;
//
//import java.io.IOException;
//
//public class WelcomeScene extends Application{
//    public void runSimulation() throws Exception {
//        SimulationEngine engine = new SimulationEngine(15, 15, "Globe",
//                10, 5, 1, 10, 20,
//                10, 5, 2, 4, "Random",
//                10, "Predestination", "Equators");
//        App newSimulation = new App(engine);
//        engine.addObserver(newSimulation);
//        Thread newAppEngineThread = new Thread(engine);
//        newAppEngineThread.start();
//        engine.run();
//    }
//
//    @Override
//    public void start(Stage primaryStage) throws Exception {
//        Stage simulationStage = new Stage();
//        Parent root = null;
//        try {
//            FXMLLoader loader =  new FXMLLoader(getClass().getResource("WelcomeScene.fxml"));
//            root = loader.load();
//        } catch (IOException e) {
//            throw new RuntimeException("Nie odnaleziono ścieżki do pliku: " + e);
//        }
//        simulationStage.setTitle("Hello animals!");
//        simulationStage.setScene( new Scene(root));
//        simulationStage.show();
//    }
//}
