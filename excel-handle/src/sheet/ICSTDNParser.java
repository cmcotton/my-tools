/*
 * 版權宣告: FDC all rights reserved.
 */
package sheet;

import io.FileIO;

import java.io.File;
import java.text.SimpleDateFormat;

/**
 * 程式資訊摘要：
 * <P>
 * 類別名稱　　：AheadDNParser.java
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
public class ICSTDNParser extends Parser {

    @Override
    protected File getNewFile() {
        File file = new File("./output/ICST_DN.csv");
        if (file.exists()) {
            file.delete();
        }
        return file;
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
            String lastDate = "";
            String domain = "";
            for (int ci = 0; ci < sheet.getColumns(); ci++) {

                startDate = sheet.getCell(2, ri).getContents();
                
                lastDate = sheet.getCell(3, ri).getContents();

                domain = sheet.getCell(1, ri).getContents();

            }
            io.writefile(newFile, ri + "," + domain + "," + startDate + "," + lastDate + "\n");
        }
    }

}
