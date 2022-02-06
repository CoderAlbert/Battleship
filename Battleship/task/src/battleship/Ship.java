package battleship;

public class Ship {
    private int cells;
    private int lifePoints;
    private String name;

    public Ship(String name, int cells) {
        this.cells = cells;
        this.lifePoints = cells;
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getLifePoints() {
        return lifePoints;
    }

    public void setLifePoints(int lifePoints) {
        this.lifePoints = lifePoints;
    }

    public int getCells() {
        return cells;
    }

    public void setCells(int cells) {
        this.cells = cells;
    }
}
