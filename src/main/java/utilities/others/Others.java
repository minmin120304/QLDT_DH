/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package utilities.others;

import entities.ThiSinh;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageTree;

public interface Others {
    static final String NUM_FORMAT = "#,###";
    static final String[] MON = {"toán", "văn", "ngoại ngữ", "vật lý", "hóa học", "sinh học", "địa lý", "lịch sử", "gdcd"};
    static final String[] MAMON = {"toan", "van", "anh", "ly", "hoa", "sinh", "dia", "su", "gdcd"};
    static final String[] KHTN = {"toán", "văn", "ngoại ngữ", "vật lý", "hóa học", "sinh học"};
    static final String[] KHXH = {"toán", "văn", "ngoại ngữ", "địa lý", "lịch sử", "gdcd"};
    static final Map<String, String[]> khoiToHop = new HashMap<>() {
        {
            put("A00", new String[]{"KHTN", "toan", "ly", "hoa"});
            put("A01", new String[]{"KHTN", "toan", "ly", "anh"});
            put("B00", new String[]{"KHTN", "toan", "hoa", "sinh"});
            put("D07", new String[]{"KHTN", "toan", "hoa", "anh"});
            put("D01", new String[]{"", "toan", "van", "anh"});
        }
    };

    public static PDDocument moveLastPageToFirst(PDDocument documentToRearrangePages) {
        PDPageTree allPages = documentToRearrangePages.getDocumentCatalog().getPages();
        if (allPages.getCount() > 1) {
            PDPage lastPage = allPages.get(allPages.getCount() - 1);
            allPages.remove(allPages.getCount() - 1);
            PDPage firstPage = allPages.get(0);
            allPages.insertBefore(lastPage, firstPage);
        }
        return documentToRearrangePages;
    }

    static String convertMaMonToMon(String maMon) {
        return switch (maMon) {
            case "toan" ->
                "toán";
            case "van" ->
                "văn";
            case "anh" ->
                "ngoại ngữ";
            case "ly" ->
                "vật lý";
            case "hoa" ->
                "hóa học";
            case "sinh" ->
                "sinh học";
            case "dia" ->
                "địa lý";
            case "su" ->
                "lịch sử";
            case "gdcd" ->
                "gdcd";
            default ->
                "";
        };
    }

    static double getTongDiem(ThiSinh thiSinh, String toHop) {
        double result = 0;
        String[] mon = khoiToHop.get(toHop);
        if (mon == null) return 0.0;

        if (mon[0].length() > 0 && !thiSinh.getToHop().getID().equals(mon[0])) return -1.0;

        for (int i = 1; i < 4; ++i) result += thiSinh.getDiem(mon[i]);
        return result;
    }

    static List<Double> getDiemToHop(ThiSinh thiSinh, String toHop) {
        List<Double> result = new ArrayList<>();
        String[] mon = khoiToHop.get(toHop);

        if (mon == null) return null;

        if (mon[0].length() > 0 && !thiSinh.getToHop().getID().equals(mon[0])) return null;

        for (int i = 1; i < 4; ++i) result.add(thiSinh.getDiem(mon[i]));
        return result;
    }

    static String formatNum(Object number) {
        return new DecimalFormat(NUM_FORMAT).format(number);
    }

    static void CreateFile(String path) {
        File yourFile = new File(path);
        try {
            ArrayList<String> a = new ArrayList<>(Arrays.asList(path.split("/")));
            a.remove(a.size() - 1);
            Files.createDirectories(Paths.get(a.stream().reduce("", (total, element) -> total + element + "/")));

            if (!yourFile.exists())
                yourFile.createNewFile();
        } catch (IOException e) {
        }
    }

    static String formatMoney(int num) {
        return formatNum(num) + " VND";
    }

    static String leftPad(String x, int a) {
        if (x.length() >= a) return x;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a - x.length(); ++i) buf.append(" ");
        return x + buf.toString();
    }

    static String rightPad(String x, int a) {
        if (x.length() >= a) return x;
        StringBuilder buf = new StringBuilder();
        for (int i = 0; i < a - x.length(); ++i) buf.append(" ");
        return buf + x;
    }

}
