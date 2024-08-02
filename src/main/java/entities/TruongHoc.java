/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package entities;

import utilities.database.Database;
import utilities.database.Table;
import java.util.HashMap;
import java.util.Map;
import javax.xml.bind.annotation.XmlAccessType;
import javax.xml.bind.annotation.XmlAccessorType;

@XmlAccessorType(XmlAccessType.FIELD)
public class TruongHoc extends Table {
    static Database<TruongHoc> db = null;

    public static Database<TruongHoc> getDB() {
        if (db == null) db = new Database<>(TruongHoc.class);
        return db;
    }

    String tenTruong;
    String tinh;

    public TruongHoc() {
        this("", "", "");
    }

    public TruongHoc(String tenTruong, String tinh, String id) {
        super(id);
        this.tenTruong = tenTruong;
        this.tinh = tinh;
    }

    public String getTenTruong() {
        return tenTruong;
    }

    public TinhThanh getTinh() {
        return TinhThanh.getDB().QUERY_ID(tinh);
    }

    @Override
    public Table export() {
        return new TruongHoc(tenTruong, tinh, id);
    }

//    @Override
//    public Map exportMap() {
//        Map result = new HashMap<>();
//        result.put("id", id);
//        result.put("tenTruong", tenTruong);
//        result.put("tinh", getTinh());
//
//        return result;
//    }

}
