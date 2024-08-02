package utilities.ui;

import javax.swing.*;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import java.awt.Dimension;
import java.util.HashMap;
import java.util.Map;

public class ContainerFrame extends JFrame {
    private Map<String, Controller> panels = new HashMap<>();
    private JScrollPane mainScrollPane;
    private JPanel mainPanel;

    public ContainerFrame(int exitAction, Map<String, Controller> data) {
        initComponents(data);
        setDefaultCloseOperation(exitAction);
        setLocationRelativeTo(null);
        setVisible(false);
    }

    private void initComponents(Map<String, Controller> data) {
        getContentPane().setLayout(new AbsoluteLayout());

        mainPanel = new JPanel(new AbsoluteLayout());

        for (String entry : data.keySet()) {
            Controller controller = data.get(entry);
            this.panels.put(entry, controller);
            JPanel panel = controller.getPanel();
            panel.setPreferredSize(new Dimension(1290, 750));
            mainPanel.add(panel, new AbsoluteConstraints(0, 0, 1290, 750));
        }

        mainScrollPane = new JScrollPane(mainPanel);
        mainScrollPane.setPreferredSize(new Dimension(1290, 750));
        getContentPane().add(mainScrollPane, new AbsoluteConstraints(0, 0, 1290, 750));

        setResizable(false);
        pack();
    }

    public void changePanel(String name) {
        if (!isVisible()) setVisible(true);
        if (panels.get(name) == null) return;

        for (Controller panel : panels.values()) panel.getPanel().setVisible(false);

        JComponent panel = panels.get(name).getPanel();
        if (name.equals("dangNhap")) {
            Dimension size = new Dimension(512, 400); // Adjust this size to match the original "dangNhap" panel size
            panel.setPreferredSize(size);
            mainPanel.setPreferredSize(size);
            mainScrollPane.setViewportView(panel);
            setSize(size.width + 20, size.height + 40);
        } else {
            panel.setPreferredSize(new Dimension(1290, 750));
            mainPanel.setPreferredSize(new Dimension(1290, 750));
            mainScrollPane.setViewportView(mainPanel);
            setSize(1290 + 20, 750 + 40);
        }

        panel.setVisible(true);
    }

    public Controller getPanel(String name) {
        return panels.get(name);
    }
}
