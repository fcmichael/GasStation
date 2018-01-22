package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.CarWash;

class CarEndWashingEvent extends BasicSimEvent<CarWash, Car> {

    private CarWash carWash;
    private Car car;

    CarEndWashingEvent(CarWash carWash, double delay, Car car) throws SimControlException {
        super(carWash, delay, car);
        this.carWash = carWash;
        this.car = car;
    }

    @Override
    protected void stateChange() throws SimControlException {
        carWash.stopWashing();
        SimApplication.getSimulationPanel().getCarWash().removeCar();
        SimApplication.logMessage("Koniec mycia samochodu nr " + car.getId(), simTime());

        if (car.isFuelWill()) {
            SimApplication.washingTime.setValue(simTime() - car.getLeavingDistributorTime());
        }

        // Zaplanuj dalsza obsluge
        if (carWash.getCarWashQueue().size() > 0) {
            new CarStartWashingEvent(carWash);
        }
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
