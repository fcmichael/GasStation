package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.Cash;

class CarStartPaymentEvent extends BasicSimEvent<Cash, Object> {

    private final Cash cash;

    CarStartPaymentEvent(Cash cash) throws SimControlException {
        this.cash = cash;
    }

    @Override
    protected void stateChange() throws SimControlException {
        if (cash.getCashQueue().size() > 0) {
            Car car = cash.startPayment();
            SimApplication.getSimulationPanel().getCashQueue().removeCar();
            SimApplication.getSimulationPanel().getCashesLine().addCar(car.getId());

            SimApplication.logMessage("Rozpoczecie placenia przez samochod nr " + car.getId(), simTime());
            double paymentTime = SimGeneratorFactory.get(SimProperties.getInstance().get("paymentDistribution"));
            new CarEndPaymentEvent(cash, paymentTime, car);
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
