package battleship;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class Player {
    private final String AIRCRAFT_CARRIER = "Aircraft Carrier";
    private final String BATTLESHIP = "Battleship";
    private final String SUBMARINE = "Submarine";
    private final String CRUISER = "Cruiser";
    private final String DESTROYER = "Destroyer";
    private final String FOG_FLAG = "FOG";
    private final String ORGINAL_GRID_FLAG = "ORGINAL_GRID";
    private List<Ship> ships = new ArrayList<Ship>();
    public Grid grid;
    private Scanner scanner = new Scanner(System.in);
    private String name;

    public Player(String name) {
        this.name = name;
        grid = new Grid();
        ships.add(new Ship(AIRCRAFT_CARRIER, 5));
        ships.add(new Ship(BATTLESHIP, 4));
        ships.add(new Ship(SUBMARINE, 3));
        ships.add(new Ship(CRUISER, 3));
        ships.add(new Ship(DESTROYER, 2));
    }

    public String getName() {
        return this.name;
    }
    //add all ships to map and check for error

    public void addShipsToGrid() {
        for (Ship ship : ships) {
            String startCoords;
            String endCoords;
            grid.printMap(ORGINAL_GRID_FLAG);
            do {
                System.out.println("\nEnter the coordinates of the " + ship.getName() + " (" + ship.getCells() + " cells):\n");
                String userInput = scanner.nextLine();
                startCoords = userInput.split(" ")[0];
                endCoords = userInput.split(" ")[1];

            }
            while (!grid.validateCoords(ship, startCoords, endCoords));

            grid.addShipToMap(startCoords, endCoords);
        }

        grid.printMap(ORGINAL_GRID_FLAG);
    }


    public static void clearConsole() {
        Scanner scanner1 = new Scanner(System.in);
        System.out.println("Press Enter and pass the move to another player");
        scanner1.nextLine();
        //System.out.println(System.lineSeparator().repeat(10));
    }
}