package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Vector2D;

public interface IWorldMap {
    public Object objectAt(Vector2D position);
    void placeAnimal(Animal animal);
    boolean isOccupied(Vector2D position);
}