package pl.michalkruk.psy.model;

import lombok.Getter;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;

import java.awt.*;
import java.util.HashMap;
import java.util.Map;

public class CarFactory {

    @Getter
    private static int id = 0;
    private static final Map<Integer, Color> colorMap = new HashMap<>();

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
            Color color = new Color((int)(Math.random() * 0x1000000));
            Car car = new Car(id, true, washWill, randomizeFuel(), arrivingAtStationTime, color);
            colorMap.put(id, color);
            return car;
        } else {
            Color color = new Color((int)(Math.random() * 0x1000000));
            Car car = new Car(id, false, true, null, arrivingAtStationTime, color);
            colorMap.put(id, color);
            return car;
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
        if (x <= 0.6) {
            return GasStation.Service.FUEL;
        } else if (x <= 0.8) {
            return GasStation.Service.WASH;
        } else {
            return GasStation.Service.WASH_AND_FUEL;
        }
    }

    public static Color getCarColor(int carId){
        return colorMap.get(carId).darker();
    }

    public static int getCarsCount(){
        return id-1;
    }
}
