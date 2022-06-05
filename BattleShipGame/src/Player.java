import java.util.ArrayList;
import java.util.HashSet;
import java.util.Random;

public class Player {
    HashSet<Ship> ships;
    int destroyedShipCount = 0;
    int areaXStart;
    int areaXEnd;
    int yLength;
    ArrayList<Coordinate> coordinates;

    Player(int start, int end, int n) {
        ships = new HashSet<>();
        areaXStart = start;
        areaXEnd = end;
        this.yLength = n;
        coordinates = new ArrayList<>();
        createCoordinates(start, end, n);
    }

    private void createCoordinates(int start, int end, int n) {
        for(int x=start; x<end; x++) {
            for(int y=0; y<n; y++) {
                coordinates.add(new Coordinate(x, y));
            }
        }
    }

    public void addShip(Ship ship) {
        ships.add(ship);
    }

    public Coordinate getCoordinate() {
        // If any other coordinate strategy to be added. It can be changed from here as this is the single point where coordinates are getting choosed from.
        return getRandomCoordinate();
    }

    private Coordinate getRandomCoordinate() {
        Random random = new Random();
        Integer randomNumber = random.nextInt(coordinates.size());
        Coordinate coordinate = coordinates.get(randomNumber);
        coordinates.remove(randomNumber);
        return coordinate;
    }

    public Ship getMissileHitDamage(Coordinate coordinate) {
        Ship ship = getShipAt(coordinate.x, coordinate.y);
        if(ship == null || ship.isDestroyed) {
            return null;
        }
        ship.isDestroyed = true;
        destroyedShipCount++;
        return ship;
    }

    public Ship getShipAt(int x, int y) {
        for(Ship ship : ships) {
            if(ship.x - ship.size/2 <= x && ship.x + ship.size/2 >= x && ship.y - ship.size/2 <= y && ship.y + ship.size/2 >= y) {
                return ship;
            }
        }
        return null;
    }

    public boolean allShipsDestroyed() {
        return ships.size() == destroyedShipCount;
    }
}


