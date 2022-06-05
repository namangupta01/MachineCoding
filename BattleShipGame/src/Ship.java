public class Ship {
    String id;
    Integer size;
    Boolean isDestroyed;
    int x;
    int y;

    Ship(String id, int size, int x, int y) {
        this.id = id;
        this.size = size;
        isDestroyed = false;
        this.x = x;
        this.y = y;
    }
}
