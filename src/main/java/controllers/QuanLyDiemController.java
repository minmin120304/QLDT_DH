/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import be.quodlibet.boxable.BaseTable;
import be.quodlibet.boxable.Row;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFileChooser;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSpinner;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.filechooser.FileSystemView;
import entities.ThiSinh;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.common.PDRectangle;
import org.apache.pdfbox.pdmodel.font.PDFont;
import org.apache.pdfbox.pdmodel.font.PDType0Font;
import utilities.others.DateUtils;
import utilities.others.Others;
import static utilities.others.Others.moveLastPageToFirst;
import utilities.ui.Controller;
import utilities.ui.Ui;
import views.QuanLyDiemView;
import views.tabView.KhoiXetTuyenTK;

public class QuanLyDiemController implements Controller {
    List<ThiSinh> _thiSinh;
    QuanLyDiemView panel;

    JButton exportFB;
    JButton resetB;
    JButton sortBtn;
    JButton findB;

    JSpinner maxF;
    JSpinner minF;

    JRadioButton bothB;
    JRadioButton namB;
    JRadioButton nuB;

    JComboBox<String> monB;
    JComboBox<String> thF;

    JTable table;
    JTextField timKiemField;

    public QuanLyDiemController() {
        panel = new QuanLyDiemView();

        exportFB = panel.getExportFB();
        resetB = panel.getResetB();
        sortBtn = panel.getSortBtn();
        findB = panel.getFindB();

        maxF = panel.getMaxF();
        minF = panel.getMinF();

        bothB = panel.getBothB();
        namB = panel.getNamB();
        nuB = panel.getNuB();

        monB = panel.getMonB();
        thF = panel.getThF();
        table = panel.getTable();
        timKiemField = panel.getTimKiemField();

        findB.addActionListener(this::findBActionPerformed);
        thF.addItemListener(this::thFItemStateChanged);
        bothB.addActionListener(this::bothBActionPerformed);
        namB.addActionListener(this::namBActionPerformed);
        nuB.addActionListener(this::nuBActionPerformed);
        exportFB.addActionListener(this::exportFBActionPerformed);
        sortBtn.addActionListener(this::sortBtnActionPerformed);
        resetB.addActionListener(this::resetBActionPerformed);
    }

    Predicate<ThiSinh> getGioiTinhFilter() {
        if (namB.isSelected()) return i -> i.getGioiTinh().getID().equals("Nam");
        if (nuB.isSelected()) return i -> i.getGioiTinh().getID().equals("Nữ");
        return i -> true;
    }

    Predicate<ThiSinh> getTextFilter() {
        String text = timKiemField.getText();
        if (text.length() == 0) return i -> true;
        return i -> (i.getSBD()
                + i.getHoTen()
                + i.getSdt()
                + i.getTruong().getTenTruong()
                + i.getToHop()).contains(text);
    }

    boolean checkVal(double min, double max, double val) {
        return min <= val && val <= max;
    }

    Predicate<ThiSinh> getDiemFilter() {
        double min = (double) minF.getValue();
        double max = (double) maxF.getValue();

        if (thF.getSelectedIndex() == 0) return i -> {
                return checkVal(min, max, i.getAnh())
                        && checkVal(min, max, i.getToan())
                        && checkVal(min, max, i.getVan())
                        && checkVal(min, max, i.getMon1())
                        && checkVal(min, max, i.getMon2())
                        && checkVal(min, max, i.getMon3());
            };

        Predicate<ThiSinh> p = i -> {
            boolean a = i.getToHop().getID().equals(thF.getSelectedItem());
            System.err.println(a + "\n");
            return a;
        };
        switch (monB.getSelectedIndex()) {
            case 0 -> {
                return p.and(i -> checkVal(min, max, i.getAnh())
                        && checkVal(min, max, i.getToan())
                        && checkVal(min, max, i.getVan())
                        && checkVal(min, max, i.getMon1())
                        && checkVal(min, max, i.getMon2())
                        && checkVal(min, max, i.getMon3()));
            }
            case 1 -> {
                return p.and(i -> checkVal(min, max, i.getToan()));
            }
            case 2 -> {
                return p.and(i -> checkVal(min, max, i.getVan()));
            }
            case 3 -> {
                return p.and(i -> checkVal(min, max, i.getAnh()));
            }
            case 4 -> {
                return p.and(i -> checkVal(min, max, i.getMon1()));
            }
            case 5 -> {
                return p.and(i -> checkVal(min, max, i.getMon2()));
            }
            case 6 -> {
                return p.and(i -> checkVal(min, max, i.getMon3()));
            }
            default -> {
                return i -> true;
            }
        }
    }

    @Override
    public void setVisible(boolean aFlag) {
        panel.setVisible(aFlag); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
        if (aFlag) resetState();

    }

    @Override
    public void resetState() {
        displayTable(ThiSinh.getDB().QUERY());
        bothB.setSelected(true);
        namB.setSelected(false);
        nuB.setSelected(false);

        thF.setSelectedIndex(0);
        Ui.updateComboBox(monB, new String[]{});
        monB.setEnabled(false);

        minF.setValue(0.0);
        maxF.setValue(10.0);

        timKiemField.setText("");
        namB.setSelected(false);
        nuB.setSelected(false);
        bothB.setSelected(true);
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

    private void findBActionPerformed(ActionEvent evt) {
        displayTable(ThiSinh.getDB().QUERY(getDiemFilter().and(getGioiTinhFilter()).and(getTextFilter())));
    }

    String[] title = {"STT", "SBD", "Ho va ten", "Gioi tinh", "Ngay sinh", "Tinh", "Truong", "SDT", "Khoi thi", "Toan", "Van", "Anh", "Mon 1", "Mon 2", "Mon 3"};
    int[] size = {4, 9, 15, 5, 7, 9, 9, 9, 5, 4, 4, 4, 5, 5, 5};

    void writePDF(File file) throws IOException {
        PDDocument mainDocument = new PDDocument();
        PDPage myPage = new PDPage(new PDRectangle(PDRectangle.A4.getHeight(), PDRectangle.A4.getWidth()));

        float height = myPage.getMediaBox().getHeight(), width = myPage.getMediaBox().getWidth();

        try (PDPageContentStream contentStream = new PDPageContentStream(mainDocument, myPage)) {
            PDFont font = PDType0Font.load(mainDocument, new File("src/resources/SourceCodePro-Regular.ttf"));
            float margin = 10;
            float y0 = height - 20;
            float tableWidth = width - (2 * margin);

            BaseTable table = new BaseTable(y0, y0, margin, tableWidth, margin, mainDocument, myPage, true, true);
            Row<PDPage> row = table.createRow(20);

            for (int i = 0; i < title.length; ++i) row.createCell(size[i], title[i]).setFont(font);
            table.addHeaderRow(row);

            for (int i = 0; i < this._thiSinh.size(); ++i) {
                ThiSinh ts = this._thiSinh.get(i);

                Row<PDPage> row_ = table.createRow(15);
                row_.createCell(size[0], String.valueOf(i + 1)).setFont(font);
                row_.createCell(size[1], ts.getSBD()).setFont(font);
                row_.createCell(size[2], ts.getHoTen()).setFont(font);
                row_.createCell(size[3], ts.getGioiTinh().getID()).setFont(font);
                row_.createCell(size[4], DateUtils.date2Str(ts.getNgaySinh())).setFont(font);
                row_.createCell(size[5], ts.getTruong().getTinh().getID()).setFont(font);
                row_.createCell(size[6], ts.getTruong().getTenTruong()).setFont(font);
                row_.createCell(size[7], ts.getSdt()).setFont(font);
                row_.createCell(size[8], ts.getToHop().getID()).setFont(font);
                row_.createCell(size[9], String.valueOf(ts.getToan())).setFont(font);
                row_.createCell(size[10], String.valueOf(ts.getVan())).setFont(font);
                row_.createCell(size[11], String.valueOf(ts.getAnh())).setFont(font);
                row_.createCell(size[12], String.valueOf(ts.getMon1())).setFont(font);
                row_.createCell(size[13], String.valueOf(ts.getMon2())).setFont(font);
                row_.createCell(size[14], String.valueOf(ts.getMon3())).setFont(font);
            }
            table.draw();
            contentStream.close();

            mainDocument.addPage(myPage);
            moveLastPageToFirst(mainDocument);

            mainDocument.save(file);
            mainDocument.close();
        }
    }

    private void exportFBActionPerformed(ActionEvent evt) {
        JFileChooser j = new JFileChooser(FileSystemView.getFileSystemView().getHomeDirectory());
        j.setSelectedFile(new File("phoDiem.pdf"));
        j.showSaveDialog(null);

        File result = j.getSelectedFile();
        if (result == null) return;

        Others.CreateFile(result.getAbsolutePath());
        try {
            writePDF(result);
        } catch (IOException ex) {
            Logger.getLogger(KhoiXetTuyenTK.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private void sortBtnActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        this._thiSinh.sort(((a, b) -> a.getSBD().compareTo(b.getSBD())));
        displayTable(_thiSinh);
    }

    private void namBActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        nuB.setSelected(false);
        bothB.setSelected(false);
    }

    private void bothBActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        nuB.setSelected(false);
        namB.setSelected(false);
    }

    private void nuBActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        namB.setSelected(false);
        bothB.setSelected(false);
    }

    private void resetBActionPerformed(ActionEvent evt) {
        // TODO add your handling code here:
        resetState();
    }

    private void thFItemStateChanged(ItemEvent evt) {
        // TODO add your handling code here:
        monB.setEnabled(true);
        switch (thF.getSelectedIndex()) {
            case 1 -> Ui.updateComboBoxAll(monB, Others.KHTN);
            case 2 -> Ui.updateComboBoxAll(monB, Others.KHXH);
            default -> {
                Ui.updateComboBox(monB, new ArrayList<>());
                monB.setEnabled(false);
            }
        }
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

}
