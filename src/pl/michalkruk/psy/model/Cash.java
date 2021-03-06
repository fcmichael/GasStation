package pl.michalkruk.psy.model;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import lombok.Getter;

import java.util.LinkedList;

public class Cash extends BasicSimObj{

    private final int cashesCount;
    private int currentlyPaying;

    @Getter
    private LinkedList<Car> cashQueue;

    Cash(int cashesCount) {
        this.cashesCount = cashesCount;
        this.currentlyPaying = 0;
        this.cashQueue = new LinkedList<>();
    }

    public Car startPayment(){
        currentlyPaying++;
        return cashQueue.removeFirst();
    }

    public void stopPayment(){
        currentlyPaying--;
    }

    public void addToQueue(Car car){
        cashQueue.addLast(car);
    }

    public boolean isAvailable(){
        return currentlyPaying < cashesCount;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
