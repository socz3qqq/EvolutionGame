package evolutionGame;

import evolutionGame.gui.GuiElementBox;
import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.IMapElement;
import evolutionGame.mapElements.Vector2D;
import evolutionGame.simulation.SimulationEngine;
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

public class SimulationService implements ISimulationDayObserver{
    private SimulationEngine engine;
    private GridPane gridPane;
    private GridPane applicationGridPane;
    private GridPane applicationControlsGridPane;
    private RowConstraints alwaysGrowH;
    private ColumnConstraints alwaysGrowW;
    private Vector2D lowerBound;
    private Vector2D upperBound;

    public SimulationService(){
        this.engine = new SimulationEngine(15, 15, "Globe",
                10, 5, 1, 10, 20,
                10, 5, 2, 4, "Random",
                10, "Predestination", "Equators");
        engine.addObserver(this);
        engine.setMoveDelay(1000);
        lowerBound = engine.getMap().getLowerBound();
        upperBound = engine.getMap().getUpperBound();
        alwaysGrowH = new RowConstraints();
        alwaysGrowW = new ColumnConstraints();

        gridPane = new GridPane();
        applicationControlsGridPane = new GridPane();
        applicationGridPane = new GridPane();
        refreshGridPane();
        Scene scene = new Scene(applicationGridPane);
        Stage simulationStage = new Stage();
        simulationStage.setScene(scene);
        simulationStage.show();
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
            System.out.println("działam");
        });
        GridPane.setHalignment(startButton, HPos.CENTER);
        applicationControlsGridPane.add(startButton, 0, 1);
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
}
