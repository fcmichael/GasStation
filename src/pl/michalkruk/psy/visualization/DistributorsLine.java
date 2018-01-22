package pl.michalkruk.psy.visualization;

import lombok.Getter;
import pl.michalkruk.psy.SimProperties;
import pl.michalkruk.psy.model.CarFactory;
import pl.michalkruk.psy.model.GasStation;

import javax.swing.*;
import java.awt.*;

public class DistributorsLine extends JComponent {

    private final int distributorsLPGCount;
    private final int[] distributorsLPG;

    private final int distributorsONCount;
    private final int[] distributorsON;

    private final int distributorsPetrolCount;
    private final int[] distributorsPetrol;

    @Getter
    private int distributorsLPGCenterX;
    @Getter
    private int distributorsONCenterX;
    @Getter
    private int distributorsPetrolCenterX;

    private int distributorsCount;

    @Getter
    private int rectBorder;

    DistributorsLine() {
        this.distributorsLPGCount = Integer.parseInt(SimProperties.getInstance().get("numOfDistributorsLPG"));
        this.distributorsLPG = new int[distributorsLPGCount];
        for (int i = 0; i < distributorsLPGCount; i++) {
            distributorsLPG[i] = 0;
        }

        this.distributorsONCount = Integer.parseInt(SimProperties.getInstance().get("numOfDistributorsON"));
        this.distributorsON = new int[distributorsONCount];
        for (int i = 0; i < distributorsONCount; i++) {
            distributorsON[i] = 0;
        }

        this.distributorsPetrolCount = Integer.parseInt(SimProperties.getInstance().get("numOfDistributorsPetrol"));
        this.distributorsPetrol = new int[distributorsPetrolCount];
        for (int i = 0; i < distributorsPetrolCount; i++) {
            distributorsPetrol[i] = 0;
        }
        calculateParameters();
    }

    private void calculateParameters() {
        distributorsCount = distributorsLPGCount + distributorsONCount + distributorsPetrolCount;
        int widthAllCashes = 120 * (distributorsCount);
        int rest = 1380 - widthAllCashes;
        rectBorder = rest / (distributorsCount + 1);

        int firstON = 150 + rectBorder;
        int lastON = 150 + rectBorder + (distributorsONCount - 1) * (rectBorder) + distributorsONCount * 120;

        distributorsONCenterX = (firstON + lastON) / 2;

        int drawnAtTheEnd = distributorsONCount + distributorsLPGCount;
        int firstLPG = 150 + rectBorder + (distributorsONCount) * (rectBorder) + distributorsONCount * 120;
        int lastLPG = 150 + rectBorder + (drawnAtTheEnd - 1) * (rectBorder) + drawnAtTheEnd * 120;
        distributorsLPGCenterX = (firstLPG + lastLPG) / 2;

        int drawnAtBeginning = distributorsONCount + distributorsLPGCount;
        int firstPetrol = 150 + rectBorder + (drawnAtBeginning) * (rectBorder) + drawnAtBeginning * 120;
        int lastPetrol = 150 + rectBorder + (distributorsCount - 1) * (rectBorder) + distributorsCount * 120;
        distributorsPetrolCenterX = (firstPetrol + lastPetrol) / 2;
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        if (distributorsCount < 10) {
            Graphics2D g2d = (Graphics2D) g;
            int alreadyDrawn = 0;

            for (int j = 0; j < distributorsONCount; j++) {
                drawDistributor(g2d, distributorsON[j], "Dystryb. ON", rectBorder, alreadyDrawn, 24);
                alreadyDrawn++;
            }

            for (int j = 0; j < distributorsLPGCount; j++) {
                drawDistributor(g2d, distributorsLPG[j], "Dystryb. LPG", rectBorder, alreadyDrawn, 22);
                alreadyDrawn++;
            }

            for (int j = 0; j < distributorsPetrolCount; j++) {
                drawDistributor(g2d, distributorsPetrol[j], "Dystryb. benzyny", rectBorder, alreadyDrawn, 10);
                alreadyDrawn++;
            }
        }
    }

    private void drawDistributor(Graphics2D g2d, int carId, String name, int rectBorder, int alreadyDrawn, int labelOffset) {
        int rectX = 150 + rectBorder * (alreadyDrawn + 1) + 120 * alreadyDrawn;
        g2d.drawRect(rectX, 330, 120, 80);
        g2d.drawString(name, rectX + labelOffset, 344);
        g2d.drawLine(rectX, 348, rectX + 120, 348);

        if (carId > 0) {
            g2d.setColor(CarFactory.getCarColor(carId));
            g2d.fillRect(rectX + 35, 363, 50, 30);
            g2d.setColor(Color.WHITE);
            String id = String.valueOf(carId);
            g2d.drawString(id, ((rectX + 35 + 20) - (id.length() * 2)), 383);
            g2d.setColor(Color.BLACK);
        }
    }

    public void addCar(int carId, GasStation.GasType gasType) {
        int[] table = getTableBasedOnGasType(gasType);
        if (table != null) {
            int firstEmpty = findFirstEmptyDistributor(table);
            if (firstEmpty != -1) {
                table[firstEmpty] = carId;
            }
            repaint();
            getParent().repaint();
        }
    }

    private int findFirstEmptyDistributor(int[] tab) {
        int length = tab.length;
        int result = -1;
        for (int i = 0; i < length; i++) {
            if (tab[i] == 0) {
                result = i;
            }
        }
        return result;
    }

    private int[] getTableBasedOnGasType(GasStation.GasType gasType) {
        int[] result = null;
        switch (gasType) {
            case ON:
                result = distributorsON;
                break;
            case LPG:
                result = distributorsLPG;
                break;
            case PETROL:
                result = distributorsPetrol;
                break;
        }
        return result;
    }

    public void removeCar(int carId, GasStation.GasType gasType) {
        int[] table = getTableBasedOnGasType(gasType);
        if (table != null) {
            int length = table.length;
            for (int i = 0; i < length; i++) {
                if (table[i] == carId) {
                    table[i] = 0;
                }
            }
            repaint();
            getParent().repaint();
        }
    }
}
