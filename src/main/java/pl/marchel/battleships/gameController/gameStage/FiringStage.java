package pl.marchel.battleships.gameController.gameStage;

import pl.marchel.battleships.App;
import pl.marchel.battleships.gameController.dto.ShotResult;
import pl.marchel.battleships.model.Battlefield;
import pl.marchel.battleships.model.BattlefieldCell;
import pl.marchel.battleships.model.Ship;

public class FiringStage {

    public static void handleFire(String input, Battlefield battlefield, Ship[] ships) {
        handleFire(input, battlefield, ships, null);
    }

    public static ShotResult handleFire(String input, Battlefield battlefield, Ship[] ships, Integer shotsRemaining) {
        try {
            ShotResult result = fireManual(input, battlefield, ships);
            if (!result.isGameFinished() && (shotsRemaining == null || shotsRemaining > 0)) {
                App.VIEW.displayAiming(shotsRemaining);
            }
            return result;
        } catch (Exception e) {
            App.VIEW.displayErrorMessage(e.getMessage());
            return new ShotResult();
        }
    }

    public static ShotResult fireRandom(Battlefield battlefield, Ship[] ships) {
        while (true) {
            int horizontalIndex = (int) (Math.random() * (battlefield.getSize() - 1));
            int verticalIndex = (int) (Math.random() * (battlefield.getSize() - 1));
            BattlefieldCell target = battlefield.getCells()[horizontalIndex][verticalIndex];
            if (!target.isDestroyed()) {
                return doFire(target, ships);
            }
        }
    }

    private static ShotResult fireManual(String input, Battlefield battlefield, Ship[] ships) throws Exception {
        int[] coordinate = Utils.coordinateToArrayFormat(input);
        BattlefieldCell target = battlefield.getCells()[coordinate[0]][coordinate[1]];
        if (target.isDestroyed()) {
            throw new Exception("Target at " + input + " already destroyed");
        }
        return doFire(target, ships);
    }

    private static ShotResult doFire(BattlefieldCell target, Ship[] ships) {
        target.setDestroyed();
        ShotResult result = new ShotResult();
        result.setTargetValid(true);
        if (target.isPartOfShip()) {
            if (target.getParentShip().isDestroyed()) {
                App.VIEW.displayShipDestroyed(target);
                if (isGameFinished(ships)) {
                    finishGame();
                    result.setGameFinished(true);
                }
            } else {
                App.VIEW.displayShipHit(target);
            }
        } else {
            App.VIEW.displayMissed(target);
        }
        return result;
    }

    private static boolean isGameFinished(Ship[] ships) {
        for (Ship ship : ships) {
            if (!ship.isDestroyed()) {
                return false;
            }
        }
        return true;
    }

    private static void finishGame() {
        App.VIEW.displayGameFinished();
        App.APP_CONTROLLER.initialize();
    }
}
