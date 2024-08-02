/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package app;

import entities.ToHop;
import entities.TruongHoc;
import entities.GioiTinh;
import entities.ThiSinh;
import entities.TinhThanh;
import utilities.database.Database;
import java.util.ArrayList;
import java.util.List;
import utilities.others.Randoms;

public interface Models {
    static Database<GioiTinh> gioiTinh = GioiTinh.getDB();
    static Database<ThiSinh> thiSinh = ThiSinh.getDB();
    static Database<TinhThanh> tinhThanh = TinhThanh.getDB();
    static Database<ToHop> toHop = ToHop.getDB();
    static Database<TruongHoc> truongHoc = TruongHoc.getDB();

    static void SaveDB() {
        gioiTinh.SAVE();
        thiSinh.SAVE();
        tinhThanh.SAVE();
        toHop.SAVE();
        truongHoc.SAVE();
    }

    static void testAddFunc() {
        List<ToHop> th_ = ToHop.getDB().QUERY();
        List<TruongHoc> truong_ = TruongHoc.getDB().QUERY();
        List<ThiSinh> ts_ = new ArrayList<>();
        for (int i = 0; i < 1000; ++i) {
            String truongID = truong_.get(Randoms.randInt(0, truong_.size() - 1)).getID();
            String thID = th_.get(Randoms.randInt(0, th_.size() - 1)).getID();
            ThiSinh ts = new ThiSinh(
                    Randoms.randNumStr(10), // SBD
                    Randoms.randOnlyStr(10), // hoTen
                    Randoms.randNumStr(10), // sdt
                    Randoms.randInt(0, 1) == 1 ? "Nam" : "Nữ", // gioiTinh
                    truongID, // truong
                    thID, // toHop
                    Randoms.randDate(), // ngaySinh
                    (double) Randoms.randInt(0, 1000) / 100, // toan
                    (double) Randoms.randInt(0, 1000) / 100, // van
                    (double) Randoms.randInt(0, 1000) / 100, // anh
                    (double) Randoms.randInt(0, 1000) / 100, // mon1
                    (double) Randoms.randInt(0, 1000) / 100, // mon2
                    (double) Randoms.randInt(0, 1000) / 100, // mon3
                    Randoms.genID() // id
            );
            ts_.add(ts);
        }

        ThiSinh.getDB().ADD(ts_);

        SaveDB();
    }
}
