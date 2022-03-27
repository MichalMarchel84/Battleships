package pl.marchel.battleships.model;

public class Battlefield {

    private final String owner;
    private final BattlefieldCell[][] cells;

    public Battlefield(String owner) {
        this.owner = owner;
        this.cells = initializeCells();
    }

    public BattlefieldCell[][] getCells() {
        return cells;
    }

    public String getOwner() {
        return owner;
    }

    public int getSize() {
        return cells[0].length;
    }

    public String deployShip(Ship ship, int[] bowLocation, int[] aftLocation) {
        int[] shipTopLeftCorner = getTopLeftCorner(bowLocation, aftLocation, 0);
        int[] shipBottomRightCorner = getBottomRightCorner(bowLocation, aftLocation, 0);
        BattlefieldCell[] shipCells = getCellsInArea(shipTopLeftCorner, shipBottomRightCorner);
        String validationError = validateShipDeployment(shipCells, ship);
        if (validationError != null) {
            return validationError;
        }
        int[] areaAroundShipTopLeftCorner = getTopLeftCorner(bowLocation, aftLocation, 1);
        int[] areaAroundShipBottomRightCorner = getBottomRightCorner(bowLocation, aftLocation, 1);
        BattlefieldCell[] cellsCloseToShip = getCellsInArea(areaAroundShipTopLeftCorner, areaAroundShipBottomRightCorner);
        for (BattlefieldCell cell: cellsCloseToShip) {
            cell.setCloseToShip();
        }
        ship.deploy(shipCells);
        return null;
    }

    private BattlefieldCell[][] initializeCells() {
        BattlefieldCell[][] cells = new BattlefieldCell[10][10];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                cells[i][j] = new BattlefieldCell(this);
            }
        }
        return cells;
    }

    private BattlefieldCell[] getCellsInArea(int[] topLeftCorner, int[] bottomRightCorner) {
        adjustToBattlefieldSize(topLeftCorner);
        adjustToBattlefieldSize(bottomRightCorner);
        int horizontalCellsCount = bottomRightCorner[0] - topLeftCorner[0] + 1;
        int verticalCellsCount = bottomRightCorner[1] - topLeftCorner[1] + 1;
        BattlefieldCell[] cells = new BattlefieldCell[horizontalCellsCount * verticalCellsCount];
        for (int i = 0; i < horizontalCellsCount; i++) {
            for (int j = 0; j < verticalCellsCount; j++) {
                cells[(i * verticalCellsCount) + j] = this.cells[i + topLeftCorner[0]][j + topLeftCorner[1]];
            }
        }
        return cells;
    }

    private void adjustToBattlefieldSize(int[] point) {
        point[0] = adjustToBattlefieldSize(point[0]);
        point[1] = adjustToBattlefieldSize(point[1]);
    }

    private int adjustToBattlefieldSize(int index) {
        if (index < 0) {
            return 0;
        } else if (index >= cells.length) {
            return cells.length - 1;
        }
        return index;
    }

    private int[] getTopLeftCorner(int[] bowLocation, int[] aftLocation, int offset) {
        int leftMostIndex = Math.min(bowLocation[0], aftLocation[0]);
        int topMostIndex = Math.min(bowLocation[1], aftLocation[1]);
        return new int[]{leftMostIndex - offset, topMostIndex - offset};
    }

    private int[] getBottomRightCorner(int[] bowLocation, int[] aftLocation, int offset) {
        int leftMostIndex = Math.max(bowLocation[0], aftLocation[0]);
        int topMostIndex = Math.max(bowLocation[1], aftLocation[1]);
        return new int[]{leftMostIndex + offset, topMostIndex + offset};
    }

    private String validateShipDeployment(BattlefieldCell[] cellsToDeploy, Ship shipToDeploy) {
        if(cellsToDeploy.length != shipToDeploy.getSize()) {
            return "Wrong ship size - expected " + shipToDeploy.getSize() + ", got " + cellsToDeploy.length;
        }
        for(BattlefieldCell cell : cellsToDeploy) {
            if (cell.isPartOfShip()) {
                return "Can't deploy here - " + cell.getParentShip().getName() + " is on the way";
            }
            if (cell.isCloseToShip()) {
                return "Can't deploy here - too close to another ship";
            }
        }
        return null;
    }
}
