package evolutionGame.mapElements;
import evolutionGame.mapTypes.AbstractWorldMap;


public class Grass extends AbstractWorldMapElement{
    private final Vector2D position;
    private AbstractWorldMap map;

    public Grass(AbstractWorldMap map, Vector2D position){
        this.map = map;
        this.position = position;
    }

    @Override
    public String getGraphicalRepresentation() {
        return "src/main/resources/grass.png";
    }

    public Vector2D getPosition() {
        return position;
    }
    @Override
    public String toString(){
        return "G";
    }
}
