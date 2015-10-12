/*
 * 版權宣告: FDC all rights reserved.
 */
package sheet;

import java.io.File;




/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：AheadDNParser.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class AheadIPParser extends Parser {
    
    @Override
    protected File getNewFile() {        
        File file =  new File("./output/" + "FORE_IP.csv");
        if(file.exists()) {
            file.delete();
        }
        
        return file;
    }

}
