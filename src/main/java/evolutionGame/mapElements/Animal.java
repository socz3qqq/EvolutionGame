package evolutionGame.mapElements;

import evolutionGame.mapTypes.AbstractWorldMap;

public class Animal {
    private MapDirections currentDirection;
    private Vector2D position;
    private AbstractWorldMap map;

    private int energy;

    private Genes genotype;

    private int numberOfChild;

    public Animal(AbstractWorldMap map, Vector2D initialPosition){
        this.map = map;
        this.position = initialPosition;
    }

    public MapDirections getCurrentDirection(){
        return currentDirection;
    }

    public Vector2D getPosition() {
        return position;
    }

    boolean isAt(Vector2D position){
        return this.position.equals(position);
    }

    void move(int rotation){
        Vector2D displacementVector;
        this.currentDirection.rotate(rotation);
        displacementVector = this.currentDirection.toUnitVector();
        //w zależności od rodzaju mapy ruch sie zmienia
    }
}
