package pl.michalkruk.psy.visualization;

import lombok.Getter;

import javax.swing.*;
import java.awt.*;

@Getter
public class SimulationPanel extends JPanel {

    private CarWash carWash;
    private CarWashQueue carWashQueue;
    private CashesLine cashesLine;
    private CashQueue cashQueue;
    private DistributorsLine distributorsLine;
    private DistributorsQueuesLine distributorsQueuesLine;

    SimulationPanel() {
        initializeSimulationObjects();
        addSimulationObjectsToFrame();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        carWash.paintComponent(g);
        carWashQueue.paintComponent(g);
        cashesLine.paintComponent(g);
        cashQueue.paintComponent(g);
        distributorsLine.paintComponent(g);
        distributorsQueuesLine.paintComponent(g);
    }

    private void initializeSimulationObjects() {
        this.carWash = new CarWash();
        this.carWashQueue = new CarWashQueue();
        this.cashesLine = new CashesLine();
        this.cashQueue = new CashQueue();
        this.distributorsLine = new DistributorsLine();
        this.distributorsQueuesLine = new DistributorsQueuesLine(
                distributorsLine.getDistributorsLPGCenterX(),
                distributorsLine.getDistributorsONCenterX(),
                distributorsLine.getDistributorsPetrolCenterX());
    }

    private void addSimulationObjectsToFrame() {
        add(this.carWash);
        add(this.carWashQueue);
        add(this.cashesLine);
        add(this.cashQueue);
        add(this.distributorsLine);
        add(this.distributorsQueuesLine);
    }

}
