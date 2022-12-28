package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Grass;
import evolutionGame.mapElements.Vector2D;

import java.util.HashMap;

public class AbstractWorldMap implements IWorldMap{
    protected int mapWidth;
    protected int mapHeight;
    protected int initialGrassCount;
    protected int energyFromGrass;
    protected int dailyGrassIncrease;
    protected int initialAnimalCount;
    protected int initialAnimalEnergy;
    //    protected int minimalStuffedEnergy;
//    protected double energyUsedForReproduction;
//    protected int minChildMutation;
//    protected int maxChildMutation;
    protected HashMap<Vector2D, Animal> animals = new HashMap<>();
    protected HashMap<Vector2D, Grass> grass = new HashMap<>();

    @Override
    public void placeAnimal(Animal animal) {}

    @Override
    public void placeGrass() {}

    @Override
    public boolean isOccupied(Vector2D position) {
        return false;
    }

    @Override
    public Vector2D adjustMoveCoordinates(Vector2D position) {
        return position;
    }
    public boolean isOccupiedByGrass(Vector2D position){
        return this.grass.containsKey(position);
    }
}
