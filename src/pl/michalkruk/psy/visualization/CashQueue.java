package pl.michalkruk.psy.visualization;

import pl.michalkruk.psy.model.CarFactory;

import javax.swing.*;
import java.awt.*;
import java.util.LinkedList;

public class CashQueue extends JComponent{

    private final LinkedList<Integer> carIds;

    CashQueue() {
        this.carIds = new LinkedList<>();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        g2d.drawRect(150, 220, 1370, 80);
        g2d.drawString("KOLEJKA DO KAS", 750, 234);
        g2d.drawLine(150, 238, 1520, 238);

        int number = 0;

        synchronized (carIds) {
            for (Integer carId : carIds) {
                if(number < 22){
                    int width = 160 + number * 60;
                    g2d.setColor(CarFactory.getCarColor(carId));
                    g2d.fillRect(width, 253, 50, 30);
                    g2d.setColor(Color.WHITE);
                    String id = String.valueOf(carId);
                    g2d.drawString(id, ((width + 20) - (id.length() * 2)), 273);
                    g2d.setColor(Color.BLACK);
                    number++;
                }
            }
        }
    }

    public void addCar(int carId) {
        synchronized (carIds) {
            carIds.addLast(carId);
        }
        repaint();
        getParent().repaint();
    }

    public void removeCar() {
        synchronized (carIds) {
            carIds.removeFirst();
        }
        repaint();
        getParent().repaint();
    }
}
