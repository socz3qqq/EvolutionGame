package evolutionGame.simulation;

import evolutionGame.observers.IAnimalObserver;
import evolutionGame.observers.ISimulationDayObserver;
import evolutionGame.gui.GuiElementBox;
import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.IMapElement;
import evolutionGame.mapElements.Vector2D;
import evolutionGame.mapTypes.AbstractWorldMap;
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

import java.util.Arrays;

import static java.lang.Math.max;

public class SimulationService implements ISimulationDayObserver, IAnimalObserver {
    private SimulationEngine engine;
    private GridPane gridPane;
    private GridPane applicationGridPane;
    private GridPane applicationControlsGridPane;
    private GridPane simulationStatsGridPane;
    private GridPane animalStatsGridPane;
    private RowConstraints alwaysGrowH;
    private ColumnConstraints alwaysGrowW;
    private Vector2D lowerBound;
    private Vector2D upperBound;
    private AbstractWorldMap map;
    private boolean simulationRunning; // true - simulation is running, false - simulation is stopped
    private Animal trackedAnimal;

//    public SimulationService(int height, int width, String corner, int grassNumber, int energyFromGrass, int dailyIncrease, int initAnimal, int initialEnergy,
//                             int minimalStuffed, int energyUsed, int minChild, int maxChild, String mutationType, int genotypeLength, String geneExc, String growType ){
//        this.engine = new SimulationEngine(height, width, corner,
//                grassNumber, energyFromGrass, dailyIncrease, initAnimal, initialEnergy,
//                minimalStuffed, energyUsed, minChild, maxChild, mutationType,
//                genotypeLength, geneExc, growType);
    public SimulationService(){
        this.engine = new SimulationEngine(15, 15, "DevilishPortal",
                10, 5, 1, 10, 20,
                10, 5, 2, 4, "Random",
                10, "Predestination", "Equators");
        this.map = engine.getMap();
        engine.addObserver(this);
        engine.setMoveDelay(1000);
        lowerBound = engine.getMap().getLowerBound();
        upperBound = engine.getMap().getUpperBound();
        alwaysGrowH = new RowConstraints();
        alwaysGrowW = new ColumnConstraints();
        trackedAnimal = null;
        gridPane = new GridPane();
        gridPane.setStyle("-fx-background-color: rgb(99, 55, 44);");

        simulationStatsGridPane = new GridPane();
        animalStatsGridPane = new GridPane();
        applicationControlsGridPane = new GridPane();
        applicationGridPane = new GridPane();
        refreshGridPane();
        Scene scene = new Scene(applicationGridPane);
        Stage simulationStage = new Stage();
        simulationStage.setScene(scene);
        simulationStage.show();
        simulationRunning = true;
        engine.start();
    }

    @Override
    public void nextDay() {
        Platform.runLater(() -> refreshGridPane());
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
                if(engine.getMap().isOccupied(position)){
                    IMapElement obj = (IMapElement) engine.getMap().objectAt(position);
                    GuiElementBox guiObj = new GuiElementBox(obj);
                    VBox objBox = new VBox(guiObj.getElementToDisplay());
                    if(obj instanceof Animal) {
                        if(obj == trackedAnimal) objBox.setStyle("-fx-font: normal bold 13px 'serif'; -fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid; -fx-background-color: grey;");
                        else objBox.setStyle("-fx-font: normal bold 13px 'serif'; -fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid; -fx-background-color: grey;");
                        objBox.setOnMouseClicked((action) -> toggleAnimalStatsWindow((Animal)obj, objBox));
                    }
                    else{
                        objBox.setStyle("-fx-background-color: lightgreen; -fx-font: normal bold 13px 'serif'; -fx-border-color: black; -fx-border-width: 1; -fx-border-style: solid");
                    }
                    objBox.setMaxWidth(50);
                    objBox.setMaxHeight(50);
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
            this.engine.setSimulationState(true);
        });
        Button stopButton = new Button("Stop simulation");
        stopButton.setOnAction(value -> {
            this.engine.setSimulationState(false);
        });
        Button endButton = new Button("End simulation");
        endButton.setOnAction(value -> {
            this.engine.endSiumlation(true);
        });
        GridPane.setHalignment(startButton, HPos.CENTER);
        applicationControlsGridPane.add(startButton, 0, 0);
        applicationControlsGridPane.add(stopButton, 0, 1);
        applicationControlsGridPane.add(endButton, 1, 1);
    }
    private void assembleAll(){
        applicationGridPane.add(gridPane, 0, 0);
        ColumnConstraints cols = new ColumnConstraints();
        cols.setPercentWidth(75);
        applicationGridPane.getColumnConstraints().add(cols);
        RowConstraints rows = new RowConstraints();
        rows.setPercentHeight(80);
        applicationGridPane.getRowConstraints().add(rows);


        ColumnConstraints inputCols = new ColumnConstraints();
        inputCols.setPercentWidth(25);
        applicationGridPane.getColumnConstraints().add(inputCols);


        applicationGridPane.add(animalStatsGridPane, 1, 0);

        applicationGridPane.add(applicationControlsGridPane, 1, 1);
        applicationGridPane.getColumnConstraints().add(new ColumnConstraints(300));



//      add simulation stats
        RowConstraints simulationRow = new RowConstraints();
        simulationRow.setPercentHeight(20);
        applicationGridPane.add(simulationStatsGridPane, 0, 1);
        GridPane.setHalignment(applicationControlsGridPane, HPos.CENTER);
    }
    private void addSimulationStats(){
        Label animalCount = new Label("Ilość zwierząt: " + String.valueOf(this.map.getAnimalCount()));
        Label grassCount = new Label("Ilość trawy: " + String.valueOf(this.map.getGrassCount()));
        Label unoccupiedTiles = new Label("Ilość wolnych pól: " + String.valueOf(this.map.getUnoccupiedTilesCount()));
        Label bestGenotype= new Label("Najpopularniejszy genotyp: " + Arrays.toString(this.map.getMostPopularGenotype().toArray()));
        Label averageEnergyLevel = new Label("Średnia ilość energii: " + String.valueOf(this.map.getAverageEnergyLevel()));
        Label averageLifeTime = new Label("Średnia długość życia: " + String.valueOf(this.map.getAverageLifeTime()));

        simulationStatsGridPane.add(animalCount, 0, 0);
        simulationStatsGridPane.add(grassCount, 0, 1);
        simulationStatsGridPane.add(unoccupiedTiles, 0, 2);
        simulationStatsGridPane.add(bestGenotype, 1, 0);
        simulationStatsGridPane.add(averageEnergyLevel, 1, 1);
        simulationStatsGridPane.add(averageLifeTime, 1, 2);
    };

    private void addTrackedAnimalInfo(){
        if(this.trackedAnimal != null){
            Label genotype = new Label("Genom zwierzaka: " + Arrays.toString(trackedAnimal.getGenotype().getGenes().toArray()));
            Label activatedGene = new Label("Aktywowany gen: " + this.trackedAnimal.getActivatedGene());
            Label energyLevel = new Label("Ilość energii: " + this.trackedAnimal.getEnergy());
            Label eatenGrass = new Label("Ilość zjedzonej trawy: " + this.trackedAnimal.getEatenGrassCount());
            Label childCount = new Label("Ilość dzieci: " + this.trackedAnimal.getNumberOfChildren());
            Label lifeTime = new Label("Długość życia: " + this.trackedAnimal.getAge());
            Label dayOfDeath = new Label("Dzień śmierci: " + this.trackedAnimal.getDayOfDeath());

            animalStatsGridPane.add(genotype, 0, 0);
            animalStatsGridPane.add(activatedGene, 0, 1);
            animalStatsGridPane.add(energyLevel, 0, 2);
            animalStatsGridPane.add(eatenGrass, 0, 3);
            animalStatsGridPane.add(childCount, 0, 4);
            animalStatsGridPane.add(lifeTime, 0, 5);
            animalStatsGridPane.add(dayOfDeath, 0, 6);
        }
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

        simulationStatsGridPane.setGridLinesVisible(false);
        simulationStatsGridPane.getChildren().clear();
        simulationStatsGridPane.getRowConstraints().clear();
        simulationStatsGridPane.getColumnConstraints().clear();
        simulationStatsGridPane.setGridLinesVisible(true);

        animalStatsGridPane.setGridLinesVisible(false);
        animalStatsGridPane.getChildren().clear();
        animalStatsGridPane.getRowConstraints().clear();
        animalStatsGridPane.getColumnConstraints().clear();
        animalStatsGridPane.setGridLinesVisible(true);

        applicationControlsGridPane.getChildren().clear();

        setLabels();
        addSimulationControls();
        addMapElements();
        addSimulationStats();
        if(this.trackedAnimal != null) addTrackedAnimalInfo();
        assembleAll();
    }


    @Override
    public void updateAnimalInfo(Animal animal) {

    }
    public void toggleAnimalStatsWindow(Animal tAnimal, VBox animalBox){
        if(this.trackedAnimal == null) {
            tAnimal.addObserver(this);
            this.trackedAnimal = tAnimal;
            showTrackedAnimalInfo();
            animalBox.setStyle("-fx-font: normal bold 13px 'serif'; -fx-border-color: red; -fx-border-width: 1; -fx-border-style: solid; -fx-background-color: grey;");
        }
        else if (this.trackedAnimal != tAnimal){
            trackedAnimal.removeObserver(this);
            tAnimal.addObserver(this);
            this.trackedAnimal = tAnimal;
        }
        else{
            this.trackedAnimal = null;
            hideTrackedAnimalInfo();
        }
    }
    private void showTrackedAnimalInfo(){};
    private void hideTrackedAnimalInfo(){};
}
