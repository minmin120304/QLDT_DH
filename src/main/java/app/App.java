/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 */
package app;

import static app.Models.gioiTinh;
import static app.Models.tinhThanh;
import static app.Models.toHop;
import static app.Models.truongHoc;
import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.List;
import entities.GioiTinh;
import entities.TinhThanh;
import entities.ToHop;
import entities.TruongHoc;
import utilities.others.Randoms;

public class App {
//    static final boolean TEST = true;
    static final boolean TEST = false;

    static void initParam() {
        gioiTinh.ADD(
                new GioiTinh("Nam"),
                new GioiTinh("Nữ")
        );
        System.out.println("gioiTinh");

        toHop.ADD(
                new ToHop("KHTN"),
                new ToHop("KHXH")
        //                new ToHop("KHAC")
        );
        System.out.println("toHop");

        if (tinhThanh.COUNT() == 0) {
            List<TinhThanh> tt = new ArrayList<>();
            for (int i = 0; i < 5; ++i) tt.add(new TinhThanh(Randoms.randStr(5)));
            tinhThanh.ADD(
                    new TinhThanh("A"),
                    new TinhThanh("B"),
                    new TinhThanh("C"),
                    new TinhThanh("D"),
                    new TinhThanh("E"),
                    new TinhThanh("F")
            );
            System.out.println("tinhThanh");
        }
        truongHoc.ADD(
                new TruongHoc("A1", "A", Randoms.genID()),
                new TruongHoc("A2", "A", Randoms.genID()),
                new TruongHoc("A3", "A", Randoms.genID()),
                new TruongHoc("A4", "A", Randoms.genID()),
                new TruongHoc("A5", "A", Randoms.genID()),
                new TruongHoc("A6", "A", Randoms.genID()),
                new TruongHoc("B1", "B", Randoms.genID()),
                new TruongHoc("B2", "B", Randoms.genID()),
                new TruongHoc("B3", "B", Randoms.genID()),
                new TruongHoc("B4", "B", Randoms.genID()),
                new TruongHoc("B5", "B", Randoms.genID()),
                new TruongHoc("C1", "C", Randoms.genID()),
                new TruongHoc("C2", "C", Randoms.genID()),
                new TruongHoc("C3", "C", Randoms.genID()),
                new TruongHoc("C4", "C", Randoms.genID()),
                new TruongHoc("C5", "C", Randoms.genID()),
                new TruongHoc("D1", "D", Randoms.genID()),
                new TruongHoc("D2", "D", Randoms.genID()),
                new TruongHoc("D3", "D", Randoms.genID()),
                new TruongHoc("D4", "D", Randoms.genID()),
                new TruongHoc("D5", "D", Randoms.genID()),
                new TruongHoc("E1", "E", Randoms.genID()),
                new TruongHoc("E2", "E", Randoms.genID()),
                new TruongHoc("E3", "E", Randoms.genID()),
                new TruongHoc("E4", "E", Randoms.genID()),
                new TruongHoc("E5", "E", Randoms.genID()),
                new TruongHoc("F1", "F", Randoms.genID()),
                new TruongHoc("F2", "F", Randoms.genID()),
                new TruongHoc("F3", "F", Randoms.genID()),
                new TruongHoc("F4", "F", Randoms.genID()),
                new TruongHoc("F5", "F", Randoms.genID())
        );
        Models.SaveDB();
    }

    public static void main(String[] args) {
        try {
//             Metal, Nimbus, CDE/Motif, Windows, Windows Classic 
            for (javax.swing.UIManager.LookAndFeelInfo info : javax.swing.UIManager.getInstalledLookAndFeels())
                if ("Windows".equals(info.getName())) {
                    javax.swing.UIManager.setLookAndFeel(info.getClassName());
                    break;
                }
        } catch (ClassNotFoundException | InstantiationException | IllegalAccessException | javax.swing.UnsupportedLookAndFeelException ex) {
        }
//        initParam();
//        if (TEST) Models.testAddFunc();
        EventQueue.invokeLater(() -> Controllers.ChangePanel("dangNhap"));
    }
}
