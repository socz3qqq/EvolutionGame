package evolutionGame.mapElements;

public class Vector2D {
    final int x, y;

    public Vector2D(int a, int b){
        this.x = a;
        this.y = b;
    }

    @Override
    public String toString(){
        return "(" + x + "," + y +")";
    }

    public boolean precedes(Vector2D other){
        return x <= other.x && y <= other.y;
    }

    public boolean follows(Vector2D other){
        return x >= other.x && y >= other.y;
    }

    public Vector2D add(Vector2D other){
        return new Vector2D(x + other.x, y + other.y);
    }

    public Vector2D subtract(Vector2D other){
        return new Vector2D(x - other.x, y - other.y);
    }


    public Vector2D upperRight(Vector2D other) {

        int firstCoord = Math.max(x, other.x);
        int secondCoord = Math.max(y, other.y);

        return new Vector2D(firstCoord, secondCoord);
    }


    public Vector2D lowerLeft(Vector2D other) {

        int firstCoord = Math.min(x, other.x);
        int secondCoord = Math.min(y, other.y);

        return new Vector2D(firstCoord, secondCoord);
    }


    public Vector2D opposite(){
        return new Vector2D(-x, -y);
    }

    @Override
    public boolean equals(Object other){
        if (this == other)
            return true;
        if (!(other instanceof Vector2D that))
            return false;
        return x == that.x && y == that.y;
    }

    @Override
    public int hashCode() {
        return x ^ y;
    }

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }
}
