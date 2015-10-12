/*
 * 版權宣告: FDC all rights reserved.
 */
package convertor;

import java.io.File;
import java.io.IOException;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;

import excel.ExcelDataReaderXLS;
import excel.ExcelDataReaderXLSX;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ExcelFileConvertor.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class ExcelFileConvertor implements Convertor {

    /* (non-Javadoc)
     * @see convertor.Convertor#generateBlusFile(java.io.File)
     */
    @Override
    public void generateBlusFile(File file) {

            try {
                ExcelDataReaderXLSX reader = new ExcelDataReaderXLSX();
                reader.saveExcelSheetAsCSV(file, 0);
                reader.saveExcelSheetAsCSV(file, 1);
                return;
            } catch (Exception e) {
                System.out.println(e.toString());
            }
            
            ExcelDataReaderXLS oldExcel = new ExcelDataReaderXLS();
            oldExcel.saveExcelSheetAsCSV(file, 0);
            oldExcel.saveExcelSheetAsCSV(file, 1);
            
    }

    /* (non-Javadoc)
     * @see convertor.Convertor#deleteOldFile(java.io.File)
     */
    @Override
    public void deleteOldFile(File file) {
        
        
    }

}
