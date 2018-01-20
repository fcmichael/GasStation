package pl.michalkruk.psy.model;

import pl.michalkruk.psy.SimProperties;

public class GasStation {

    private static final GasStation instance = new GasStation();
    private final SimProperties properties = SimProperties.getInstance();
    private static Emplacement emplacementLPG;
    private static Emplacement emplacementON;
    private static Emplacement emplacementPetrol;
    private static Cash cash;

    private GasStation() {
        emplacementLPG = new Emplacement(
                Integer.parseInt(properties.get("numOfDistributorsLPG")), Integer.parseInt(properties.get("queueSizeOfDistributorsON")));
        emplacementON = new Emplacement(
                Integer.parseInt(properties.get("numOfDistributorsON")), Integer.parseInt(properties.get("queueSizeOfDistributorsLPG")));
        emplacementPetrol = new Emplacement(
                Integer.parseInt(properties.get("numOfDistributorsPetrol")), Integer.parseInt(properties.get("queueSizeOfDistributorsPetrol")));
        cash = new Cash(Integer.parseInt(properties.get("numOfCashes")));
    }

    public static GasStation getInstance() {
        return instance;
    }

    public Emplacement getEmplacementBasedOnGasType(GasType gasType) {
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
}
