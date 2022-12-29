package evolutionGame.mapElements;

import java.util.Random;

public class Genes {
    private int numberOfGenes = 0;
    //sugeruje stworzenie klasy ze stałymi
    private int currentGeneIndex = 0;
    private int[] genes;
    private Random rand = new Random();

    public int getCurrentGene(String moveVariant){
        int currentGene;
        switch (moveVariant){
            case "Predestination" -> {}
            case "LittleMadness" -> {
                int probability = rand.nextInt(5);
                if(probability == 3){
                    currentGeneIndex = rand.nextInt(numberOfGenes);
                }
            }
            default -> throw new IllegalArgumentException("Wrong move Variant");
        }
        currentGene = genes[currentGeneIndex];
        currentGeneIndex = (currentGeneIndex + 1) % numberOfGenes;
        return currentGene;
    }
}
