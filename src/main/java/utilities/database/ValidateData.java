/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package utilities.database;

import java.util.Date;
import java.util.GregorianCalendar;

public interface ValidateData {
    static boolean validateInt(String x) {
        try {
            if (x.length() == 0) return false;
            return x.chars().allMatch(Character::isDigit);
        } catch (NumberFormatException e) {
            return false;
        }
    }

    static boolean validateStr(String x) {
        if (x.length() == 0) return false;
        return !x.chars().anyMatch(Character::isDigit);
    }

    static boolean validateDate(Date d) {
        if (d == null) return false;
        Date now = new Date();
        Date past = new GregorianCalendar(1900, 0, 1).getTime();
        return d.after(past) && (d.before(now) || d.equals(now));
    }

    static boolean validateDiem(String diem) {
        if (diem.length() == 0) return false;
        double d = Double.parseDouble(diem);
        return 0 <= d && d <= 10;
    }
}
