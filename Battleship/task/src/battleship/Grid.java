package battleship;

import java.util.Scanner;

public class Grid {
    String[][] grid = new String[10][10];
    String[][] fogGrid = new String[10][10];
    private final String MAP_COL = "ABCDEFGHIJ";
    private final String FOG_FLAG = "FOG";
    private final String ORGINAL_GRID_FLAG = "ORGINAL_GRID";
    Scanner scanner = new Scanner(System.in);

    public Grid() {
        this.generateMap();
    }
    private void generateMap() {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 10; row++) {
                grid[col][row] = "~";
                fogGrid[col][row] = "~";
            }
        }
    }

    public void printMap(String flagMap) {
        String[][] currentGrid = flagMap.equals(FOG_FLAG) ? fogGrid : grid;
        System.out.println("\n  1 2 3 4 5 6 7 8 9 10");
        for (int col = 0; col < 10; col++) {
            System.out.print(MAP_COL.charAt(col) + " "); // print letter of column
            for (int row = 0; row < 10; row++) {
                System.out.print(currentGrid[col][row] + " ");
            }

            System.out.println();
        }

    }

    public boolean validateCoords(Ship ship, String startCoords, String endCoords) {
        boolean horizontal = startCoords.charAt(0) == endCoords.charAt(0);
        boolean vertical = Integer.parseInt(endCoords.substring(1)) == Integer.parseInt(startCoords.substring(1));
        if((!vertical && !horizontal) || (!horizontal && !validateShipLocation(ship, startCoords, endCoords))) {
            System.out.println("Error! Wrong ship location! Try again:");
            return false;
        }
        if(horizontal && !isLengthShipSameAsCoordinates(ship, startCoords, endCoords)) {
            System.out.println("Error! Wrong length of the " + ship.getName() + "! Try again:");
            return false;
        }

        if(isShipToClose(startCoords, endCoords)) {
            System.out.println("Error! You placed it too close to another one. Try again:");
            return false;
        }

        return true;
    }

    private boolean isShipToClose(String startCoords, String endCoords) {
        int startCol = MAP_COL.indexOf(startCoords.charAt(0));
        int endCol = MAP_COL.indexOf(endCoords.charAt(0));

        int smallestCol = Math.min(startCol, endCol) == 0 ? Math.min(startCol, endCol) : Math.min(startCol, endCol) - 1;
        int largestCol = Math.max(endCol, startCol) == 9 ? Math.max(endCol, startCol) : Math.max(endCol, startCol) + 1;

        int startRow = Integer.parseInt(startCoords.substring(1));
        int endRow = Integer.parseInt(endCoords.substring(1));

        int smallestRow = Math.min(startRow, endRow) == 0 ? Math.min(startRow, endRow) : Math.min(startRow, endRow) - 1;
        int largestRow = Math.max(startRow, endRow) == 10 ? Math.max(startRow, endRow) : Math.max(startRow, endRow) + 1;

        for(int col = smallestCol; col <= largestCol; col++) {
            for (int row = smallestRow; row <= (largestRow - 1); row++) {
                if (grid[col][row].equals("O")) return true;
            }
        }

        return false;
    }

    private boolean validateShipLocation(Ship ship, String startCoords, String endCoords) {
        int startCol = MAP_COL.indexOf(startCoords.charAt(0));
        int endCol = MAP_COL.indexOf(endCoords.charAt(0));

        return ship.getCells() == (Math.abs(endCol - startCol) + 1);
    }

    private boolean isLengthShipSameAsCoordinates(Ship ship, String startCoords, String endCoords) {
        int startCoord = Integer.parseInt(startCoords.substring(1));
        int endCoord = Integer.parseInt(endCoords.substring(1));

        return (Math.abs(endCoord - startCoord) + 1) == ship.getCells();
        }


    public void addShipToMap(String startCoords, String endCoords) {
        int startCol = MAP_COL.indexOf(startCoords.charAt(0));
        int endCol = MAP_COL.indexOf(endCoords.charAt(0));

        int smallestCol = Math.min(startCol, endCol);
        int largestCol = Math.max(endCol, startCol);

        int startRow = Integer.parseInt(startCoords.substring(1));
        int endRow = Integer.parseInt(endCoords.substring(1));

        int smallestRow = Math.min(startRow, endRow) - 1;
        int largestRow = Math.max(startRow, endRow) - 1;

        for(int col = smallestCol; col <= largestCol; col++) {
            for (int row = smallestRow; row <= largestRow; row++) {
                grid[col][row] = "O";
            }
        }
    }

    public boolean isGameOver() {
        return this.areAllShipsSank();
    }

    public void addShotToFoqMap(boolean hit, String coords) {
        String value = hit ? "X" : "M";
        int col = MAP_COL.indexOf(coords.charAt(0));
        int row = Integer.parseInt(coords.substring(1)) - 1;

        fogGrid[col][row] = value;
    }

    public String enterValidCoords() {
        String coords = scanner.nextLine();

        if(!areCoordValid(coords)) {
            while(!areCoordValid(coords)) {
                System.out.println("Error! You entered the wrong coordinates! Try again:");
                coords = scanner.nextLine();
            }
        }

        return coords;
    }

    public boolean takeShot(String coords) {
        int col = MAP_COL.indexOf(coords.charAt(0));
        int row = Integer.parseInt(coords.substring(1)) - 1;
        if (grid[col][row].equals("O") || grid[col][row].equals("X")) {
            grid[col][row] = "X";

            String message = isShipSank(col, row) ? "You sank a ship! Specify a new target:" : "You hit a ship! Try again:";
            if(this.isGameOver()) {
                message = "You sank the last ship. You won. Congratulations!";
            }

            System.out.println(message);

            return true;
        }

        System.out.println("You missed!");
        return false;

    }

    private boolean areCoordValid(String coords) {
        return MAP_COL.indexOf(coords.charAt(0)) != -1 &&
                Integer.parseInt(coords.substring(1)) <= 10 &&
                Integer.parseInt(coords.substring(1)) >= 0;
    }

    private boolean areAllShipsSank() {
        for (int col = 0; col < 10; col++) {
            for (int row = 0; row < 10; row++) {
                if(grid[col][row].equals("O")) return false;
            }
        }

        return true;
    }

    private boolean isShipSank(int col, int row) {
        int smallestCol = col > 0 ? col - 1 : col;
        int largestCol = col < 9 ? col + 1 : col;

        int smallestRow = row > 0 ? row - 1 : row;
        int largestRow = row < 9 ? row + 1 : row;
        for(int i = smallestCol; i <= largestCol; i++) {
            for (int j = smallestRow; j <= (largestRow - 1); j++) {
                if (grid[i][j].equals("O")) return false;
            }
        }

        return true;
    }

}

