package evolutionGame.mapElements;

import java.util.Random;

public class Genes {
    private int numberOfGenes;
    private int currentGeneIndex = 0;
    private int[] genes;
    private Random rand = new Random();

    public Genes(int howManyGenes){
        numberOfGenes = howManyGenes;
        genes = new int[numberOfGenes];
        for (int i = 0; i < numberOfGenes; i++){
            genes[i] = rand.nextInt(8);
        }
    }

    public Genes(int[] genotype, int numberOfGenes){
        this.numberOfGenes = numberOfGenes;
        this.genes = genotype;
    }

    public int getCurrentGene(String moveVariant){
        switch (moveVariant){
            case "Predestination" -> {}
            case "LittleMadness" -> {
                int probability = rand.nextInt(5);
//                20% szansy na szaleństwo
                if(probability == 1){
                    currentGeneIndex = rand.nextInt(numberOfGenes);
                }
            }
            default -> throw new IllegalArgumentException("Wrong move Variant");
        }
        int currentGene;
        currentGene = genes[currentGeneIndex];
        currentGeneIndex = (currentGeneIndex + 1) % numberOfGenes;
        System.out.println(currentGene + " index: " + currentGeneIndex );
        return currentGene;
    }

    public int[] getGenes() {
        return genes;
    }
}
