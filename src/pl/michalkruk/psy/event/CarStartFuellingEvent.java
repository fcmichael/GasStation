package pl.michalkruk.psy.event;

import dissimlab.simcore.BasicSimEvent;
import dissimlab.simcore.SimControlException;
import pl.michalkruk.psy.SimApplication;
import pl.michalkruk.psy.SimGeneratorFactory;
import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.Car;
import pl.michalkruk.psy.model.Emplacement;

import java.math.BigDecimal;

class CarStartFuellingEvent extends BasicSimEvent<Emplacement, Car>{

    private Emplacement emplacement;

    CarStartFuellingEvent(Emplacement emplacement) throws SimControlException {
        super(emplacement);
        this.emplacement = emplacement;
    }

    @Override
    protected void stateChange() throws SimControlException {
        if(emplacement.getDistributorsQueue().size() > 0){
            Car car = emplacement.startFuelling();
            SimApplication.logMessage("Rozpoczecie tankowania samochodu nr " + car.getId(), simTime());
            String gasType = car.getGasType().toString();
            double fuellingTime = SimGeneratorFactory.get(SimProperties.getInstance().get("fuelling" + gasType + "Distribution"));
            new CarEndFuellingEvent(emplacement, fuellingTime, car);
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
