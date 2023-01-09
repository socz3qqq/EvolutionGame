package evolutionGame;

import evolutionGame.mapElements.Animal;
import evolutionGame.mapElements.Vector2D;
import evolutionGame.mapTypes.IWorldMap;
import org.junit.jupiter.api.Test;

public class TestAnimal {
    @Test
    void TestCopulating() {
        IWorldMap map = new IWorldMap() {
            @Override
            public Object objectAt(Vector2D position) {
                return null;
            }

            @Override
            public void placeAnimal(Animal animal) {

            }

            @Override
            public boolean isOccupied(Vector2D position) {
                return false;
            }
        };
//        Animal zwierze1 = new Animal()
    }
}