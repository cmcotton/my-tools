/*
 * 版權宣告: FDC all rights reserved.
 */
package word;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import java.util.LinkedList;
import java.util.List;

import org.apache.poi.xwpf.usermodel.XWPFDocument;
import org.apache.poi.xwpf.usermodel.XWPFTable;
import org.apache.poi.xwpf.usermodel.XWPFTableCell;
import org.apache.poi.xwpf.usermodel.XWPFTableRow;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Word2007Reader.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class Word2007Reader extends WordReader {
    
    @Override
    public void readWordFile(File wordFile, LinkedList<String> emailList, LinkedList<String> nameList,
            LinkedList<String> titleList, LinkedList<String> secList) throws Exception {

        InputStream fis = new FileInputStream(wordFile);

        XWPFDocument document = new XWPFDocument(fis);
        
        List<XWPFTable> tables = document.getTables();
        for (int i = 0; i < tables.size(); i++) {
            XWPFTable table = tables.get(i);

            List<XWPFTableRow> tableRows = table.getRows();

            int nameI = -1;
            int emailI = -1;
            int titleI = -1;
            int secI = -1;

            for (int row = 0; row < tableRows.size(); row++) {

                XWPFTableRow tableRow = tableRows.get(row);

                List<XWPFTableCell> tableCells = tableRow.getTableCells();
                for (int column = 0; column < tableCells.size(); column++) {
                    XWPFTableCell tableCell = tableCells.get(column);

                    if (row == 0) {
                        String content = tableCell.getText().trim();
                        if (content.equals("電子郵件帳號")) {
                            emailI = column;
                            continue;
                        } else if (tableCell.getText().equals("姓名")) {
                            nameI = column;
                            continue;
                        } else if (tableCell.getText().equals("職稱")) {
                            titleI = column;
                            continue;
                        } else if (tableCell.getText().equals("科別")) {
                            secI = column;
                            continue;
                        }
                    } else {
                        if (emailI == column) {
                            emailList.add(tableCell.getText());
                        } else if (nameI == column) {
                            nameList.add(tableCell.getText());
                        } else if (titleI == column) {
                            titleList.add(tableCell.getText());
                        } else if (secI == column) {
                            secList.add(tableCell.getText());
                        }

                    }

                }

                if (row > 0) {
                    if (emailI == -1) {
                        emailList.add("");
                    }

                    if (nameI == -1) {
                        nameList.add("");
                    }

                    if (titleI == -1) {
                        titleList.add("");
                    }

                    if (secI == -1) {
                        secList.add("");
                    }
                }
            }
        }
    }

}
