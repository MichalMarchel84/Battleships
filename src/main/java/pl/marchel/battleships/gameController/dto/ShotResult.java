package pl.marchel.battleships.gameController.dto;

public class ShotResult {

    private boolean targetValid;
    private boolean gameFinished;

    public void setTargetValid(boolean targetValid) {
        this.targetValid = targetValid;
    }

    public void setGameFinished(boolean gameFinished) {
        this.gameFinished = gameFinished;
    }

    public boolean isTargetValid() {
        return targetValid;
    }

    public boolean isGameFinished() {
        return gameFinished;
    }
}
