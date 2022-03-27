package pl.marchel.battleships.input;

import pl.marchel.battleships.gameController.GameController;

import java.util.Scanner;

public class ConsoleInput {
    private GameController currentGame;
    private boolean keepScanning = true;

    public void setCurrentListener(GameController game) {
        this.currentGame = game;
    }

    public void setKeepScanning(boolean keepScanning) {
        this.keepScanning = keepScanning;
    }

    public void scanForInput() {
        Scanner scanner = new Scanner(System.in);
        while (keepScanning) {
            if(scanner.hasNextLine()){
                currentGame.handleInput(scanner.nextLine());
            }
        }
        scanner.close();
    }
}
