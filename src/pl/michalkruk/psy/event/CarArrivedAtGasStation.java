package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.CarFactory;

public class CarArrivedAtGasStation extends BasicSimEvent<Car, Object> {

    public CarArrivedAtGasStation(Car entity, double delay) throws SimControlException {
        super(entity, delay);
    }

    @Override
    protected void stateChange() throws SimControlException {
        Car car = getSimObj();
        SimApplication.LOGGER
                .info("SimTime (" + simTime() + ") : Przybycie samochodu nr " + car.getId() + "," +
                        car.getGasType() + ",wash=" + car.isWashWill() + ",fuel=" + car.isFuelWill());

        new CarArrivedAtGasStation(CarFactory.generateCar(),
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
