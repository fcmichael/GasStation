package pl.michalkruk.psy.visualization;

import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.CarFactory;

import javax.swing.*;
import java.awt.*;

public class CashesLine extends JComponent {

    private final int cashesCount;
    private final int[] cashes;

    CashesLine() {
        this.cashesCount = Integer.parseInt(SimProperties.getInstance().get("numOfCashes"));
        this.cashes = new int[cashesCount];
        for (int i = 0; i < cashesCount; i++) {
            cashes[i] = 0;
        }
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (cashesCount < 10) {
            Graphics2D g2d = (Graphics2D) g;

            int widthAllCashes = 120 * cashesCount;
            int rest = 1380 - widthAllCashes;
            int border = rest / (cashesCount + 1);

            for (int i = 0; i < cashesCount; i++) {
                int rectX = 150 + border * (i + 1) + 120 * i;

                g2d.drawRect(rectX, 120, 120, 80);
                g2d.drawString("KASA " + (i + 1), rectX + 42, 134);
                g2d.drawLine(rectX, 138, rectX + 120, 138);

                int carInCash = cashes[i];
                if (carInCash > 0) {
                    g2d.setColor(CarFactory.getCarColor(carInCash));
                    g2d.fillRect(rectX + 35, 153, 50, 30);
                    g2d.setColor(Color.WHITE);
                    String id = String.valueOf(carInCash);
                    g2d.drawString(id, ((rectX + 35 + 20) - (id.length() * 2)), 173);
                    g2d.setColor(Color.BLACK);
                }
            }
        }
    }

    public void addCar(int carId) {
        int firstEmpty = findFirstEmptyCash();
        if (firstEmpty != -1) {
            cashes[firstEmpty] = carId;
            repaint();
            getParent().repaint();
        }
    }

    public void removeCar(int carId) {
        int length = cashes.length;
        for (int i = 0; i < length; i++) {
            if (cashes[i] == carId) {
                cashes[i] = 0;
            }
        }
        repaint();
        getParent().repaint();
    }

    private int findFirstEmptyCash() {
        int length = cashes.length;
        int result = -1;
        for (int i = 0; i < length; i++) {
            if (cashes[i] == 0) {
                result = i;
            }
        }
        return result;
    }

}
