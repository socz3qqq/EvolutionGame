package evolutionGame.mapElements;

abstract public class AbstractWorldMapElement implements IMapElement {
    Vector2D position;
    @Override
    public Vector2D getPosition() {
        return this.position;
    }

}
