package pl.michalkruk.psy.model;

import pl.michalkruk.psy.SimProperties;

public class GasStation {

    public enum Service {
        WASH,
        FUEL,
        WASH_AND_FUEL
    }

    public enum GasType {
        LPG,
        ON,
        PETROL;

        @Override
        public String toString() {
            return this.name();
        }
    }

    private static final GasStation instance = new GasStation();
    private final SimProperties properties = SimProperties.getInstance();
    private Emplacement emplacementLPG;
    private Emplacement emplacementON;
    private Emplacement emplacementPetrol;
    private Cash cash;
    private CarWash carWash;

    private GasStation() {
        emplacementLPG = new Emplacement(
                Integer.parseInt(properties.get("numOfDistributorsLPG")), Integer.parseInt(properties.get("queueSizeOfDistributorsON")));
        emplacementON = new Emplacement(
                Integer.parseInt(properties.get("numOfDistributorsON")), Integer.parseInt(properties.get("queueSizeOfDistributorsLPG")));
        emplacementPetrol = new Emplacement(
                Integer.parseInt(properties.get("numOfDistributorsPetrol")), Integer.parseInt(properties.get("queueSizeOfDistributorsPetrol")));
        cash = new Cash(Integer.parseInt(properties.get("numOfCashes")));
        carWash = new CarWash();
    }

    public static GasStation getInstance() {
        return instance;
    }

    public Emplacement getEmplacementBasedOnGasType(GasStation.GasType gasType) {
        Emplacement emplacement = null;
        switch (gasType) {
            case LPG:
                emplacement = emplacementLPG;
                break;
            case ON:
                emplacement = emplacementON;
                break;
            case PETROL:
                emplacement = emplacementPetrol;
                break;
        }
        return emplacement;
    }

    public Cash getCash() {
        return cash;
    }

    public CarWash getCarWash() {
        return carWash;
    }
}
