package pl.michalkruk.psy;

import dissimlab.monitors.MonitoredVar;
import dissimlab.monitors.Statistics;
import dissimlab.simcore.SimControlEvent;
import dissimlab.simcore.SimControlException;
import dissimlab.simcore.SimManager;
import dissimlab.simcore.SimParameters;
import org.apache.log4j.Logger;
import pl.michalkruk.psy.event.CarArriveAtGasStationEvent;
import pl.michalkruk.psy.model.CarFactory;

import java.math.BigDecimal;

public class SimApplication {

    private static final Logger LOGGER = Logger.getRootLogger();
    public static final MonitoredVar fuellingTime = new MonitoredVar();
    public static final MonitoredVar washingTime = new MonitoredVar();
    public static final MonitoredVar carsInQueues = new MonitoredVar();
    public static int resignedCount = 0;

    public static void main(String[] args) {
        SimManager simManager = SimManager.getInstance();

        try {
            new CarArriveAtGasStationEvent(CarFactory.generateCar(0.0), 0.0);
            new SimControlEvent(Double.parseDouble(SimProperties.getInstance().get("simulationTime")), SimParameters.SimControlStatus.STOPSIMULATION);
            simManager.startSimulation();
        } catch (SimControlException e) {
            e.printStackTrace();
        }

        double carsInQueuesMean = Statistics.arithmeticMean(carsInQueues);
        double fuellingTimeMean = Statistics.arithmeticMean(fuellingTime);
        double washingTimeMean = Statistics.arithmeticMean(washingTime);
        double resignedProbability = (double) resignedCount / (double) CarFactory.getCarsCount();

        SimApplication.LOGGER.info("Oczekiwana graniczna liczba samochodow w kolejkach: " + carsInQueuesMean);
        SimApplication.LOGGER.info("Oczekiwany graniczny czas tankowania samochodu: " + fuellingTimeMean);
        SimApplication.LOGGER.info("Oczekiwany graniczny czas mycia samochodu: " + washingTimeMean);
        SimApplication.LOGGER.info("Graniczne prawdopodobienstwo rezygnacji z obslugi: " + resignedProbability);

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
