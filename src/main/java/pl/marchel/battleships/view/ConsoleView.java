package pl.marchel.battleships.view;

import pl.marchel.battleships.App;
import pl.marchel.battleships.model.Battlefield;
import pl.marchel.battleships.model.BattlefieldCell;
import pl.marchel.battleships.model.Ship;

public class ConsoleView {

    private final Battlefield[] battlefields = new Battlefield[2];
    private final String COLUMN_LABELS = "   A B C D E F G H I J";

    public void setVisibleBattlefield(Battlefield battlefield) {
        battlefields[0] = battlefield;
    }

    public void setHiddenBattlefield(Battlefield battlefield) {
        battlefields[1] = battlefield;
    }

    public void displayInitialView() {
        System.out.println("Choose game:");
        for (int i = 0; i < App.AVAILABLE_GAMES.length; i++) {
            System.out.println((i + 1) + ". " + App.AVAILABLE_GAMES[i].getName());
        }
        System.out.println((App.AVAILABLE_GAMES.length + 1) + ". Exit program");
    }

    public void displayGoodbyeMessage() {
        System.out.println("See you around!");
    }

    public void displayErrorMessage(String message) {
        System.out.println(message);
    }

    public void displayShipHit(BattlefieldCell destroyedCell) {
        System.out.println(getShooterName(destroyedCell) + " hit a ship!");
    }

    public void displayShipDestroyed(BattlefieldCell destroyedCell) {
        System.out.println(getShooterName(destroyedCell) + " sunk " + destroyedCell.getParentShip().getName() + "!");
    }

    public void displayMissed(BattlefieldCell cell) {
        System.out.println(getShooterName(cell) + " missed");
    }

    public void displayShipDeployment(Ship ship) {
        displaySingleBattlefield(battlefields[0], false);
        System.out.println("Enter location of " + ship.getName() + " (" + ship.getSize() + ")");
    }

    public void displayAiming() {
        displayAiming(null);
    }

    public void displayAiming(Integer shotsRemaining) {
        displayBattlefields();
        displayAimingMessage(shotsRemaining);
    }

    public void displayGameFinished() {
        System.out.println("Game over");
        System.out.println("All targets destroyed");
        System.out.println();
    }

    public void displaySelectOption(String message, String[] selectOptions) {
        String options = selectOptions[0];
        for (int i = 1; i < selectOptions.length; i++) {
            options += "/" + selectOptions[i];
        }
        System.out.println(message + " (" + options + ")");
    }

    private void displayAimingMessage(Integer shotsRemaining) {
        if (shotsRemaining != null) {
            System.out.println(shotsRemaining + " shots remaining");
        }
        System.out.println("Select target:");
    }

    private String getShooterName(BattlefieldCell destroyedCell) {
        if (battlefields[0] == null || battlefields[1] == null) {
            return destroyedCell.getParentBattlefield().getOwner();
        }
        if (battlefields[0].equals(destroyedCell.getParentBattlefield())) {
            return battlefields[1].getOwner();
        }
        return battlefields[0].getOwner();
    }

    private void displayBattlefields() {
        String[] leftRows = getBattlefieldRows(battlefields[0], false);
        String[] rightRows = getBattlefieldRows(battlefields[1], true);
        StringBuilder output = new StringBuilder();
        output.append("\n" + COLUMN_LABELS + "  " + COLUMN_LABELS + " \n");
        for (int i = 0; i < leftRows.length; i++) {
            appendRow(output, i + 1, leftRows[i]);
            output.append("  ");
            appendRow(output, i + 1, rightRows[i]);
            output.append("\n");
        }
        System.out.println(output);
    }

    private void displaySingleBattlefield(Battlefield battlefield, boolean hideShips) {
        String[] rows = getBattlefieldRows(battlefield, hideShips);
        StringBuilder output = new StringBuilder();
        output.append("\n" + COLUMN_LABELS + "\n");
        int rowNumber = 1;
        for (String row : rows) {
            appendRow(output, rowNumber, row);
            rowNumber++;
            output.append("\n");
        }
        System.out.println(output);
    }

    private void appendRow(StringBuilder stringBuilder, int rowNumber, String row) {
        stringBuilder.append(rowNumber);
        if (rowNumber < 10) {
            stringBuilder.append(" ");
        }
        stringBuilder.append(row);
    }

    private String[] getBattlefieldRows(Battlefield battlefield, boolean hideShips) {
        BattlefieldCell[][] cells = battlefield.getCells();
        String[] rows = new String[cells[0].length];
        for (int i = 0; i < cells.length; i++) {
            for (int j = 0; j < cells[i].length; j++) {
                if (rows[j] == null) {
                    rows[j] = getCellSymbol(cells[i][j], hideShips);
                } else {
                    rows[j] += getCellSymbol(cells[i][j], hideShips);
                }
            }
        }
        return rows;
    }

    private String getCellSymbol(BattlefieldCell cell, boolean hideShips) {
        if (cell.isDestroyed()) {
            if (cell.isPartOfShip()) {
                return cell.getParentShip().isDestroyed() ? " #" : " X";
            } else {
                return " O";
            }
        } else if (!hideShips && cell.isPartOfShip()) {
            return " S";
        }
        return " ~";
    }
}
