package utilities.others;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Period;
import java.time.ZoneId;
import java.util.Date;

public interface DateUtils {
    static final String formatDateString = "dd/MM/yyyy";
    static DateFormat format = new SimpleDateFormat(formatDateString);

    static Date str2Date(String str) {
        try {
            return format.parse(str);
        } catch (ParseException e) {
            return null;
        }
    }

    static String date2Str(Date date) {
        if (date == null)
            return "";
        return format.format(date);
    }

    static boolean checkField(String ngay, String thang, String nam) {
        return Integer.parseInt(ngay) < checkMonth(Integer.parseInt(thang), Integer.parseInt(nam));
    }

    static String createStrDate(String ngay, String thang, String nam) {
        return ngay + "/" + thang + "/" + nam;
    }

    static int checkMonth(int thang, int nam) {
        switch (thang) {
            case 1, 3, 5, 7, 8, 10, 12 -> {
                return 31;
            }
            case 4, 6, 9, 11 -> {
                return 30;
            }
            case 2 -> {
                return isLeapYear(nam) ? 29 : 28;
            }
            default -> {
                return 0;
            }
        }
    }

    static boolean isLeapYear(int year) {
        if (year % 4 != 0) return false;
        else if (year % 400 == 0) return true;
        else if (year % 100 == 0) return false;
        return true;
    }

    static int calcAge(Date birthday) {
        LocalDate date1 = new Date().toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        LocalDate date2 = birthday.toInstant().atZone(ZoneId.systemDefault()).toLocalDate();
        return Math.abs(Period.between(date2, date1).getYears());
    }
}
