package evolutionGame.mapElements;
import evolutionGame.mapTypes.AbstractWorldMap;


public class Grass {
    private final Vector2D position;
    private AbstractWorldMap map;

    public Grass(AbstractWorldMap map, Vector2D position){
        this.map = map;
        this.position = position;
    }

    public Vector2D getPosition() {
        return position;
    }
    @Override
    public String toString(){
        return "G";
    }
}
