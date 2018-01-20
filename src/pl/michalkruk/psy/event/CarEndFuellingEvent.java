package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.Emplacement;

class CarEndFuellingEvent extends BasicSimEvent<Emplacement, Car> {

    private Emplacement emplacement;

    CarEndFuellingEvent(Emplacement entity, double delay, Car car) throws SimControlException {
        super(entity, delay, car);
        this.emplacement = entity;
    }

    @Override
    protected void stateChange() throws SimControlException {
        emplacement.stopFuelling();
        SimApplication.logMessage("Koniec tankowania samochodu nr " + transitionParams.getId(), simTime());

        // Zaplanuj dalsza obsluge
        if(emplacement.getDistributorsQueue().size() > 0){
            new CarStartFuellingEvent(emplacement);
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
