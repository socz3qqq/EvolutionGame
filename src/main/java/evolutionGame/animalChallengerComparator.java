package evolutionGame;

import evolutionGame.mapElements.Animal;

import java.util.Comparator;
import java.util.Random;

public class animalChallengerComparator implements Comparator<Animal>{
    Random rand = new Random();

    @Override
    public int compare(Animal a1, Animal a2) {
        if(a1.getEnergy() == a2.getEnergy()){
            if(a1.getAge() == a2.getAge()){
                if(a1.getNumberOfChildren() == a2.getNumberOfChildren()){
                    return this.rand.nextInt(-2,2);
                }
                else return a1.getNumberOfChildren() - a2.getNumberOfChildren();
            }
            else return a1.getAge() - a2.getAge();
        }
        else return a1.getEnergy() - a2.getEnergy();

    }
}
