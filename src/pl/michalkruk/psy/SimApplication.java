package pl.michalkruk.psy;

import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import pl.michalkruk.psy.event.CarArriveAtGasStation;
import pl.michalkruk.psy.model.CarFactory;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SimApplication {

    public static final Logger LOGGER = Logger.getLogger("gasStation");
    public static int resignedCount = 0;

    public static void main(String[] args) {
        SimManager simManager = SimManager.getInstance();

        try {
//            LOGGER.setUseParentHandlers(false);
            LOGGER.addHandler(new FileHandler("log/sim_" + Calendar.getInstance().getTimeInMillis() + ".xml"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }

        try {
            new CarArriveAtGasStation(CarFactory.generateCar(), 0.0);
            new SimControlEvent(1000.0, SimParameters.SimControlStatus.STOPSIMULATION);
            simManager.startSimulation();
        } catch (SimControlException e) {
            e.printStackTrace();
        }

        System.out.println("resigned: " + resignedCount);

    }

    public static void logMessage(String message, double simTime) {
        SimApplication.LOGGER.info("SimTime (" + formatSimTime(simTime) + ") : " + message);
    }

    private static double formatSimTime(double simTime) {
        return BigDecimal.valueOf(simTime)
                .setScale(3, BigDecimal.ROUND_UP)
                .doubleValue();
    }
}
