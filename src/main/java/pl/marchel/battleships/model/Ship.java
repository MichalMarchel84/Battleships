package pl.marchel.battleships.model;

public class Ship {
    private BattlefieldCell[] cells;
    private final String name;
    private final int size;

    public Ship(String name, int size) {
        this.name = name;
        this.size = size;
    }

    public boolean isDeployed() {
        return cells != null;
    }

    public String getName() {
        return name;
    }

    public int getSize() {
        return size;
    }

    public void deploy(BattlefieldCell[] cells) {
        this.cells = cells;
        for (BattlefieldCell cell : cells) {
            cell.setParentShip(this);
        }
    }

    public boolean isDestroyed() {
        for (BattlefieldCell cell : cells) {
            if (!cell.isDestroyed()) {
                return false;
            }
        }
        return true;
    }
}
