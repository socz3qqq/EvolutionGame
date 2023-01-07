package evolutionGame.mapElements;

public enum MapDirections {
    NORTH,
    NORTHEAST,
    EAST,
    SOUTHEAST,
    SOUTH,
    SOUTHWEST,
    WEST,
    NORTHWEST;

    @Override
    public String toString() {
        return switch (this){
            case NORTH -> "N";
            case NORTHEAST -> "NE";
            case EAST -> "E";
            case SOUTHEAST -> "SE";
            case SOUTH -> "S";
            case SOUTHWEST -> "SW";
            case WEST -> "W";
            case NORTHWEST -> "Nw";
        };
    }

    public MapDirections rotate(int a){
        return MapDirections.values()[(this.ordinal() + a) % 8];
    }

    public Vector2D toUnitVector(){
        int firstCoord=0, secondCoord=0;
        switch (this){
            case NORTH -> {
                firstCoord = 0;
                secondCoord = 1;
            }
            case NORTHEAST -> {
                firstCoord = 1;
                secondCoord = 1;
            }
            case EAST -> {
                firstCoord = 1;
                secondCoord = 0;
            }
            case SOUTHEAST -> {
                firstCoord = 1;
                secondCoord = -1;
            }
            case SOUTH -> {
                firstCoord = 0;
                secondCoord = -1;
            }
            case SOUTHWEST -> {
                firstCoord = -1;
                secondCoord = -1;
            }
            case WEST -> {
                firstCoord = -1;
                secondCoord = 0;
            }
            case NORTHWEST -> {
                firstCoord = -1;
                secondCoord = 1;
            }
        }
        return new Vector2D(firstCoord, secondCoord);
    }

}
