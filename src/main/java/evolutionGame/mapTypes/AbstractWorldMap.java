package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Grass;
import evolutionGame.mapElements.Vector2D;

import java.util.*;


abstract public class AbstractWorldMap implements IWorldMap{
    protected final Random rand = new Random();
    protected int mapWidth;
    protected int mapHeight;
    protected Vector2D upperBound;
    protected Vector2D lowerBound;

//    protected List<Animal> animals;
    private List<Animal> deadAnimals;
    protected HashMap<Vector2D, List<Animal>> animals = new HashMap<>();
    protected HashMap<Vector2D, Grass> grass = new HashMap<>();

    public AbstractWorldMap(){}
    @Override
    public void placeAnimal(Animal animal) {

        if(animals.containsKey(animal.getPosition())){
            List<Animal> animalsAtPosition = this.animals.get(animal.getPosition());
            animalsAtPosition.add(animal);
            this.animals.remove(animal.getPosition());
            this.animals.put(animal.getPosition(), animalsAtPosition);
        }
        else{
            this.animals.put(animal.getPosition(), List.of(animal));
        }
    }

    @Override
    public void placeGrass(String grassType) {
        Grass grass = new Grass(this, grassType);
        this.grass.put(grass.getPosition(), grass);
    }

    @Override
    public boolean isOccupied(Vector2D position) {
        return this.animals.containsKey(position) || this.grass.containsKey(position);
    }
    public boolean isOccupiedByGrass(Vector2D position){
        return this.grass.containsKey(position);
    }

    public int getMapHeight() {
        return mapHeight;
    }
    public int getMapWidth(){
        return mapWidth;
    }
    public void removeDeadAnimals(){
        Iterator<Map.Entry<Vector2D, List<Animal>>> iterator = animals.entrySet().iterator();
        while (iterator.hasNext()){
            Map.Entry<Vector2D, List<Animal>> entry = iterator.next();
            for (Animal being : entry.getValue()){
                if (being.getEnergy() == 0){
                    entry.getValue().remove(being);
                }
            }
        }
    }
    public void removeAnimal(Animal animal){
        this.animals.remove(animal);
        this.deadAnimals.add(animal);
    }
    public void moveAllAnimals(String geneExecution){
        for(Animal animal: animals){
            animal.move(geneExecution);
        }
    }
    public void eatGrass(){
        for(Vector2D grassPosition: this.grass.keySet()){
//           to może nie działać, bo nie wiem czy value w hashmapie jest tu przekazywane przez referencję
            List<Animal> challengerList = this.animals.get(grassPosition);
            Animal winner = challengerList.get(0);
//            int maxEnergy = ;
//            for(Animal challenger: challengerList){
//                if( maxEnergy < )
//            }
        }
    }
}
