package evolutionGame;

import evolutionGame.mapTypes.IWorldMap;

public class SimulationEngine {
    IWorldMap map;
    String grassGrowType;
    public SimulationEngine(IWorldMap map, String grassGrowType, String mutationType, String geneExecution, int grassCount, int animalCount){
        this.map = map;
        this.grassGrowType = grassGrowType;
        this.prepareMap(grassCount, animalCount);

    }
    public void run(){

    };

    private void prepareMap(int grassCount, int animalCount){
        placeAllAnimals(animalCount);
        placeAllGrass(grassCount);
    };

    private void placeAllAnimals(int animalCount){
        for (int i = 0; i < animalCount; i++) {
            this.map.placeAnimalRandomly();
        }
    };
    private void placeAllGrass(int grassCount){
        for (int i = 0; i < grassCount ; i++) {
            this.map.placeGrass(this.grassGrowType);
        }
    };
}
