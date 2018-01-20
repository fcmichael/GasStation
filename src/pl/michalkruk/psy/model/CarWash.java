package pl.michalkruk.psy.model;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import lombok.Getter;
import pl.michalkruk.psy.SimApplication;

import java.util.LinkedList;

@Getter
public class CarWash extends BasicSimObj{

    private boolean free;

    @Getter
    private LinkedList<Car> carWashQueue;

    CarWash() {
        this.free = true;
        this.carWashQueue = new LinkedList<>();
    }

    public Car startWashing(){
        free = false;
        Car car = carWashQueue.removeFirst();
        SimApplication.carsInQueues.setValue(carWashQueue.size());
        return car;
    }

    public void stopWashing(){
        free = true;
    }

    public void addToQueue(Car car){
        carWashQueue.addLast(car);
        SimApplication.carsInQueues.setValue(carWashQueue.size());
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
