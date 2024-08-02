/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utilities.ui;

import java.awt.BorderLayout;
import java.util.List;
import javax.swing.JComboBox;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import org.jfree.chart.ChartPanel;

public interface Ui {
    static void selectItem(JComboBox cb, Object item) {
        for (int i = 0; i < cb.getItemCount(); ++i)
            if (cb.getItemAt(i).equals(item)) cb.setSelectedIndex(i);
    }

    static void displayTable(JTable table, Object[]... data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (data == null) return;
        for (int i = 0; i < data.length; ++i) model.addRow(data[i]);
    }

    static void displayTable(JTable table, List<Object[]> data) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);

        if (data == null) return;
        for (var item : data) model.addRow(item);
    }

    static void clearTable(JTable table) {
        DefaultTableModel model = (DefaultTableModel) table.getModel();
        model.setRowCount(0);
    }

    static void updateComboBoxAll(JComboBox cb, List<String> item) {
        cb.removeAllItems();
        cb.addItem("TẤT CẢ");
        for (String i : item) cb.addItem(i);
        if (!item.isEmpty()) cb.setSelectedIndex(0);
    }

    static void updateComboBoxAll(JComboBox cb, String[] item) {
        cb.removeAllItems();
        cb.addItem("TẤT CẢ");
        for (String i : item) cb.addItem(i.toUpperCase());
        if (item.length > 0) cb.setSelectedIndex(0);
    }

    static void updateComboBox(JComboBox cb, List<String> item) {
        cb.removeAllItems();
        for (String i : item) cb.addItem(i);
        if (!item.isEmpty()) cb.setSelectedIndex(0);
    }

    static void updateComboBox(JComboBox cb, String[] item) {
        cb.removeAllItems();
        for (String i : item) cb.addItem(i.toUpperCase());
        if (item.length > 0) cb.setSelectedIndex(0);
    }

    static void displayGraph(JPanel panel, ChartPanel chart) {
        chart.setPreferredSize(panel.getSize());
        panel.removeAll();
        panel.setLayout(new java.awt.BorderLayout());
        panel.add(chart, BorderLayout.CENTER);
        panel.validate();
    }
}
