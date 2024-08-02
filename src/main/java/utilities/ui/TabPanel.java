package utilities.ui;

import java.awt.Font;
import java.awt.Dimension;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.event.ChangeEvent;
import org.netbeans.lib.awtextra.AbsoluteConstraints;
import org.netbeans.lib.awtextra.AbsoluteLayout;
import utilities.others.Others;

public class TabPanel extends JPanel implements Controller {
    private Map<String, Controller> panels = new HashMap<>();
    private String current = "";
    private JTabbedPane tabs;

    public TabPanel(int direction, Map<String, Controller> panels) {
        initComponents(panels, direction);
        this.panels = panels;
    }

    private void initComponents(Map<String, Controller> panels, int direction) {
        tabs = new JTabbedPane();
        tabs.setTabPlacement(direction);
        tabs.setFont(new Font("Consolas", 0, 12));
        tabs.setPreferredSize(new Dimension(1280, 720));

        setLayout(new AbsoluteLayout());

        List<String> keys = new ArrayList<>(panels.keySet());
        keys.sort(String::compareTo);
        int maxLength = 1 + Collections.max(keys, (String a, String b) -> Integer.compare(a.length(), b.length())).length();

        for (String item : keys) {
            panels.get(item).getPanel().setSize(1280, 720);
            tabs.addTab(Others.leftPad(item, maxLength), panels.get(item).getPanel());
            this.panels.put(item, panels.get(item));
        }

        tabs.addChangeListener((ChangeEvent e) -> {
            if (getPanels(current) != null) getPanels(current).setVisible(false);
            current = tabs.getTitleAt(tabs.getSelectedIndex()).stripTrailing();
            getPanels(current).setVisible(true);
        });
        add(tabs, new AbsoluteConstraints(0, 0, 1280, 720));
    }

    public Controller getPanels(String panel) {
        return panels.get(panel);
    }

    @Override
    public void setVisible(boolean aFlag) {
        super.setVisible(aFlag);
        tabs.setSelectedIndex(0);
        for (String panel : panels.keySet()) {
            panels.get(panel).setVisible(aFlag && current.equals(panel));
        }
    }

    @Override
    public void resetState() {}

    @Override
    public JPanel getPanel() {
        return this;
    }
}
