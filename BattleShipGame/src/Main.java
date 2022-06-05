public class Main {
    public static void main(String[] args) {
        Game game = new Game();
        game.initGame(6);
        game.addShip("SH1", 2, 1, 5, 4, 4);
        game.addShip("SH2", 2, 2, 1, 5, 1);
        game.startGame();
    }
}
