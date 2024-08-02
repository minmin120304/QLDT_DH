/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controllers;

import java.util.List;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;
import entities.GioiTinh;
import entities.ThiSinh;
import utilities.ui.Controller;
import views.ThongKeView;

public class ThongKeController implements Controller {
    ThongKeView panel;

    JTextField diaTBF;
    JTextField suTBF;
    JTextField gdcdTBF;

    JTextField lyTBF;
    JTextField hoaTBF;
    JTextField sinhTBF;

    JTextField tongTSF;
    JTextField toanTBF;
    JTextField anhTBF;
    JTextField vanTBF;

    JTextField maxDXHF;
    JTextField maxdTNF;
    JTextField minDTNF;
    JTextField minDXHF;

    JTextField namF;
    JTextField nuF;

    JTabbedPane tabs;

    public ThongKeController() {
        panel = new ThongKeView();

        diaTBF = panel.getDiaTBF();
        suTBF = panel.getSuTBF();
        gdcdTBF = panel.getGdcdTBF();

        lyTBF = panel.getLyTBF();
        hoaTBF = panel.getHoaTBF();
        sinhTBF = panel.getSinhTBF();

        tongTSF = panel.getTongTSF();
        toanTBF = panel.getToanTBF();
        anhTBF = panel.getAnhTBF();
        vanTBF = panel.getVanTBF();

        maxDXHF = panel.getMaxDXHF();
        maxdTNF = panel.getMaxdTNF();
        minDTNF = panel.getMinDTNF();
        minDXHF = panel.getMinDXHF();
        
        namF = panel.getNamF();
        nuF = panel.getNuF();
        
        tabs = panel.getTabs();
    }

    @Override
    public void resetState() {
        int c = ThiSinh.getDB().COUNT();
        tongTSF.setText(String.valueOf(ThiSinh.getDB().COUNT()));
        List<GioiTinh> gt = GioiTinh.getDB().QUERY();
        namF.setText(String.valueOf(ThiSinh.getDB().COUNT(i -> i.getGioiTinh().equals(gt.get(0)))));
        nuF.setText(String.valueOf(ThiSinh.getDB().COUNT(i -> i.getGioiTinh().equals(gt.get(1)))));

        List<ThiSinh> tsTN = ThiSinh.getDB().QUERY((ThiSinh ts) -> ts.getToHop().equals("KHTN"));
        List<ThiSinh> tsXH = ThiSinh.getDB().QUERY((ThiSinh ts) -> ts.getToHop().equals("KHXH"));

        List<Double> tn = tsTN.stream().map(i -> i.getAnh() + i.getToan() + i.getVan() + i.getMon1() + i.getMon2() + i.getMon3()).toList();
        List<Double> xh = tsXH.stream().map(i -> i.getAnh() + i.getToan() + i.getVan() + i.getMon1() + i.getMon2() + i.getMon3()).toList();
//        tn.sort();

        double maxTN = 0, minTN = 0, maxXH = 0, minXH = 0;
        if (!tn.isEmpty()) {
            maxTN = tn.stream().max((a, b) -> a.compareTo(b)).get();
            minTN = tn.stream().max((a, b) -> b.compareTo(a)).get();
        }
        if (!xh.isEmpty()) {
            maxXH = xh.stream().max((a, b) -> a.compareTo(b)).get();
            minXH = xh.stream().max((a, b) -> b.compareTo(a)).get();

        }

        maxdTNF.setText(String.format("%.2f", maxTN));
        minDTNF.setText(String.format("%.2f", minTN));

        maxDXHF.setText(String.format("%.2f", maxXH));
        minDXHF.setText(String.format("%.2f", minXH));

        double tbToan = ThiSinh.getDB().MAP(i -> i.getToan()).reduce(0.0, (acc, i) -> acc + i) / c;
        double tbVan = ThiSinh.getDB().MAP(i -> i.getVan()).reduce(0.0, (acc, i) -> acc + i) / c;
        double tbNN = ThiSinh.getDB().MAP(i -> i.getAnh()).reduce(0.0, (acc, i) -> acc + i) / c;
        toanTBF.setText(String.format("%.2f", tbToan));
        vanTBF.setText(String.format("%.2f", tbVan));
        anhTBF.setText(String.format("%.2f", tbNN));

//        List<ThiSinh> tn = ThiSinh.getDB().QUERY((ThiSinh i) -> i.getToHop().getID().equals("KHTN"));
        int tnc = tn.size();
        double tbLy = tsTN.stream().map(i -> i.getMon1()).reduce(0.0, (acc, i) -> acc + i);
        double tbHoa = tsTN.stream().map(i -> i.getMon2()).reduce(0.0, (acc, i) -> acc + i);
        double tbSinh = tsTN.stream().map(i -> i.getMon3()).reduce(0.0, (acc, i) -> acc + i);
        lyTBF.setText(String.format("%.2f", tbLy / tnc));
        hoaTBF.setText(String.format("%.2f", tbHoa / tnc));
        sinhTBF.setText(String.format("%.2f", tbSinh / tnc));

        int xhc = tn.size();
        double tbDia = tsXH.stream().map(i -> i.getMon1()).reduce(0.0, (acc, i) -> acc + i);
        double tbSu = tsXH.stream().map(i -> i.getMon2()).reduce(0.0, (acc, i) -> acc + i);
        double tbGDCD = tsXH.stream().map(i -> i.getMon3()).reduce(0.0, (acc, i) -> acc + i);
        diaTBF.setText(String.format("%.2f", tbDia / xhc));
        suTBF.setText(String.format("%.2f", tbSu / xhc));
        gdcdTBF.setText(String.format("%.2f", tbGDCD / xhc));
    }

    @Override
    public void setVisible(boolean aFlag) {
        panel.setVisible(aFlag);
        if (aFlag) resetState();
    }

    @Override
    public JPanel getPanel() {
        return panel;
    }

}
