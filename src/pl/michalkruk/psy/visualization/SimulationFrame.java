package pl.michalkruk.psy.visualization;

import lombok.Getter;
import lombok.Setter;

import javax.swing.*;
import java.awt.*;

@Getter
@Setter
public class SimulationFrame extends JFrame {

    private SimulationPanel content;

    public SimulationFrame() throws HeadlessException {
        super("Symulacja stacji paliw");
        setSize(1530,880);
        setResizable(false);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        content = new SimulationPanel();
        add(content);
        setVisible(true);
    }
}
