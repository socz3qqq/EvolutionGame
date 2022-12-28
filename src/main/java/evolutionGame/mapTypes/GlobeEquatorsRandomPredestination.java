package evolutionGame.mapTypes;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Grass;
import evolutionGame.mapElements.Vector2D;
import java.util.Random;

public class GlobeEquatorsRandomPredestination extends AbstractWorldMap {
    private final Random rand = new Random();
    public GlobeEquatorsRandomPredestination(int width, int height, int grassCount, int animalCount){
        this.mapWidth = width;
        this.mapHeight = height;
        this.initialGrassCount = grassCount;
        this.initialAnimalCount = animalCount;
    }
    @Override
    public Vector2D adjustMoveCoordinates(Vector2D position){
        if(position.getX() < 0) position.setX(position.getX() + this.mapWidth);
        if(position.getY() < 0) position.setY(position.getY() + this.mapHeight);
        return new Vector2D(position.getX() % mapWidth, position.getY() % mapHeight);
    }
    public void placeAllGrass(){
        for (int i = 0; i < initialGrassCount; i++) {
            placeGrass();
        }
    }
    @Override
    public void placeGrass(){
        int preferredTile = rand.nextInt(5);
        int x;
        int y;
        if(preferredTile == 1){
            do {
                x = rand.nextInt(this.mapWidth);
                double upperBound = (this.mapHeight) * 0.8;
                y = rand.nextInt(0, (int)upperBound);
                if(y > (int)(upperBound/2)){
                    y += (int)(this.mapHeight*0.2);
                }
            }
            while (isOccupiedByGrass(new Vector2D(x, y)));
        }
        else{
            do {
                x = rand.nextInt(this.mapWidth);
                int lowerBound =  (int)(this.mapHeight*0.4);
                int upperBound = (int)(this.mapHeight*0.6);
                y = rand.nextInt(lowerBound, upperBound);
            }
            while (isOccupiedByGrass(new Vector2D(x, y)));
        }
        Vector2D grassPosition = new Vector2D(x, y);
//         Dodaj trawę
        Grass grassTile = new Grass();
        this.grass.put(grassPosition, grassTile);
    }
    public void placeAllAnimals(){
        for (int i = 0; i < initialAnimalCount; i++) {
            int x = rand.nextInt(mapWidth);
            int y = rand.nextInt(mapHeight);
//            Dodaj animala
            placeAnimal(new Animal(this,  new Vector2D(x, y)));
        }
    }

    @Override
    public void placeAnimal(Animal animal) {
//        this.animals.put(animal);
    }
}
