package evolutionGame.simulation;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Vector2D;
import evolutionGame.mapTypes.*;

import java.util.List;

public class SimulationEngine implements  Runnable{

    private int mapHeight; //wysokosc mapy
    private int mapWidth; //szerokość mapy
    private String cornerBehaviour; //wariant ruchu na granicach (kula ziemska | piekielny portal)
    private int initialGrassNumber; //startowa liczba roślin
    private int energyFromGrass; //energia ze zjedzenia rośliny
    private int dailyGrassIncrease; //liczba roślin wyrastająca każdego dnia
    private final AbstractWorldMap map; //rodzaj mapy ze wzgledu na wzrost (rowniki | trupy)
    private int initialAnimalNumber; //startowa liczba zwierząt
    private int initialAnimalEnergy; //startowa energia zwierzat
    private int minimalStuffedEnergy; //minimalna energia najedzenia
    private int energyUsedForReproduction; //energia zuzyta na rozmnazanie
    private int minChildMutation; //min liczba mutacji
    private int maxChildMutation; //max liczba mutacji
    private String mutationType; //wariant mutacji (pelna losowosc | lekka korekta)
    private int genotypeLength; //dlugosc genomu
    private String geneExecution; //wariant zachowania (predestynacja | nieco szalenstwa)
    private int currentDayOfSimulation; // aktualny dzień symulacji
    private final String grassGrowType; //wariant mapy (rowniki | trupy)


    public SimulationEngine(int mapHeight, int mapWidth, String cornerBehaviour, int initialGrassNumber, int energyFromGrass, int dailyGrassIncrease, int initialAnimalNumber,
                            int initialAnimalEnergy, int minimalStuffedEnergy, int energyUsedForReproduction, int minChildMutation, int maxChildMutation, String mutationType,
                            int genotypeLength, String geneExecution, String grassGrowType){

        this.mapHeight = mapHeight; //wysokosc mapy
        this.mapWidth = mapWidth; //szerokość mapy
        this.cornerBehaviour = cornerBehaviour; //wariant ruchu na granicach (kula ziemska | piekielny portal)
        this.initialGrassNumber = initialGrassNumber; //startowa liczba roślin
        this.energyFromGrass = energyFromGrass; //energia ze zjedzenia rośliny
        this.dailyGrassIncrease = dailyGrassIncrease; //liczba roślin wyrastająca każdego dnia
        this.initialAnimalNumber = initialAnimalNumber; //startowa liczba zwierząt
        this.initialAnimalEnergy = initialAnimalEnergy; //startowa energia zwierzat
        this.minimalStuffedEnergy = minimalStuffedEnergy; //minimalna energia najedzenia
        this.energyUsedForReproduction = energyUsedForReproduction; //energia zuzyta na rozmnazanie
        this.minChildMutation = minChildMutation; //min liczba mutacji
        this.maxChildMutation = maxChildMutation; //max liczba mutacji
        this.mutationType = mutationType; //wariant mutacji (pelna losowosc | lekka korekta)
        this.genotypeLength = genotypeLength; //dlugosc genomu
        this.geneExecution = geneExecution; //wariant zachowania (predestynacja | nieco szalenstwa)
        this.grassGrowType = grassGrowType; //wariant mapy (rowniki | trupy)
        this.currentDayOfSimulation = 0;
        switch(grassGrowType){
            case "Equators" -> this.map = new Equators(this.mapWidth, this.mapHeight, this.energyFromGrass);
            case "ToxicCorpses" -> this.map = new ToxicCorpses(this.mapWidth, this.mapHeight, this.energyFromGrass);
            default -> throw new IllegalArgumentException();
        };
    }

    public void run(){
        prepareMap();
        while(this.map.areThereAnyAnimalsLeft()) {
            this.map.removeDeadAnimals();
            this.map.moveAllAnimals(geneExecution, this.cornerBehaviour);
            this.map.eatGrass();
            this.map.animalReproduction(currentDayOfSimulation);
            this.map.grassGrow(dailyGrassIncrease);
            showCurrentSimulationStatus();
            try {
                Thread.sleep(50);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }

    };

    private void prepareMap(){
        placeAllAnimals(this.initialAnimalNumber);
        placeAllGrass(this.initialGrassNumber);
        System.out.println(map.toString());
    };

    private void placeAllAnimals(int animalCount){
        for (int i = 0; i < animalCount; i++) {
            Animal animal = new Animal(this.map, this.genotypeLength, this.initialAnimalEnergy,
                    minimalStuffedEnergy, energyUsedForReproduction,
                    minChildMutation, maxChildMutation);
            this.map.placeAnimal(animal);
        }
    };

    private void placeAllGrass(int grassCount){
        for (int i = 0; i < grassCount ; i++) {
            this.map.placeGrass();
        }
    };
    public void showCurrentSimulationStatus(){
        System.out.println("Ilość zwierząt: " + this.map.getAnimalCount());
        System.out.println("Ilość trawy: " + this.map.getGrassCount());
        System.out.println("Liczba wolnych pól" + this.map.getUnoccupiedTilesCount());
//        System.out.println(this.map.getTheMostPopularGenotype());
        System.out.println("Średnia energia zwierząt żyjących: " + this.map.getAverageEnergyLevel());
        System.out.println("Średnia długość życia" + this.map.getAverageLifeTime());
        System.out.println(this.map.toString());


    }
}
