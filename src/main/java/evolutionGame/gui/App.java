package evolutionGame.gui;

import evolutionGame.ISimulationDayObserver;
import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.IMapElement;
import evolutionGame.mapElements.Vector2D;
import evolutionGame.mapTypes.AbstractWorldMap;
import evolutionGame.simulation.SimulationEngine;
import javafx.application.Application;
import javafx.application.Platform;
import javafx.geometry.HPos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;

import static java.lang.Math.max;


public class App extends Application implements ISimulationDayObserver {
    private AbstractWorldMap map;
    private GridPane gridPane;
    private GridPane applicationGridPane;
    private GridPane applicationControlsGridPane;
    private Stage stage;
    private SimulationEngine engine;
    private Vector2D[] positions;
    private RowConstraints alwaysGrowH;
    private ColumnConstraints alwaysGrowW;
    private Vector2D lowerBound;
    private Vector2D upperBound;

//    public App(SimulationEngine engine) throws Exception {
//        this.engine = engine;
////      utils for map scaling
//        alwaysGrowH = new RowConstraints();
//        alwaysGrowW = new ColumnConstraints();
//
//        try {
//            this.map = engine.getMap();
//            lowerBound = map.getLowerBound();
//            upperBound = map.getUpperBound();
//            gridPane = new GridPane();
//            applicationControlsGridPane = new GridPane();
//            applicationGridPane = new GridPane();
//            gridPane.setStyle("-fx-background-color: rgb(99, 55, 44);");
//
//        } catch (IllegalArgumentException ex){
//            System.out.println(ex);
//        }
//        refreshGridPane();
////        Parent root = FXMLLoader.load(getClass().getResource("SimulationScene.fxml"));
//        Scene scene= new Scene(applicationGridPane);
//        Stage stage = new Stage();
//        stage.setScene(scene);
//        stage.show();
//
//    }
@Override
public void init() throws Exception {
    super.init();

//      utils for map scaling
    alwaysGrowH = new RowConstraints();
    alwaysGrowW = new ColumnConstraints();

    try {
        this.engine = new SimulationEngine(15, 15, "Globe",
                10, 5, 1, 10, 20,
                10, 5, 2, 4, "Random",
                10, "Predestination", "Equators");
        this.engine.addObserver(this);
        this.map = engine.getMap();
        engine.setMoveDelay(1000);
        gridPane = new GridPane();
        applicationControlsGridPane = new GridPane();
        applicationGridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: rgb(99, 55, 44);");
        lowerBound = map.getLowerBound();
        upperBound = map.getUpperBound();

    } catch (IllegalArgumentException ex){
        System.out.println(ex);
    }
}
    private void setLabels(){
// map scaling
        alwaysGrowW.setPercentWidth(max(40, 100/(upperBound.getX() - lowerBound.getX() + 2)));
        alwaysGrowH.setPercentHeight(max(40, 100/(upperBound.getY() - lowerBound.getY() + 2)));
// setting xy label
        Label chartTitle = new Label("y/x");
        chartTitle.setStyle("-fx-font: normal bold 20px 'serif';-fx-text-fill: white;");
        gridPane.add(chartTitle, 0, 0);
        gridPane.getColumnConstraints().add(alwaysGrowW);
        gridPane.getRowConstraints().add(alwaysGrowH);
        GridPane.setHalignment(chartTitle, HPos.CENTER);
// setting column labels
        for (int i = lowerBound.getX(); i <= upperBound.getX(); i++) {
            Label tile = new Label(""+ i);
            tile.setStyle("-fx-font: normal bold 20px 'serif';-fx-text-fill: white;");
            gridPane.add(tile, i - lowerBound.getX() + 1, 0);
            gridPane.getColumnConstraints().add(alwaysGrowW);
            GridPane.setHalignment(tile, HPos.CENTER);
        }
// setting row labels
        for (int i = lowerBound.getY(); i <= upperBound.getY() ; i++) {
            Label tile = new Label(i+ "");
            tile.setStyle(" -fx-font: normal bold 20px 'serif';-fx-text-fill: white;");
            gridPane.add(tile, 0, upperBound.getY() - i + 1, 1, 1);
            gridPane.getRowConstraints().add(alwaysGrowH);

            GridPane.setHalignment(tile, HPos.CENTER);
        }
    }
    private void addMapElements(){
        for (int i = lowerBound.getX(); i <= upperBound.getX() ; i++) {
            for (int j = lowerBound.getY(); j <= upperBound.getY() ; j++) {
                Vector2D position = new Vector2D(i, j);
                if(map.isOccupied(position)){
                    IMapElement obj = (IMapElement) map.objectAt(position);
                    GuiElementBox guiObj = new GuiElementBox(obj);
                    VBox objBox = new VBox(guiObj.getElementToDisplay());
                    if(obj instanceof Animal) {
                        objBox.setStyle("-fx-font: normal bold 13px 'serif'; -fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid; -fx-background-color: grey;");
                    }
                    else{
                        objBox.setStyle("-fx-background-color: lightgreen; -fx-font: normal bold 13px 'serif'; -fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid");
                    }

                    gridPane.add(objBox, position.getX() - lowerBound.getX() + 1, upperBound.getY() - position.getY() + 1);

                    GridPane.setHalignment(objBox, HPos.CENTER);
                }
            }
        }
    }
    private void addSimulationControls(){
//        add button
        Button startButton = new Button("Start simulation");
        startButton.setOnAction(value -> {
            runNewSimulation();
        });
        GridPane.setHalignment(startButton, HPos.CENTER);
        applicationControlsGridPane.add(startButton, 0, 1);
    }
    private void runNewSimulation(){
        Stage newSimulationStage = new Stage();
        GridPane
        Scene scene = new Scene();
    }
    private void assembleAll(){
        applicationGridPane.add(gridPane, 0, 0);
        ColumnConstraints cols = new ColumnConstraints();
        cols.setPercentWidth(75);
        applicationGridPane.getColumnConstraints().add(cols);
        RowConstraints rows = new RowConstraints();
        rows.setPercentHeight(100);
        applicationGridPane.getRowConstraints().add(rows);


        ColumnConstraints inputCols = new ColumnConstraints();
        inputCols.setPercentWidth(25);
        applicationGridPane.getColumnConstraints().add(inputCols);

        applicationGridPane.add(applicationControlsGridPane, 1, 0);
        applicationGridPane.getColumnConstraints().add(new ColumnConstraints(300));
        GridPane.setHalignment(applicationControlsGridPane, HPos.CENTER);
    }
    private void refreshGridPane(){
        gridPane.setGridLinesVisible(false);
        gridPane.getChildren().clear();
        gridPane.getColumnConstraints().clear();
        gridPane.getRowConstraints().clear();
        gridPane.setGridLinesVisible(true);

        applicationGridPane.setGridLinesVisible(false);
        applicationGridPane.getChildren().clear();
        applicationGridPane.getRowConstraints().clear();
        applicationGridPane.getColumnConstraints().clear();
        applicationGridPane.setGridLinesVisible(true);

        applicationControlsGridPane.getChildren().clear();

        setLabels();
        addSimulationControls();
        addMapElements();
        assembleAll();
    }

    @Override
    public void nextDay() {
        Platform.runLater(() -> refreshGridPane());
        //updates the map after move
    }

    @Override
    public void start(Stage primaryStage) throws Exception {
        Thread engineThread = new Thread(engine);
        refreshGridPane();
        Scene scene = new Scene(applicationGridPane, 20*40, 20*40);
        stage = primaryStage;
        stage.setScene(scene);
        stage.show();
        engineThread.start();

    }
}
