package evolutionGame;

import evolutionGame.mapTypes.Globe;
import evolutionGame.simulation.SimulationEngine;

public class Game {
    public static void main(String[] args){
        SimulationEngine engine = new SimulationEngine(new Globe(5, 5), "Equator", "Random", "Predestination", 3, 4);
    }
}
