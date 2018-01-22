package pl.michalkruk.psy.visualization;

import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.CarFactory;
import pl.michalkruk.psy.model.GasStation;

import javax.swing.*;
import java.awt.*;
import java.awt.geom.AffineTransform;
import java.util.LinkedList;

public class DistributorsQueuesLine extends JComponent {

    private final LinkedList<Integer> ONQueueCarIds;
    private final LinkedList<Integer> LPGQueueCarIds;
    private final LinkedList<Integer> PetrolQueueCarIds;

    private final int maxQueueSizeForDrawing = 6;
    private final int ONQueueSize;
    private final int LPGQueueSize;
    private final int PetrolQueueSize;

    private final int distributorsLPGCenterX;
    private final int distributorsONCenterX;
    private final int distributorsPetrolCenterX;

    DistributorsQueuesLine(int distributorsLPGCenterX, int distributorsONCenterX, int distributorsPetrolCenterX) {
        this.ONQueueCarIds = new LinkedList<>();
        this.LPGQueueCarIds = new LinkedList<>();
        this.PetrolQueueCarIds = new LinkedList<>();
        this.ONQueueSize = Integer.parseInt(SimProperties.getInstance().get("queueSizeOfDistributorsON"));
        this.LPGQueueSize = Integer.parseInt(SimProperties.getInstance().get("queueSizeOfDistributorsLPG"));
        this.PetrolQueueSize = Integer.parseInt(SimProperties.getInstance().get("queueSizeOfDistributorsPetrol"));
        this.distributorsLPGCenterX = distributorsLPGCenterX;
        this.distributorsONCenterX = distributorsONCenterX;
        this.distributorsPetrolCenterX = distributorsPetrolCenterX;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;

        drawDistributorQueue(g2d, ONQueueSize, distributorsONCenterX, "Kolejka ON", 26, ONQueueCarIds);
        drawDistributorQueue(g2d, LPGQueueSize, distributorsLPGCenterX, "Kolejka LPG", 26, LPGQueueCarIds);
        drawDistributorQueue(g2d, PetrolQueueSize, distributorsPetrolCenterX, "Kolejka benzyna", 11, PetrolQueueCarIds);
    }

    private void drawDistributorQueue(Graphics2D g2d, int queueSize, int distributorsCenterX, String name, int nameOffset, LinkedList<Integer> queue) {
        int rectX = distributorsCenterX - 60;
        int height = (int) (((double) queueSize / maxQueueSizeForDrawing) * 380); // calculate based on queue size
        if ((double) queueSize / maxQueueSizeForDrawing < 1) {
            height += 30;
        }

        g2d.drawRect(rectX, 450, 120, height);
        g2d.drawString(name, rectX + nameOffset, 464);
        g2d.drawLine(rectX, 468, rectX + 120, 468);

        Font originalFont = g2d.getFont();

        AffineTransform affineTransform = new AffineTransform();
        affineTransform.rotate(Math.toRadians(-90), 0, 0);
        Font font = new Font(null, Font.PLAIN, 12);
        Font rotatedFont = font.deriveFont(affineTransform);
        g2d.setFont(rotatedFont);

        int number = 0;

        synchronized (queue) {
            for (Integer carId : queue) {
                if (number < 6) {
                    g2d.setColor(CarFactory.getCarColor(carId));
                    g2d.fillRect(distributorsCenterX - 15, 468 + 10 + number * 60, 30, 50);
                    g2d.setColor(Color.WHITE);
                    String id = String.valueOf(carId);

                    g2d.setColor(Color.WHITE);
                    g2d.drawString(id, (distributorsCenterX + 4), (468 + +number * 60 + 10 + 28 + (id.length() * 3)));
                    g2d.setColor(Color.BLACK);
                    number++;
                }
            }
        }
        g2d.setFont(originalFont);
    }

    public void addCar(int carId, GasStation.GasType gasType) {
        LinkedList<Integer> queue = getQueueBasedOnGasType(gasType);

        if (queue != null) {
            synchronized (queue) {
                queue.addLast(carId);
                repaint();
                getParent().repaint();
            }
        }
    }

    private LinkedList<Integer> getQueueBasedOnGasType(GasStation.GasType gasType) {
        LinkedList<Integer> result = null;
        switch (gasType) {
            case ON:
                result = ONQueueCarIds;
                break;
            case LPG:
                result = LPGQueueCarIds;
                break;
            case PETROL:
                result = PetrolQueueCarIds;
                break;
        }
        return result;
    }

    public void removeCar(GasStation.GasType gasType) {
        LinkedList<Integer> queue = getQueueBasedOnGasType(gasType);

        if (queue != null) {
            synchronized (queue) {
                queue.removeFirst();
                repaint();
                getParent().repaint();
            }
        }
    }
}
