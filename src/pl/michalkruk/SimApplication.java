package pl.michalkruk;

import java.io.IOException;
import java.util.Calendar;
import java.util.logging.FileHandler;
import java.util.logging.Logger;

public class SimApplication {

    public static void main(String[] args) {
        try {
            Logger.getLogger(SimApplication.class.toString())
                    .addHandler(new FileHandler("log/sim_" + Calendar.getInstance().getTimeInMillis() + ".xml"));
        } catch (SecurityException | IOException e1) {
            e1.printStackTrace();
        }
    }
}
