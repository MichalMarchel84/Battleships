package pl.marchel.battleships.gameController;

public interface GameController {
    void initialize();
    void handleInput(String input);
    String getName();
}
