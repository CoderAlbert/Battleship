package battleship;

import java.io.IOException;
import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws IOException {
        Scanner scanner = new Scanner(System.in);
        Player p1 = new Player("Player 1");
        System.out.println( p1.getName() + ", place your ships on the game field");
        p1.addShipsToGrid();

        Player.clearConsole();
        Player p2 = new Player("Player 2");
        System.out.println(p2.getName() + ", place your ships on the game field");

        p2.addShipsToGrid();
        Player.clearConsole();

        Game game = new Game(p1, p2);
        System.out.println("The game starts!");

        game.startGame();
    }
}