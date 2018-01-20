package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.Cash;

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
        SimApplication.logMessage("Koniec placenia przez samochod nr " + car.getId(), simTime());

        // Zaplanuj dalsza obsluge
        if(cash.getCashQueue().size() > 0){
            new CarStartPaymentEvent(cash);
        }

        if(car.isWashWill()){
            // wash
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
