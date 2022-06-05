package board;

public class Ladder {
    private Integer start;
    private Integer end;

    public Ladder(Integer start, Integer end) {
        this.start = start;
        this.end = end;
    }

    public Integer getStart() {
        return this.start;
    }

    public Integer getEnd() {
        return this.end;
    }
}
