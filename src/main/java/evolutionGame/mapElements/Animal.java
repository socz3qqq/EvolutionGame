package evolutionGame.mapElements;

import evolutionGame.mapTypes.AbstractWorldMap;
import evolutionGame.mapTypes.IWorldMap;

import java.util.ArrayList;
import java.util.Objects;
import java.util.Random;

public class Animal extends AbstractWorldMapElement {
    private Random rand = new Random();

    private MapDirections currentDirection = MapDirections.values()[rand.nextInt(8)];; //orientacja na mapie
    private Vector2D position; //pozycja na mapie
    private AbstractWorldMap map; //mapa
    private int energy; //obecna energia
    public int age = 0;
    private int dayOfBirth = 0; //dzien narodzin
    private int dayofDeath; //dzien smierci
    private Genes genotype; //genom
    private int numberOfChildren = 0; //liczba dzieci

    //stałe globalne
    private int minimalStuffedEnergy; //minimalna energia najedzenia
    private int energyUsedForReproduction; //energia zuzyta na rozmnazanie
    private int minChildMutation; //min liczba mutacji
    private int maxChildMutation; //max liczba mutacji
    private int genotypeLength; //dlugosc genomu


    //    jeśli nie podamy pozycji zwierzaka, to pojawi się w losowym miejscu
    public Animal(AbstractWorldMap map, int genotypeLength, int initialEnergy, int minimalStuffedEnergy, int energyUsedForReproduction, int minChildMutation, int maxChildMutation){
        this.map = map;
        this.position = new Vector2D(rand.nextInt(map.getMapWidth()), rand.nextInt(map.getMapHeight()));
        this.genotype = new Genes(genotypeLength);
        this.energy = initialEnergy;
        this.minimalStuffedEnergy = minimalStuffedEnergy;
        this.energyUsedForReproduction = energyUsedForReproduction;
        this.minChildMutation = minChildMutation;
        this.maxChildMutation = maxChildMutation;
        this.genotypeLength = genotypeLength;
    }

    //szkielet Animala do tworzenia dziecka
    public Animal(Vector2D position, AbstractWorldMap map, int dayOfBirth){
        this.map = map;
        this.position = position;
        this.dayOfBirth = dayOfBirth;
    }

    public Animal copulate(Animal other, int currentDayOfSimulation){
        if (this.energy >= minimalStuffedEnergy && other.getEnergy() >= minimalStuffedEnergy) {
            Animal child = new Animal(this.position, this.map, currentDayOfSimulation);
            this.numberOfChildren += 1;
            other.numberOfChildren += 1;

            int numberOfThisGenes = (int) ((float)(((float)this.energy/((float)this.energy + (float)other.getEnergy()))*(float)genotypeLength)); //ile genow bierze od this
            int numberOfOtherGenes = genotypeLength - numberOfThisGenes; //ile genow bierze od other
            System.out.println("geny pierwszego: " + numberOfThisGenes + "  geny drugiego: " + numberOfOtherGenes + "genotypelength" + (float)((this.energy/(this.energy + other.getEnergy()))*genotypeLength));

            this.decreaseEnergy(this.energyUsedForReproduction);
            other.decreaseEnergy(this.energyUsedForReproduction);
            child.minimalStuffedEnergy = this.minimalStuffedEnergy;
            child.energyUsedForReproduction = this.energyUsedForReproduction;
            child.minChildMutation = this.minChildMutation;
            child.maxChildMutation = this.maxChildMutation;
            child.energy = 2 * this.energyUsedForReproduction;
            child.genotypeLength = this.genotypeLength;


            ArrayList<Integer> childGenome = new ArrayList<>(); //geny dziecka
            ArrayList<Integer> thisGenome = this.genotype.getGenes(); //geny this
            ArrayList<Integer> otherGenome = other.genotype.getGenes(); //geny other
            int randomness = rand.nextInt(2);
            System.out.println("Długość genomu dizecka: " + genotypeLength);

            switch (randomness){
                //case 1: dziecko bierze od this z lewej strony
                case 1 -> {
                    for (int i=0; i < numberOfThisGenes; i++){
                        childGenome.add(i, thisGenome.get(i));
                    }
                    for (int i = numberOfThisGenes; i < genotypeLength; i++){
                        childGenome.add(i, otherGenome.get(i));
                    }
                }
                //case 0: dziecko bierze od this z prawej strony
                case 0 -> {
                    for (int i=0; i<numberOfOtherGenes; i++){
                        childGenome.add(i, otherGenome.get(i));
                    }
                    for (int i = numberOfOtherGenes; i < this.genotypeLength; i++){
                        childGenome.add(i, thisGenome.get(i));
                    }
                }
            };
            child.genotype = new Genes(childGenome, this.genotypeLength);
            return child;
        }
        return null;
    }

    public Animal(AbstractWorldMap map){
        this.map = map;
        this.position = new Vector2D(rand.nextInt(map.getMapWidth()), rand.nextInt(map.getMapHeight()));
    }

    public MapDirections getCurrentDirection(){
        return currentDirection;
    }

    @Override
    public String getGraphicalRepresentation() {
        return "src/main/resources/up.png";
    }

    public Vector2D getPosition() {
        return position;
    }

    boolean isAt(Vector2D position){
        return this.position.equals(position);
    }

    public void move(String moveVariant, String cornerBehaviour){
        int rotation = genotype.getCurrentGene(moveVariant);
        System.out.println(rotation + "  " + currentDirection);
        this.currentDirection = this.currentDirection.rotate(rotation);

        Vector2D displacementVector = this.currentDirection.toUnitVector();
        Vector2D newPosition = this.position.add(displacementVector);
//        System.out.println(this.position + " chce przejść na "+ newPosition);

        //w zależności od rodzaju mapy ruch sie zmienia
        this.position = this.map.adjustMoveCoordinates(this, newPosition, cornerBehaviour);
//        System.out.println("po korekcie: " + this.position);
        decreaseEnergy(1);
        this.age += 1;
    }
    public int getEnergy(){
        return this.energy;
    }
    public int getAge(){return  this.age;}
    public int getNumberOfChildren(){return this.numberOfChildren;}
    public void increaseEnergy(int energy){
        this.energy += energy;
    }
    public void decreaseEnergy(int energy){
        this.energy -= energy;
    }
    @Override
    public String toString(){
        return "A";
    }

    public Genes getGenotype() {
        return genotype;
    }
}
