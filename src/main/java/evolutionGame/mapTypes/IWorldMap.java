package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Grass;
import evolutionGame.mapElements.Vector2D;

public interface IWorldMap {
    void placeAnimal(Animal animal);
    void placeGrass();
    boolean isOccupied(Vector2D position);
    Vector2D adjustMoveCoordinates(Vector2D position);
}