package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Grass;
import evolutionGame.mapElements.Vector2D;

import java.util.HashMap;
import java.util.Random;

abstract public class AbstractWorldMap implements IWorldMap{
    protected final Random rand = new Random();
    protected int mapWidth;
    protected int mapHeight;
    protected Vector2D upperBound;
    protected Vector2D lowerBound;
    protected HashMap<Vector2D, Animal> animals = new HashMap<>();
    protected HashMap<Vector2D, Grass> grass = new HashMap<>();

    public AbstractWorldMap(){}
    @Override
    public void placeAnimal(Animal animal) {
        this.animals.put(animal.getPosition(), animal);
    }

    @Override
    public void placeAnimalRandomly(){
        Animal newAnimal = new Animal(this, new Vector2D(rand.nextInt(mapWidth), rand.nextInt(mapHeight)));
        placeAnimal(newAnimal);
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
}
