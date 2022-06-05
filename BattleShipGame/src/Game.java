public class Game {
    Player playerA;
    Player playerB;
    int size;

    public Game() { }

    public void initGame(int n) {
        this.size = n;
        playerA = new Player(0, n/2, n);
        playerB = new Player(n/2 + 1, n, n);
    }

    public void addShip(String id, int size, int playerAx, int playerAy, int playerBx, int playerBy) {
        Ship sha = new Ship(id, size, playerAx, playerAy);
        Ship shb = new Ship(id, size, playerBx, playerBy);
        playerA.addShip(sha);
        playerB.addShip(shb);
    }

    public void viewBattleField() {
        for(int x=0; x<size; x++) {
            for(int y=0; y<size; y++) {
                if(playerA.areaXStart <= x && playerA.areaXEnd >= x) {
                    Ship ship = playerA.getShipAt(x, y);
                    if (ship!= null){
                        System.out.print("A-" + ship.id + " ");
                    }
                    else {
                        System.out.print("  --  ");
                    }
                }
                if(playerB.areaXStart <= x && playerB.areaXEnd >= x) {
                    Ship ship = playerB.getShipAt(x, y);
                    if (ship!= null) {
                        System.out.print("B-" + ship.id + " ");
                    } else {
                        System.out.print("  --  ");
                    }
                }
            }
            System.out.println();
        }
    }

    public void startGame() {
        int playerTurn = 1;
        int winner = -1; // -1 Represents no winner, 1 Represents A is a winner, 2 Represents B is a Winner

        while(winner == -1) {
            if(playerTurn == 1) {
                // Get random Coordinate of Player B Area
                Coordinate coordinate = playerB.getCoordinate();
                Ship destroyedShip = playerB.getMissileHitDamage(coordinate);
                viewBattleField();
                if(destroyedShip == null) {
                    // Borders of Grid are printed
                    System.out.println("Player A's turn: Missile fired at (" + coordinate.x + ", " + coordinate.y + "). Miss");
                } else {
                    // Borders of Grid are printed
                    System.out.println("Player A's turn: Missile fired at (" + coordinate.x + ", " + coordinate.y + "). Hit. PlayerB's ship with id" + destroyedShip.id + " destroyed");
                }


                if (playerB.allShipsDestroyed()) {
                   winner = 1;
                   break;
                }

                playerTurn = 2;
            } else {
                // Get random Coordinate of Player A Area
                Coordinate coordinate = playerA.getCoordinate();
                Ship destroyedShip = playerA.getMissileHitDamage(coordinate);
                viewBattleField();
                if(destroyedShip != null) {
                    // Borders of Grid are printed
                    System.out.println("Player A's turn: Missile fired at (" + coordinate.x + ", " + coordinate.y + "). Hit. PlayerB's ship with id" + destroyedShip.id + " destroyed");
                } else {
                    // Borders of Grid are printed
                    System.out.println("Player A's turn: Missile fired at (" + coordinate.x + ", " + coordinate.y + "). Miss");
                }

                if (playerA.allShipsDestroyed()) {
                    winner = 2;
                    break;
                }

                playerTurn = 1;
            }
        }
    }
}
