package battleship;

public class Game {
    private final String FOG_FLAG = "FOG";
    private final String ORGINAL_GRID_FLAG = "ORGINAL_GRID";

    Player player1;
    Player player2;
    boolean player1Turn;
    boolean gameOver = false;

    Game(Player player1, Player player2) {
        this.player1 = player1;
        this.player2 = player2;
        this.player1Turn = true;
    }

    public void startGame() {
        while(!gameOver) {
            if(player1Turn) {
                this.executeSteps(player1, player2);
                player1Turn = false;
            }

            else {
                this.executeSteps(player2, player1);
                player1Turn = true;
            }

            Player.clearConsole();
        }
    }

    private void executeSteps (Player currentplayer, Player otherPlayer) {
        currentplayer.grid.printMap(FOG_FLAG);
        System.out.println("---------------------");
        currentplayer.grid.printMap(ORGINAL_GRID_FLAG);
        System.out.println(currentplayer.getName() + ", it's your turn:");
        String coords = currentplayer.grid.enterValidCoords();
        boolean hit = otherPlayer.grid.takeShot(coords);
        currentplayer.grid.addShotToFoqMap(hit, coords);
        gameOver = otherPlayer.grid.isGameOver();
    }
}
