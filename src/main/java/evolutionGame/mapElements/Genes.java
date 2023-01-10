package evolutionGame.mapElements;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Random;

public class Genes {
    private int numberOfGenes;
    private int currentGeneIndex = 0;
    private ArrayList<Integer> genes;
    private Random rand = new Random();

    public Genes(int howManyGenes){
        numberOfGenes = howManyGenes;
        genes = new ArrayList<>(numberOfGenes);
        for (int i = 0; i < numberOfGenes; i++){
            genes.add(rand.nextInt(8));
        }
    }

    public Genes(ArrayList<Integer> genotype, int numberOfGenes){
        this.numberOfGenes = numberOfGenes;
        this.genes = (ArrayList<Integer>) genotype.clone();
        System.out.println("stworzono dziecko o genomie: " + genes.toString());
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
        currentGene = genes.get(currentGeneIndex);
        currentGeneIndex = (currentGeneIndex + 1) % numberOfGenes;
        System.out.println(currentGene + " index: " + currentGeneIndex );
        return currentGene;
    }

    public ArrayList<Integer> getGenes() {
        return genes;
    }
    public int getActivatedGene(){
        return this.genes.get(currentGeneIndex);
    }
}
