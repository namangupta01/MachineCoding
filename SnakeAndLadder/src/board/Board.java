package board;

import java.util.ArrayList;
import java.util.HashMap;

public class Board {
    ArrayList<Snake> snakes;
    ArrayList<Ladder> ladders;
    HashMap<Integer, Snake> snakeHeadToSnakeMap;
    HashMap<Integer, Ladder> ladderEndToLadderMap;
    Integer size = 100;

    Board(ArrayList<Ladder> ladders, ArrayList<Snake> snakes) {
        this.ladders = ladders;
        this.snakes = snakes;
        ladderEndToLadderMap = new HashMap<>();
        snakeHeadToSnakeMap = new HashMap<>();
        ladders.forEach(ladder -> ladderEndToLadderMap.put(ladder.getStart(), ladder));
        snakes.forEach(snake -> snakeHeadToSnakeMap.put(snake.getHead(), snake));
    }

    public boolean isEligibleMove(int position) {
        return size >= position;
    }

    public Integer snakeTail(int position) {
        Snake snake = snakeHeadToSnakeMap.getOrDefault(position, null);
        return snake == null ? -1 : snake.getTail();
    }

    public Integer ladderEnd(int position) {
        Ladder ladder = ladderEndToLadderMap.getOrDefault(position, null);
        return ladder == null ? -1 : ladder.getEnd();
    }

    public Boolean isWinningPos(int position) {
        return position == 100;
    }
}
