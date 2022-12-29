package evolutionGame.mapElements;

import evolutionGame.mapTypes.AbstractWorldMap;

import java.util.Random;

public class Animal {
    private MapDirections currentDirection;
    private Vector2D position;
    private AbstractWorldMap map;
    private int energy;

    private Genes genotype;

    private int numberOfChild;
    private Random rand = new Random();

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

    public void move(String moveVariant){
        int rotation = genotype.getCurrentGene(moveVariant);
        this.currentDirection.rotate(rotation);

        Vector2D displacementVector = this.currentDirection.toUnitVector();
        Vector2D newPosition = this.position.add(displacementVector);
        this.position = this.map.adjustMoveCoordinates(this, newPosition);
        //w zależności od rodzaju mapy ruch sie zmienia
    }
    public void increaseEnergy(int energy){
        this.energy += energy;
    }
    public void decreaseEnergy(int energy){
        this.energy -= energy;
    }
}
