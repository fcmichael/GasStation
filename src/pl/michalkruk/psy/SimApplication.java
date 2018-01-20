package pl.michalkruk.psy;

import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import pl.michalkruk.psy.event.CarArrivedAtGasStation;
import pl.michalkruk.psy.model.CarFactory;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SimApplication {

    public static final Logger LOGGER = Logger.getLogger("gasStation");

    public static void main(String[] args) {
        SimManager simManager = SimManager.getInstance();

        try {
            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(new FileHandler("log/sim_" + Calendar.getInstance().getTimeInMillis() + ".xml"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }

        try {
            new CarArrivedAtGasStation(CarFactory.generateCar(), 0.0);
            new SimControlEvent(200.0, SimParameters.SimControlStatus.STOPSIMULATION);
            simManager.startSimulation();
        } catch (SimControlException e) {
            e.printStackTrace();
        }

    }
}
