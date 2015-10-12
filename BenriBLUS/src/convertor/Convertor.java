/*
 * 版權宣告: FDC all rights reserved.
 */
package convertor;

import java.io.File;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：Convertor.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public interface Convertor {
    
    void generateBlusFile(File file);
    
    void deleteOldFile(File file);

}
