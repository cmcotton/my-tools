/*
 * 版權宣告: FDC all rights reserved.
 */
package sheet;

import io.FileIO;

import java.io.File;
import java.text.SimpleDateFormat;
import java.util.Iterator;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：Parser.java
 * <P>
 * 程式內容說明：
 * <P>
 * 程式修改記錄：
 * <P>
 * XXXX-XX-XX：
 * <P>
 * 
 * @author chtd
 * @version 1.0
 * @since 1.0
 */
public class Parser {

    /**
     * 
     */
    public Parser() {
        super();
    }

    public void saveContent(jxl.Sheet sheet) {
        FileIO io = new FileIO();
        File newFile = getNewFile();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        // String today = sdf.format(new Date());

        for (int ri = 0; ri < sheet.getRows(); ri++) {
            if (ri == 0) {
                io.writefile(newFile, "SN,DN-List,First-Date,Last-Date\n");
                continue;
            }
            
            String startDate = "";
            String domain = "";
            for (int ci = 0; ci < sheet.getColumns(); ci++) {
                
                if (ci == 1) {
                    try {
                        startDate = sheet.getCell(ci, ri).getContents();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                } else if (ci == 2) {
                    domain = sheet.getCell(ci, ri).getContents();
                }

                
            }
            io.writefile(newFile, ri + "," + domain + "," + startDate + "," + startDate + "\n");
        }
    }

    public void saveContent(Sheet sheet) {

        FileIO io = new FileIO();
        File newFile = getNewFile();

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy/MM/dd");
        // String today = sdf.format(new Date());

        int ri = 0;

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext()) {
            Row row = rowIterator.next();

            if (ri == 0) {
                io.writefile(newFile, "SN,DN-List,First-Date,Last-Date\n");
                ri++;
                continue;
            }

            int ci = 0;

            String startDate = "";
            String domain = "";
            // For each row, iterate through each columns
            Iterator<Cell> cellIterator = row.cellIterator();
            while (cellIterator.hasNext()) {

                Cell cell = cellIterator.next();

                if (ci == 1) {
                    try {
                        startDate = cell.getStringCellValue();
                    } catch (Exception e) {
                        startDate = sdf.format(cell.getDateCellValue());
                    }
                } else if (ci == 2) {
                    domain = cell.getStringCellValue();
                }

                ci++;
            }
            io.writefile(newFile, ri + "," + domain + "," + startDate + "," + startDate + "\n");
            ri++;
        }

    }

    protected File getNewFile() {
        File file = new File("./output/" + "FORE_DN.csv");
        if (file.exists()) {
            file.delete();
        }

        return file;
    }

}