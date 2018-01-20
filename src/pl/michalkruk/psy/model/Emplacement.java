package pl.michalkruk.psy.model;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import lombok.Getter;

public class Emplacement extends BasicSimObj {

    private final int distributorsCount;
    private int currentlyFuelling;

    @Getter
    private DistributorsQueue distributorsQueue;

    Emplacement(int distributorsCount, int queueSize) {
        this.distributorsCount = distributorsCount;
        this.distributorsQueue = new DistributorsQueue(queueSize);
        this.currentlyFuelling = 0;
    }

    public Car startFuelling(){
        currentlyFuelling++;
        return distributorsQueue.removeFirst();
    }

    public void stopFuelling(){
        currentlyFuelling--;
    }

    public boolean addToQueue(Car car){
        return distributorsQueue.add(car);
    }

    public boolean isAvailable(){
        return currentlyFuelling < distributorsCount;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
