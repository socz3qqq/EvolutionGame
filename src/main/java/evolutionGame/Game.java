package evolutionGame;

import evolutionGame.simulation.SimulationEngine;



public class Game {
    public static void main(String[] args){
            SimulationEngine engine = new SimulationEngine(15, 15, "Globe",
                    10, 5, 1, 10, 20,
                    10, 5, 2, 4, "Random",
                    10, "Predestination", "Equators");
            Thread engineThread = new Thread(engine);
            engineThread.start();

    }
}
