package pl.michalkruk.psy.model;

import lombok.Getter;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;

public class CarFactory {

    @Getter
    private static int id = 0;

    public static Car generateCar(double arrivingAtStationTime) {
        id++;
        GasStation.Service service = randomizeService();
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

    private static GasStation.GasType randomizeFuel() {
        double x = SimGeneratorFactory.get(SimProperties.getInstance().get("gasChooseDistribution"));
        if (x <= 0.33) {
            return GasStation.GasType.PETROL;
        } else if (x <= 0.66) {
            return GasStation.GasType.LPG;
        } else {
            return GasStation.GasType.ON;
        }
    }

    private static GasStation.Service randomizeService() {
        double x = SimGeneratorFactory.get(SimProperties.getInstance().get("serviceChooseDistribution"));
        if (x <= 0.33) {
            return GasStation.Service.FUEL;
        } else if (x <= 0.66) {
            return GasStation.Service.WASH;
        } else {
            return GasStation.Service.WASH_AND_FUEL;
        }
    }

    public static int getCarsCount(){
        return id-1;
    }
}
