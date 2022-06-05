import board.Game;
import board.Ladder;
import board.Player;
import board.Snake;

import java.util.ArrayList;
import java.util.Scanner;

public class Main {
    public static void main(String[] args) {
        Scanner s = new Scanner(System.in);
        ArrayList<Snake> snakes = snakeInput(s);
        ArrayList<Ladder> ladders = ladderInput(s);
        ArrayList<Player> players = inputPlayers(s);
        Game game = new Game(ladders, snakes, players);
        game.run();
    }

    private static ArrayList<Snake> snakeInput(Scanner s) {
        System.out.println("Please Snakes Input ");
        int snakesCount =  s.nextInt();
        ArrayList<Snake> snakes = new ArrayList<>();
        for(int i=0; i<snakesCount; i++) {
            snakes.add(new Snake(s.nextInt(), s.nextInt()));
        }
        return snakes;
    }

    private static ArrayList<Ladder> ladderInput(Scanner s) {
        System.out.println("Please Ladder Input ");
        int laddersCount =  s.nextInt();
        ArrayList<Ladder> ladders = new ArrayList<>();
        for(int i=0; i<laddersCount; i++) {
            ladders.add(new Ladder(s.nextInt(), s.nextInt()));
        }
        return ladders;
    }

    private static ArrayList<Player> inputPlayers(Scanner s) {
        System.out.println("Please Player Input ");
        int playersCount =  s.nextInt();
        s.nextLine();
        ArrayList<Player> players = new ArrayList<>();
        for(int i=0; i<playersCount; i++) {
            players.add(new Player(s.nextLine()));
        }
        return players;
    }
}
