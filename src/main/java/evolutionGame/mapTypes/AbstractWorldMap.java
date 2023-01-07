// to do:
// -zrobić zmianę pozycji zwierzęta poprzez obserwatora



package evolutionGame.mapTypes;

import evolutionGame.IPositionChangeObserver;
import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Grass;
import evolutionGame.mapElements.Vector2D;
import evolutionGame.animalChallengerComparator;

import java.util.*;


abstract public class AbstractWorldMap implements IWorldMap, IPositionChangeObserver {
    protected final Random rand = new Random();
    protected int mapWidth;
    protected int mapHeight;
    protected Vector2D upperBound;
    protected Vector2D lowerBound;
    protected int energyFromGrass;
    protected int animalCount;
    protected List<Animal> deadAnimals;
    protected HashMap<Vector2D, List<Animal>> animals = new HashMap<>();
    protected HashMap<Vector2D, Grass> grass = new HashMap<>();
    protected MapVisualizer mapVisualizer;

    public AbstractWorldMap(int energyFromGrass){
        this.mapVisualizer = new MapVisualizer(this);
        this.deadAnimals = new ArrayList<>();
        this.energyFromGrass = energyFromGrass;
    }
    @Override
    public Object objectAt(Vector2D position) {
        if(animals.getOrDefault(position, null) != null && !animals.get(position).isEmpty()){
            return animals.get(position).get(0);}
        return grass.getOrDefault(position, null);
    }
    @Override
    public String toString() {
        return mapVisualizer.draw(this.lowerBound, this.upperBound);
    }
    @Override
    public void placeAnimal(Animal animal) {
        if(animals.containsKey(animal.getPosition())){
            List<Animal> animalsAtPosition = this.animals.get(animal.getPosition());
            animalsAtPosition.add(animal);
            this.animals.remove(animal.getPosition());
            this.animals.put(animal.getPosition(), animalsAtPosition);
        }
        else{
            List<Animal> newAnimals = new ArrayList<>();
            newAnimals.add(animal);
            this.animals.put(animal.getPosition(), newAnimals);
        }
        this.animalCount += 1;
    }
    public void placeGrass() {
        Grass grass = new Grass(this, getGrassPosition());
        this.grass.put(grass.getPosition(), grass);
    }
    @Override
    public boolean isOccupied(Vector2D position) {
        return this.animals.containsKey(position) || this.grass.containsKey(position);
    }
    public int getMapHeight() {
        return mapHeight;
    }
    public int getMapWidth(){
        return mapWidth;
    }
    public void removeDeadAnimals(){
        if(this.animals.isEmpty()){
            throw new RuntimeException("Zwierzęta się skończyły przed usuwaniem ich z mapy :(");
        }
        List<Animal> currentAnimals = new ArrayList<>();
        for (Map.Entry<Vector2D, List<Animal>> entry : animals.entrySet()) {
            currentAnimals.addAll(entry.getValue());
        }
        for(Animal being : currentAnimals){
            if (being.getEnergy() <= 0) {
                System.out.println(being);
                removeAnimal(being);
            }
        }
    }
    public void removeAnimal(Animal animal){
        List<Animal> animalsAtPosition = this.animals.get(animal.getPosition());

        this.animals.remove(animal.getPosition());
        animalsAtPosition.remove(animal);
        if(!animalsAtPosition.isEmpty())
            this.animals.put(animal.getPosition(), animalsAtPosition);

        System.out.println("zwierzak zdecchł :(, ostatnio widziano go na pozycji: "+ animal.getPosition());
        this.deadAnimals.add(animal);
        this.animalCount -= 1;
    }
    public void moveAllAnimals(String geneExecution, String cornerBehaviour){
        if(this.animals.isEmpty()){
            throw new RuntimeException("Zwierzęta się skończyły :(");
        }
        List<Animal> currentAnimals = new ArrayList<>();
        for (Map.Entry<Vector2D, List<Animal>> entry : animals.entrySet()) {
            currentAnimals.addAll(entry.getValue());
        }
        for(Animal being : currentAnimals) {
            changeAnimalPosition(being, geneExecution, cornerBehaviour);
            printAnimalInformation(being);

        }
    }
    public void eatGrass(){
        Set<Vector2D> currentGrassPositions = Set.copyOf(this.grass.keySet());
        System.out.println(currentGrassPositions);
        for(Vector2D grassPosition: currentGrassPositions){
            List<Animal> challengerList = this.animals.get(grassPosition);

            if(challengerList == null || challengerList.isEmpty()) {
                continue;
            }
//          Sorting animal list according to:
//          1) energy
//          2) age
//          3) number of children
//          4) if the winner is still not found, the grass is eaten by random animal
            challengerList.sort(new animalChallengerComparator());
            Animal winner = challengerList.get(0);
//          the animal eats grass, and increases his energy
            winner.increaseEnergy(this.energyFromGrass);
            removeGrass(grassPosition);
            System.out.println("grass was eaten! grass position: " + grassPosition);
        }
    }
    abstract public void removeGrass(Vector2D position);
    public Vector2D adjustMoveCoordinates(Animal animal, Vector2D position, String cornerBehaviour) {
        switch (cornerBehaviour) {
            case "DevilishPost" -> {
                if (!position.follows(lowerBound) || !position.precedes(upperBound)) {
                    //            trzeba wybrać ile ma się zmieniać ta energia
                    animal.decreaseEnergy(10);
                    position = new Vector2D(rand.nextInt(mapWidth), rand.nextInt(mapHeight));
                }
                return position;
            }
            case "Globe" -> {
                if (position.getX() < 0) position.setX(position.getX() + this.mapWidth);
                if (position.getY() < 0) position.setY(position.getY() + this.mapHeight);
                return new Vector2D(position.getX() % mapWidth, position.getY() % mapHeight);
            }
            default -> throw new IllegalArgumentException();
        }
    }
    abstract public Vector2D getGrassPosition();

    public void animalReproduction(int currentDayOfSimulation){
        List<Animal> children = new ArrayList<>();
        for (Map.Entry<Vector2D, List<Animal>> entry : animals.entrySet()) {
            List<Animal> competitors = new ArrayList<>(List.<Animal>copyOf(entry.getValue()));
            competitors.sort(new animalChallengerComparator());
            for (int i = 0; i < competitors.size() - 1; i+=2) {
                System.out.println("Rodzice: " + Arrays.toString(competitors.get(i).getGenotype().getGenes().toArray()) + Arrays.toString(competitors.get(i+1).getGenotype().getGenes().toArray()));
                Animal childAnimal = competitors.get(i).copulate(competitors.get(i+1), currentDayOfSimulation);
                if(childAnimal == null) break;
                children.add(childAnimal);
                System.out.println("Urodziło się nowe zwierzę! " + childAnimal.getPosition());
            }
        }
        for(Animal child: children){
            placeAnimal(child);
            printAnimalInformation(child);
        };
    };
    public void grassGrow(int count) {
        for (int i = 0; i < count; i++) {
            Vector2D newGrassPosition = getGrassPosition();
            if(newGrassPosition != null){
                Grass grass = new Grass(this, newGrassPosition);
                this.grass.put(grass.getPosition(), grass);
            }
        }
    }

    private void changeAnimalPosition(Animal animal, String geneExecution, String cornerBehaviour){
    if(!this.animals.get(animal.getPosition()).isEmpty()) {

//      delete an animal from his current position
        List<Animal> animalsAtCurrentPosition = this.animals.get(animal.getPosition());
        animalsAtCurrentPosition.remove(animal);
        this.animals.remove(animal.getPosition());
        if(!animalsAtCurrentPosition.isEmpty())
            this.animals.put(animal.getPosition(), animalsAtCurrentPosition);

//      move the animal
        animal.move(geneExecution, cornerBehaviour);

//      add the animal to its new position
        List<Animal> animalsAtNewPosition = this.animals.getOrDefault(animal.getPosition(), new ArrayList<>());
        this.animals.remove(animal.getPosition());
        animalsAtNewPosition.add(animal);
        this.animals.put(animal.getPosition(), animalsAtNewPosition);

    }
    }
// trzeba tego obserwera zaimplementować
    @Override
    public void positionChanged(Vector2D oldPosition, Vector2D newPosition){

    }
    public boolean areThereAnyAnimalsLeft(){
        return !this.animals.isEmpty();
    }
    public static void printAnimalInformation(Animal animal){
        System.out.println("pozycja: " + animal.getPosition());
        System.out.println("energia: " + animal.getEnergy());
        System.out.println("geny: " + animal.getGenotype().getGenes());
    }
    public int getAnimalCount(){
        return this.animalCount;
    }
    public int getGrassCount(){
        return this.grass.size();
    }
    public int getUnoccupiedTilesCount(){
        int count = 0;
        for (int x = 0; x < mapWidth; x++) {
            for (int y = 0; y < mapHeight; y++) {
                if(!isOccupied(new Vector2D(x, y))) count += 1;
            }
        }
        return count;
    }
    public int getAverageEnergyLevel(){
        int allEnergy = 0;
        for (Map.Entry<Vector2D, List<Animal>> entry : animals.entrySet()) {
            for(Animal animal: entry.getValue()){
                allEnergy += animal.getEnergy();
            }

        }
        return allEnergy/this.animalCount;
    }
    public int getAverageLifeTime(){
        if(deadAnimals.isEmpty()) return 0;
        int lifeTimeSum = 0;
        for(Animal deadAnimal: deadAnimals){
            lifeTimeSum += deadAnimal.age;
        }
        return lifeTimeSum/deadAnimals.size();
    }

}