package board;

public class Player {
    String name;
    Integer position;

    public Player(String name) {
        this.name = name;
        this.position = 0;
    }

    public String getName() {
        return this.name;
    }

    public Integer getDiceNumber() {
        return (int)(Math.random()*5 + 1);
    }

    public Integer getPosition() {
        return this.position;
    }

    public Integer setPosition(Integer p) {
        this.position = p;
        return this.position;
    }
}
