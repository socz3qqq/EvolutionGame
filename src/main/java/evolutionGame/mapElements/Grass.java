package evolutionGame.mapElements;
import evolutionGame.mapTypes.AbstractWorldMap;

import java.util.Random;

public class Grass {
    private final Vector2D position;
    private AbstractWorldMap map;
    private Random rand = new Random();

    public Grass(AbstractWorldMap map, String grassType){
        this.map = map;
        switch (grassType){
            case "ToxicCorpse" -> {
                this.position = getToxicPosition();
            }
            case "Equator" -> {
                this.position = getEquatorPosition();
            }
            default -> throw new IllegalArgumentException("Nieprawidłowy wariant umieszczenia trawy");
        }
    }

    public Vector2D getPosition() {
        return position;
    }

    private Vector2D getEquatorPosition(){
        int preferredTile = rand.nextInt(5);
        int x;
        int y;
//        20% szans
        if(preferredTile == 1){
            do {
                x = rand.nextInt(this.map.getMapWidth());
                double upperBound = (this.map.getMapHeight()) * 0.8;
                y = rand.nextInt(0, (int)upperBound);
                if(y > (int)(upperBound/2)){
                    y += (int)(this.map.getMapHeight()*0.2);
                }
            }
            while (this.map.isOccupiedByGrass(new Vector2D(x, y)));
        }
//        80% szans
        else{
            do {
                x = rand.nextInt(this.map.getMapWidth());
                int lowerBound =  (int)(this.map.getMapHeight()*0.4);
                int upperBound = (int)(this.map.getMapHeight()*0.6);
                y = rand.nextInt(lowerBound, upperBound);
            }
            while (this.map.isOccupiedByGrass(new Vector2D(x, y)));
        }
        return new Vector2D(x, y);
    }

    private Vector2D getToxicPosition() {
        return new Vector2D(rand.nextInt(), rand.nextInt());
    }
}
