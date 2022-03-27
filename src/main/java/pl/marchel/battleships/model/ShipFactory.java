package pl.marchel.battleships.model;

public class ShipFactory {

    private static final Ship[] predefinedShips ={
            new Ship("Speedboat", 1),
            new Ship("Destroyer", 2),
            new Ship("Cruiser", 3),
            new Ship("Battleship", 4),
            new Ship("Aircraft carrier", 5)
    };

    public static Ship getBySize(int size) {
        Ship requestedShip = null;
        for (Ship ship : predefinedShips) {
            if (ship.getSize() == size) {
                requestedShip = ship;
                break;
            }
        }
        return requestedShip != null ? new Ship(requestedShip.getName(), requestedShip.getSize()) : null;
    }

    public static Ship getByName(String name) {
        Ship requestedShip = null;
        for (Ship ship : predefinedShips) {
            if (ship.getName().equals(name)) {
                requestedShip = ship;
                break;
            }
        }
        return requestedShip != null ? new Ship(requestedShip.getName(), requestedShip.getSize()) : null;
    }
}
