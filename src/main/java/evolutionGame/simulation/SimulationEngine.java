package evolutionGame.simulation;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Vector2D;
import evolutionGame.mapTypes.AbstractWorldMap;
import evolutionGame.mapTypes.Globe;
import evolutionGame.mapTypes.IWorldMap;

import java.util.List;

public class SimulationEngine {
    private final AbstractWorldMap map;
    private int energyFromGrass;
    private int dailyGrassIncrease;
    private int initialAnimalEnergy;

    private int minimalStuffedEnergy;
    private double energyUsedForReproduction;
    private int minChildMutation;
    private int maxChildMutation;
    private int genotypeLength;
    private String mutationType;
    private String geneExecution;
    private final String grassGrowType;
    public SimulationEngine(AbstractWorldMap map, String grassGrowType, String mutationType, String geneExecution, int grassCount, int animalCount){
        this.map = map;
        this.grassGrowType = grassGrowType;
        this.prepareMap(grassCount, animalCount);

    }

    public SimulationEngine(int width, int height, int energyFromGrass, int dailyGrassIncrease, int initialAnimalEnergy,
                            int minimalStuffedEnergy, int energyUsedForReproduction, int minChildMutation, int maxChildMutation,
                            String grassGrowType, String mutationType, String geneExecution, int grassCount, int animalCount,
                            int genotypeLength){

        this.map = new Globe(width, height);
        this.energyFromGrass = energyFromGrass;
        this.dailyGrassIncrease = dailyGrassIncrease;
        this.initialAnimalEnergy = initialAnimalEnergy;
        this.minimalStuffedEnergy = minimalStuffedEnergy;
        this.energyUsedForReproduction = energyUsedForReproduction;
        this.minChildMutation = minChildMutation;
        this.maxChildMutation = maxChildMutation;
        this.grassGrowType = grassGrowType;
        this.mutationType = mutationType;
        this.geneExecution = geneExecution;
        this.genotypeLength = genotypeLength;
        this.prepareMap(grassCount, animalCount);

    }
    public void run(){
        this.map.removeDeadAnimals();
        this.map.moveAllAnimals(geneExecution);
        this.map.eatGrass();
        
        animalReproduction();
        grassGrow();
    };

    private void prepareMap(int grassCount, int animalCount){
        placeAllAnimals(animalCount);
        placeAllGrass(grassCount);
    };

    private void placeAllAnimals(int animalCount){
        for (int i = 0; i < animalCount; i++) {
            Animal animal = new Animal(this.map, genotypeLength);

            this.map.placeAnimal(animal);
        }
    };
    private void placeAllGrass(int grassCount){
        for (int i = 0; i < grassCount ; i++) {
            this.map.placeGrass(this.grassGrowType);
        }
    };
}
