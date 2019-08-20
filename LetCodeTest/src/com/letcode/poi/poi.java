package com.letcode.poi;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import java.io.FileInputStream;
import java.util.*;

public class poi {
    public static void main(String[] args){
        testWord2();
    }
    public static List<Set<String>> testWord2() {
        List<Set<String>> lists = new ArrayList<>();
        try {
            FileInputStream in = new FileInputStream("resources/PMS2.0系统核查规则.docx");// 载入文档
            XWPFDocument xwpfDocument = new XWPFDocument(in);
            List<XWPFTable> tables = xwpfDocument.getTables();

            tables.forEach(table ->{
                // 迭代行，默认从0开始
                HashSet<String> strings = new HashSet<>();
                table.getRows().forEach(tr ->{
                    List<XWPFTableCell> ts = tr.getTableCells();

                    XWPFTableCell td1 = ts.get(1);
                    XWPFTableCell td2 = ts.get(2);
                    XWPFTableCell td3= ts.get(3);
                    XWPFTableCell td4 = ts.get(4);
                    System.out.println(td1.getText().trim() + td2.getText().trim()+td3.getText().trim() + td4.getText().trim());
                });
                //lists.add(strings);
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
        return lists;
    }

}
