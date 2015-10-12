/*
 * 版權宣告: FDC all rights reserved.
 */
package excel;

import java.io.File;
import java.io.IOException;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.ss.usermodel.WorkbookFactory;

/**
 * 程式資訊摘要：<P>
 * 類別名稱　　：ExcelHandler.java<P>
 * 程式內容說明：<P>
 * 程式修改記錄：<P>
 * XXXX-XX-XX：<P>
 *@author chtd
 *@version 1.0
 *@since 1.0
 */
public class ExcelHandler {

    /**
     * 
     */
    public ExcelHandler() {
        super();
    }

    protected Workbook createWorkbook() {
        // 
        HSSFWorkbook workbook = new HSSFWorkbook();       
    
        return workbook;
    }
    
    protected Workbook openWorkbook(File oriFile) throws InvalidFormatException, IOException {
        Workbook workbook = null;
    
        workbook = WorkbookFactory.create(oriFile);
    
        return workbook;
    }

    public void drawBorder(Workbook workbook, Cell cell) {
        
        CellStyle style = workbook.createCellStyle();
        
        
        style.setWrapText(true);
        
        style.setVerticalAlignment(CellStyle.VERTICAL_CENTER);
        
        style.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        style.setBorderTop(HSSFCellStyle.BORDER_THIN);
        style.setBorderRight(HSSFCellStyle.BORDER_THIN);
        style.setBorderLeft(HSSFCellStyle.BORDER_THIN);
    
        cell.setCellStyle(style);
    }

}