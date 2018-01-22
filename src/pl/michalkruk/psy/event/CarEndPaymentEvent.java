package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.CarWash;
import pl.michalkruk.psy.model.Cash;
import pl.michalkruk.psy.model.GasStation;

class CarEndPaymentEvent extends BasicSimEvent<Cash, Car> {

    private Cash cash;
    private Car car;

    CarEndPaymentEvent(Cash cash, double delay, Car car) throws SimControlException {
        super(cash, delay, car);
        this.cash = cash;
        this.car = car;
    }

    @Override
    protected void stateChange() throws SimControlException {
        cash.stopPayment();
        SimApplication.getSimulationPanel().getCashesLine().removeCar(car.getId());
        SimApplication.logMessage("Koniec placenia przez samochod nr " + car.getId(), simTime());

        // Zaplanuj dalsza obsluge
        if(cash.getCashQueue().size() > 0){
            new CarStartPaymentEvent(cash);
        }

        if(car.isWashWill()){
            CarWash carWash = GasStation.getInstance().getCarWash();
            carWash.addToQueue(car);
            SimApplication.getSimulationPanel().getCarWashQueue().addCar(car.getId());

            if(carWash.getCarWashQueue().size() == 1 && carWash.isFree()){
                new CarStartWashingEvent(carWash);
            }
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
