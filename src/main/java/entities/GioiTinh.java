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
public class GioiTinh extends Table {
    static Database<GioiTinh> db = null;

    public static Database<GioiTinh> getDB() {
        if (db == null) db = new Database<>(GioiTinh.class);
        return db;
    }

    public GioiTinh() {
        this("");
    }

    public GioiTinh(String id) {
        super(id);
    }

    @Override
    public Table export() {
        return new GioiTinh(id);
    }
//    @Override
//    public Map exportMap() {
//        Map data = new HashMap();
//        data.put("id", id);
//        return data;
//    }
}
