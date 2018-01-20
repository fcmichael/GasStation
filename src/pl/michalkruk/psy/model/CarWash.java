package pl.michalkruk.psy.model;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import lombok.Getter;

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
        return carWashQueue.removeFirst();
    }

    public void stopWashing(){
        free = true;
    }

    public void addToQueue(Car car){
        carWashQueue.addLast(car);
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
