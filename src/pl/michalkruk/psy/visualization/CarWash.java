package pl.michalkruk.psy.visualization;

import pl.michalkruk.psy.model.CarFactory;

import javax.swing.*;
import java.awt.*;

public class CarWash extends JComponent {

    private int carId = 0;

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawRect(10, 10, 120, 80);
        g2d.drawString("MYJNIA", 52, 24);
        g2d.drawLine(10, 28, 130, 28);

        if (carId > 0) {
            g2d.setColor(CarFactory.getCarColor(carId));
            g2d.fillRect(45, 43, 50, 30);
            g2d.setColor(Color.WHITE);
            String id = String.valueOf(carId);
            g2d.drawString(id, (65-(id.length()*2)),63);
            g2d.setColor(Color.BLACK);
        }

    }

    public void setCar(int carId){
        this.carId = carId;
        repaint();
        getParent().repaint();
    }

    public void removeCar(){
        carId = 0;
        repaint();
        getParent().repaint();
    }

}
