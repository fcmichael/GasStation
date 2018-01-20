package pl.michalkruk.psy.model;

import lombok.Getter;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;

public class CarFactory {

    @Getter
    private static int id = 0;

    public static Car generateCar(double arrivingAtStationTime) {
        id++;
        GasStationService service = randomizeService();
        boolean fuelWill = false;
        boolean washWill = false;

        switch (service) {
            case FUEL:
                fuelWill = true;
                washWill = false;
                break;
            case WASH:
                fuelWill = false;
                washWill = true;
                break;
            case WASH_AND_FUEL:
                fuelWill = true;
                washWill = true;
                break;
        }

        if(fuelWill){
            return new Car(id, true, washWill, randomizeFuel(), arrivingAtStationTime);
        } else {
            return new Car(id, false, true, null, arrivingAtStationTime);
        }
    }

    private static GasType randomizeFuel() {
        double x = SimGeneratorFactory.get(SimProperties.getInstance().get("gasChooseDistribution"));
        if (x <= 0.33) {
            return GasType.PETROL;
        } else if (x <= 0.66) {
            return GasType.LPG;
        } else {
            return GasType.ON;
        }
    }

    private static GasStationService randomizeService() {
        double x = SimGeneratorFactory.get(SimProperties.getInstance().get("serviceChooseDistribution"));
        if (x <= 0.33) {
            return GasStationService.FUEL;
        } else if (x <= 0.66) {
            return GasStationService.WASH;
        } else {
            return GasStationService.WASH_AND_FUEL;
        }
    }

    public static int getCarsCount(){
        return id-1;
    }
}
