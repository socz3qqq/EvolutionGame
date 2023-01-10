package evolutionGame.Controllers;

import evolutionGame.simulation.SimulationService;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
import org.xml.sax.SAXException;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import java.io.File;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class WelcomeScene extends Application {
    List<SimulationService> simulationServiceList = new ArrayList<>();
    Node config;
    private String configuration;
    public void runSimulation(){
        SimulationService newSimulation = new SimulationService();
        this.simulationServiceList.add(newSimulation);
    }

    public void geConfiguration(int i){
        URL url = WelcomeScene.class.getResource("/configs.xml");
        File file = new File(url.getPath());
        DocumentBuilderFactory dbf = DocumentBuilderFactory.newInstance();
        DocumentBuilder db = null;
        try {
            db = dbf.newDocumentBuilder();
        } catch (ParserConfigurationException e) {
            throw new RuntimeException(e);
        }
        Document doc = null;
        try {
            doc = db.parse(file);
        } catch (SAXException e) {
            throw new RuntimeException(e);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        doc.getDocumentElement().normalize();
        NodeList nodeList = doc.getElementsByTagName("config");
        config = nodeList.item(i);
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
