package pl.michalkruk.psy.model;

import dissimlab.broker.INotificationEvent;
import dissimlab.broker.IPublisher;
import dissimlab.simcore.BasicSimObj;
import lombok.Getter;
import lombok.Setter;

import java.awt.*;

@Setter
@Getter
public class Car extends BasicSimObj {
    private int id;
    private boolean fuelWill;
    private boolean washWill;
    private GasStation.GasType gasType;
    private double arrivingAtStationTime;
    private double leavingDistributorTime;
    private Color color;

    Car(int id, boolean fuelWill, boolean washWill, GasStation.GasType gasType, double arrivingAtStationTime, Color color) {
        this.id = id;
        this.fuelWill = fuelWill;
        this.washWill = washWill;
        this.gasType = gasType;
        this.arrivingAtStationTime = arrivingAtStationTime;
        this.color = color;
    }

    @Override
    public void reflect(IPublisher iPublisher, INotificationEvent iNotificationEvent) {

    }

    @Override
    public boolean filter(IPublisher iPublisher, INotificationEvent iNotificationEvent) {
        return false;
    }
}
