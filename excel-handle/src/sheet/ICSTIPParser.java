/*
 * 版權宣告: FDC all rights reserved.
 */
package sheet;

import io.FileIO;

import java.io.File;
import java.text.SimpleDateFormat;

public class ICSTIPParser extends Parser {

    @Override
    protected File getNewFile() {
        File file = new File("./output/ICST_IP.csv");
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
                io.writefile(newFile, "SN,IP-List,First-Date,Last-Date\n");
                continue;
            }

            String startDate = "";
            String lastDate = "";
            String domain = "";
            for (int ci = 0; ci < sheet.getColumns(); ci++) {

                startDate = sheet.getCell(3, ri).getContents();
                lastDate = sheet.getCell(4, ri).getContents();
                domain = sheet.getCell(1, ri).getContents();

            }
            io.writefile(newFile, ri + "," + domain + "," + startDate + "," + lastDate + "\n");
        }
    }
}
