package pl.michalkruk.psy.model;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class Car extends BasicSimObj {
    private int id;
    private boolean fuelWill;
    private boolean washWill;
    private GasType gasType;
    private double arrivingAtStationTime;
    private double leavingDistributorTime;

    Car(int id, boolean fuelWill, boolean washWill, GasType gasType, double arrivingAtStationTime) {
        this.id = id;
        this.fuelWill = fuelWill;
        this.washWill = washWill;
        this.gasType = gasType;
        this.arrivingAtStationTime = arrivingAtStationTime;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
