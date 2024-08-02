package utilities.others;

import java.text.ParseException;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.UUID;

public interface Randoms {
    static final String Z = "1234567890qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    static final String Z1 = "qwertyuiopasdfghjklzxcvbnmQWERTYUIOPASDFGHJKLZXCVBNM";
    static final String Z2 = "0123456789";

    static int randInt(int min, int max) {
        return (int) Math.floor(Math.random() * (max - min + 1)) + min;
    }

    static String randStr(int len) {
        String res = "";
        for (int i = 0; i < len; ++i)
            res += Z.charAt(randInt(0, Z.length() - 1));
        return res;
    }

    static String randOnlyStr(int len) {
        String res = "";
        for (int i = 0; i < len; ++i)
            res += Z1.charAt(randInt(0, Z1.length() - 1));
        return res;
    }

    static String randNumStr(int len) {
        String res = "";
        for (int i = 0; i < len; ++i)
            res += Z2.charAt(randInt(0, Z2.length() - 1));
        return res;
    }

    static Date randDate() {
        try {
            int nam = randInt(1900, 2024);
            int thang = randInt(1, 12);
            int ngay = randInt(1, DateUtils.checkMonth(thang, nam));

            return new GregorianCalendar(nam, thang, ngay).getTime();
        } catch (Exception e) {
            return null;
        }
    }

    static String genID() {
        return UUID.randomUUID().toString();
    }
}
