/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import com.toedter.calendar.JDateChooser;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ComponentEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTable;
import javax.swing.JTextField;
import entities.GioiTinh;
import entities.ThiSinh;
import entities.TinhThanh;
import entities.ToHop;
import entities.TruongHoc;
import utilities.database.ValidateData;
import utilities.others.DateUtils;
import utilities.others.Randoms;
import utilities.ui.Controller;
import utilities.ui.Ui;
import views.ThiSinhView;

public class ThiSinhController implements Controller {
    List<ThiSinh> _thiSinh;
    ThiSinh temp;

    ThiSinhView panel;

    private JButton addBtn;
    private JButton clearBtn;
    private JButton delBtn;
    private JButton editBtn;
    private JButton reloadBtn;

    private JCheckBox sortF;
    private JTable table;
    private JComboBox<String> gtF;
    private JComboBox<String> thF;
    private JComboBox<String> tinhF;
    private JComboBox<String> truongF;
    private JTextField tenField;
    private JTextField sbdField;
    private JTextField sdtField;

    private JTextField toanField;
    private JTextField vanField;
    private JTextField nnField;
    private JTextField mon1Field;
    private JTextField mon2Field;
    private JTextField mon3Field;

    private JDateChooser ngaySinhF;

    public ThiSinhController() {
        panel = new ThiSinhView();
        addBtn = panel.getAddBtn();
        clearBtn = panel.getClearBtn();
        delBtn = panel.getDelBtn();
        editBtn = panel.getEditBtn();

        sortF = panel.getSortF();
        table = panel.getTable();
        gtF = panel.getGtF();
        thF = panel.getThF();
        tinhF = panel.getTinhF();
        truongF = panel.getTruongF();
        tenField = panel.getTenField();
        sbdField = panel.getSbdField();
        sdtField = panel.getSdtField();

        toanField = panel.getToanField();
        vanField = panel.getVanField();
        nnField = panel.getNnField();
        mon1Field = panel.getMon1Field();
        mon2Field = panel.getMon2Field();
        mon3Field = panel.getMon3Field();

        ngaySinhF = panel.getNgaySinhF();
        table.addMouseListener(new MouseAdapter() {
            @Override
            public void mouseClicked(MouseEvent evt) {
                tableMouseClicked(evt);
            }
        });
        tenField.addActionListener(this::tenFieldActionPerformed);
        addBtn.addActionListener(this::addBtnActionPerformed);
        editBtn.addActionListener(this::editBtnActionPerformed);
        delBtn.addActionListener(this::delBtnActionPerformed);
        gtF.addActionListener(this::gtFActionPerformed);
        tinhF.addItemListener(this::tinhFItemStateChanged);
        clearBtn.addActionListener(this::clearBtnActionPerformed);
//        reloadBtn.addActionListener(this::reloadBtnActionPerformed);
        sortF.addActionListener(this::sortFActionPerformed);
    }

    public void showMessage(String message) {
        JOptionPane.showMessageDialog(panel, message);
    }

    @Override
    public void setVisible(boolean aFlag) {
//        super.setVisible(aFlag); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        panel.setVisible(aFlag);

        if (aFlag == false) return;
        resetState();
    }

    @Override
    public void resetState() {
        Ui.updateComboBox(gtF, GioiTinh.getDB().MAP(i -> i.getID()).toList());
        Ui.updateComboBox(tinhF, TinhThanh.getDB().MAP(i -> i.getID()).toList());
        Ui.updateComboBox(thF, ToHop.getDB()
                .QUERY(i -> !i.getID().equals("KHAC"))
                .stream().map(i -> i.getID()).toList()
        );
        updateTruongF();

        clearField();
        displayTable(ThiSinh.getDB().QUERY());
        updateState(true);
    }

    void displayTable(List<ThiSinh> thiSinh) {
        _thiSinh = thiSinh;
        List<Object[]> data = new ArrayList<>();
        for (int j = 0; j < _thiSinh.size(); ++j) {
            ThiSinh i = _thiSinh.get(j);
            data.add(new Object[]{
                j + 1,
                i.getSBD(),
                i.getHoTen(),
                i.getGioiTinh(),
                DateUtils.date2Str(i.getNgaySinh()),
                i.getTruong().getTinh(),
                i.getTruong().getTenTruong(),
                i.getSdt(),
                i.getToHop(),
                i.getToan(),
                i.getVan(),
                i.getAnh(),
                i.getMon1(),
                i.getMon2(),
                i.getMon3()
            });
        }

        Ui.displayTable(table, data);
    }

    ThiSinh validateField() {
        if (!ValidateData.validateInt(sbdField.getText())) {
            showMessage("SBD khong hop le. (Chi duoc chua chu so)");
            sbdField.requestFocusInWindow();
            return null;
        }
        if (!ValidateData.validateStr(tenField.getText())) {
            tenField.requestFocusInWindow();
            showMessage("Ho Ten khong hop le. (Khong duoc chua chu so)");
            return null;
        }
        if (!ValidateData.validateDate(ngaySinhF.getDate())) {
            ngaySinhF.requestFocusInWindow();
            showMessage("Ngay sinh khong hop le.");
            return null;
        }
        if (!ValidateData.validateInt(sdtField.getText())) {
            sdtField.requestFocusInWindow();
            showMessage("SDT khong hop le.");
            return null;
        }

        if (!ValidateData.validateDiem(toanField.getText())) {

            toanField.requestFocusInWindow();
            showMessage("Diem Toan khong hop le.");
            return null;
        }

        if (!ValidateData.validateDiem(vanField.getText())) {
            vanField.requestFocusInWindow();
            showMessage("Diem Van khong hop le.");
            return null;
        }
        if (!ValidateData.validateDiem(nnField.getText())) {
            nnField.requestFocusInWindow();
            showMessage("Diem NgoaiNgu khong hop le.");
            return null;
        }
        if (!ValidateData.validateDiem(mon1Field.getText())) {
            mon1Field.requestFocusInWindow();
            showMessage("Diem mon 1 khong hop le.");
            return null;
        }
        if (!ValidateData.validateDiem(mon2Field.getText())) {
            mon2Field.requestFocusInWindow();
            showMessage("Diem mon 2 khong hop le.");
            return null;
        }
        if (!ValidateData.validateDiem(mon3Field.getText())) {
            mon3Field.requestFocusInWindow();
            showMessage("Diem mon 3 khong hop le.");
            return null;
        }

        return new ThiSinh(
                sbdField.getText(),
                tenField.getText(),
                sdtField.getText(),
                (String) gtF.getSelectedItem(),
                TruongHoc.getDB().QUERY_ONE(i -> i.getTenTruong().equals((String) truongF.getSelectedItem())).getID(),
                (String) thF.getSelectedItem(),
                ngaySinhF.getDate(),
                Double.parseDouble(toanField.getText()),
                Double.parseDouble(vanField.getText()),
                Double.parseDouble(nnField.getText()),
                Double.parseDouble(mon1Field.getText()),
                Double.parseDouble(mon2Field.getText()),
                Double.parseDouble(mon3Field.getText()),
                temp == null ? Randoms.genID() : temp.getID()
        );
    }

    void updateTruongF() {
        int index = tinhF.getSelectedIndex();
        if (index == -1) {
            truongF.removeAllItems();
            return;
        }

        Ui.updateComboBox(truongF, TruongHoc.getDB()
                .QUERY(i -> i.getTinh().getID().equals((String) tinhF.getItemAt(index)))
                .stream().map(i -> i.getTenTruong()).toList()
        );
    }

    void clearField() {
        sbdField.setText("");
        tenField.setText("");
        ngaySinhF.setDate(null);
        sdtField.setText("");

        toanField.setText("");
        vanField.setText("");
        nnField.setText("");
        mon1Field.setText("");
        mon2Field.setText("");
        mon3Field.setText("");
    }

    void updateState(boolean state) {
        addBtn.setEnabled(state);
        editBtn.setEnabled(!state);
        delBtn.setEnabled(!state);
    }

    private void addBtnActionPerformed(ActionEvent evt) {
        ThiSinh a = validateField();
        if (a == null) return;
        ThiSinh.getDB().ADD(a);
        resetState();
    }

    private void editBtnActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        System.out.println("Testing");
        ThiSinh result = validateField();
        if (result == null) return;

        ThiSinh.getDB().EDIT(result);
        resetState();
    }

    private void delBtnActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        ThiSinh.getDB().DELETE(temp);
        resetState();
    }

    private void clearBtnActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        resetState();
    }

    private void reloadBtnActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        resetState();
    }

    private void tinhFItemStateChanged(ItemEvent evt) {
        // TODO add your handling code here:
        updateTruongF();
    }

    private void tableMouseClicked(MouseEvent evt) {
        // TODO add your handling code here:
        int row = table.getSelectedRow();
        if (row < 0) return;

        temp = _thiSinh.get(row);
        sbdField.setText(temp.getSBD());
        tenField.setText(temp.getHoTen());
        Ui.selectItem(gtF, temp.getGioiTinh().getID());
        ngaySinhF.setDate(temp.getNgaySinh());
        Ui.selectItem(tinhF, temp.getTruong().getTinh().getID());
        updateTruongF();
        Ui.selectItem(truongF, temp.getTruong().getTenTruong());
        sdtField.setText(temp.getSdt());
        Ui.selectItem(thF, temp.getToHop().getID());

        toanField.setText(String.valueOf(temp.getToan()));
        vanField.setText(String.valueOf(temp.getVan()));
        nnField.setText(String.valueOf(temp.getAnh()));
        mon1Field.setText(String.valueOf(temp.getMon1()));
        mon2Field.setText(String.valueOf(temp.getMon2()));
        mon3Field.setText(String.valueOf(temp.getMon3()));
        updateState(false);
    }

    private void sortFActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void formComponentResized(ComponentEvent evt) {
        // TODO add your handling code here:
        adjustComponentSizes();
    }

    private void gtFActionPerformed(java.awt.event.ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void tenFieldActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
    }

    private void adjustComponentSizes() {
        Dimension size = panel.getSize();
        int newWidth = size.width;
        int newHeight = size.height;
        for (Component component : panel.getComponents())
            if (component instanceof JComponent) {
                JComponent jComponent = (JComponent) component;
                Rectangle bounds = jComponent.getBounds();
                jComponent.setBounds(bounds.x, bounds.y, newWidth / 2, bounds.height); // Example resize logic
            }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

}
