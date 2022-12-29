package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Vector2D;

public class DevilishPortal extends AbstractWorldMap{
    public DevilishPortal(int width, int height){
        this.mapWidth = width;
        this.mapHeight = height;
        this.lowerBound = new Vector2D(0, 0);
        this.upperBound = new Vector2D(mapWidth, mapHeight);
    }

    @Override
    public Vector2D adjustMoveCoordinates(Animal animal, Vector2D position) {
        if(!position.follows(lowerBound) || !position.precedes(upperBound)){
//            trzeba wybrać ile ma się zmieniać ta energia
            animal.decreaseEnergy(10);
            position = new Vector2D(rand.nextInt(mapWidth), rand.nextInt(mapHeight));
        }
        return position;
    }
}
