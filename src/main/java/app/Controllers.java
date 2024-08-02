/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

//import PieChart_AWT;
import controllers.LogInController;
import controllers.QuanLyDiemController;
import controllers.ThiSinhController;
import controllers.ThongKeController;
import utilities.ui.*;
//import controlller.TabPanel;
import java.util.Map;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.WindowConstants;
import views.QuanLyDiemView;
import views.ThiSinhView;
import views.ThongKeView;

public interface Controllers {
    static Map<String, Controller> panels = Map.ofEntries(
            Map.entry("dangNhap", new LogInController()),
            Map.entry("mainView", new TabPanel(JTabbedPane.TOP, Map.ofEntries(
                    Map.entry("1. Quản lý điểm", new QuanLyDiemController()),
                    Map.entry("2. Thí sinh", new ThiSinhController()),
                    Map.entry("3. Thống kê", new ThongKeController())
            )))
    );

    static ContainerFrame views = new ContainerFrame(WindowConstants.EXIT_ON_CLOSE, panels);

    public static void ChangePanel(String name) {
        views.changePanel(name);
    }
}
