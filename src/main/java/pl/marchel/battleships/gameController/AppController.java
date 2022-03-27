package pl.marchel.battleships.gameController;

import pl.marchel.battleships.App;

public class AppController implements GameController {

    @Override
    public void initialize() {
        App.INPUT.setCurrentListener(this);
        App.VIEW.displayInitialView();
    }

    @Override
    public void handleInput(String input) {
        try {
            int selectedGameIndex = Integer.parseInt(input) - 1;
            if (selectedGameIndex == App.AVAILABLE_GAMES.length) {
                App.INPUT.setKeepScanning(false);
                App.VIEW.displayGoodbyeMessage();
            } else {
                startNewGame(App.AVAILABLE_GAMES[selectedGameIndex]);
            }
        } catch (Exception e) {
            App.VIEW.displayErrorMessage("Wrong input! Type number from list.");
        }
    }

    @Override
    public String getName() {
        return "";
    }

    private void startNewGame(GameController gameController) {
        gameController.initialize();
        App.INPUT.setCurrentListener(gameController);
    }
}
