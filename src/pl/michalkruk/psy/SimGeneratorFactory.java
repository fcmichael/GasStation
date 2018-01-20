package pl.michalkruk.psy;

import dissimlab.random.SimGenerator;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.LinkedList;
import java.util.List;
import java.util.StringTokenizer;

public class SimGeneratorFactory {

    private static final SimGenerator generator = new SimGenerator();
    private static final String DELIMETER = ",";

    public static double get(String expression) {
        try {
            Class c = Class.forName("dissimlab.random.SimGenerator");
            Method m = c.getMethod(parseName(expression), parseParemeterTypes(expression));
            return (double) m.invoke(generator, parseArguments(expression));
        } catch (ClassNotFoundException | NoSuchMethodException | SecurityException | IllegalAccessException | IllegalArgumentException | InvocationTargetException e) {
            e.printStackTrace();
        }
        return 0;
    }


    private static Object[] parseArguments(String expression) {
        StringTokenizer tokenizer = new StringTokenizer(expression, DELIMETER);
        tokenizer.nextToken();
        List<Object> classes = new LinkedList<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.contains(".")) {
                classes.add(Double.parseDouble(token));
            } else {
                classes.add(Integer.parseInt(token));
            }
        }
        Object[] classTable = new Object[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            classTable[i] = classes.get(i);
        }

        return classTable;
    }

    private static Class[] parseParemeterTypes(String expression) {
        StringTokenizer tokenizer = new StringTokenizer(expression, DELIMETER);
        tokenizer.nextToken();
        List<Class> classes = new LinkedList<>();
        while (tokenizer.hasMoreTokens()) {
            String token = tokenizer.nextToken();
            if (token.contains(".")) {
                classes.add(double.class);
            } else {
                classes.add(int.class);
            }
        }
        Class[] classTable = new Class[classes.size()];
        for (int i = 0; i < classes.size(); i++) {
            classTable[i] = classes.get(i);
        }

        return classTable;

    }

    private static String parseName(String expression) {
        StringTokenizer tokenizer = new StringTokenizer(expression, DELIMETER);
        return tokenizer.nextToken();
    }
}
