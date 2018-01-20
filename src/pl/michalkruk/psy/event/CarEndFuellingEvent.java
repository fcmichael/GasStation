package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.Cash;
import pl.michalkruk.psy.model.Emplacement;
import pl.michalkruk.psy.model.GasStation;

class CarEndFuellingEvent extends BasicSimEvent<Emplacement, Car> {

    private Emplacement emplacement;
    private Car car;

    CarEndFuellingEvent(Emplacement entity, double delay, Car car) throws SimControlException {
        super(entity, delay, car);
        this.emplacement = entity;
        this.car = car;
    }

    @Override
    protected void stateChange() throws SimControlException {
        emplacement.stopFuelling();

        SimApplication.fuellingTime.setValue(simTime() - car.getArrivingAtStationTime());
        SimApplication.logMessage("Koniec tankowania samochodu nr " + car.getId(), simTime());

        car.setLeavingDistributorTime(simTime());

        // Zaplanuj dalsza obsluge
        if(emplacement.getDistributorsQueue().size() > 0){
            new CarStartFuellingEvent(emplacement);
        }

        Cash cash = GasStation.getInstance().getCash();
        cash.addToQueue(car);

        // Aktywuj obsluge, jezeli kolejka byla pusta (gniazdo "spalo")
        if (cash.getCashQueue().size() == 1 && cash.isAvailable()) {
            new CarStartPaymentEvent(cash);
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
