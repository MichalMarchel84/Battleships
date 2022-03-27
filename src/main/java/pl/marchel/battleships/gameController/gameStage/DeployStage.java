package pl.marchel.battleships.gameController.gameStage;

import pl.marchel.battleships.App;
import pl.marchel.battleships.model.Battlefield;
import pl.marchel.battleships.model.Ship;

public class DeployStage{

    public static final String[] DEPLOYMENT_METHOD_OPTIONS = {"yes", "no"};

    public static void displaySelectDeploymentMethod() {
        App.VIEW.displaySelectOption("Do you like to deploy ships manually?", DEPLOYMENT_METHOD_OPTIONS);
    }

    public static int handleDeploymentMethodSelection(String input, Battlefield battlefield, Ship[] ships) {
        return handleDeploymentMethodSelection(input, battlefield, ships, null);
    }

    public static int handleDeploymentMethodSelection(String input, Battlefield battlefield, Ship[] ships, Integer shotsPerTurn) {
        if (input.equals(DEPLOYMENT_METHOD_OPTIONS[0])) {
            displayDeployInfo(ships);
            return 1;
        } else if (input.equals(DEPLOYMENT_METHOD_OPTIONS[1])) {
            deployShipsRandom(battlefield, ships);
            App.VIEW.displayAiming(shotsPerTurn);
            return 2;
        } else {
            App.VIEW.displayErrorMessage("Wrong input: type " + DEPLOYMENT_METHOD_OPTIONS[0] + " or " + DEPLOYMENT_METHOD_OPTIONS[1]);
            return 0;
        }
    }

    public static void deployShipsRandom(Battlefield battlefield, Ship[] ships) {
        while (true) {
            Ship shipToDeploy = getShipToDeploy(ships);
            if (shipToDeploy == null) {
                break;
            }
            DeployStage.deployToRandomLocation(battlefield, shipToDeploy);
        }
    }

    public static int handleDeploy(String input, Battlefield battlefield, Ship[] ships){
        return handleDeploy(input, battlefield, ships, null);
    }

    public static int handleDeploy(String input, Battlefield battlefield, Ship[] ships, Integer shotsPerTurn) {
        try {
            Ship shipToDeploy = getShipToDeploy(ships);
            deployShip(input, battlefield, shipToDeploy);
            if (getShipToDeploy(ships) == null) {
                App.VIEW.displayAiming(shotsPerTurn);
                return 1;
            } else {
                displayDeployInfo(ships);
                return 0;
            }
        } catch (Exception e) {
            App.VIEW.displayErrorMessage(e.getMessage());
            return 0;
        }
    }

    private static void deployShip(String input, Battlefield battlefield, Ship ship) throws Exception{
        int[][] coordinates = parseInput(input);
        String deploymentError = battlefield.deployShip(ship, coordinates[0], coordinates[1]);
        if (deploymentError != null) {
            throw new Exception(deploymentError);
        }
    }

    private static void deployToRandomLocation(Battlefield battlefield, Ship ship) {
        boolean isHorizontal = Math.random() <= 0.5;
        boolean isNotDeployed = true;
        int maxIndex = battlefield.getSize() - 1;

        while (isNotDeployed) {
            int[] bowLocation = {(int) (Math.random() * maxIndex), (int) (Math.random() * maxIndex)};
            int[] aftLocation = new int[2];
            if (isHorizontal) {
                aftLocation[0] = bowLocation[0] + ship.getSize() - 1;
                aftLocation[1] = bowLocation[1];
            } else {
                aftLocation[0] = bowLocation[0];
                aftLocation[1] = bowLocation[1] + ship.getSize() - 1;
            }
            if (aftLocation[0] > maxIndex || aftLocation[1] > maxIndex) {
                continue;
            }
            String deploymentError = battlefield.deployShip(ship, bowLocation, aftLocation);
            if (deploymentError == null) {
                isNotDeployed = false;
            }
        }
    }

    private static Ship getShipToDeploy(Ship[] ships) {
        for (Ship ship : ships) {
            if (!ship.isDeployed()) {
                return ship;
            }
        }
        return null;
    }

    private static void displayDeployInfo(Ship[] ships) {
        Ship shipToDeploy = getShipToDeploy(ships);
        App.VIEW.displayShipDeployment(shipToDeploy);
    }

    private static int[][] parseInput(String input) throws Exception{
        String[] params = input.split(" ");
        if (params.length != 2) {
            throw new Exception("Wrong number of parameters - expected 2, got " + params.length);
        }
        int[][] result = new int[2][2];
        for (int i = 0; i < params.length; i++) {
            result[i] = Utils.coordinateToArrayFormat(params[i]);
        }
        if (result[0][0] != result[1][0] && result[0][1] != result[1][1]) {
            throw new Exception("Ship must be either horizontal or vertical");
        }
        return result;
    }
}
