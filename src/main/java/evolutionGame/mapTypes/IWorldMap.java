package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Vector2D;

public interface IWorldMap {
    void placeAnimal(Animal animal);
    void placeGrass(String grassType);
    boolean isOccupied(Vector2D position);
    public Vector2D adjustMoveCoordinates(Animal animal, Vector2D position);
}