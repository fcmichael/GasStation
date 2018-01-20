package pl.michalkruk.psy.model;

public enum GasType {
    LPG,
    ON,
    PETROL;

    @Override
    public String toString() {
        return this.name();
    }
}
