package pl.michalkruk.psy;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class SimProperties {

    private static SimProperties instance = null;
    private final Properties properties;

    private SimProperties() {
        properties = new Properties();
        loadPropertiesFile();
    }

    private void loadPropertiesFile() {
        InputStream input = null;

        try {
            input = new FileInputStream("resources/application.properties");
            properties.load(input);
        } catch (IOException ex) {
            ex.printStackTrace();
        } finally {
            if (input != null) {
                try {
                    input.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public static SimProperties getInstance() {
        if (instance == null) {
            instance = new SimProperties();
        }
        return instance;
    }


    public int intFromProperties(String propName, int defaultValue) {
        int ret = defaultValue;
        String intString = properties.getProperty(propName);

        if (intString != null) {
            try {
                ret = Integer.parseInt(intString.trim());
            } catch (NumberFormatException e) {
                ret = defaultValue;
            }
        }
        return ret;
    }

    public String get(String propName) {
        return properties.getProperty(propName);
    }

}
