package pl.marchel.battleships.model;

public class BattlefieldCell {
    private final Battlefield parentBattlefield;
    private Ship parentShip;
    private boolean isCloseToShip;
    private boolean isDestroyed;

    public BattlefieldCell(Battlefield parentBattlefield) {
        this.parentBattlefield = parentBattlefield;
    }

    public Battlefield getParentBattlefield() {
        return parentBattlefield;
    }

    public Ship getParentShip() {
        return parentShip;
    }

    public void setParentShip(Ship parentShip) {
        this.parentShip = parentShip;
    }

    public boolean isCloseToShip() {
        return isCloseToShip;
    }

    public void setCloseToShip() {
        isCloseToShip = true;
    }

    public boolean isDestroyed() {
        return isDestroyed;
    }

    public boolean isPartOfShip() {
        return parentShip != null;
    }

    public void setDestroyed() {
        isDestroyed = true;
    }
}
