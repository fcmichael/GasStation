package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.*;

public class CarArriveAtGasStationEvent extends BasicSimEvent<Car, Object> {

    public CarArriveAtGasStationEvent(Car car, double delay) throws SimControlException {
        super(car, delay);
    }

    @Override
    protected void stateChange() throws SimControlException {
        GasStation gasStation = GasStation.getInstance();
        Car car = getSimObj();
        SimApplication.logMessage("Przybycie samochodu nr " + car.getId() + "," +
                car.getGasType() + ",wash=" + car.isWashWill() + ",fuel=" + car.isFuelWill(), simTime());

        if (car.isFuelWill()) {
            Emplacement emplacement = gasStation.getEmplacementBasedOnGasType(car.getGasType());

            if (emplacement.addToQueue(car)) {

                // Aktywuj obsluge, jezeli kolejka byla pusta (gniazdo "spalo")
                if (emplacement.getDistributorsQueue().size() == 1 && emplacement.isAvailable()) {
                    new CarStartFuellingEvent(emplacement);
                }

            } else {
                SimApplication.resignedCount++;
            }
        } else {
            Cash cash = gasStation.getCash();
            cash.addToQueue(car);

            // Aktywuj obsluge, jezeli kolejka byla pusta (gniazdo "spalo")
            if (cash.getCashQueue().size() == 1 && cash.isAvailable()) {
                new CarStartPaymentEvent(cash);
            }
        }


        new CarArriveAtGasStationEvent(CarFactory.generateCar(),
                SimGeneratorFactory.get(SimProperties.getInstance().get("arrivalDistribution")));
    }

    @Override
    protected void onTermination() throws SimControlException {

    }

    @Override
    protected void onInterruption() throws SimControlException {

    }

    @Override
    public Object getEventParams() {
        return null;
    }
}
