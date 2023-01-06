package evolutionGame.mapTypes;

import evolutionGame.mapElements.Vector2D;

import java.util.*;

public class Equators extends AbstractWorldMap {
    Queue<Vector2D> availableGrassPositionsOnEquator = new LinkedList<>();
    Queue<Vector2D> availableGrassPositionsOutsideEquator = new LinkedList<>();

    public Equators(int width, int height, int energyFromGrass){
        super(energyFromGrass);
        this.mapWidth = width;
        this.mapHeight = height;
        this.lowerBound = new Vector2D(0, 0);
        this.upperBound = new Vector2D(mapWidth - 1, mapHeight - 1);
        prepareGrassPositions();
    }
    public void prepareGrassPositions(){
        List<Vector2D> allEquatorPositions = new ArrayList<>();
        List<Vector2D> allNotEquatorPositions = new ArrayList<>();
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++){
                if(y < (int) (0.4*mapHeight) || y >= (int)(0.6*mapHeight)){
                    allNotEquatorPositions.add(new Vector2D(x, y));
                }
                else allEquatorPositions.add(new Vector2D(x, y));
            }
        }
        System.out.println(allEquatorPositions);
        Collections.shuffle(allEquatorPositions);
        System.out.println(allEquatorPositions);
        Collections.shuffle(allNotEquatorPositions);

        this.availableGrassPositionsOnEquator.addAll(allEquatorPositions);
        this.availableGrassPositionsOutsideEquator.addAll(allNotEquatorPositions);
    }
    @Override
    public Vector2D getGrassPosition(){
        int preferredTile = rand.nextInt(5);
        Vector2D newGrassPosition;
//        20% szans
        if(preferredTile == 1 && !availableGrassPositionsOutsideEquator.isEmpty())
            return this.availableGrassPositionsOutsideEquator.remove();

//        80% szans
        else if(!availableGrassPositionsOnEquator.isEmpty())
            return this.availableGrassPositionsOnEquator.remove();
        else return null;

    }


    @Override
    public void removeGrass(Vector2D position){
        this.grass.remove(position);
        if(position.follows(new Vector2D(0, (int)(mapHeight*0.4))) && position.precedes(new Vector2D(mapWidth - 1, (int)(mapHeight*0.6))))
            this.availableGrassPositionsOnEquator.add(position);
        else this.availableGrassPositionsOutsideEquator.add(position);
    }
}
