
//do zrobienia całość
package evolutionGame.mapTypes;

import evolutionGame.mapElements.Vector2D;

import org.javatuples.Pair;
import java.util.PriorityQueue;

public class ToxicCorpses extends AbstractWorldMap {
    private class grassWithPriority{

    }

    PriorityQueue<Pair<Vector2D, Integer>> availableGrassPositions = new PriorityQueue<Pair<Vector2D, Integer>>();
    public ToxicCorpses(int width, int height, int energyFromGrass){
        super(energyFromGrass);
        this.mapWidth = width;
        this.mapHeight = height;
        this.lowerBound = new Vector2D(0, 0);
        this.upperBound = new Vector2D(mapWidth - 1, mapHeight - 1);
        prepareGrassPositions();
    }

    @Override
    public void removeGrass(Vector2D position) {
        this.grass.remove(position);
    }

    @Override
    public Vector2D getGrassPosition() {
        return new Vector2D(rand.nextInt(mapWidth), rand.nextInt(mapHeight));
    }
    public void prepareGrassPositions(){
        for (int x = 0; x < this.mapWidth; x++) {
            for(int y = 0; y < this.mapHeight; y++) {
                availableGrassPositions.add(new Pair<>(new Vector2D(x, y), 0));
            }
        }
    }
}