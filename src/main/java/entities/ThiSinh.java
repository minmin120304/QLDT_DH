/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import utilities.database.Database;
import utilities.database.Table;
import java.util.Date;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class ThiSinh extends Table {
    static Database<ThiSinh> db = null;

    public static Database<ThiSinh> getDB() {
        if (db == null) db = new Database<>(ThiSinh.class);
        return db;
    }

    String SBD;
    String hoTen;
    String sdt;
    String gioiTinh;
    String truong;
    String toHop;
    Date ngaySinh;
    double toan;
    double van;
    double anh;
    double mon1;
    double mon2;
    double mon3;

    public ThiSinh() {
    }

    public ThiSinh(String SBD, String hoTen, String sdt, String gioiTinh, String truong, String toHop, Date ngaySinh, double toan, double van, double anh, double mon1, double mon2, double mon3, String id) {
        super(id);
        this.SBD = SBD;
        this.hoTen = hoTen;
        this.sdt = sdt;
        this.gioiTinh = gioiTinh;
        this.truong = truong;
        this.toHop = toHop;
        this.ngaySinh = ngaySinh;
        this.toan = toan;
        this.van = van;
        this.anh = anh;
        this.mon1 = mon1;
        this.mon2 = mon2;
        this.mon3 = mon3;
    }

    public GioiTinh getGioiTinh() {
        return GioiTinh.getDB().QUERY_ID(gioiTinh);
    }

    public String getHoTen() {
        return hoTen;
    }

    public ToHop getToHop() {
        return ToHop.getDB().QUERY_ID(toHop);
    }

    public String getSBD() {
        return SBD;
    }

    public String getSdt() {
        return sdt;
    }

    public TruongHoc getTruong() {
        return TruongHoc.getDB().QUERY_ID(truong);
    }

    public Date getNgaySinh() {
        return ngaySinh;
    }

    public double getVan() {
        return van;
    }

    public double getToan() {
        return toan;
    }

    public double getAnh() {
        return anh;
    }

    public double getMon3() {
        return mon3;
    }

    public double getMon2() {
        return mon2;
    }

    public double getMon1() {
        return mon1;
    }

    public double getDiem(String diem) {
        switch (diem.toLowerCase()) {
            case "toan":
                return toan;
            case "van":
                return van;
            case "anh":
                return anh;
            case "ly":
            case "dia":
                return mon1;
            case "hoa":
            case "su":
                return mon2;
            case "sinh":
            case "gdcd":
                return mon3;
        }
        return -1;
    }

    @Override
    public Table export() {
        return new ThiSinh(SBD, hoTen, sdt, gioiTinh, truong, toHop, ngaySinh, toan, van, anh, mon1, mon2, mon3, id);
    }

    @Override
    public boolean isValidate() {
        return super.isValidate(); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/OverriddenMethodBody
    }
    
}
