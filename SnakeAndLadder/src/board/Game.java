package board;

import java.util.ArrayList;

public class Game {
    private Board board;
    private ArrayList<Player> players;
    private Player winner;


    public Game(ArrayList<Ladder> ladders, ArrayList<Snake> snakes, ArrayList<Player> players) {
        board = new Board(ladders, snakes);
        this.players = players;
    }

    public Player run() {
        while(winner == null) {
            players.forEach(player -> {
                playTurn(player);
                if(winner != null) return;
            });
        }
        System.out.println(winner.getName() + " wins the game");
        return winner;
    }

    public void playTurn(Player player) {
        int diceNumber = player.getDiceNumber();
        int curPos = player.getPosition();
        if(board.isEligibleMove(player.getPosition() + diceNumber)) {
            updatePlayerPosition(player, player.getPosition() + diceNumber);
        }
        System.out.println(player.getName() + " rolled a " + diceNumber + " and moved from " + curPos + " to " + player.getPosition());
    }

    public void updatePlayerPosition(Player p, Integer newPostion) {
        p.setPosition(newPostion);
        checkForSnakeAndTakeAction(p);
        checkForLadderAndTakeAction(p);
        if(board.isWinningPos(p.getPosition())) winner = p;
    }

    private void checkForSnakeAndTakeAction(Player player) {
        Integer snakeTail = board.snakeTail(player.getPosition());
        if(snakeTail != -1) updatePlayerPosition(player, snakeTail);
    }

    private void checkForLadderAndTakeAction(Player player) {
        Integer ladderEnd = board.ladderEnd(player.getPosition());
        if(ladderEnd != -1) updatePlayerPosition(player, ladderEnd);
    }


}
