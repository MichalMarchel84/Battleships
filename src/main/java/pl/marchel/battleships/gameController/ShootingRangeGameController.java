package pl.marchel.battleships.gameController;

import pl.marchel.battleships.App;
import pl.marchel.battleships.gameController.gameStage.DeployStage;
import pl.marchel.battleships.gameController.gameStage.FiringStage;
import pl.marchel.battleships.model.Battlefield;
import pl.marchel.battleships.model.Ship;
import pl.marchel.battleships.model.ShipFactory;

public class ShootingRangeGameController implements GameController {

    private Battlefield battlefield;
    private Ship[] ships;
    private int currentStage = 0;

    @Override
    public void initialize() {
        battlefield = new Battlefield("You");
        App.VIEW.setVisibleBattlefield(battlefield);
        App.VIEW.setHiddenBattlefield(battlefield);
        setShips();
        DeployStage.displaySelectDeploymentMethod();
    }

    @Override
    public void handleInput(String input) {
        switch (currentStage) {
            case 0:
                currentStage += DeployStage.handleDeploymentMethodSelection(input, battlefield, ships);
                break;
            case 1:
                currentStage += DeployStage.handleDeploy(input, battlefield, ships);
                break;
            case 2:
                FiringStage.handleFire(input, battlefield, ships);
                break;
        }
    }

    @Override
    public String getName() {
        return "Shooting range";
    }

    private void setShips(){
        ships = new Ship[2];
        ships[0] = ShipFactory.getByName("Battleship");
        ships[1] = ShipFactory.getByName("Cruiser");
    }
}
