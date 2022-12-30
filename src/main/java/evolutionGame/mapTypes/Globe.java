package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Vector2D;

import java.util.Random;

public class Globe extends AbstractWorldMap{
    private final Random rand = new Random();
    public Globe(int width, int height){
        this.mapWidth = width;
        this.mapHeight = height;
        this.upperBound = new Vector2D(0, 0);
        this.lowerBound = new Vector2D(mapWidth - 1, mapHeight - 1);
    }
    @Override
    public Vector2D adjustMoveCoordinates(Animal animal, Vector2D position){
        if(position.getX() < 0) position.setX(position.getX() + this.mapWidth);
        if(position.getY() < 0) position.setY(position.getY() + this.mapHeight);
        return new Vector2D(position.getX() % mapWidth, position.getY() % mapHeight);
    }
}
