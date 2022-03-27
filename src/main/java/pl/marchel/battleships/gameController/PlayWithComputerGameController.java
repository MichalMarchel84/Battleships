package pl.marchel.battleships.gameController;

import pl.marchel.battleships.App;
import pl.marchel.battleships.gameController.dto.ShotResult;
import pl.marchel.battleships.gameController.gameStage.DeployStage;
import pl.marchel.battleships.gameController.gameStage.FiringStage;
import pl.marchel.battleships.model.Battlefield;
import pl.marchel.battleships.model.Ship;
import pl.marchel.battleships.model.ShipFactory;

import java.util.ArrayList;
import java.util.List;

public class PlayWithComputerGameController implements GameController{

    private final int shotsPerTurn = 5;
    private final Integer shipAmountLimit;
    private final String name;

    private Battlefield playerBattlefield;
    private Battlefield computerBattlefield;
    private Ship[] playerShips;
    private Ship[] computerShips;
    private int currentStage;
    private int shotsRemaining;

    public PlayWithComputerGameController(String name, Integer shipAmountLimit) {
        this.name = name;
        this.shipAmountLimit = shipAmountLimit;
    }

    @Override
    public void initialize() {
        currentStage = 0;
        shotsRemaining = shotsPerTurn;
        playerBattlefield = new Battlefield("You");
        computerBattlefield = new Battlefield("Computer");
        playerShips = getShips();
        computerShips = getShips();
        DeployStage.deployShipsRandom(computerBattlefield, computerShips);
        App.VIEW.setVisibleBattlefield(playerBattlefield);
        App.VIEW.setHiddenBattlefield(computerBattlefield);
        DeployStage.displaySelectDeploymentMethod();
    }

    @Override
    public void handleInput(String input) {
        switch (currentStage) {
            case 0:
                currentStage += DeployStage.handleDeploymentMethodSelection(input, playerBattlefield, playerShips, shotsPerTurn);
                break;
            case 1:
                currentStage += DeployStage.handleDeploy(input, playerBattlefield, playerShips, shotsPerTurn);
                break;
            case 2:
                ShotResult result = FiringStage.handleFire(input, computerBattlefield, computerShips, shotsRemaining - 1);
                if (result.isTargetValid()) {
                    shotsRemaining--;
                }
                if (shotsRemaining < 1 && !result.isGameFinished()) {
                    shotsRemaining = shotsPerTurn;
                    executeComputerTurn();
                }
                break;
        }
    }

    @Override
    public String getName() {
        return name;
    }

    private Ship[] getShips() {
        List<Ship> ships = new ArrayList<>();
        int shipsCount = 1;
        while (shipsCount < 5 && isBelowShipLimit(ships.size())) {
            for (int i = 0; i < shipsCount; i++) {
                ships.add(ShipFactory.getBySize(5 - shipsCount));
                if (!isBelowShipLimit(ships.size())) {
                    break;
                }
            }
            shipsCount++;
        }
        return ships.toArray(new Ship[0]);
    }

    private boolean isBelowShipLimit(int value) {
        if (shipAmountLimit == null) {
            return true;
        }
        return shipAmountLimit > value;
    }

    private void executeComputerTurn() {
        ShotResult result = new ShotResult();
        while (shotsRemaining > 0) {
            result = FiringStage.fireRandom(playerBattlefield, playerShips);
            if (result.isGameFinished()) {
                break;
            }
            shotsRemaining--;
        }
        if (!result.isGameFinished()) {
            shotsRemaining = shotsPerTurn;
            App.VIEW.displayAiming(shotsRemaining);
        }
    }
}
