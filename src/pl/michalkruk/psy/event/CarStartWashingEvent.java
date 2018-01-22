package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.CarWash;

class CarStartWashingEvent extends BasicSimEvent<CarWash, Car>{

    private CarWash carWash;

    CarStartWashingEvent(CarWash carWash) throws SimControlException {
        super(carWash);
        this.carWash = carWash;
    }

    @Override
    protected void stateChange() throws SimControlException {
        if(carWash.getCarWashQueue().size() > 0){
            Car car = carWash.startWashing();
            SimApplication.getSimulationPanel().getCarWashQueue().removeCar();
            SimApplication.getSimulationPanel().getCarWash().setCar(car.getId());
            SimApplication.logMessage("Rozpoczecie mycia samochodu nr " + car.getId(), simTime());
            double washingTime = SimGeneratorFactory.get(SimProperties.getInstance().get("washDistribution"));
            new CarEndWashingEvent(carWash, washingTime, car);
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
