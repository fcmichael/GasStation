package pl.michalkruk.psy.model;

import java.util.LinkedList;

public class DistributorsQueue extends LinkedList<Car> {

    private final int MAX_SIZE;

    DistributorsQueue(int maxSize) {
        MAX_SIZE = maxSize;
    }

    @Override
    public boolean add(Car car) {
        if (size() < MAX_SIZE) {
            super.addLast(car);
            return true;
        } else {
            return false;
        }
    }
}
