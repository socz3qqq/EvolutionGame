package evolutionGame.mapElements;

import evolutionGame.mapTypes.AbstractWorldMap;
import evolutionGame.mapTypes.IWorldMap;

import java.util.Random;

public class Animal {
    private MapDirections currentDirection;
    private Vector2D position;
    private AbstractWorldMap map;
    private int energy;
    private int age;

    private Genes genotype;

    private int numberOfChild;
    private Random rand = new Random();

    public Animal(AbstractWorldMap map, int genotypeLength, Vector2D initialPosition){
        this.map = map;
        this.position = initialPosition;
        this.genotype = new Genes(genotypeLength);
    }
    public Animal(AbstractWorldMap map, int genotypeLength){
        this.map = map;
        this.position = new Vector2D(rand.nextInt(map.getMapWidth()), rand.nextInt(map.getMapHeight()));
        this.genotype = new Genes(genotypeLength);
    }
    public Animal(AbstractWorldMap map){
        this.map = map;
        this.position = new Vector2D(rand.nextInt(map.getMapWidth()), rand.nextInt(map.getMapHeight()));
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
        //w zależności od rodzaju mapy ruch sie zmienia
        this.position = this.map.adjustMoveCoordinates(this, newPosition);
    }
    public int getEnergy(){
        return this.energy;
    }
    public void increaseEnergy(int energy){
        this.energy += energy;
    }
    public void decreaseEnergy(int energy){
        this.energy -= energy;
    }
    public void increaseEnergy(int energy){
        this.energy += energy;
    }
    public void decreaseEnergy(int energy){
        this.energy -= energy;
    }
}
