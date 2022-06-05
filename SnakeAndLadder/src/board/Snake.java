package board;

public class Snake {
    private Integer head;
    private Integer tail;

    public Snake(Integer head, Integer tail) {
        this.head = head;
        this.tail = tail;
    }

    public Integer getHead() {
        return this.head;
    }

    public Integer getTail() {
        return this.tail;
    }
}
