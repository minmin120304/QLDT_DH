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
public class TinhThanh extends Table {
    static Database<TinhThanh> db = null;

    public static Database<TinhThanh> getDB() {
        if (db == null) db = new Database<>(TinhThanh.class);
        return db;
    }

    public TinhThanh() {
        this("");
    }

    public TinhThanh(String id) {
        super(id);
    }

    @Override
    public Table export() {
        return new TinhThanh( id);
    }
//    @Override
//    public Map exportMap() {
//        Map data = new HashMap();
//        data.put("id", id);
//        return data;
//    }
}
