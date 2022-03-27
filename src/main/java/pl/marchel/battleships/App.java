package pl.marchel.battleships;

import pl.marchel.battleships.gameController.AppController;
import pl.marchel.battleships.gameController.GameController;
import pl.marchel.battleships.gameController.PlayWithComputerGameController;
import pl.marchel.battleships.gameController.ShootingRangeGameController;
import pl.marchel.battleships.input.ConsoleInput;
import pl.marchel.battleships.view.ConsoleView;

public class App {
    public static final ConsoleInput INPUT = new ConsoleInput();
    public static final ConsoleView VIEW = new ConsoleView();
    public static final AppController APP_CONTROLLER = new AppController();
    public static final GameController[] AVAILABLE_GAMES = {
            new ShootingRangeGameController(),
            new PlayWithComputerGameController("Play with computer (quick game)", 3),
            new PlayWithComputerGameController("Play with computer (full game)", null)
    };

    public static void main(String[] args) {
        APP_CONTROLLER.initialize();
        INPUT.scanForInput();
    }
}
